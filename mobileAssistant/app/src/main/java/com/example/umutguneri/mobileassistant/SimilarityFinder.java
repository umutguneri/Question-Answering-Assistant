package com.example.umutguneri.mobileassistant;

import java.util.ArrayList;
import java.util.List;

import info.debatty.java.stringsimilarity.Cosine;

public class SimilarityFinder {
    private FileOperator fileOperator;
    private List<String> questions = new ArrayList<String>();
    private List<String> answers = new ArrayList<String>();
    private List<String> possibleQuestions = new ArrayList<String>(5);
    private List<Double> maxResults = new ArrayList <Double> (5);;
    private List<Integer> indexList=  new ArrayList <Integer> (5);
    private ArrayList<Double> results = new ArrayList<Double>();

    SimilarityFinder() {
        this.fileOperator = new FileOperator();
    }

    public ArrayList<Double> findSimilarityArray(String input) {
        Cosine sim = new Cosine();
        questions.clear();
        answers.clear();
        results.clear();
        indexList.clear();
        maxResults.clear();

        fileOperator.readFile();
        questions = fileOperator.getQuestions();
        answers = fileOperator.getAnswers();
        double result;

        for (String question : questions) {
            result = sim.similarity(input, question);
            this.results.add(result);
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
        return answers.get(indexList.get(index));
    }

    public double findMaxSimilarity(String input) {
        if (this.results == null)
            findSimilarityArray(input);
        return this.maxResults.get(0);
    }


    public List<Integer> getMaxIndexs(ArrayList<Double> results) {
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
