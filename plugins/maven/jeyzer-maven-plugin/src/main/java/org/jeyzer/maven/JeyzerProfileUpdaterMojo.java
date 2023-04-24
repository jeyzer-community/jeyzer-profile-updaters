package org.jeyzer.maven;

/*-
 * ---------------------------LICENSE_START---------------------------
 * Jeyzer Profile Updater Maven Plugin
 * --
 * Copyright (C) 2020 - 2023 Jeyzer
 * --
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 * ----------------------------LICENSE_END----------------------------
 */


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.jeyzer.profile.JeyzerProfileUpdater;
import org.jeyzer.profile.error.InvalidPatternsException;
import org.jeyzer.profile.error.ProfileUpdaterInitException;

import java.io.File;

/**
 * Updates the Jeyzer patterns XML file with the pattern entry changes that were generated from the Jeyzer annotations at compilation time
 */
@Mojo( name = "update-profile", defaultPhase = LifecyclePhase.PACKAGE )
public class JeyzerProfileUpdaterMojo extends AbstractMojo
{
    /**
     * The source patterns XML file path. Mandatory parameter.
     * 
     * @parameter 
     */
    @Parameter( defaultValue = "${basedir}/src/main/config/jeyzer/${project.name}/analysis/patterns.xml", property = "sourcePatterns", required = true )
    private File sourcePatterns;

    /* The generated-sources has been added. Somehow the annotation processor generates it now in that directory, based on the StandardLocation.SOURCE_OUTPUT */
    
    /**
     * The new pattern entries XML file path. By default ${project.build.directory}/generated-sources/jeyzer/new_pattern_entries.xml
     * 
     * @parameter 
     */
    @Parameter( defaultValue = "${project.build.directory}/generated-sources/jeyzer/new_pattern_entries.xml", property = "newPatternEntries", required = false )
    private File newPatternEntries;
        
    /**
     * The patterns output directory. By default ${project.build.directory}/generated-sources/jeyzer
     * 
     * @parameter 
     */
    @Parameter( defaultValue = "${project.build.directory}/generated-sources/jeyzer", property = "targetPatternsDir", required = false )
    private File targetPatternsDir;

    /**
     * The generated patterns file name. By default equal to the source patterns file name
     * 
     * @parameter 
     */
    @Parameter( defaultValue = "${project.name}_profile", property = "targetProfileName", required = false )
    private String targetPatternsName;
    
    public void execute() throws MojoExecutionException
    {
    	JeyzerProfileUpdater updater = new JeyzerProfileUpdater();
    	
    	// security check
    	if (targetPatternsDir.exists() && targetPatternsDir.isFile())
    		throw new MojoExecutionException ("Profile target directory is a file. Please check the profile updater parameters. Given directory path is : " + targetPatternsDir);

    	// make sure that the destination directory exists
    	if (!targetPatternsDir.exists()) {
    		boolean result = targetPatternsDir.mkdirs();
    		if (!result)
        		throw new MojoExecutionException ("Profile target directory creation failed. Given directory path is : " + targetPatternsDir);
    	}
    	
    	File patternsTarget = new File(targetPatternsDir, targetPatternsName + ".xml");
    	
    	getLog().info("Generating analysis patterns  : " + patternsTarget.getPath());
    	getLog().info("  from source patterns        : " + sourcePatterns.getPath());
    	getLog().info("  with pattern entries from   : " + newPatternEntries.getPath());
    	
    	try {
			updater.execute(sourcePatterns, patternsTarget, newPatternEntries);
		} catch (InvalidPatternsException ex) {
			throw new MojoExecutionException(ex.toString());
		} catch (ProfileUpdaterInitException ex) {
			throw new MojoExecutionException(ex.toString());
		} catch (Exception ex) {
			throw new MojoExecutionException(ex.toString());
		}
    }
}
