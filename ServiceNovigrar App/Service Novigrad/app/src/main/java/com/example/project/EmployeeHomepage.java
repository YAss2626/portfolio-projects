package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class EmployeeHomepage extends AppCompatActivity {

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_homepage);
        Intent intent = getIntent();
        //Variables
        Button btnManageServices = findViewById(R.id.btnManageServices);
        Button btnManageBranchProfile = findViewById(R.id.btnManageBranchProfile);
        Button btnViewServiceRequests = findViewById(R.id.btnViewServiceRequests);
        email = intent.getStringExtra("EMAIL");
        //ouvre la page pour ajouter un service
        btnManageServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeHomepage.this, EmployeeManageServices.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
            }
        });

        //ouvre la page pour voir le profil de la branche
        btnManageBranchProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeHomepage.this, EmployeeBranchProfile.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
            }
        });

        btnViewServiceRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeHomepage.this, EmployeeManageRequests.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
            }
        });
    }
}