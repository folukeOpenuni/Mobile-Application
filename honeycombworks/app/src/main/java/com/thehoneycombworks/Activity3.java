package com.thehoneycombworks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class Activity3 extends AppCompatActivity {

    Button addEvent;
    private int dayOfMonth, month, year;
    String habitToWorkOn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        Intent intent = getIntent();
        String foreNameText = intent.getStringExtra(MainActivity2.EXTRA_TEXT);
        String mes = "Thanks" + " " + foreNameText;
        habitToWorkOn = getIntent().getStringExtra("keyName");

        TextView txtView = findViewById(R.id.message);
        TextView secondTxtView = findViewById(R.id.extra_message);
        txtView.setText(mes);
        secondTxtView.setText(R.string.message);

        addEvent = findViewById(R.id.addEvent);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventToCalender();
            }
        });
    }

    private void addEventToCalender() {
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year, month, dayOfMonth, 7, 30);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Habit to Work on")
                .putExtra(CalendarContract.Events.DESCRIPTION, habitToWorkOn);

        startActivity(intent);
    }
}