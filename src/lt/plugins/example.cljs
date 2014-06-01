(ns lt.plugins.example ; our namespace
  (:require [lt.object :as object]
            [lt.objs.command :as cmd]

            [lt.objs.files :as files]
            [lt.objs.proc :as proc]
            [lt.objs.platform :as platform]

            [lt.util.dom :as dom]
            [lt.objs.tabs :as tabs]
            [lt.objs.popup :as popup]
            [lt.objs.notifos :as notifos]

            [clojure.string :as string])
  ; see https://github.com/LightTable/LightTable/tree/master/src/lt/objs
  ; for more LightTable builtins
  (:require-macros [lt.macros :refer [defui behavior]]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; UI Element, Object, Tag, Trigger, Behavior

; an event handler factory
; (i think) we must re-raise the DOM event as a LT trigger
(defn re-raise [this trigger]
  (fn [event]
   (dom/prevent event)
   (object/raise this trigger)))

(defui ok-button [this]
  [:input {:type "submit" :value "OK"}] ; first, the element
  :click (re-raise this :click)) ; then, the events and handlers

; <div class="Class" id="Id">x</h1>
; styled by example.css
(defui hello-panel [this x]
  [:div#Id.Class {:height 200 :width 200}
   [:input {:type "text" :placeholder "New Plugin"}]
   (ok-button this)]) ; call one defui macro from another

; click anywhere in this expression to eval it.
; change "world!" and eval -> the open tab changes too.
; re-evaling object/object* re-defines the object named ::hello, which is re-inited (?)
(object/object* ::hello
                :tags [:hello]
                :name "hello"
                :init (fn [this]
                        (hello-panel this "world!")))

; closing a tab only raises the :close trigger
; we tag the ::hello object with the :hello tag in (object/object* ...)
; we trigger the ::destroy-on-close behvaior with the :close trigger in (behavior ...)
; we tag the ::destroy-on-close behavior with the :hello tag in example.behaviors
(behavior ::destroy-on-close
          :triggers #{:close} ; a Set
          :reaction (fn [this]
                      (object/raise this :destroy)))

(defn input [] ; get the val of the last input elem
  (dom/val (dom/$ "input")))

(behavior ::print-on-submit
          :triggers #{:click}
          :reaction (fn [this]
                      (let [name (input)]
                       (when-not (string/blank? name)
                         (run name)))))


; :: is a "namespace-qualified symbol"
; i.e. (= ::hello :lt.plugins.example/hello)
(def hello (object/create ::hello))

; change :desc and eval -> the Command Bar description changes too.
(cmd/command {:command ::say-hello
              :desc "Plugins: new plugin" ; what you search for in the Command Bar
              :exec (fn []
                      (tabs/add-or-focus! hello))})

; once you connect this file to the Lighttable UI, you can eval this expression.
; with the tab open, you can get it; with the tab closed, you get null.
; close the hello tab and open a search bar -> now it's new text -> LightTable is one big DOM window
(dom/val (dom/$ "input"))

;when ready, connect your plugin to LightTable's repo by:
; changing "source" in "plugin.json"
; running the "Plugins: Submit a plugin" command


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; I/O


(def runner (files/join plugins-dir
                        "Example"
                        (if (platform/win?)
                             "run/example.bat"
                             "run/example.sh")))

(def plugins-dir (files/join files/data-path "plugins"))


(defn str->dir [phrase]
  (string/join "_"
               (filter #(not (string/blank? %))
                       (map string/capitalize
                            (string/split phrase #"\s+")))))
(defn str->file [phrase]
  (string/join "-"
               (filter #(not (string/blank? %))
                       (map string/lower-case
                            (string/split phrase #"\s+")))))
(str->dir " new  Plugin ")
(str->file " new  Plugin ")

(defn run [name]
 (proc/exec {:command runner
             :args [(str->file name)]
             :cwd plugins-dir
             :env {"LIGHTTABLE_PLUGINS" plugins-dir
                   "LEIN_BREAK_CONVENTION" true}
             :obj (object/create ::connecting-notifier)}))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; Command

(object/object* ::connecting-notifier
                :triggers []
                :behaviors [::on-out ::on-error ::on-exit]
                :init (fn [this] nil))

(behavior ::on-out
          :triggers #{:proc.out}
          :reaction (fn [this data]
                      (let [out (.toString data)]
                        (print (str "[out]\n" out))
                        (object/update! this [:buffer] str out)
                        (when (> (.indexOf out "Connected") -1)
                          (object/merge! this {:connected true})))))

(behavior ::on-error
          :triggers #{:proc.error}
          :reaction (fn [this data]
                      (let [out (.toString data)]
                        (print (str "[error]\n" out)))))

(behavior ::on-exit
          :triggers #{:proc.exit}
          :reaction (fn [this data]
                      (do
                        (notifos/done-working)
                        (print (str "[exit]\n" data)))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
