package org.jeyzer.profile.error;

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




public class InvalidProfileEntryException extends Exception {

	private static final long serialVersionUID = -2010081515417421164L;	
	
	public InvalidProfileEntryException(String message) {
		super(message);
	}
	
}
