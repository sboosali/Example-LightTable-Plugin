echo Connected
echo ">" %0 %*
@echo off

:: i have no idea if this works. send me a pull request.

where lein
IF NOT ERRORLEVEL 0 (
 echo "no `lein` cmd: install leiningen with your favorite package manager."
 exit 1
)

IF EXIST "%2" (
 echo "plugin '$2' already exists: take it away or pick a new name."
 exit 1
)

IF EXIST "%1" (
 echo "plugin '$2' already exists: take it away or pick a new name."
 exit 1
)

cd %LIGHTTABLE_PLUGINS%\Example\Clojure\template
lein install

cd %LIGHTTABLE_PLUGINS%
lein new lighttable "%1"
rename "%1" "%2"
