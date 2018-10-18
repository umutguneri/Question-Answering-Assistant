/*
 * #%L
 * Simmetrics Examples
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * #L%
 */
package org.chatbot.assistant;

import static java.util.Arrays.asList;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.chatbot.assistant.SetMetric;
import org.chatbot.assistant.StringDistance;
import org.chatbot.assistant.StringMetric;
import org.chatbot.assistant.builders.StringDistanceBuilder;
import org.chatbot.assistant.builders.StringMetricBuilder;
import org.chatbot.assistant.metrics.CosineSimilarity;
import org.chatbot.assistant.metrics.EuclideanDistance;
import org.chatbot.assistant.metrics.OverlapCoefficient;
import org.chatbot.assistant.metrics.StringMetrics;
import org.chatbot.assistant.simplifiers.Simplifiers;
import org.chatbot.assistant.tokenizers.Tokenizers;

/**
 * Examples from README.md
 */
@SuppressWarnings("javadoc")
public final class ReadMeExample {
	public static float example01() {

		String str1 = "This is a sentence. It is made of words";
		String str2 = "This sentence is similar. It has almost the same words";

		StringMetric metric = StringMetrics.cosineSimilarity();

		float result = metric.compare(str1, str2); // 0.4767

		return result;
	}

	public static float example02() {

		String str1 = "This is a sentence. It is made of words";
		String str2 = "This sentence is similar. It has almost the same words";

		StringMetric metric = StringMetricBuilder.with(new CosineSimilarity<String>())
				.simplify(Simplifiers.toLowerCase(Locale.ENGLISH)).simplify(Simplifiers.replaceNonWord())
				.tokenize(Tokenizers.whitespace()).build();

		float result = metric.compare(str1, str2); // 0.5720

		return result;
	}

	public static float example03() {

		String str1 = "This is a sentence. It is made of words";
		String str2 = "This sentence is similar. It has almost the same words";

		StringDistance metric = StringDistanceBuilder.with(new EuclideanDistance<String>())
				.simplify(Simplifiers.toLowerCase(Locale.ENGLISH)).simplify(Simplifiers.replaceNonWord())
				.tokenize(Tokenizers.whitespace()).build();

		float result = metric.distance(str1, str2); // 3.0000

		return result;
	}

	public static float example04() {

		Set<Integer> scores1 = new HashSet<>(asList(1, 1, 2, 3, 5, 8, 11, 19));
		Set<Integer> scores2 = new HashSet<>(asList(1, 2, 4, 8, 16, 32, 64));

		SetMetric<Integer> metric = new OverlapCoefficient<>();

		float result = metric.compare(scores1, scores2); // 0.4285

		return result;
	}

}
