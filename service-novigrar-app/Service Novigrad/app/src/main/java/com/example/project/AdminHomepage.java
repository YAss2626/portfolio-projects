package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;

public class AdminHomepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);


        // Variables
        Button btnManageServices = findViewById(R.id.btnManageServices);
        Button btnManageUsers = findViewById(R.id.btnManageUsers);
        Button btnManageBranches = findViewById(R.id.btnManageBranches);

        //ouvre la page pour gerer les services
        btnManageServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomepage.this, AdminManageServices.class));
            }
        });

        //ouvre la page pour gerer les utilisateurs
        btnManageUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomepage.this, AdminManageUsers.class));
            }
        });

        //ouvre la page pour gerer les succursales/employees
        btnManageBranches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomepage.this, AdminManageBranches.class));
            }
        });
    }
}