
Profiler update Maven plugin
============================

This Maven plugin permits to call the Jeyzer profile updater module during the Maven package phase.
The Jeyzer profile updater is responsible for updating the Jeyzer patterns XML file with the pattern entry changes 
that were generated from the Jeyzer annotations at compilation time.
The resulting patterns file is generated along with its original source file, suffixed by .source to allow easy file diff. 

The plugin takes as parameters :
- The source patterns XML file path. Mandatory parameter.
- The new pattern entries XML file path. By default ${project.build.directory}/generated-sources/jeyzer/new_pattern_entries.xml
- The patterns output directory. By default ${project.build.directory}/generated-sources/jeyzer
- The generated patterns file name. By default equal to the source patterns file name


Prerequisites
----------------
- Build the profile updater project.
- Make the plugin accessible to your Maven build file.
For this, you need to publish this maven plugin into your local Maven repository by executing the following command :
>mvn install:install-file -Dfile=<project root path>\profile-updaters\plugins\maven\jeyzer-maven-plugin\target\jeyzer-maven-plugin.jar -DpomFile=<project root path>\profile-updaters\plugins\maven\jeyzer-maven-plugin\pom.xml


Usage
----------------
Usage example is available in the jeyzer-demo project maven build file.

To call the plugin from any maven build file, add into it the following entry :

			<plugin>
				<groupId>org.jeyzer.build</groupId>
				<artifactId>jeyzer-maven-plugin</artifactId>
				<version>2.0</version>
				<executions>
					<execution>
						<phase>package</phase>
						<configuration>
							<sourcePatterns>${basedir}/src/main/config/profiles/demo-features-mx/analysis/patterns.xml</sourcePatterns>
							<!--newPatternEntries>${project.build.directory}/generated-sources/jeyzer/new_pattern_entries.xml</newProfileEntries-->
							<!--targetPatternsDir>${project.build.directory}/generated-sources/jeyzer</targetPatternsDir-->
							<targetPatternsName>patterns</targetPatternsName>
						</configuration>
						<goals>
							<goal>update-profile</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
