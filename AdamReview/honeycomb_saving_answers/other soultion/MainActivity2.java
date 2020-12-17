package com.thehoneycombworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private DatabaseReference dbRef;

    TextView txtView;
    TextView questionNoteView;
    Button nextButton;
    Button previousButton;

    private ArrayList<Question> questions = new ArrayList<>();

    private TextView answerEditText;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtView = findViewById(R.id.q_txt_view);
        answerEditText = findViewById(R.id.editTxt_view);
        questionNoteView = findViewById(R.id.q_txt_view2);
        nextButton = findViewById(R.id.next_button);
        previousButton = findViewById(R.id.previous_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Gets the answer for the question that was just on screen
                    questions.get(counter).setAnswer(answerEditText.getText().toString());

                    counter++;

                    displayCurrentQuestion(questions.get(counter));
                } catch (IndexOutOfBoundsException | NullPointerException exception) {
                    exception.getMessage();
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Gets the answer for the question that was just on screen
                    questions.get(counter).setAnswer(answerEditText.getText().toString());

                    counter--;
                    displayCurrentQuestion(questions.get(counter));

                } catch (IndexOutOfBoundsException | NullPointerException exception) {
                    exception.getMessage();
                }
            }
        });


        //get database reference
        dbRef = FirebaseDatabase.getInstance().getReference().child("questions");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get string value of Questions node
                snapshot.getValue();
                for (DataSnapshot questionSnapshot : snapshot.getChildren()) {
                    Question question = new Question();
                    String questionNote = questionSnapshot.child("questionNote").getValue(String.class);
                    String questionText = questionSnapshot.child("questionText").getValue(String.class);

                    if (questionNote != null) {
                        question.setQuestionNote(questionNote);
                    }
                    if (questionText != null) {
                        question.setQuestionText(questionText);
                    }
                    if (question != null) {
                        questions.add(question);
                    }

                }

                displayCurrentQuestion(questions.get(0));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Lost: onCancelled", error.toException());
            }
        });
    }


    private void displayCurrentQuestion(Question question) {
        txtView.setText(question.getQuestionText());
        questionNoteView.setText(question.getQuestionNote());

        answerEditText.setText(question.getAnswer());
    }
}


Q