package com.thehoneycombworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    private DatabaseReference dbRef;

    TextView txtView;
    TextView questionNoteView;
    Button nextButton;
    Button previousButton;
    Button submitButton;
    ArrayList<Question> questions = new ArrayList<>();

    private TextView answerEditText;
    private int dayOfMonth, month, year;
    //ImageView datePickerCalender;

    //Hashmap to store users answers
    private HashMap<Integer, String> answers = new HashMap<>();

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        txtView = findViewById(R.id.question_txt_view);
        answerEditText = findViewById(R.id.editTxt_view);
        questionNoteView = findViewById(R.id.question_note_view);
        //datePickerCalender = findViewById(R.id.date_picker);

        submitButton = (Button) findViewById(R.id.submit_btn);
        submitButton.setVisibility(View.INVISIBLE);

        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if user provide answer before pressing the next button
//                if(!TextUtils.isEmpty(answerEditText.getText().toString())){
//                }

                //Gets the answer for the question that was just on screen
                answers.put(counter, answerEditText.getText().toString());

                //Clear EditText ready for the next answer
                answerEditText.setText("");
                counter++;
                try {
                    txtView.setText(questions.get(counter).getQuestionText());
                    questionNoteView.setText(questions.get(counter).getQuestionNote());

                    Log.d("Question is: ", questions.get(counter).getQuestionText());

                    //show submit button if there is no more question
                    if(counter == 7){
                        submitButton.setVisibility(View.VISIBLE);
                        //nextButton.setVisibility(View.INVISIBLE);
                    }
                }catch (IndexOutOfBoundsException exception){
                    exception.getMessage();
                }
            }
        });

        previousButton = (Button) findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gets the answer for the question that was just on screen
                answers.put(counter, answerEditText.getText().toString());

                counter--;
                try {
                    txtView.setText(questions.get(counter).getQuestionText());
                    questionNoteView.setText(questions.get(counter).getQuestionNote());
                    answerEditText.setText(answers.get(counter));

                }catch (IndexOutOfBoundsException exception){
                    exception.getMessage();
                }
            }
        });

//        datePickerCalender.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar c = Calendar.getInstance();
//                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
//                month = c.get(Calendar.MONTH);
//                year = c.get(Calendar.YEAR);
//
//            }
//        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create unique id to be use as primary key
                String id = dbRef.push().getKey();
                //instantiate UserAnswers class object
                UserAnswers ans = new UserAnswers(id, answers.get(0), answers.get(1), answers.get(2), answers.get(3),
                        answers.get(4), answers.get(5), answers.get(6), answers.get(7));

                dbRef = FirebaseDatabase.getInstance().getReference("answers");
                dbRef.child(id).setValue(ans);
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

    /**
     * Handles the button click to create a new date picker fragment and
     * show it.
     *
     * @param view View that was clicked
     */
    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),
                getString(R.string.datepicker));
    }

    /**
     * Process the date picker result into strings that can be pass to db
     * @param year Chosen year
     * @param month Chosen month
     * @param day Chosen day
     */
    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (day_string + "/" + month_string + "/" + year_string);
        answerEditText.setText(dateMessage);
    }

}