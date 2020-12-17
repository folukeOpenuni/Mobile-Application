package com.thehoneycombworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity2 extends AppCompatActivity {

    private DatabaseReference dbRef;
    public static final String EXTRA_TEXT = "com.thehoneycombworks.EXTRA_TEXT";

    TextView txtView;
    TextView questionNoteView;
    Button nextButton;
    Button previousButton;
    Button submitButton;
    Button addEvent;
    ArrayList<Question> questions = new ArrayList<>();

    private TextView answerEditText;
    private int dayOfMonth, month, year;
    ImageView datePickerCalender;

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
        datePickerCalender = findViewById(R.id.date_picker);
        addEvent = findViewById(R.id.addEvent);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventToCalender();
            }
        });

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
                if(questions.size() -1 == counter){
                    submitAnswers();
                }else{
                    counter++;
                }
                try {
                    loadQuestion();

                    //show submit button if there is no more question
                    if(questions.size() -1 == counter){
                        submitButton.setVisibility(View.VISIBLE);
                        nextButton.setText(R.string.submit);
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
                    loadQuestion();

                }catch (IndexOutOfBoundsException exception){
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
                    String type = questionSnapshot.child("type").getValue(String.class);

                    DataSnapshot dropOptionRef = questionSnapshot.child("dropDownOption");

                    if(dropOptionRef.exists()){
                        ArrayList<String> dropDownOption = new ArrayList<>();

                        for(Iterator<DataSnapshot> i = dropOptionRef.getChildren().iterator(); i.hasNext();){
                            dropDownOption.add(i.next().getValue(String.class));
                        }
                        if(dropDownOption != null){
                            question.setDropDownOption(dropDownOption);
                        }
                    }

                    if(type != null){
                        question.setType(type);
                    }

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

    private void submitAnswers() {
        String id = dbRef.push().getKey();
        //instantiate UserAnswers class object
        UserAnswers ans = new UserAnswers(id, answers.get(0), answers.get(1), answers.get(2), answers.get(3),
                answers.get(4), answers.get(5), answers.get(6), answers.get(7));

        dbRef = FirebaseDatabase.getInstance().getReference("answers");
        dbRef.child(id).setValue(ans);

        openActivity3();
    }

    private void loadQuestion(){
        txtView.setText(questions.get(counter).getQuestionText());
        questionNoteView.setText(questions.get(counter).getQuestionNote());
        answerEditText.setText(answers.get(counter));
        if(questions.get(counter).getType().toLowerCase().equals("date")){
            datePickerCalender.setVisibility(View.VISIBLE);
        }else{
            datePickerCalender.setVisibility(View.GONE);
        }
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
        dayOfMonth = day;
        this.month = month;
        this.year = year;
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (day_string + "/" + month_string + "/" + year_string);
        answerEditText.setText(dateMessage);
    }

    /**
     * open activity3 when submit button is clicked
     */
    public void openActivity3(){
        //get user's first name to pass to activity3
        String fName = answers.get(0);

        Intent intent = new Intent(this, Activity3.class);
        intent.putExtra(EXTRA_TEXT, fName);
        startActivity(intent); //open third activity
    }

    private void addEventToCalender(){
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year, month, dayOfMonth, 7, 30);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Yoga")
                .putExtra(CalendarContract.Events.DESCRIPTION, "Group class");
               // .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym")
                //.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

        startActivity(intent);
    }
}