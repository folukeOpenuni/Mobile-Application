package com.example.challengetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRegisterClick(View view) {
        TextView txtFirstName = findViewById(R.id.txtFirstName);
        EditText editTxtFName = findViewById(R.id.editTxtFname);
        txtFirstName.setText("First Name: " + editTxtFName.getText().toString());

        TextView txtLastName = findViewById(R.id.txtLastName);
        EditText editTxtLastName = findViewById(R.id.editTxtSurName);
        txtLastName.setText("Last Name: " + editTxtLastName.getText().toString());

        TextView txtEmail = findViewById(R.id.txtEmail);
        EditText editTxtEmail = findViewById(R.id.editTxtEmail);
        txtEmail.setText("Email " + editTxtEmail.getText().toString());
    }
}