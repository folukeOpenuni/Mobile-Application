//MainActivity.java

package com.example.quizapplication;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button trueButton;
    Button falseButton;

    Button nextButton;
    Button backButton;

    Button helpButton;
    Button aboutButton;

    TextView textView;
    /*private Answer[] questionBank = new Answer [] {
    new Answer (R.string.question_1, true),
    new Answer (R.string.question_2, false),
    new Answer (R.string.question_3, false),
    new Answer (R.string.question_4, true),
    new Answer (R.string.question_5, true),
    new Answer (R.string.question_6, true),

    };
    */
    private int currentIndex = 0;
    private String SAVED_INDEX = "0";
    private DatabaseOpenHelper db;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseOpenHelper(this);
        db.insertQuestions("Is Cat an Animal?", "true");
        db.insertQuestions("Is Cow an Animal?", "true");
        db.insertQuestions("Is Robot an Animal?", "false");
        db.insertQuestions("Is Wall an Animal?", "false");

        final int question_bank_size = db.getQuestionsCount();

        textView = (TextView) findViewById(R.id.question_text_view);
        if (savedInstanceState != null) {
            int temp_index = savedInstanceState.getInt(SAVED_INDEX);
            String question = db.getQuestion(temp_index).getQuestion();
            textView.setText(question);

        } else {
            String question = db.getQuestion(currentIndex).getQuestion();
            if (question != null) {
                textView.setText(question);

            }
        }


//we want to watch if there any clicks on any of these buttons and respond accordingly..
        trueButton = (Button) findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
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


        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % question_bank_size;
                String question = db.getQuestion(currentIndex).getQuestion();
                textView.setText(question);
            }
        });

        backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + (question_bank_size - 1)) % question_bank_size;
                String question = db.getQuestion(currentIndex).getQuestion();
                textView.setText(question);
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


    }


    private void checkAnswer(boolean userChoice) {

//first find out what is the answer to this question
        Answer ans = db.getQuestion(currentIndex);

//if users choice matches the answer
        if (ans.getAnswer().equalsIgnoreCase(Boolean.toString(userChoice))) {
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INDEX, currentIndex);
    }


}


