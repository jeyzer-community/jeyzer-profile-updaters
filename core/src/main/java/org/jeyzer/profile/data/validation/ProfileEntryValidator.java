package org.jeyzer.profile.data.validation;

/*-
 * ---------------------------LICENSE_START---------------------------
 * Jeyzer Profile Updater
 * --
 * Copyright (C) 2020 - 2023 Jeyzer
 * --
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 * ----------------------------LICENSE_END----------------------------
 */


import org.jeyzer.profile.data.ProfileEntry;
import org.jeyzer.profile.error.InvalidProfileEntryException;
import org.w3c.dom.Element;

public class ProfileEntryValidator {

	protected static final int MIN_PRIORITY = 101;
	protected static final int MAX_PRIORITY = 1000;	
	
	public void validate(Element node) throws InvalidProfileEntryException {
		if (getName(node).isEmpty())
			throw new InvalidProfileEntryException("Missing or empty profile entry name");
		
		validatePattern(node);
		
		if (node.getAttribute(ProfileEntry.JZR_PRIORITY).isEmpty())
			return;
		
		if (getPriority(node) < MIN_PRIORITY || getPriority(node) > MAX_PRIORITY)
	    	throw new InvalidProfileEntryException(
    			"Priority value \"" + node.getAttribute(ProfileEntry.JZR_PRIORITY) + "\" is invalid. It must be set between " + MIN_PRIORITY + " and " + MAX_PRIORITY);
	}
	
	// can be overriden
	protected void validatePattern(Element node) throws InvalidProfileEntryException{
		if (getPattern(node).isEmpty())
			throw new InvalidProfileEntryException("Missing or empty profile entry pattern");
	}
	
	protected String getName(Element node) {
		return node.getAttribute(ProfileEntry.JZR_NAME);
	}

	protected String getPattern(Element node) {
		return node.getAttribute(ProfileEntry.JZR_PATTERN);
	}

	protected int getPriority(Element node) {
		try{
			return Integer.parseInt(node.getAttribute(ProfileEntry.JZR_PRIORITY));
		}catch(NumberFormatException ex){
			return -1;
		}
	}
	
}
