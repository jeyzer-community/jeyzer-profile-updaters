package org.jeyzer.gradle;

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

public class JeyzerProfileUpdaterPluginExtension {

    /**
     * Inputs
     */
    private String sourcePatterns;

    private String newPatternEntries;
    
    /**
     * Outputs
     */
    private String targetPatternsDir;

    private String targetPatternsName;
    
	public String getSourcePatterns() {
		return sourcePatterns;
	}

	public void setSourcePatterns(String sourcePatterns) {
		this.sourcePatterns = sourcePatterns;
	}

	public String getNewPatternEntries() {
		return newPatternEntries;
	}

	public void setNewPatternEntries(String newPatternEntries) {
		this.newPatternEntries = newPatternEntries;
	}

	public String getTargetPatternsDir() {
		return targetPatternsDir;
	}

	public void setTargetPatternsDir(String targetPatternsDir) {
		this.targetPatternsDir = targetPatternsDir;
	}

	public String getTargetPatternsName() {
		return targetPatternsName;
	}

	public void setTargetPatternsName(String targetPatternsName) {
		this.targetPatternsName = targetPatternsName;
	}	
}