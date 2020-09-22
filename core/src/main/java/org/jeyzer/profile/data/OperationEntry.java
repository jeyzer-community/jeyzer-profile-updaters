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

public class OperationEntry extends ProfileEntry {
	
	public static final String JZR_TYPE = "type";
	
	public OperationEntry(Element node, Element nextNode) throws InvalidProfileEntryException {
		super(node, nextNode);
	}
	
	@Override
	public boolean nodeEquals(Object obj){
		if (!super.nodeEquals(obj))
			return false;
		
		Element nodetoCompare = (Element) obj;
		return contentionTypeEquals(nodetoCompare);
	}

	protected String getContentionType() {
		return node.getAttribute(JZR_TYPE); // can be null
	}
	
	public boolean contentionTypeEquals(Element nodetoCompare) {
		String value = nodetoCompare.getAttribute(JZR_TYPE);
		return value.equals(getContentionType());
	}
	
	public void updateContentionType(Element sourceNode) {
		this.node.setAttribute(
				JZR_TYPE, 
				sourceNode.getAttribute(JZR_TYPE)
				);
	}
	
	public void removeContentionType() {
		this.node.removeAttribute(JZR_TYPE);
	}
	
}
