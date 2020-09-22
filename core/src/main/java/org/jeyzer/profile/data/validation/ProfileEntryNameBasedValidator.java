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

public class ProfileEntryNameBasedValidator extends ProfileEntryValidator {
	
	@Override
	protected void validatePattern(Element node) throws InvalidProfileEntryException{
		if (getPattern(node).isEmpty() && getRegexPattern(node).isEmpty())
			throw new InvalidProfileEntryException("Missing or empty profile entry pattern/regular expression pattern");

		if (!getPattern(node).isEmpty() && !getRegexPattern(node).isEmpty())
			throw new InvalidProfileEntryException("Profile entry has both pattern and regular expression pattern set. Only one is allowed.");
	}
	
	protected String getRegexPattern(Element node) {
		return node.getAttribute(ProfileEntry.JZR_PATTERN_REGEX);
	}
}
