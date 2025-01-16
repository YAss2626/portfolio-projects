package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button btnLogin;
    TextView sign;
    TextView switchToSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Variables
        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputPassword = findViewById(R.id.inputPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView sign = findViewById(R.id.sign);
        TextView switchToSignup = findViewById(R.id.switchToSignup);
        RadioButton userRadioButton = findViewById(R.id.userRadioButton);
        RadioButton employeeRadioButton = findViewById(R.id.employeeRadioButton);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();

                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                //checks if all fields are filled out
                if(email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill out all required fields", Toast.LENGTH_SHORT).show();
                }
                //admin login
                else if (email.equals("admin") && password.equals("admin")) {
                    startActivity(new Intent(LoginActivity.this, AdminHomepage.class));
                    Toast.makeText(LoginActivity.this, "administrator login succesful", Toast.LENGTH_SHORT).show();
                }
                //Checks if neither or both types of accounts are selected
                else if (!userRadioButton.isChecked() && !employeeRadioButton.isChecked()) {
                    Toast.makeText(LoginActivity.this, "please select the kind of account you are logging in as", Toast.LENGTH_SHORT).show();
                    userRadioButton.setChecked(false);
                    employeeRadioButton.setChecked(false);
                }
                else if (userRadioButton.isChecked() && employeeRadioButton.isChecked()) {
                    Toast.makeText(LoginActivity.this, "Please select only one type of account", Toast.LENGTH_SHORT).show();
                }

                else {
                    if (userRadioButton.isChecked()) { // login for user accounts
                        databaseReference.child("User accounts/").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild(email)){
                                    String getPassword = snapshot.child(email).child("password").getValue(String.class);
                                    if (password.equals(getPassword)) { // if succesful, move to hompage with a welcome message
                                        Intent intent = new Intent(LoginActivity.this, UserSearchBranchesActivity.class);
                                        intent.putExtra("name", snapshot.child(email).child("firstName").getValue(String.class));
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this, "invalid email or password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else if (employeeRadioButton.isChecked()) { // login for employee accounts
                        databaseReference.child("Employee accounts/").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild(email)){
                                    String getPassword = snapshot.child(email).child("password").getValue(String.class);
                                    if (password.equals(getPassword)) { // if succesful, move to hompage with a welcome message
                                        Intent intent = new Intent(LoginActivity.this, EmployeeHomepage.class);
                                        intent.putExtra("EMAIL", email);
                                        intent.putExtra("name", snapshot.child(email).child("firstName").getValue(String.class));
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this, "invalid email or password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
        });

        // permet de switcher entre l'ecran de sign in et sign up
        switchToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

    }



}


