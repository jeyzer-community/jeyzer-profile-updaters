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



import org.w3c.dom.Element;

public abstract class ProfileEntry {
	
	public static final String JZR_PATTERN = "pattern";
	public static final String JZR_PATTERN_REGEX = "pattern_regex";
	public static final String JZR_NAME = "name";
	public static final String JZR_SIZE = "size";
	public static final String JZR_PRIORITY = "priority";
	
	protected Element node;
	protected Element nextNode;

	public ProfileEntry(Element node, Element nextNode) {
		this.node = node;
		this.nextNode = nextNode;
	}
	
	public String getName() {
		return node.getAttribute(ProfileEntry.JZR_NAME);
	}

	protected String getPattern() {
		return node.getAttribute(ProfileEntry.JZR_PATTERN); // can be null
	}
	
	public Element getNextNode() {
		return nextNode; // can be null
	}
	
	public int getPriority() {
		String value = node.getAttribute(ProfileEntry.JZR_PRIORITY);
		// value has already been validated : parsing will work
		return (!value.isEmpty())? Integer.parseInt(value) : -1;
	}

	public boolean nodeEquals(Object obj){
		if (!(obj instanceof Element))
			return false;
		
		Element nodetoCompare = (Element) obj;
		if (!getName().equals(nodetoCompare.getAttribute(ProfileEntry.JZR_NAME)))
			return false;
		
		if (!patternEquals(nodetoCompare))
			return false;
		
		return priorityEquals(nodetoCompare);
	}
	
	// can be overridden
	public boolean patternEquals(Element nodetoCompare) {
		// contains and not equals as existing profile may have shorten pattern here
		return nodetoCompare.getAttribute(ProfileEntry.JZR_PATTERN).contains(getPattern());
	}

	public boolean priorityEquals(Element nodetoCompare) {
		String value = nodetoCompare.getAttribute(ProfileEntry.JZR_PRIORITY);
		int priorityToCompare = (!value.isEmpty())? Integer.parseInt(value) : -1;  
		return getPriority() == priorityToCompare;
	}
	
	public void updatePriority(Element sourceNode){
		this.node.setAttribute(
				JZR_PRIORITY, 
				sourceNode.getAttribute(ProfileEntry.JZR_PRIORITY)
				);
	}

	public void removePriority() {
		this.node.removeAttribute(JZR_PRIORITY);
	}
	
}
