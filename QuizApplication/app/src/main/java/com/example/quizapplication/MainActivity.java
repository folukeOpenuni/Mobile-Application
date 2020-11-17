package com.example.quizapplication;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity  extends Activity {

    Button trueButton;
    Button falseButton;
    Button helpButton;
    Button aboutButton;
    Button nextButton;
    Button backButton;
    private int currentIndex = 0;
    private Answer[] questionBank = new Answer [] {
            new Answer (R.string.question_1, true),
            new Answer (R.string.question_2, false),
            new Answer (R.string.question_3, false),
            new Answer (R.string.question_4, true),
            new Answer (R.string.question_5, true)
    };

    protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
        //TextView textView = (TextView) findViewById(R.id.question_text_view);
        final TextView txtQuestion = findViewById(R.id.question_text_view);
        txtQuestion.setText(questionBank[currentIndex].getQuestion());

    //we want to watch if there any clicks on any of these buttons and respond accordingly..
    trueButton = (Button) findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            checkAnswer(true);
        }
    });

    falseButton = (Button) findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkAnswer(false);
        }
    });

    helpButton = (Button) findViewById(R.id.help_button);
        helpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(MainActivity.this, "Help is here", Toast.LENGTH_SHORT).show();
            }
        });

    aboutButton = (Button) findViewById(R.id.about_button);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Design by Foluke Agbede", Toast.LENGTH_SHORT).show();
            }
        });

    nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % questionBank.length;
                int question = questionBank[currentIndex].getQuestion();
                txtQuestion.setText(question);
            }
        });

    backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = questionBank.length;
                currentIndex = ((currentIndex + (length -1) )% length);
                int question = questionBank[currentIndex].getQuestion();
                txtQuestion.setText(question);
            }
        });

    }

    public void checkAnswer (boolean usersAnswer){
        //first find out what is the answer to this question
        boolean answer = questionBank[currentIndex].isAnswer();
        //if users choice matches the answer
        if(usersAnswer == answer){
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

}
