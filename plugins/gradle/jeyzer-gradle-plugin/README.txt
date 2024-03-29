
Profiler update Gradle plugin
============================

This Gradle plugin permits to call the Jeyzer profile updater module within the Gradle build phase.
The Jeyzer profile updater is responsible for updating the Jeyzer patterns XML file with the entry changes 
that were generated from the Jeyzer annotations at compilation time.
The resulting patterns file is generated along with its original source file, suffixed by .source to allow easy file diff. 

The plugin takes as parameters :
- The source patterns XML file path. Mandatory parameter.
- The new pattern entries XML file path. By default ${project.build.directory}/jeyzer/new_profile_entries.xml
- The patterns output directory. By default ${project.build.directory}/jeyzer
- The generated patterns file name. By default equal to the source patterns file name


Prerequisites
----------------
Below steps do assumes that the Gradle plugin will be stored within a Maven repository.

- Gradle 6.3+ must be available.
- Maven 3.6.3+ must be available.
- Build the profile updater project (maven build).
- Make the plugin accessible to your Gradle build file.
  To publish the profile updater Gradle plugin in the Maven repository, execute the following command :
  > gradle clean build publish --info

  
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
    id 'org.jeyzer.profile-updater' version '3.0'
    [...]
}


profileUpdateJeyzerTaskSettings {
	sourcePatterns     "$projectDir/src/main/config/profiles/master/demo-features-mx/analysis/patterns.xml"
	newPatternEntries  "$buildDir/../target/generated-sources/jeyzer/new_pattern_entries.xml"
	targetPatternsDir  "$buildDir/generated-sources/jeyzer"
	targetPatternsName "updated-patterns"
}

build.dependsOn(profileUpdateJeyzerTask)
