echo Connected
echo ">" %0 %*

cd %LIGHTTABLE_PLUGINS%\Example\Clojure\template
lein install

:: cd %LIGHTTABLE_PLUGINS%
:: lein new lighttable "%1"
:: rename "%1" "%2"
