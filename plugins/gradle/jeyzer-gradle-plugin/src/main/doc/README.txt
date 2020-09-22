
Profiler update Gradle plugin
============================

This Gradle plugin permits to call the Jeyzer profile updater module within the Gradle build phase.
The Jeyzer profile updater is responsible for updating the Jeyzer profile XML file with the entry changes 
that were generated from the Jeyzer annotations at compilation time.
The resulting profile file is generated along with its original source file, suffixed by .source to allow easy file diff. 

The plugin takes as parameters :
- The source profile XML file path. Mandatory parameter.
- The new profile entries XML file path. By default ${project.build.directory}/jeyzer/new_profile_entries.xml
- The profile output directory. By default ${project.build.directory}/jeyzer
- The generated profile file name. By default equal to the source profile file name


Prerequisites
----------------
Below steps do assumes that the Gradle plugin will be stored within a Maven repository.

- Maven must be available.
- Make the plugin accessible to your Gradle build file.
For this, you need to publish this Gradle plugin as well as the profile updater library into your local Maven repository by executing the following command :
  >mvn install:install-file -Dfile=.\lib\profile-updater-2.0.jar -DpomFile=pom-profile-updater.xml
  >mvn install:install-file -Dfile=.\lib\jeyzer-gradle-plugin.jar -DpomFile=pom.xml

  
Usage
----------------
Usage example is available in the jeyzer-demo project Gradle build file.

In the settings.gradle, add :

pluginManagement {
    repositories {
        maven {
            url = uri('C:/Dev/programs/Build/maven-repository')
        }
    }
}

To call the plugin from any Gradle build file, add into it the following entries :

plugins {
    id 'org.jeyzer.profile-updater' version '2.0'
    [...]
}

profileUpdateJeyzerTaskSettings {
	sourcePatterns     "$projectDir/src/main/config/profiles/master/demo-features-mx/analysis/patterns.xml"
	newPatternEntries  "$buildDir/../target/generated-sources/jeyzer/new_pattern_entries.xml"
	targetPatternsDir  "$buildDir/generated-sources/jeyzer"
	targetPatternsName "updated-patterns"
}

build.dependsOn(profileUpdateJeyzerTask)