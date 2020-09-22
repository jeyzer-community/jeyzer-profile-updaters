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


import org.jeyzer.profile.error.InvalidPatternsException;
import org.w3c.dom.Document;

public class PatternUpdaterFactory {

	private static final PatternUpdaterFactory factory = new PatternUpdaterFactory();
	
	private PatternUpdaterFactory(){
	}
	
	public static PatternUpdaterFactory newInstance(){
		return factory;
	}
	
	public PatternUpdater getPatternUpdater(String type, Document doc) throws InvalidPatternsException{
		if(PatternUpdater.JZR_FUNCTION.equals(type)){
			return new FunctionUpdater(doc);
		}
		else if(PatternUpdater.JZR_OPERATION.equals(type)){
			return new OperationUpdater(doc);
		}
		else if(PatternUpdater.JZR_LOCKER.equals(type)){
			return new LockerUpdater(doc);
		}
		else if(PatternUpdater.JZR_EXCLUDE.equals(type)){
			return new ExcludeUpdater(doc);
		}
		else if(PatternUpdater.JZR_EXECUTOR.equals(type)){
			return new ExecutorUpdater(doc);
		}
		else if(PatternUpdater.JZR_EXECUTOR_THREAD_NAME.equals(type)){
			return new ExecutorThreadNameUpdater(doc);
		}
		else if(PatternUpdater.JZR_EXCLUDE_THREAD_NAME.equals(type)){
			return new ExcludeThreadNameUpdater(doc);
		}
		return null;
	}
}
