package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserSearchBranchesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_branches);

        //Variables
        TextView welcomeMessage = findViewById(R.id.txtWelcome);
        Button btnSearchByAdresse = findViewById(R.id.btnSearchByAdresse);
        Button btnSearchByHours = findViewById(R.id.btnSearchByHours);
        Button btnSearchByServices = findViewById(R.id.btnSearchByServices);
        Button btnViewAllBranches = findViewById(R.id.btnViewAllBranches);

        btnSearchByAdresse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSearchBranchesActivity.this , UserSearchByAdresse.class);
                startActivity(intent);
            }
        });

        btnSearchByHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSearchBranchesActivity.this , UserSearchByHours.class);
                startActivity(intent);
            }
        });

        btnSearchByServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSearchBranchesActivity.this , UserSearchByService.class);
                startActivity(intent);
            }
        });

        btnViewAllBranches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSearchBranchesActivity.this , UserActivity.class);
                startActivity(intent);
            }
        });

        // displays welcome message with the user's name.
        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("name");
        welcomeMessage.setText("Welcome " + data);
    }
}