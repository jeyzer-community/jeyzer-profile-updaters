package org.jeyzer.profile.data.validation;

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


import org.jeyzer.profile.data.ProfileEntry;
import org.jeyzer.profile.error.InvalidProfileEntryException;
import org.w3c.dom.Element;

public class ExcludeThreadNameValidator extends ProfileEntryNameBasedValidator {
	
	protected static final int MIN_SIZE = 1;
	
	@Override
	public void validate(Element node) throws InvalidProfileEntryException {
		super.validate(node);
		
		if (node.getAttribute(ProfileEntry.JZR_SIZE).isEmpty())
			return;
		
		if (getSize(node) < MIN_SIZE)
	    	throw new InvalidProfileEntryException(
    			"Size value \"" + node.getAttribute(ProfileEntry.JZR_SIZE) + "\" is invalid. It must be greater than " + MIN_SIZE);
	}
	
	protected int getSize(Element node) {
		try{
			return Integer.parseInt(node.getAttribute(ProfileEntry.JZR_SIZE));
		}catch(NumberFormatException ex){
			return -1;
		}
	}
}
