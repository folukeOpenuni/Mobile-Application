package com.thehoneycombworks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Activity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        Intent intent = getIntent();
        String foreNameText = intent.getStringExtra(MainActivity2.EXTRA_TEXT);

        TextView txtView = findViewById(R.id.message);
        TextView secondTxtView = findViewById(R.id.extra_message);
        txtView.setText("Thanks " + foreNameText);
        secondTxtView.setText(R.string.message);
    }
}