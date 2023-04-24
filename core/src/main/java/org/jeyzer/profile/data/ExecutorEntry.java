package org.jeyzer.profile.data;

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


import org.jeyzer.profile.error.InvalidProfileEntryException;
import org.w3c.dom.Element;

public class ExecutorEntry extends ProfileEntry {

	public ExecutorEntry(Element node, Element nextNode) throws InvalidProfileEntryException {
		super(node, nextNode);
	}

}
