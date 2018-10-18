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
package org.chatbot.assistant.metrics;

import static org.junit.Assert.assertSame;

import org.chatbot.assistant.Metric;
import org.chatbot.assistant.StringMetricTest;
import org.chatbot.assistant.metrics.Identity;
import org.chatbot.assistant.metrics.StringMetrics.ForMultisetWithSimplifier;
import org.chatbot.assistant.simplifiers.Simplifier;
import org.chatbot.assistant.simplifiers.Simplifiers;
import org.chatbot.assistant.tokenizers.Tokenizer;
import org.chatbot.assistant.tokenizers.Tokenizers;
import org.junit.Test;

import com.google.common.collect.Multiset;

@SuppressWarnings("javadoc")
public class ForMultisetWithSimplifierTest extends StringMetricTest{

	private final Tokenizer tokenizer = Tokenizers.whitespace();
	private final Metric<Multiset<String>> metric = new Identity<>();
	private final Simplifier simplifier = Simplifiers.toLowerCase();
	
	@Override
	protected ForMultisetWithSimplifier getMetric() {
		return new ForMultisetWithSimplifier(metric, simplifier, tokenizer);
	}
	
	@Override
	protected T[] getTests() {
		return new T[]{
				new T(0.0f, "To repeat repeat is to repeat", ""),
				new T(1.0f, "To repeat repeat is to repeat", "to repeat repeat is to repeat"),
				new T(1.0f, "To repeat repeat is to repeat", "to  repeat  repeat  is  to  repeat")
		};
	}
	
	@Override
	protected boolean satisfiesCoincidence() {
		return false;
	}
	
	@Override
	protected boolean toStringIncludesSimpleClassName() {
		return false;
	}
	
	@Test
	public void shouldReturnTokenizer(){
		assertSame(tokenizer,getMetric().getTokenizer());
	}
	
	@Test
	public void shouldReturnMetric(){
		assertSame(metric, getMetric().getMetric());
	}
	
	@Test
	public void shouldReturnSimplifier(){
		assertSame(simplifier, getMetric().getSimplifier());
	}
}
