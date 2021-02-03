package com.thehoneycombworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {

    private DatabaseReference dbRef;
    public static final String EXTRA_TEXT = "com.thehoneycombworks.EXTRA_TEXT";

    TextView txtView;
    TextView questionNoteView;
    TextView progPercentage;
    Button nextButton;
    Button previousButton;
    Button trackMyLocation;
    Spinner spinner;
    ProgressBar progressBar;

    //Button submitButton;
    ArrayList<Question> questions = new ArrayList<>();
    ArrayAdapter<String> adapter;

    private TextView answerEditText;
    private int dayOfMonth, month, year;

    private FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_LOCATION_PERMISSION = 1; //use for request code.


    ImageView datePickerCalender;

    //Hashmap to store users answers
    private HashMap<Integer, String> answers = new HashMap<>();

    private int counter = 0;
    private double prog = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtView = findViewById(R.id.question_txt_view);
        progPercentage = findViewById(R.id.progressPercentage);
        answerEditText = findViewById(R.id.editTxt_view);
        questionNoteView = findViewById(R.id.question_note_view);
        datePickerCalender = findViewById(R.id.date_picker);
        spinner = (Spinner) findViewById(R.id.dropdown);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //progressBar.setProgress(counter);

        // Initialize the FusedLocationClient.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        //initialize and Set listener for trackMyLocation button.
        trackMyLocation = findViewById(R.id.trackLocation);
        trackMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        //submitButton = (Button) findViewById(R.id.submit_btn);
        //submitButton.setVisibility(View.INVISIBLE);

        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if user provide answer before pressing the next button
                if(!TextUtils.isEmpty(answerEditText.getText().toString())) {


                    progressBar.incrementProgressBy(12);

                    if (prog <= 100) {
                        prog += 12;
                    }

                    progPercentage.setText(String.format("%s%s", prog, getString(R.string.percent)));
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItem = spinner.getSelectedItem().toString();
                            answerEditText.setText(selectedItem);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            //answerEditText.setHint(R.string.answerHint);
                            //answerEditText.setText("");
                        }
                    });

                    //Gets the answer for the question that was just on screen
                    answers.put(counter, answerEditText.getText().toString());

                    //Clear EditText ready for the next answer
                    answerEditText.setText("");
                    if (questions.size() - 1 == counter) {
                        //if it's the last question and user click submit call submitAnswer method.
                        submitAnswers();
                    } else {
                        counter++;
                    }
                    try {
                        loadQuestion();

                        //change next button to submit button if there is no more question
                        if (questions.size() - 1 == counter) {
                            //submitButton.setVisibility(View.VISIBLE);
                            nextButton.setText(R.string.submit);
                        } else {
                            nextButton.setText(R.string.next);
                        }
                    } catch (IndexOutOfBoundsException exception) {
                        exception.getMessage();
                    }
                }else if(TextUtils.isEmpty(answerEditText.getText().toString())){
                    answerEditText.setError("answer required");
                    answerEditText.requestFocus();
                }
            }
        });

        previousButton = (Button) findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gets the answer for the question that was just on screen
                answers.put(counter, answerEditText.getText().toString());

                progressBar.incrementProgressBy(-12);
                if (prog >= 10) {
                    prog -= 12;
                }
                progPercentage.setText(String.format("%s%s", prog, getString(R.string.percent)));


                counter--;
                try {
                    loadQuestion();

                } catch (IndexOutOfBoundsException exception) {
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

                    if (dropOptionRef.exists()) {
                        ArrayList<String> dropDownOption = new ArrayList<>();

                        for (Iterator<DataSnapshot> i = dropOptionRef.getChildren().iterator(); i.hasNext(); ) {
                            dropDownOption.add(i.next().getValue(String.class));
                        }
                        if (dropDownOption != null) {
                            question.setDropDownOption(dropDownOption);
                        }
                    }

                    if (type != null) {
                        question.setType(type);
                    }

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

                txtView.setText(questions.get(0).getQuestionText());
                txtView.setVisibility(View.VISIBLE);
                questionNoteView.setText(questions.get(0).getQuestionNote());
                adapter = new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_spinner_dropdown_item,
                        questions.get(0).getDropDownOption());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Lost: onCancelled", error.toException());
            }
        });
    }

    /**
     * get all answers/input and store it in google firebase
     * then open activity3
     */
    private void submitAnswers() {
        String id = dbRef.push().getKey();
        //instantiate UserAnswers class object
        UserAnswers ans = new UserAnswers(id, answers.get(0), answers.get(1), answers.get(2), answers.get(3),
                answers.get(4), answers.get(5), answers.get(6), answers.get(7), answers.get(8));

        dbRef = FirebaseDatabase.getInstance().getReference("answers");
        dbRef.child(id).setValue(ans).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity2.this, "Data saved", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity2.this, "Something went wrong please retry", Toast.LENGTH_LONG).show();
                    //Log the error message
                    //Log.e(tag, "onComplete: ERROR: " + task.getException().getLocalizedMessage() );
                }
            }
        });

        openActivity3();
    }

    /**
     * load question text and question note to view
     * make date picker visible with the right question
     */
    private void loadQuestion() {
        txtView.setText(questions.get(counter).getQuestionText());
        questionNoteView.setText(questions.get(counter).getQuestionNote());
        answerEditText.setText(answers.get(counter));
        adapter = new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_spinner_dropdown_item,
                questions.get(counter).getDropDownOption());
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        if (questions.get(counter).getType().toLowerCase().equals("date")) {//if question relate to date
            datePickerCalender.setVisibility(View.VISIBLE); //show calender image for date picker
            answerEditText.setHint(R.string.calenderHint);
        } else {
            datePickerCalender.setVisibility(View.GONE);
            answerEditText.setHint(R.string.answerHint);
        }

        if(questions.get(counter).getType().toLowerCase().equals("geolocation")){
            trackMyLocation.setVisibility(View.VISIBLE);
            answerEditText.setHint(R.string.geoHint);
        }else{
            trackMyLocation.setVisibility(View.GONE);
        }

        if(questions.get(counter).getType().toLowerCase().equals("dropdown")){
            spinner.setVisibility(View.VISIBLE);
        }else {
            spinner.setVisibility(View.GONE);
        }

        if(questions.get(counter).getType().toLowerCase().equals("email")){
            if(!TextUtils.isEmpty(answerEditText.getText().toString()) &&
                    !Patterns.EMAIL_ADDRESS.matcher(answerEditText.getText().toString()).matches()){
            answerEditText.setError("Please enter a valid email");
            answerEditText.requestFocus();
            //nextButton.setEnabled(false);
            }
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
    public void openActivity3() {
        //get user's first name to pass to activity3
        String fName = answers.get(0);
        //get user's habit to be worked on
        String habitToWork = answers.get(3);

        Intent intent = new Intent(this, Activity3.class);
        intent.putExtra(EXTRA_TEXT, fName);
        //intent.putExtra(EXTRA_TEXT, habitToWork);
        intent.putExtra("keyName", habitToWork);
        startActivity(intent); //open third activity
    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //REQUEST_LOCATION_PERMISSION
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }else{
            mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if(location != null){
                        //Create a Geocoder object
                        Geocoder geocoder = new Geocoder(MainActivity2.this, Locale.getDefault());
                        //create address list
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            answerEditText.setText(addresses.get(0).getAddressLine(0));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }


}