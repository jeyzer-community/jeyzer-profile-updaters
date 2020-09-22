package org.jeyzer.profile.pattern;

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


import org.jeyzer.profile.data.ExcludeThreadNameEntry;
import org.jeyzer.profile.data.ProfileEntry;
import org.jeyzer.profile.data.validation.ExcludeThreadNameValidator;
import org.jeyzer.profile.data.validation.ProfileEntryValidator;
import org.jeyzer.profile.error.InvalidProfileEntryException;
import org.jeyzer.profile.error.InvalidPatternsException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ExcludeThreadNameUpdater extends PatternUpdater {

	public ExcludeThreadNameUpdater(Document doc) throws InvalidPatternsException {
		super(doc);
	}

	@Override
	public String getName() {
		return PatternUpdater.JZR_EXCLUDE_THREAD_NAME;
	}

	@Override
	protected ProfileEntry createProfileEntry(Element node, Element nextNode) throws InvalidProfileEntryException {
		getValidator().validate(node);
		return new ExcludeThreadNameEntry(node, nextNode);
	}
	
	@Override
	protected ProfileEntryValidator getValidator() {
		return new ExcludeThreadNameValidator();
	}

	@Override
	protected void updatePatternOptionalFields(ProfileEntry entry, Element newNode) {
		super.updatePatternOptionalFields(entry, newNode);
		
		if (!(entry instanceof ExcludeThreadNameEntry))
			return;

		// update size
		ExcludeThreadNameEntry excludeEntry = (ExcludeThreadNameEntry)entry; 
		
		if (!excludeEntry.sizeEquals(newNode)){
			String value = newNode.getAttribute(ProfileEntry.JZR_SIZE);
			int newSize = (!value.isEmpty())? Integer.parseInt(value) : -1;
			
			if (newSize == -1){
				excludeEntry.removeSize();
			}else{
				excludeEntry.updateSize(newNode);
			}
		}
	}
	
}
