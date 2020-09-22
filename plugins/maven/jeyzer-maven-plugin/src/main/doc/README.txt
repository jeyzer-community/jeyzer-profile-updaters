
Profiler update Maven plugin
============================

This Maven plugin permits to call the Jeyzer profile updater module during the Maven package phase.
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
- Make the plugin accessible to your Maven build file.
For this, you need to publish this maven plugin as well as the profile updater library into your local Maven repository by executing the following command :
>mvn install:install-file -Dfile=.\lib\profile-updater-2.0.jar -DpomFile=pom-profile-updater.xml
>mvn install:install-file -Dfile=.\lib\jeyzer-maven-plugin.jar -DpomFile=pom.xml


Usage
----------------
Usage example is available in the jeyzer-demo project maven build file.

To call the plugin from any maven build file, add into it the following entry :

			<plugin>
				<groupId>org.jeyzer.maven</groupId>
				<artifactId>jeyzer-maven-plugin</artifactId>
				<version>2.0</version>
				<executions>
					<execution>
						<phase>package</phase>
						<configuration>
							<sourceProfile>${basedir}/src/main/config/profiles/demo-features-mx/demo-features-mx_profile.xml</sourceProfile>
							<!--newProfileEntries>${project.build.directory}/jeyzer/new_profile_entries.xml</newProfileEntries-->
							<!--targetProfileDir>${project.build.directory}/jeyzer</targetProfileDir-->
							<!--targetProfileName>demo-features-mx_profile.xml</targetProfileName-->
						</configuration>
						<goals>
							<goal>update-profile</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
