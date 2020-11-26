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

    private static String q1, q2, q3, q3Note, q4, q5, q5Note, q6, q6Note, q7, q7Note, q8, q8Note;

    //To read or write data from the database, an instance of DatabaseReference is needed
    FirebaseDatabase dbInstance;

    private DatabaseReference dbRef;

    TextView txtView;
    TextView questionNoteView;
    Button nextButton;
    Button previousButton;
    ArrayList<Question> questions = new ArrayList<>();
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //createQuestions();


        txtView = findViewById(R.id.q_txt_view);
        questionNoteView = findViewById(R.id.q_txt_view2);
        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                try {
                    txtView.setText(questions.get(counter).getQuestionText());
                    questionNoteView.setText(questions.get(counter).getQuestionNote());
                }catch (IndexOutOfBoundsException exception){
                    exception.getMessage();
                }
            }
        });
        previousButton = (Button) findViewById(R.id.previous_button);



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

                    if(questionNote != null){
                        question.setQuestionNote(questionNote);
                    }
                    if(questionText != null){
                        question.setQuestionText(questionText);
                    }
                    if(question != null){
                        questions.add(question);
                    }

                }

            txtView.setText(questions.get(0).getQuestionText());
                txtView.setVisibility(View.VISIBLE);
            questionNoteView.setText(questions.get(0).getQuestionNote());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Lost: onCancelled", error.toException());
            }
        });


    }

    public void createQuestions(){
        q1 = "First Name";  q2 = "Last name";   q3 = "Email*";
        q3Note = "We will only use this information to send you reminders and follow ups to see how you got on";
        q4 = "What habit do you want to work on? Select from the drop down menu.";
        q5 = "What situations could I try this in? What opportunities to practice do I have? This question is required.*";
        q5Note = "What is the specific thing you'll do differently? The exact action you will take to make" +
                " a change By being as specific as possible and identifying those situations ahead of time, " +
                "you're more likely to commit to action";
        q6 = "When am I going to work on this habit?";
        q6Note = "Set aside a date in the next week when you know you'll have an opportunity" +
                "to work on it and add to your calendar to make sure you stick to your commitment";
        q7 = "I've planned what I'm going to do and when." +
                " What might stop me or get in my way? These could be practical things" +
                "(e.g important tasks) or other things (e.g. personal fears or concerns)This question is required. *";
        q7Note = "This step is really important -" +
                " by considering the things that could stop you, you'll be able to come up" +
                " with strategies to make sure they don't";
        q8 = "What can I do to make sure these things don't stop me?" +
                " What can I do now or what can I tell myself to make sure I go ahead?This question is required. *";
        q8Note = "People who plan how to deal with the things that could " +
                " stop them are much more likely to stick to their commitments";

        //instantiate question class
        Questions questions = new Questions(q1, q2, q3, q3Note, q4, q5, q5Note, q6, q6Note, q7, q7Note, q8, q8Note);

        //get firebase instance
         dbInstance = FirebaseDatabase.getInstance();

        //get Reference to a question node, created new node if non exist.
        DatabaseReference questionNode = dbInstance.getReference("Questions");

        //write question class object to reference
        questionNode.setValue(questions);

        //dbRef.child("Questions").child(questionId).setValue(questions);
    }

    public void getAllQuestions(){
        //get database reference
        dbRef = FirebaseDatabase.getInstance().getReference().child("questions");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get string value of Questions node
                snapshot.getValue();
                for (DataSnapshot questionSnapshot : snapshot.getChildren()) {
                    System.out.println("");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Lost: onCancelled", error.toException());
            }
        });

    }
}