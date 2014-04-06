(ns lt.plugins.example ; our namespace
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
; re-evaling object/object* re-defines the object named ::hello, which is re-inited.
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

(behavior ::print-on-submit
          :triggers #{:click}
          :reaction (fn [this]
                      (let [name (dom/val (dom/$ "input"))] ; get the val of last input
                       (print name))))

; :: is a "namespace-qualified symbol"
; i.e. (= ::hello :lt.plugins.example/hello)
(def hello (object/create ::hello))

; change :desc and eval -> the Command Bar description changes too.
(cmd/command {:command ::say-hello
              :desc "Example: Say Hey" ; what you search for in the Command Bar
              :exec (fn []
                      (tabs/add-or-focus! hello))})


; once you connect this file to the Lighttable UI, you can eval this expression.
; with the tab open, you get the attr; with the tab closed, you get null.
(dom/val (dom/$ "input"))

