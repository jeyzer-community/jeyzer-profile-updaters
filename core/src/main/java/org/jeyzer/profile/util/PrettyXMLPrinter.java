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


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class PrettyXMLPrinter {
	
	private PrettyXMLPrinter() {}
	
	public static void makePrettyXML(File srcFilePath, File destFilePath) {
		int indent = 2;
		
	    try {
	    	String xml = readFile(srcFilePath);
	    	
	        // Turn xml string into a document
	    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    	dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
	    	dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
	    	Document document = dbf.newDocumentBuilder()
					.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));

	        // Remove white spaces outside tags
	        XPath xPath = XPathFactory.newInstance().newXPath();
	        NodeList nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']",
	                                                      document,
	                                                      XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); ++i) {
	            Node node = nodeList.item(i);
	            node.getParentNode().removeChild(node);
	        }

	        // Setup pretty print options
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
	        transformerFactory.setAttribute("indent-number", indent);
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

	        // Return pretty print xml string
	        StringWriter stringWriter = new StringWriter();
	        transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
	        
	        writeFile(destFilePath, stringWriter.toString());
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	public static String readFile(File file) throws IOException {
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    try (
	    		FileReader fr = new FileReader(file);
	    		BufferedReader reader = new BufferedReader(fr);
	    	)
	    {
	        while((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	            stringBuilder.append(ls);
	        }

	        return stringBuilder.toString();
	    }
	}
	
	public static void writeFile(File file, String content) throws IOException {
		try (
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			)
		{
			writer.write(content);
		}
	}
}
