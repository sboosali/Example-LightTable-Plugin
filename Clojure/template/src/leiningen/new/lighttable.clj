(ns leiningen.new.lighttable
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]])
  (:require [clojure.string :as string]))

(defn lighttable [plugin]
  (let [render (fn [name]
                 (let [text ((renderer "lighttable") name {:name plugin})
                       output (format "{\"file\": \"%s/%s\"}" plugin (string/replace name #"\{\{\w+\}\}" plugin))]
                   (do
                     (println output)
                     [name text])))]

    (->files {:name plugin}
             (render "plugin.json")
             (render "project.clj")
             (render "{{name}}.behaviors")
             (render "{{name}}.css")
             (render "{{name}}.html")
             (render "{{name}}.keymap")
             (render "src/lt/plugins/{{name}}.cljs")
             (render "README.md")
             (render "LICENSE.md")
             (render ".gitignore")
             (render "run/{{name}}.bat")
             (render "run/{{name}}.sh"))))
