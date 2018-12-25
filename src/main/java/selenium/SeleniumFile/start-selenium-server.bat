@ECHO OFF

set LISTEN_PORT=4004

if "%1" NEQ "" set LISTEN_PORT=%1

set APP_PATH=%~dp0
set PATH=%APP_PATH%;%PATH%
set APP_FULL_PATH=%APP_PATH%selenium-server-standalone.jar
set LOG_PATH=%APP_PATH%logs

if not exist "%LOG_PATH%" md "%LOG_PATH%"

set JAVA_OPTIONS=-server -Xmx512m -Xms128m -Xmn128m

set PORT=-port %LISTEN_PORT%
set BROWSER_TIMEOUT=-browserTimeout 600
set SESSION_TIMEOUT=-sessionTimeout 3600
set JETTY_MAX_THREADS=-jettyMaxThreads 200
set LOG_FILE=-log %APP_PATH%logs\selenium-server-%LISTEN_PORT%.log
rem set LOG_FILE=

@ECHO ON

java %JAVA_OPTIONS% -jar %APP_FULL_PATH% %PORT% %BROWSER_TIMEOUT% %SESSION_TIMEOUT% %JETTY_MAX_THREADS% %LOG_FILE%
