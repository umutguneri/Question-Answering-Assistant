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

import static java.util.Arrays.asList;

import org.chatbot.assistant.SetMetric;
import org.chatbot.assistant.SetMetricTest;
import org.chatbot.assistant.metrics.OverlapCoefficient;

@SuppressWarnings("javadoc")
public final class OverlapCoefficientTest extends SetMetricTest {

	@Override
	protected boolean satisfiesCoincidence() {
		return false;
	}
	
	@Override
	protected SetMetric<String> getMetric() {
		return new OverlapCoefficient<>();
	}

	@Override
	protected T[] getTests() {

		return new T[] {
				new T(0.5000f, "test string1", "test string2"),
				new T(1.0000f, "test", "test string2"),
				new T(0.0000f, "", "test string2"),
				new T(0.5000f, asList("test", null), asList("test","string2")),

				new T(0.7500f, "aaa bbb ccc ddd", "aaa bbb ccc eee"),
				new T(0.7500f, "aaa bbb ccc ddd aaa bbb ccc ddd", "aaa bbb ccc eee"),
				
				new T(0.7500f, "a b c d", "a b c e"),
				new T(0.5000f, "a b c d", "a b e f"),
				new T(1.0000f, "a b c", "a b c e f g"),
				new T(1.0000f, "a b b c c", "a b c e f g"),
				
				new T(0.0000f, "Healed", "Sealed"),
				new T(0.0000f, "Healed", "Healthy"),
				new T(0.0000f, "Healed", "Heard"),
				new T(0.0000f, "Healed", "Herded"),
				new T(0.0000f, "Healed", "Help"),
				new T(0.0000f, "Healed", "Sold"),
				new T(0.0000f, "Healed", "Help"),
				
				new T(0.3333f, "Sam J Chapman", "Samuel John Chapman"),
				new T(0.5000f, "Sam Chapman", "S Chapman"),
				new T(0.5000f, "John Smith", "Samuel John Chapman"),
				new T(0.0000f, "John Smith", "Sam Chapman"),
				new T(0.0000f, "John Smith", "Sam J Chapman"),
				new T(0.0000f, "John Smith", "S Chapman"),
				
				new T(1.0000f, "Web Database Applications",
						"Web Database Applications with PHP & MySQL"),
				new T(1.0000f, "Web Database Applications",
						"Creating Database Web Applications with PHP and ASP"),
				new T(1.0000f, "Web Database Applications",
						"Building Database Applications on the Web Using PHP3"),
				new T(1.0000f, "Web Database Applications",
						"Building Web Database Applications with Visual Studio 6"),
				new T(0.3333f, "Web Database Applications",
						"Web Application Development With PHP"),
				new T(
						1.0000f,
						"Web Database Applications",
						"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(0.0000f, "Web Database Applications",
						"Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(0.0000f, "Web Database Applications",
						"How to Find a Scholarship Online"),
				new T(0.5000f, "Web Aplications",
						"Web Database Applications with PHP & MySQL"),
				new T(0.5000f, "Web Aplications",
						"Creating Database Web Applications with PHP and ASP"),
				new T(0.5000f, "Web Aplications",
						"Building Database Applications on the Web Using PHP3"),
				new T(0.5000f, "Web Aplications",
						"Building Web Database Applications with Visual Studio 6"),
				new T(0.5000f, "Web Aplications",
						"Web Application Development With PHP"),
				new T(
						0.5000f,
						"Web Aplications",
						"WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection"),
				new T(0.0000f, "Web Aplications",
						"Structural Assessment: The Role of Large and Full-Scale Testing"),
				new T(0.0000f, "Web Aplications",
						"How to Find a Scholarship Online") };

	}


	

}
