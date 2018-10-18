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
package org.chatbot.assistant.tokenizers;

import static java.util.regex.Pattern.compile;
import static org.chatbot.assistant.tokenizers.Tokenizers.Split;

import org.chatbot.assistant.tokenizers.Tokenizer;

@SuppressWarnings("javadoc")
public class SplitTest extends TokenizerTest {

	@Override
	protected Tokenizer getTokenizer() {
		return new Split(compile(","));
	}

	@Override
	protected T[] getTests() {

		return new T[] { 
				new T("", ""), 
				new T(" ", " "),
				new T(",", "", ""), 
				new T(",,", "", "", ""),
				new T("A,B,C", "A", "B", "C"),
				new T(",A,B,C,","","A", "B", "C",""),
				new T("A,,B,,C", "A", "", "B", "", "C"), };
	}
}
