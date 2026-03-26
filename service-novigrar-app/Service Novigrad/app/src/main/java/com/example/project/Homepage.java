package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Homepage extends AppCompatActivity {

    TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            // displays welcome message with the user's name.
            Bundle bundle = getIntent().getExtras();
            String data = bundle.getString("name");
            TextView welcomeMessage = findViewById(R.id.welcomeMessage);
            welcomeMessage.setText("Welcome " + data);
    }
}