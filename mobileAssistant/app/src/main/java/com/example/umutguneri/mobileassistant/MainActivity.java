package com.example.umutguneri.mobileassistant;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public Button sendButton;
    public TextInputEditText textInputEditText;
    public TextView outputTextView, textLabel;
    public String question;
    public String closestAnswer;
    public SimilarityFinder answerFinder;
    public ArrayList<String> possibleQuestions;
    public FloatingActionButton fabOk, fabNotOk, fabPossibles;
    public FileOperator fileOperator;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = findViewById((R.id.sendButton));
        textInputEditText = findViewById((R.id.textInputEditText));
        textLabel= findViewById(R.id.textView);
        outputTextView = findViewById(R.id.outputTextView);
        outputTextView.setMovementMethod(new ScrollingMovementMethod());

        fabOk = findViewById(R.id.fabOk);
        fabNotOk = findViewById(R.id.fabNotOk);
        fabPossibles = findViewById(R.id.fabPossibles);

        this.answerFinder = new SimilarityFinder();
        this.possibleQuestions = new ArrayList<String>();

        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                question = String.valueOf(textInputEditText.getText());
                String soru = "Sorunuz: " + question;

                answerFinder.findSimilarityArray(question);
                fileOperator = new FileOperator();
                double maxSimilarity = answerFinder.findMaxSimilarity(question);
                String closestQuestion = answerFinder.findClosestQuestion(question);
                closestAnswer = answerFinder.findClosestAnswer(question);
                possibleQuestions = (ArrayList<String>) answerFinder.findPossibleQuestions(question);

                String cevap = "Cevap: " + closestAnswer + "\n";

                outputTextView.append(soru + "\n" + "\n" + cevap + "\n");
                outputTextView.append("En benzer soru: " + closestQuestion + "\n");
                outputTextView.append("Benzerlik Oranı: " + maxSimilarity + "\n\n\n");



                Toast.makeText(getApplicationContext(), closestQuestion, Toast.LENGTH_SHORT).show();
                if(maxSimilarity<0.90) {
                    fabOk.setVisibility(View.VISIBLE);
                    fabNotOk.setVisibility(View.VISIBLE);
                    textLabel.setVisibility(View.VISIBLE);
                    fabPossibles.setVisibility(View.VISIBLE);
                    /*
                    if(maxSimilarity<0.70) {
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
                    */
                }
                else{
                    fabOk.setVisibility(View.GONE);
                    fabNotOk.setVisibility(View.GONE);
                    textLabel.setVisibility(View.GONE);
                    fabPossibles.setVisibility(View.GONE);
                }

            }
        });

        fabOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileOperator.addQuestionToDatabase(question, closestAnswer);
                Toast.makeText(getApplicationContext(), "'" + question + "'" + " sorusu veritabanına eklendi", Toast.LENGTH_SHORT).show();
                fabOk.setVisibility(View.GONE);
                fabNotOk.setVisibility(View.GONE);
                textLabel.setVisibility(View.GONE);
            }
        });

        fabNotOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileOperator.addNotAnsweredQuestionToDatabase(question);
                Toast.makeText(getApplicationContext(),  "Görüşünüz için teşekkürler. " + "'" + question + "'" + " sorusuna yakın zamanda daha iyi cevap bulunacaktır", Toast.LENGTH_SHORT).show();
                fabOk.setVisibility(View.GONE);
                fabNotOk.setVisibility(View.GONE);
                textLabel.setVisibility(View.GONE);
            }
        });

        fabPossibles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sormak istediğiniz sorunun üzerine tıklayın!", Toast.LENGTH_SHORT).show();
                CharSequence[] possible = possibleQuestions.toArray(new CharSequence[possibleQuestions.size()]);
                AlertDialog.Builder builder;

                builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Sorunuza en benzer sorular:")
                        .setItems(possible, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), possible[which], Toast.LENGTH_SHORT).show();

                                String soru = "Sorunuz: " + possible[which];
                                String cevap = "Cevap: " + answerFinder.getAnswer(which) + "\n";
                                outputTextView.append(soru + "\n" + "\n" + cevap + "\n");

                            }

                        })
                        .setIcon(android.R.drawable.ic_menu_help)
                        .show();

            }
        });





    }






}
