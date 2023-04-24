package org.jeyzer.profile;

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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jeyzer.profile.error.InvalidPatternsException;
import org.jeyzer.profile.error.ProfileUpdaterInitException;
import org.jeyzer.profile.pattern.PatternUpdater;
import org.jeyzer.profile.pattern.PatternUpdaterFactory;
import org.jeyzer.profile.util.ConfigUtil;
import org.jeyzer.profile.util.PrettyXMLPrinter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class JeyzerProfileUpdater {

	private static final String JEYZER_PATTERNS_SOURCE_PATH = "jeyzer.patterns.source";
	private static final String JEYZER_PATTERNS_TARGET_PATH = "jeyzer.patterns.target";
	private static final String JEYZER_NEW_PATTERN_ENTRIES_PATH = "jeyzer.new.patterns.entries";

	private static final String PATTERNS_SOURCE_COPY_SUFFIX = ".source";
	
	public void execute(File patternsSource, File profileTarget, File newPatternEntries) throws InvalidPatternsException, ProfileUpdaterInitException, Exception {
		validateInputs(patternsSource, newPatternEntries);
		copyPatternsSource(patternsSource, profileTarget);
		Element newPatterns = loadNewPatternEntries(newPatternEntries);
		updatePatterns(profileTarget, newPatterns);
	}
	
	private void validateInputs(File patternsSource, File newPatternEntries) throws ProfileUpdaterInitException {
        if (!patternsSource.exists())
        {
        	throw new ProfileUpdaterInitException( "Jeyzer patterns source file not found : " + patternsSource.getPath());
        }
        
        if (!newPatternEntries.exists())
        {
        	throw new ProfileUpdaterInitException( "Jeyzer new pattern entries file not found : " + newPatternEntries.getPath());
        }
	}

	private Element loadNewPatternEntries(File newPatternEntriesPath) throws InvalidPatternsException, Exception {
		Document sourceDoc = ConfigUtil.loadXMLFile(newPatternEntriesPath);
		if (sourceDoc == null)
			throw new InvalidPatternsException("Failed to open the pattern entries configuration using path : " + newPatternEntriesPath);

		NodeList nodes = sourceDoc.getElementsByTagName(PatternUpdater.JZR_PATTERNS);
		return (Element)nodes.item(0);
	}

	private void copyPatternsSource(File patternsSourcePath, File patternsTargetPath) throws IOException {
		Files.copy(patternsSourcePath.toPath(), patternsTargetPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
		
		// make it pretty to facilitate the file diff
		PrettyXMLPrinter.makePrettyXML(patternsSourcePath, new File(patternsTargetPath.getPath() + PATTERNS_SOURCE_COPY_SUFFIX));
	}

	private void updatePatterns(File patternsTargetPath, Element newPatterns) throws InvalidPatternsException, Exception {
		Document doc;
		
		doc = ConfigUtil.loadXMLFile(patternsTargetPath);
		if (doc == null)
			throw new InvalidPatternsException("Failed to open the target patterns configuration using path : " + patternsTargetPath);
			
		PatternUpdaterFactory factory = PatternUpdaterFactory.newInstance();

		factory.getPatternUpdater(PatternUpdater.JZR_FUNCTION, doc).updatePatterns(newPatterns);
		factory.getPatternUpdater(PatternUpdater.JZR_OPERATION, doc).updatePatterns(newPatterns);
		factory.getPatternUpdater(PatternUpdater.JZR_LOCKER, doc).updatePatterns(newPatterns);
		factory.getPatternUpdater(PatternUpdater.JZR_EXECUTOR, doc).updatePatterns(newPatterns);
		factory.getPatternUpdater(PatternUpdater.JZR_EXECUTOR_THREAD_NAME, doc).updatePatterns(newPatterns);
		factory.getPatternUpdater(PatternUpdater.JZR_EXCLUDE, doc).updatePatterns(newPatterns);
		factory.getPatternUpdater(PatternUpdater.JZR_EXCLUDE_THREAD_NAME, doc).updatePatterns(newPatterns);

		// write the content into xml file
		updateXml(doc, patternsTargetPath);
			
		PrettyXMLPrinter.makePrettyXML(patternsTargetPath, patternsTargetPath);
	}

	private void updateXml(Document doc, File patternsTargetPath) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(patternsTargetPath);
		transformer.transform(source, result);
	}

	public static void main(String[] args) {
		JeyzerProfileUpdater updater = new JeyzerProfileUpdater();
		
		File patternsSource = new File(loadStartupSystemProperty(JEYZER_PATTERNS_SOURCE_PATH));
		File patternsTarget = new File(loadStartupSystemProperty(JEYZER_PATTERNS_TARGET_PATH));
		File newPatternEntries = new File(loadStartupSystemProperty(JEYZER_NEW_PATTERN_ENTRIES_PATH));
		
		System.out.println("Generating patterns    : " + patternsSource.getPath());
		System.out.println("  from source patterns : " + patternsTarget.getPath());
		System.out.println("  with pattern entries from   : " + newPatternEntries.getPath());
		
		try {
			updater.execute(patternsSource, patternsTarget, newPatternEntries);
			System.out.println("Analysis patterns update succeeded. Result and source diff can be performed between those 2 files :");
			System.out.println("  Source : " + patternsTarget.getPath() + PATTERNS_SOURCE_COPY_SUFFIX);
			System.out.println("  Target : " + patternsTarget.getPath());
		} catch (Exception ex) {
			System.err.println("Analysis patterns update failed.");
			ex.printStackTrace();
		}
	}

	private static String loadStartupSystemProperty(String propertyName) {
		String value = System.getProperty(propertyName);
		if (value == null){
			System.err.println("Profile updater system property  " + propertyName + " is missing.");
			System.exit(-1);
		}
		return value;
	}

}
