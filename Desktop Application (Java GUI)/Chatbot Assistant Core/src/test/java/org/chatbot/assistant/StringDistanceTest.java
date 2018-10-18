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
package org.chatbot.assistant;

@SuppressWarnings("javadoc")
public abstract class StringDistanceTest extends DistanceTest<String> {
	
	protected static final class T extends TestCase<String>{
		public T(float similarity, String a, String b) {
			super(similarity, a, b);
		}
	}
	
	@Override
	protected abstract T[] getTests();
	
	@Override
	protected final String getEmpty() {
		return "";
	}
}
