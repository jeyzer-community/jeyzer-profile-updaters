@echo off

rem -------------------------------------
rem 
rem Jeyzer Profile Updater
rem 
rem -------------------------------------

if exist "%JAVA_HOME%\bin\java.exe" goto okJava

echo JAVA_HOME must point to JDK/JRE 1.7+ installation
rem set JAVA_HOME=D:\dev\jdk1.7.0_10
goto end

:okJava

rem yellow font over blue back ground
color 1E


rem -----------------------------------------------------------
rem Jeyzer Profile Updater parameters
rem -----------------------------------------------------------

SET "JEYZER_PROFILE_SOURCE_PATH=%~dp0\\test\\src\\demo-features_profile.xml"

SET "JEYZER_PROFILE_TARGET_PATH=%~dp0\\test\\target\\demo-features_profile.xml"

SET "JEYZER_NEW_PROFILE_ENTRIES_PATH=%~dp0\\test\\patterns\\new_profile_entries.xml"


rem -----------------------------------------------------------
rem Internals - do not edit
rem -----------------------------------------------------------

rem reset JAVA_OPTS
set JAVA_OPTS=

SET "JEYZER_PROFILE_UPDATER_PARAMS=-Djeyzer.profile.source=%JEYZER_PROFILE_SOURCE_PATH% -Djeyzer.profile.target=%JEYZER_PROFILE_TARGET_PATH% -Djeyzer.new.profile.entries=%JEYZER_NEW_PROFILE_ENTRIES_PATH%"

rem Java debug options
rem set "JAVA_OPTS=%JAVA_OPTS% -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5000"

rem JVM options
rem set "JAVA_OPTS=%JAVA_OPTS% -Xmn15m -Xms20m -Xmx20m"

rem Jeyzer profile updater library
set "CLASSPATH=%CLASSPATH%;lib\profile-updater.jar;lib\guava-${com.google.guava.guava.version}.jar"

call "%JAVA_HOME%\bin\java.exe" %JEYZER_PROFILE_UPDATER_PARAMS% %JAVA_OPTS% -cp %CLASSPATH% org.jeyzer.profile.JeyzerProfileUpdater
goto end

:end