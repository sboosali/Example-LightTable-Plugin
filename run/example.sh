#!/bin/bash
echo Connected
echo "\$ $0 $@"

if [ ! `which lein` ]
then
 echo "no `lein` cmd: install leiningen with your favorite package manager."
 exit 1
fi

if [[ -e "$2" || -e "$1" ]]
then
 echo "plugin '$2' already exists: take it away or pick a new name." 1>&2
 exit 1
fi

cd "$LIGHTTABLE_PLUGINS/Example/Clojure/template"
lein install > /dev/null

cd "$LIGHTTABLE_PLUGINS"
lein new lighttable "$1"
mv "$1" "$2"

find "$2" -type f
