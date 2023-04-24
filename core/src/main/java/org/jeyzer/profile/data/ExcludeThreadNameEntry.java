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

public class ExcludeThreadNameEntry extends ProfileEntry {
	
	public ExcludeThreadNameEntry(Element node, Element nextNode) throws InvalidProfileEntryException {
		super(node, nextNode);
	}
	
	@Override
	public boolean patternEquals(Element nodetoCompare) {
		// contains and not equals as existing profile may have shorten pattern here
		return (nodetoCompare.getAttribute(ProfileEntry.JZR_PATTERN).contains(getPattern()) 
				|| nodetoCompare.getAttribute(ProfileEntry.JZR_PATTERN_REGEX).contains(getPattern()));
	}
	
	@Override
	public boolean nodeEquals(Object obj){
		if (!super.nodeEquals(obj))
			return false;
		
		Element nodetoCompare = (Element) obj;
		return sizeEquals(nodetoCompare);
	}	
	
	public boolean sizeEquals(Element nodetoCompare) {
		String value = nodetoCompare.getAttribute(ProfileEntry.JZR_SIZE);
		int sizeToCompare = (!value.isEmpty())? Integer.parseInt(value) : -1;  
		return getSize() == sizeToCompare;
	}

	public int getSize() {
		String value = node.getAttribute(ProfileEntry.JZR_SIZE);
		// value has already been validated : parsing will work
		return (!value.isEmpty())? Integer.parseInt(value) : -1;
	}

	public void updateSize(Element sourceNode) {
		this.node.setAttribute(
				JZR_SIZE, 
				sourceNode.getAttribute(ProfileEntry.JZR_SIZE)
				);
	}
	
	public void removeSize() {
		this.node.removeAttribute(JZR_SIZE);
	}

}
