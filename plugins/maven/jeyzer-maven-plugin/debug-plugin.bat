@echo off

set ANNOTATION_PROCESSORS_DIR=C:\Dev\src\jeyzer\jeyzer-annotation-processors
set ANNOTATIONS_DIR=C:\Dev\src\jeyzer\jeyzer-annotations

set PROFILE_UPDATER_CORE_DIR=C:\Dev\src\jeyzer\jeyzer-profile-updater\core
set PROFILE_UPDATER_MAVEN_PLUGIN_DIR=C:\Dev\src\jeyzer\jeyzer-profile-updater\plugins\maven\jeyzer-maven-plugin
set PROFILE_UPDATER_VERSION=3.0

set SAMPLES_DIR=C:\Dev\src\jeyzer\samples

rem reset MAVEN_OPTS
set MAVEN_OPTS=

rem Compile annotations - Optional
rem cd %ANNOTATIONS_DIR%
rem call mvn install
rem call mvn install:install-file -Dfile=%ANNOTATION_PROCESSORS_DIR%\target\jeyzer-annotation-processors-3.0.jar -DpomFile=%ANNOTATION_PROCESSORS_DIR%\pom.xml

rem Move to annotations project
rem cd %ANNOTATION_PROCESSORS_DIR%

rem Compile annotation processor - Optional
rem call mvn clean install

rem Push it on mvn repository
rem call mvn install:install-file -Dfile=%ANNOTATION_PROCESSORS_DIR%\target\jeyzer-annotation-processors-3.0.jar -DpomFile=%ANNOTATION_PROCESSORS_DIR%\pom.xml


rem Move to profile updater core project
cd %PROFILE_UPDATER_CORE_DIR%

rem Compile profile updater core
call mvn clean install

rem Push it on mvn repository
call mvn install:install-file -Dfile=%PROFILE_UPDATER_CORE_DIR%\target\profile-updater-%PROFILE_UPDATER_VERSION%.jar -DpomFile=%PROFILE_UPDATER_CORE_DIR%\pom.xml


rem Move to profile updater maven plugin project
cd %PROFILE_UPDATER_MAVEN_PLUGIN_DIR%

rem Compile profile updater core
call mvn clean install

rem Move to samples project
cd %SAMPLES_DIR%

rem Clean the samples project
call mvn clean

rem Set Maven in debug mode
set MAVEN_OPTS=-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005

rem Compile the samples to debug the annotation module
call mvn package

rem Reset path
cd %PROFILE_UPDATER_MAVEN_PLUGIN_DIR%
set MAVEN_OPTS=

