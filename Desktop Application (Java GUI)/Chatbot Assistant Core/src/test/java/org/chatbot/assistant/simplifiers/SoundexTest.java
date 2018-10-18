/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.chatbot.assistant.simplifiers;

import org.chatbot.assistant.simplifiers.Simplifier;
import org.chatbot.assistant.simplifiers.Soundex;

@SuppressWarnings("javadoc")
@Deprecated
public class SoundexTest extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new Soundex();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "T526"),
				new T("James", "J520"),
				new T("", ""),
				new T("Travis", "T612"),
				new T("Marcus", "M622"),
				
				new T("Ozymandias", "O255"),
				new T("Jones", "J520"),
				new T("Jenkins", "J525"),
				new T("Trevor", "T616"),
				new T("Marinus", "M652"),
		};
	}

}
