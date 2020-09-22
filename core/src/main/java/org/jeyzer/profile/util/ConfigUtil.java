package org.jeyzer.profile.util;

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


import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class ConfigUtil {
	
	private ConfigUtil(){}
	
	public static Document loadXMLFile(File file) throws Exception{
		Document doc = null;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		DocumentBuilder db = dbf.newDocumentBuilder();
		doc = db.parse(file);
		doc.getDocumentElement().normalize();

		return doc;
	}
	
	public static Element getFirstChildNode(Element node, String name){
		NodeList nodes = node.getElementsByTagName(name);
		if (nodes.getLength() == 0)
			return null;
		else
			return (Element)nodes.item(0);
	}
	
	public static String getAttributeValue(Element node, String name){
		String value;
		
		value = node.getAttribute(name);
		
		return value;
	}
	
}
