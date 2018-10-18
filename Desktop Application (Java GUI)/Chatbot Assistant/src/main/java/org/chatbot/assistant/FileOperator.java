package org.chatbot.assistant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileOperator {
	private List<String> questions = new ArrayList<String>();
	private List<String> answers = new ArrayList<String>();

	FileOperator() {

	}

	public void readFile() {
		int i = 0;
		try {
			FileInputStream fstream = new FileInputStream("dataset.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				if (i % 2 == 0)
					questions.add(strLine);
				else
					answers.add(strLine);
				i++;
			}
			fstream.close();
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	public List<String> getQuestions() {
		return questions;
	}

	public List<String> getAnswers() {
		return answers;
	}
	
	public void addQuestionToDatabase(String newQuestion, String answer) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("dataset.txt",true));
			writer.append(newQuestion + "\n");
			writer.append(answer + "\n");
			writer.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public void addNotAnsweredQuestionToDatabase(String newQuestion, String answer) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("notAnsweredQuestions.txt",true));
			writer.append(newQuestion + "\n");
			writer.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

}
