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

import org.chatbot.assistant.simplifiers.Caverphone2;
import org.chatbot.assistant.simplifiers.Simplifier;

@SuppressWarnings("javadoc")
@Deprecated
public class CaverPhone2Test extends SimplifierTest {

	@Override
	protected Simplifier getSimplifier() {
		return new Caverphone2();
	}

	@Override
	protected T[] getTests() {
		return new T[] { 
				new T("Tannhauser", "TNSA111111"),
				new T("James", "YMS1111111"),
				new T("", "1111111111"),
				new T("Travis", "TRFS111111"),
				new T("Marcus", "MKS1111111"),
				new T("Ozymandias", "ASMNTS1111"),
				new T("Jones", "YNS1111111"),
				new T("Jenkins", "YNKNS11111"),
				new T("Trevor", "TRFA111111"),
				new T("Marinus", "MRNS111111"),
		};
	}

}
