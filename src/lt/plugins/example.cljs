(ns lt.plugins.example
  (:require [lt.object :as object]
            [lt.objs.command :as cmd]

            [lt.objs.files :as files]
            [lt.objs.proc :as proc]
            [lt.objs.platform :as platform]

            [lt.util.dom :as dom]
            [lt.objs.tabs :as tabs]
            [lt.objs.popup :as popup]
            [lt.objs.notifos :as notifos]
            [lt.objs.sidebar.command :as sidebar]
            [lt.objs.console :as console]

            [clojure.string :as string])
  (:require-macros [lt.macros :refer [defui behavior]]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; Util

(defn in? [x xs]
  (> (.indexOf xs x) -1))

(def bad #"[^A-Za-z0-9._-]+")

(defn good? [char]
  (in? char "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789._-"))

(defn ->file [name]
  (string/join "" (filter good? name)))

(defn str->file [phrase]
  (string/join "-"
               (filter #(not (string/blank? %))
                       (map string/lower-case
                            (string/split phrase bad)))))
(defn str->dir [phrase]
  (string/join "_"
               (filter #(not (string/blank? %))
                       (map string/capitalize
                            (string/split phrase bad)))))

(str->file " new \\ Plugin / ")
(str->dir  " new \\ Plugin / ")


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; UI (Sidebar Input)

; user types "new plugin" into sidebar and picks ::make-plugin command
; -> replace sidebar html with option html
; -> user types text into option and hits "enter"
; -> raise :select trigger
; -> trigger ::exec-active! behavior
; -> call (sidebar/exec-active! args...)
; -> find active command
; -> call (cmd/exec! ::make-plugin args...)
; -> call (new-plugin! args...)

(def name-input (sidebar/options-input {:placeholder "name your plugin"}))

(behavior ::exec-active!
          :triggers #{:select}
          :reaction (fn [this name]
                      (sidebar/exec-active! name)))

(object/add-behavior! name-input ::exec-active!)

(cmd/command {:command ::make-plugin
              :desc "Plugins: new plugin"
              :options name-input
              :exec (fn [name]
                      (when (not (empty? name))
                        (new-plugin! name)))})


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; I/O

; the directory where LightTable keeps plugins
(def PLUGINS (files/join files/data-path "plugins"))

; the script
(def runner (files/join PLUGINS
                        "Example"
                        (if (platform/win?)
                          "run/example.bat"
                          "run/example.sh")))
; $ cd :cwd
; $ export :env
; $ :command :args
(defn new-plugin! [name]
  (let [plugin-name (str->file name)
        Plugin_Dir  (str->dir name)
        cljs (files/join PLUGINS Plugin_Dir "src" "lt" "plugins" (str plugin-name ".cljs"))]
    ;^ e.g. .../New_Plugin/src/lt/plugins/new-plugin.cljs

    (proc/exec {:command runner
                :args [plugin-name Plugin_Dir]
                :cwd PLUGINS
                :env {"LIGHTTABLE_PLUGINS" PLUGINS
                      "LEIN_BREAK_CONVENTION" true}
                :obj (object/create ::new-plugin {:cljs cljs})})))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; I/O

(object/object* ::new-plugin
                :triggers []
                :behaviors [::on-out ::on-error ::on-exit ::on-success ::on-failure]
                :cljs "" ; the clojurescript file in the new plugin
                :init (fn [this data] ; kwargs for state
                        (object/merge! this data)))

(behavior ::on-out
          :triggers #{:proc.out}
          :reaction (fn [this data]
                      (let [out (.toString data)]
                        (print (str "[out]\n" out))

                        (object/update! this [:buffer] str out)
                        (when (in? "Connected" out)
                          (object/merge! this {:connected true})))))

(behavior ::on-error
          :triggers #{:proc.error}
          :reaction (fn [this data]
                      (let [err (.toString data)]
                        (print (str "[err]\n" err)))))

(behavior ::on-exit
          :triggers #{:proc.exit}
          :reaction (fn [this data]
                      (let [exit-code (.toString data)]
                        (print (str "[exit]\n" exit-code))

                        (if (= "0" exit-code)
                          (object/raise this :succeeded)
                          (object/raise this :failed)))))

(behavior ::on-success
          :triggers #{:succeeded}
          :reaction (fn [this] ; open the new files we made
                      (let [cljs (get @this :cljs)]
                        (print "open" cljs)
                        (cmd/exec! :open-path cljs)
                        (cmd/exec! :tabs.move-new-tabset))))

(behavior ::on-failure
          :triggers #{:failed}
          :reaction (fn [this] ; focus on the console
                      (cmd/exec! :console-tab)
                      (cmd/exec! :toggle-console)
                      (popup/popup! {:header "couldn't make the plugin, check console log."})))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
