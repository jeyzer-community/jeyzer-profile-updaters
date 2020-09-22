package org.jeyzer.profile.data;

/*-
 * ---------------------------LICENSE_START---------------------------
 * Jeyzer Profile Updater
 * --
 * Copyright (C) 2020 Jeyzer SAS
 * --
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 * ----------------------------LICENSE_END----------------------------
 */


import org.jeyzer.profile.error.InvalidProfileEntryException;
import org.w3c.dom.Element;

public class ExecutorThreadNameEntry extends ProfileEntry {
	
	public ExecutorThreadNameEntry(Element node, Element nextNode) throws InvalidProfileEntryException {
		super(node, nextNode);
	}
	
	@Override
	public boolean patternEquals(Element nodetoCompare) {
		// contains and not equals as existing profile may have shorten pattern here
		return (nodetoCompare.getAttribute(ProfileEntry.JZR_PATTERN).contains(getPattern()) 
				|| nodetoCompare.getAttribute(ProfileEntry.JZR_PATTERN_REGEX).contains(getPattern()));
	}
	
	public String getRegexPattern() {
		return node.getAttribute(ProfileEntry.JZR_PATTERN_REGEX); // can be empty
	}
	
}
