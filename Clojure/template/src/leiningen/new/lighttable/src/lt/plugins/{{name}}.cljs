; Welcome To LightTable!
; read the comments and learn how to make your own plugin.
; they talk about ClojureScript, the BOT, LightTable's UI,
; and how to write a plugin.

; in this file,
; save to build the plugin
; type pmeta-a + pmeta-enter to eval everything
; when prompted, connect to the "LightTable UI"

; in the sidebar,
; run "refresh plugin list" (and see your plugin in "show plugin manager")
; run "reload keymaps" and "reload beaviors"
; run "toggle console"
; run "hello" in the sidebar, or type the "cmd-h"

; if a tab came up, now you're all set up.
; edit/eval away!

(ns lt.plugins.{{name}} ; our namespace
  (:require [lt.object :as object]
            [lt.objs.tabs :as tabs]
            [lt.objs.command :as cmd]
            [lt.util.dom :as dom]
            [lt.objs.files :as files]
            [lt.objs.proc :as proc]
            [lt.objs.popup :as popup]
            [clojure.string :as string])
  ; see https://github.com/LightTable/LightTable/tree/master/src/lt/objs
  ; for more LightTable builtins
  (:require-macros [lt.macros :refer [defui behavior]]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; UI Element, Object, Tag, Trigger, Behavior

; an event handler factory
; we must re-raise the DOM event as a LightTable trigger, for BOT (?)
(defn re-raise [this trigger]
  (fn [event]
   (dom/prevent event)
   (object/raise this trigger)))

(defui ok-button [this]
  [:input {:type "submit" :value "Say It"}] ; first, the element
  :click (re-raise this :click)) ; then, the events and handlers

; <div class="Class" id="Id">_</h1>
; styled by {{name}}.css
(defui hello-panel [this x]
  [:div#Id.Class {:height 200 :width 200}
   [:input {:type "text" :placeholder "Hello LightTable!"}]
   (ok-button this)]) ; call one defui macro from another

; click anywhere in this expression to eval it.
; change "world!" and eval -> the open tab changes too.
; re-evaling object/object* re-defines the object named ::hello, which is re-inited.
(object/object* ::hello
                :tags [:hello]
                :name "hello"
                :init (fn [this]
                        (hello-panel this "world!")))

; closing a tab only raises the :close trigger
; we tag the ::hello object with the :hello tag in (object/object* ...)
; we trigger the ::destroy-on-close behvaior with the :close trigger in (behavior ...)
; we tag the ::destroy-on-close behavior with the :hello tag in {{name}}.behaviors
(behavior ::destroy-on-close
          :triggers #{:close} ; a Set
          :reaction (fn [this]
                      (object/raise this :destroy)))

(behavior ::print-on-submit
          :triggers #{:click}
          :reaction (fn [this]
                      (let [name (dom/val (dom/$ "input"))] ; get the val of last input
                       (when-not (string/blank? name)
                         (println "{{name}}" name)))))

; "::" makes a "namespace-qualified symbol"
; i.e. (= ::hello :lt.plugins.{{name}}/hello)
(def hello (object/create ::hello))

; change :desc and eval -> the Command Bar description changes too.
(cmd/command {:command ::hi
              :desc "{{name}}: Hello" ; what you search for in the Command Bar
              :exec (fn []
                      (tabs/add-or-focus! hello))})


; once you connect this file to the Lighttable UI, you can eval this expression.
; with the tab open, you can get it; with the tab closed, you get null.
(dom/val (dom/$ "input"))

;when ready, connect your plugin to LightTable's repo by:
; changing "source" in "plugin.json"
; running the "Plugins: Submit a plugin" command


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
