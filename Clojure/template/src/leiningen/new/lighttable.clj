(ns leiningen.new.lighttable
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]))

(defn lighttable [plugin]
  (let [render (fn [name]
                 (let [text ((renderer "lighttable") name {:name plugin})] ; this dict replaces {{name}} in contents
                   [name text]))]

    (->files {:name plugin} ; this dict replaces {{name}} in paths
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
