package org.chatbot.assistant;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Screen extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextField question;
	private JTextArea textArea;
	private JScrollPane scroll;
	private Choice choice;
	private Choice possibleQuestion;
	private JFrame mainFrame;
	private JLabel feedBackLabel;
	private JLabel methodLabel;
	private JLabel spaceLabel;
	private JLabel gapLabel;
	private JLabel questionLabel;
	private JLabel maybeLabel;
	private JPanel controlPanel;
	private Font font_bold;
    private JButton choiceButton;
    private JButton possibleQuestionButton;
    private JButton likeButton;
    private JButton unlikeButton;
    private JButton sendButton;
	private SimilarityFinder answerFinder;
	private String answer;
	private ImageIcon likeIcon; 
	private ImageIcon unlikeIcon;
	private FileOperator fileOperator;
	
	public Screen() {
		this.answerFinder = new SimilarityFinder();
		this.fileOperator = new FileOperator();
		prepareGUI();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Assistant");
		mainFrame.setSize(1180,800);
		mainFrame.setLayout(new GridLayout(1, 1));
		mainFrame.setResizable(false);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		
		this.font_bold = new Font("", Font.BOLD, 16);
		
		feedBackLabel = new JLabel("Cevaptan memnun kaldınız mı?:", JLabel.LEFT);
		methodLabel = new JLabel("Kullanılan Benzerlik Algoritması:", JLabel.LEFT);
		spaceLabel = new JLabel("                                                                                                                                                                                                                                                                          ");
     	gapLabel = new JLabel("                                                                                                                                                                                                                                                                                      ");
		questionLabel = new JLabel("Soruyu Giriniz: ", JLabel.LEFT);
		maybeLabel = new JLabel("Sormak istediğiniz soru bu sorulardan biri olabilir mi?:", JLabel.RIGHT);
		
		feedBackLabel.setVisible(false);
		maybeLabel.setVisible(false);
		
		likeIcon = new ImageIcon("ok.png");
		unlikeIcon = new ImageIcon("notOk.png");
		
		possibleQuestionButton = new JButton("Tamam");
		choiceButton = new JButton("Tamam");
		likeButton = new JButton(likeIcon);
		unlikeButton = new JButton(unlikeIcon);
		sendButton = new JButton("Sor");
		
		possibleQuestionButton.setVisible(false);
		unlikeButton.setVisible(false);
		likeButton.setVisible(false);
		
		textArea = new JTextArea(20,65);
		textArea.setFont(font_bold);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		
		this.scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		this.choice = new Choice();
		choice.add("Cosine Similarity");
		choice.add("Levenshtein");
		choice.add("Qgram");
		
		this.possibleQuestion = new Choice();
		possibleQuestion.setVisible(false);

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
	
		mainFrame.add(controlPanel);
		mainFrame.setVisible(true);
	}

	private void showTextField() {
		questionLabel.setFont(font_bold);
		question = new JTextField(65);
		question.setFont(font_bold);
		
		choiceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String chosen_type = choice.getSelectedItem(); 
				System.out.println("Seçilen Benzerlik Methodu: " + chosen_type);
				answerFinder.setSimilarityMethod(chosen_type);
			}
		});
		
		possibleQuestionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String selectedQuestion = possibleQuestion.getSelectedItem(); 
			    int index = possibleQuestion.getSelectedIndex();
			    String selectedAnswer = answerFinder.getAnswer(index);
			    answer = selectedAnswer;
			    
				System.out.println("Seçilen Benzer Soru: " + selectedQuestion);
				System.out.println("Cevap: " + answer);
				
				String soru = "Sorunuz: " + selectedQuestion;
				String cevap = "Cevap: " + selectedAnswer + "\n";
				
				textArea.append(soru + "\n" + "\n" + cevap + "\n");
				
			}
		});

		
		likeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				likeButton.setVisible(false);
				unlikeButton.setVisible(false);
				String newQuestion = question.getText();
				fileOperator.addQuestionToDatabase(newQuestion, answer);
				feedBackLabel.setText("'" + newQuestion + "'" + " sorusu veritabanına eklendi");
			}
		});

		unlikeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				likeButton.setVisible(false);
				unlikeButton.setVisible(false);
				String newQuestion  = question.getText();
				fileOperator.addNotAnsweredQuestionToDatabase(newQuestion, answer);
				feedBackLabel.setText(" Görüşünüz için teşekkürler. " + "'" + newQuestion + "'" + " sorusuna yakın zamanda daha iyi cevap bulunacaktır");
			}
		});

		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String soru = "Sorunuz: " + question.getText();

				ArrayList<Float> similarityArray = answerFinder.findSimilarityArray(question.getText());
				float maxSimilarity = answerFinder.findMaxSimilarity(question.getText());
				String closestQuestion = answerFinder.findClosestQuestion(question.getText());
				String closestAnswer = answerFinder.findClosestAnswer(question.getText());
				java.util.List<String> possibleQuestions = answerFinder.findPossibleQuestions(question.getText());

				System.out.println(soru);
				System.out.println("Benzerlik Oranları: " + similarityArray);
				System.out.println("En Yüksek Benzerlik Oranı: " + maxSimilarity);
				System.out.println("En Yakın Soru: " + closestQuestion);
				System.out.println("Benzer 5 Soru: " + possibleQuestions);
				System.out.println("Cevap: " + closestAnswer);
				
				answer = closestAnswer;
				String cevap = "Cevap: " + closestAnswer + "\n";

				textArea.append(soru + "\n" + "\n" + cevap + "\n");
				textArea.append("En benzer soru: " + closestQuestion + "\n");
				textArea.append("Benzerlik Oranı: " + maxSimilarity + "\n\n\n");
				
				feedBackLabel.setText("Cevaptan memnun Kaldınız mı?:");
				if(maxSimilarity<0.95) {
					feedBackLabel.setVisible(true);
					likeButton.setVisible(true);
					unlikeButton.setVisible(true);
					if(maxSimilarity<0.90) {
						possibleQuestion.removeAll();
						for(int i=0 ; i< possibleQuestions.size() ; i++) {
							possibleQuestion.add(possibleQuestions.get(i));
						}
						maybeLabel.setVisible(true);
						possibleQuestion.setVisible(true);
						possibleQuestionButton.setVisible(true);
					}
					else {
						maybeLabel.setVisible(false);
						possibleQuestion.setVisible(false);
						possibleQuestionButton.setVisible(false);
					}
				}
				else{
					feedBackLabel.setVisible(false);
					likeButton.setVisible(false);
					unlikeButton.setVisible(false);
					maybeLabel.setVisible(false);
					possibleQuestion.setVisible(false);
					possibleQuestionButton.setVisible(false);
				}

			}
		});
	
		controlPanel.add(methodLabel);
		controlPanel.add(choice);
		controlPanel.add(choiceButton);
		controlPanel.add(gapLabel);
		controlPanel.add(questionLabel);
		controlPanel.add(question);
		controlPanel.add(sendButton);
		controlPanel.add(scroll);
		controlPanel.add(maybeLabel);
		controlPanel.add(possibleQuestion);
		controlPanel.add(possibleQuestionButton);
		controlPanel.add(spaceLabel);
		controlPanel.add(feedBackLabel);
		controlPanel.add(likeButton);
		controlPanel.add(unlikeButton);
		
		mainFrame.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {

	}

	public static void main(String[] args) {
		Screen screen = new Screen();
		screen.showTextField();
	}

}
