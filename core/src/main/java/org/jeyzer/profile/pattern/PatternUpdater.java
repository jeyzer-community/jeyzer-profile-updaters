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


import java.util.Collection;

import org.jeyzer.profile.data.ProfileEntry;
import org.jeyzer.profile.data.validation.ProfileEntryValidator;
import org.jeyzer.profile.error.InvalidProfileEntryException;
import org.jeyzer.profile.error.InvalidPatternsException;
import org.jeyzer.profile.util.ConfigUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

public abstract class PatternUpdater {
	
	public static final String JZR_PROFILE = "profile";
	public static final String JZR_PATTERNS = "patterns";
	

	public static final String JZR_FUNCTION = "function";
	public static final String JZR_OPERATION = "operation";
	public static final String JZR_LOCKER = "locker";
	public static final String JZR_EXCLUDE = "exclude";
	public static final String JZR_EXCLUDE_THREAD_NAME = "exclude_thread_name";
	public static final String JZR_EXECUTOR = "executor";
	public static final String JZR_EXECUTOR_THREAD_NAME = "executor_thread_name";
	
	protected Document doc;

	// functions, operations..
	protected Element typePatternsNode;
	
	// ex : all function objects
	protected NodeList targetNodes;
	
	public PatternUpdater(Document doc) throws InvalidPatternsException {
		this.doc = doc;
		
		// profile
		NodeList nodes = doc.getElementsByTagName(JZR_PROFILE);
		if (nodes.getLength() == 0)
			throw new InvalidPatternsException(JZR_PROFILE + " node is missing in target profile file " + doc.getDocumentURI());
		
		Element profileNode = (Element)nodes.item(0);
		
		// patterns
		Element patternsNode = ConfigUtil.getFirstChildNode(profileNode, JZR_PATTERNS);
		if (patternsNode == null)
			throw new InvalidPatternsException(JZR_PATTERNS + " node is missing in target profile file " + doc.getDocumentURI());
		
		typePatternsNode = getTypePatternsNode(patternsNode, true);
		
		targetNodes = typePatternsNode.getElementsByTagName(this.getName()); // can be empty 
	}

	public abstract String getName();
	
	public void updatePatterns(Element newPatterns) {
		Element newTypePatternsNode = getTypePatternsNode(newPatterns, false);
		if (newTypePatternsNode == null)
			return;
		
		NodeList newNodes = newTypePatternsNode.getElementsByTagName(this.getName());
		if (newNodes.getLength() == 0)
			return;
		
		// Build cache only if new nodes must be inserted
		Multimap<String, ProfileEntry> targetNodeCache = buildCache(targetNodes);
		
		for (int i=0; i<newNodes.getLength(); i++){
			Element newNode = (Element)newNodes.item(i);
			
			// validate the new node
			try{
				this.getValidator().validate(newNode);
			}catch(InvalidProfileEntryException ex){
				System.err.println("Warning : Invalid profile entry to add : " + ex.getMessage());
				continue;
			}
			
			insertNewPattern(newNode, targetNodeCache);
		}
	}
	
	protected abstract ProfileEntry createProfileEntry(Element node, Element nextNode) throws InvalidProfileEntryException;
	
	protected abstract ProfileEntryValidator getValidator();

	// can be overridden
	protected void updatePatternOptionalFields(ProfileEntry entry, Element newNode) {
		// update priority
		if (!entry.priorityEquals(newNode)){
			String value = newNode.getAttribute(ProfileEntry.JZR_PRIORITY);
			int newPriority = (!value.isEmpty())? Integer.parseInt(value) : -1;
			
			if (newPriority == -1){
				entry.removePriority();
			}else{
				entry.updatePriority(newNode);
			}
		}
	}

	// can be overridden
	protected void appendNewPattern(Element newNode) {
		Element newChild = (Element)newNode.cloneNode(true);
		this.doc.adoptNode(newChild);
		this.typePatternsNode.appendChild(newChild);
	}
	
	private void insertNewPattern(Element newNode, Multimap<String, ProfileEntry> targetNodeCache) {
		Collection<ProfileEntry> entries = targetNodeCache.get(newNode.getAttribute(ProfileEntry.JZR_NAME));
		if (entries.isEmpty())
			appendNewPattern(newNode);
		else
			insertNewPattern(newNode, entries);
	}

	private void insertNewPattern(Element newNode, Collection<ProfileEntry> entries) {
		ProfileEntry lastEntry = null;
		
		for (ProfileEntry entry : entries){
			if (entry.nodeEquals(newNode))
				return; 	// already there
			else if (entry.patternEquals(newNode)){
				updatePatternOptionalFields(entry, newNode); // possibly update priority or any other field
				return;
			}
			lastEntry = entry;
		}
		
		if (lastEntry == null)
			return; // should not happen
		
		// new entry under the same name: add it after the last entry
		insertNewPattern(newNode, lastEntry);
	}

	private void insertNewPattern(Element newNode, ProfileEntry lastEntry) {
		Element nextNode = lastEntry.getNextNode();
		
		if (nextNode == null)
			appendNewPattern(newNode);
		else{
			insertNewPattern(newNode, nextNode);
		}
	}

	protected void insertNewPattern(Element newNode, Element nextNode) {
		Element newChild = (Element)newNode.cloneNode(true);
		this.doc.adoptNode(newChild);
		this.typePatternsNode.insertBefore(newChild, nextNode);
	}

	private Multimap<String, ProfileEntry> buildCache(NodeList targetNodes) {
		Multimap<String, ProfileEntry> targetNodeCache = LinkedHashMultimap.create();
		
		for (int i=0; i<targetNodes.getLength(); i++){
			Element node = (Element)targetNodes.item(i);
			Element nextNode = (i != targetNodes.getLength()) ? (Element)targetNodes.item(i+1) : null;
			
			try {
				ProfileEntry entry = createProfileEntry(node, nextNode);
				targetNodeCache.put(entry.getName(), entry);
			} catch (InvalidProfileEntryException ex) {
				System.err.print("Warning : Failed to create profile entry. " + ex.getMessage()); // ignore entry
			}
		}
		
		return targetNodeCache;
	}

	private Element getTypePatternsNode(Element patternsNode , boolean create) {
		NodeList patternTypesNodes = patternsNode.getElementsByTagName(this.getName() + "s");
		if (patternTypesNodes.getLength() == 0){
			return create? this.doc.createElement(this.getName() + "s") : null;
		}else{
			return (Element)patternTypesNodes.item(0);
		}
	}	
	
}
