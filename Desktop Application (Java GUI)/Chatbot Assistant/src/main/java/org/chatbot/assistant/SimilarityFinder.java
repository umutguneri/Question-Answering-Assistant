package org.chatbot.assistant;

import static com.google.common.base.Predicates.in;
import static org.chatbot.assistant.builders.StringMetricBuilder.with;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.chatbot.assistant.SetMetric;
import org.chatbot.assistant.StringDistance;
import org.chatbot.assistant.StringMetric;
import org.chatbot.assistant.builders.StringDistanceBuilder;
import org.chatbot.assistant.builders.StringMetricBuilder;
import org.chatbot.assistant.metrics.CosineSimilarity;
import org.chatbot.assistant.metrics.EuclideanDistance;
import org.chatbot.assistant.metrics.Levenshtein;
import org.chatbot.assistant.metrics.OverlapCoefficient;
import org.chatbot.assistant.metrics.StringMetrics;
import org.chatbot.assistant.simplifiers.Simplifiers;
import org.chatbot.assistant.tokenizers.Tokenizers;
import com.google.common.base.Predicates;
import org.chatbot.assistant.StringDistance;
import org.chatbot.assistant.metrics.StringDistances;

public class SimilarityFinder {
	private FileOperator fileOperator;
	private List<String> questions = new ArrayList<String>();
	private List<String> answers = new ArrayList<String>();
	private List<String> possibleQuestions = new ArrayList<String>(5);
	private List<Float> maxResults = new ArrayList <Float> (5);;
	private List<Integer> indexList=  new ArrayList <Integer> (5);
	private ArrayList<Float> results = new ArrayList<Float>();
	private String similarity_type;
	private int count = 0;

	SimilarityFinder() {
		this.fileOperator = new FileOperator();
		this.similarity_type = "Cosine Similarity";
	}

	public ArrayList<Float> findSimilarityArray(String input) {
		questions.clear();
		answers.clear();
		results.clear();
		indexList.clear();
		maxResults.clear();
		
		fileOperator.readFile();
		questions = fileOperator.getQuestions();
		answers = fileOperator.getAnswers();
		float result;
		StringMetric metric;
		
        switch (similarity_type) {
            case "Cosine Similarity":
	            	metric = StringMetricBuilder.with(new CosineSimilarity<String>())
	            			.simplify(Simplifiers.toLowerCase()).simplify(Simplifiers.replaceNonWord())
	            			.tokenize(Tokenizers.whitespace()).build();
                    break;
                     
            case "Levenshtein":
	            	metric = with(new Levenshtein()).simplify(Simplifiers.removeDiacritics())
	        				.simplify(Simplifiers.toLowerCase()).build();
	                break;
	                 
            case "Qgram":
	            	metric = with(new CosineSimilarity<String>()).simplify(Simplifiers.toLowerCase())
	        				.simplify(Simplifiers.removeNonWord()).tokenize(Tokenizers.whitespace())
	        				.tokenize(Tokenizers.qGram(3)).build();
	                break;
	                 
            default: metric = StringMetricBuilder.with(new CosineSimilarity<String>())
					.simplify(Simplifiers.toLowerCase()).simplify(Simplifiers.replaceNonWord())
					.tokenize(Tokenizers.whitespace()).build();
                     break;
        }
        
		for (String question : questions) {
			result = metric.compare(input, question); 
			this.results.add((float) result);
		}
		getMaxIndexs(results);
		return results;
	}

	public String findClosestQuestion(String input) {
		if (this.results == null) 
			findSimilarityArray(input);
		return questions.get(indexList.get(0));
	}

	public String findClosestAnswer(String input) {
		if (this.results == null)
			findSimilarityArray(input);
		return answers.get(indexList.get(0));
	}
	
	public String getAnswer(int index) {
		/*if (this.results == null)
			findSimilarityArray(input);*/
		return answers.get(indexList.get(index));
	}

	public float findMaxSimilarity(String input) {
		if (this.results == null)
			findSimilarityArray(input);
		return this.maxResults.get(0);
	}

	public static float getMax(ArrayList<Float> results) {
		float maxValue = results.get(0);
		for (int i = 0; i < results.size(); i++) {
			if (results.get(i) > maxValue) {
				maxValue = results.get(i);
			}
		}
		return maxValue;
	}
	
	public List<Integer> getMaxIndexs(ArrayList<Float> results) {
		indexList.add(0, 0);
		indexList.add(1, 0);
		indexList.add(2, 0);
		indexList.add(3, 0);
		indexList.add(4, 0);
		
		maxResults.add(0, results.get(0));
		maxResults.add(1, results.get(0));
		maxResults.add(2, results.get(0));
		maxResults.add(3, results.get(0));
		maxResults.add(4, results.get(0));
		
		for (int i = 1; i < results.size(); i++) {
			if (results.get(i) > maxResults.get(0)) {
				maxResults.set(4, maxResults.get(3));
				maxResults.set(3, maxResults.get(2));
				maxResults.set(2, maxResults.get(1));
				maxResults.set(1, maxResults.get(0));
				maxResults.set(0, results.get(i));
				
				indexList.set(4, indexList.get(3));
				indexList.set(3, indexList.get(2));
				indexList.set(2, indexList.get(1));
				indexList.set(1, indexList.get(0));
				indexList.set(0, i);
			}
			else if(results.get(i) > maxResults.get(1)) {
				maxResults.set(4, maxResults.get(3));
				maxResults.set(3, maxResults.get(2));
				maxResults.set(2, maxResults.get(1));
				maxResults.set(1, results.get(i));
				
				indexList.set(4, indexList.get(3));
				indexList.set(3, indexList.get(2));
				indexList.set(2, indexList.get(1));
				indexList.set(1, i);
				
			}
			else if(results.get(i) > maxResults.get(2)) {
				maxResults.set(4, maxResults.get(3));
				maxResults.set(3, maxResults.get(2));
				maxResults.set(2, results.get(i));
				
				indexList.set(4, indexList.get(3));
				indexList.set(3, indexList.get(2));
				indexList.set(2, i);
			}
			else if(results.get(i) > maxResults.get(3)) {
				maxResults.set(4, maxResults.get(3));
				maxResults.set(3, results.get(i));
				
				indexList.set(4, indexList.get(3));
				indexList.set(3, i);
			}
			else if(results.get(i) > maxResults.get(4)) {
				maxResults.set(4, results.get(i));
				
				indexList.set(4, i);
			}	
		}
		System.out.println("Maksimum Benzerlik Oranları:"+maxResults);
		return indexList;
	}

	public static int getMaxIndex(ArrayList<Float> results) {
		int index = 0;
		float maxValue = results.get(0);
		for (int i = 1; i < results.size(); i++) {
			if (results.get(i) > maxValue) {
				maxValue = results.get(i);
				index = i;
			}
		}
		return index;
	}
	
	public void setSimilarityMethod(String similarity_type) {
		this.similarity_type = similarity_type;
	}
	
	public List<String> findPossibleQuestions(String input) {	
		possibleQuestions.clear();
		
		if (this.results == null)
			findSimilarityArray(input);
		
		for (int i = 0; i < 5; i++) {
			possibleQuestions.add(questions.get(indexList.get(i)));		
		}
		return possibleQuestions;
	}

}
