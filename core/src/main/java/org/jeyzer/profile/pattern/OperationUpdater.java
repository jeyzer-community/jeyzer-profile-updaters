package org.jeyzer.profile.pattern;

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


import org.jeyzer.profile.data.OperationEntry;
import org.jeyzer.profile.data.ProfileEntry;
import org.jeyzer.profile.data.validation.ProfileEntryValidator;
import org.jeyzer.profile.error.InvalidProfileEntryException;
import org.jeyzer.profile.error.InvalidPatternsException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class OperationUpdater extends PatternUpdater {

	protected Element firstLowLevelPatternNode; // can be null
	
	public OperationUpdater(Document doc) throws InvalidPatternsException {
		super(doc);
		
		for (int i=0; i<targetNodes.getLength(); i++){
			Element node = (Element)targetNodes.item(i);
			String value = node.getAttribute(ProfileEntry.JZR_PATTERN);
			if (value.endsWith(".")){
				this.firstLowLevelPatternNode = node;
				break;
			}
		}
	}
	
	@Override
	public String getName() {
		return PatternUpdater.JZR_OPERATION;
	}

	@Override
	protected ProfileEntry createProfileEntry(Element node, Element nextNode) throws InvalidProfileEntryException {
		getValidator().validate(node);
		return new OperationEntry(node, nextNode);
	}

	@Override
	protected ProfileEntryValidator getValidator() {
		return new ProfileEntryValidator();
	}
	
	@Override
	protected void appendNewPattern(Element newNode) {
		String value = newNode.getAttribute(ProfileEntry.JZR_PATTERN);
		if (!value.endsWith(".")){
			if (firstLowLevelPatternNode != null)
				super.insertNewPattern(newNode, firstLowLevelPatternNode);
			else
				super.appendNewPattern(newNode);
		}else{
			super.appendNewPattern(newNode);
			if (firstLowLevelPatternNode == null)
				firstLowLevelPatternNode = newNode; 
		}
	}
	
	@Override
	protected void updatePatternOptionalFields(ProfileEntry entry, Element newNode) {
		super.updatePatternOptionalFields(entry, newNode);
		
		if (!(entry instanceof OperationEntry))
			return;

		// update size
		OperationEntry operationEntry = (OperationEntry)entry; 
		
		if (!operationEntry.contentionTypeEquals(newNode)){
			String value = newNode.getAttribute(OperationEntry.JZR_TYPE);
			
			if (value.isEmpty()){
				operationEntry.removeContentionType();
			}else{
				operationEntry.updateContentionType(newNode);
			}
		}
	}

}
