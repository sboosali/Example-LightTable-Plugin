; Welcome To LightTable!
; read the comments and learn how to make your own plugin.
; they talk about ClojureScript, the BOT, and LightTable's UI.

; first, you must:
; save this file to build the plugin
; type pmeta-a + pmeta-enter to eval everything
; when prompted, connect to the LightTable UI
; then in the sidebar:
; run "refresh plugin list" (and see your plugin in "show plugin manager")
; run "reload keymaps"
; run "reload beaviors"
; run "toggle console" to bring it up
; run "hello" in the sidebar, or type "cmd-h"

; if a tab came up, you're all set up.
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

; <div class="Class" id="Id">...</div>
; styled by {{name}}.css
(defui hello-panel [this x]
  [:div#Id.Class {:height 200 :width 200}
   [:input#hello {:type "text" :placeholder "Hello LightTable!"}]
   (ok-button this)]) ; one defui macro can call another defui macro

; <input>...</input>
; $(this).bind(re-raise)
(defui ok-button [this]
  [:input {:type "submit" :value "Log To Console"}] ; first, the element
  :click (re-raise this :click)) ; then, the events and handlers

; click anywhere in this expression to eval it.
; change "world!" and eval -> the open tab changes too.
; re-evaling object/object* re-defines the object named ::hello, which is re-inited.
(object/object* ::hello
                :tags [:hello]
                :name "hello"
                :init (fn [this]
                        (hello-panel this "world!")))

; closing a tab only raises the :close trigger, thus:
; we tag the ::hello object with the :hello tag in (object/object* ... :tags ...)
; we bind the ::destroy-on-close behvaior to the :close trigger in (behavior ... :triggers ...)
; we tag the ::destroy-on-close behavior with the :hello tag in {{name}}.behaviors
(behavior ::destroy-on-close
          :triggers #{:close} ; a Set
          :reaction (fn [this]
                      (object/raise this :destroy)))

(behavior ::print-on-submit
          :triggers #{:click}
          :reaction (fn [this]
                      (let [name (dom/val (dom/$ "#hello"))] ; jquery-style
                       (when-not (string/blank? name)
                         (println "{{name}} says" name)))))

; "::" makes a "namespace-qualified symbol"
; "::hello" in this namespace is ":lt.plugins.{{name}}/hello" in other namespaces
(def hello (object/create ::hello))

; change :desc and eval -> the Command Bar description changes too.
(cmd/command {:command ::hi
              :desc "{{name}}: Hello" ; what you search for in the Command Bar
              :exec (fn []
                      (tabs/add-or-focus! hello))})


; once you connect this file to the Lighttable UI, you can eval this expression.
(dom/val (dom/$ "input"))
; with the tab open, you can get the text; with the tab closed, you get nil.
; an open search bar will come before our open tab, and you'll get that text.
; the LightTable UI is one big DOM Window, be careful.

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; connect your local repo to a remote repo with:
;    git init
;    git add README.md
;    git commit -m "first commit"
;    git remote add origin git@github.com:$USER/{name}.git
;    git push -u origin master

; when ready, you can push your plugin to LightTable's repo (and to everyone's LightTable) by:
;    changing "source" in "plugin.json"
;    running the "Plugins: Submit a plugin" command
