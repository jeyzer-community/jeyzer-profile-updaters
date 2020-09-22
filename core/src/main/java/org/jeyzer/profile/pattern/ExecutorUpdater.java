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


import org.jeyzer.profile.data.ExecutorEntry;
import org.jeyzer.profile.data.ProfileEntry;
import org.jeyzer.profile.data.validation.ProfileEntryValidator;
import org.jeyzer.profile.error.InvalidProfileEntryException;
import org.jeyzer.profile.error.InvalidPatternsException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ExecutorUpdater extends PatternUpdater {

	public ExecutorUpdater(Document doc) throws InvalidPatternsException {
		super(doc);
	}

	@Override
	public String getName() {
		return PatternUpdater.JZR_EXECUTOR;
	}

	@Override
	protected ProfileEntry createProfileEntry(Element node, Element nextNode) throws InvalidProfileEntryException {
		getValidator().validate(node);
		return new ExecutorEntry(node, nextNode);
	}
	
	@Override
	protected ProfileEntryValidator getValidator() {
		return new ProfileEntryValidator();
	}

}
