package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    EditText inputFirstName;
    EditText inputLastName;
    EditText inputEmail;
    EditText inputPassword;
    EditText inputPasswordConfirm;
    Button btnCreateAccount;
    TextView sign;
    TextView switchToSignin;
    RadioButton userRadioButton;
    RadioButton employeeRadioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Variables
        inputFirstName = (EditText) findViewById(R.id.inputFirstName);
        inputLastName = (EditText) findViewById(R.id.inputLastName);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        inputPasswordConfirm = (EditText) findViewById(R.id.inputPasswordConfirm);
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        sign = (TextView) findViewById(R.id.sign);
        switchToSignin = (TextView) findViewById(R.id.switchToSignin);
        userRadioButton = (RadioButton) findViewById(R.id.userRadioButton);
        employeeRadioButton = (RadioButton) findViewById(R.id.employeeRadioButton);


        // Method to Create Account
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();


                String firstName = inputFirstName.getText().toString();
                String lastName = inputLastName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String passwordConfirm = inputPasswordConfirm.getText().toString();

                //Checks if all fields are filled out
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill out all required fields", Toast.LENGTH_SHORT).show();

                }
                //Checks if both passwords match
                else if (!password.equals(passwordConfirm)){
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();

                }
                //Checks if neither or both types of accounts are selected
                else if (!userRadioButton.isChecked() && !employeeRadioButton.isChecked()){
                    Toast.makeText(SignupActivity.this, "please select the kind of account you are creating", Toast.LENGTH_SHORT).show();

                }
                else if (userRadioButton.isChecked() && employeeRadioButton.isChecked()) {
                    Toast.makeText(SignupActivity.this, "Please select only one type of account", Toast.LENGTH_SHORT).show();

                }
                else {
                    //Creates account based on the selected type
                    if (userRadioButton.isChecked()) {
                        DatabaseReference newUserAccount = firebaseDatabase.getReference("User accounts/");
                        UserAccount newUser = new UserAccount(firstName, lastName, email, password);
                        newUserAccount.child(email).setValue(newUser);

                    } else if (employeeRadioButton.isChecked()) {
                        DatabaseReference newEmployeeAccount = firebaseDatabase.getReference("Employee accounts/");
                        EmployeeAccount newEmployee = new EmployeeAccount(firstName, lastName, email, password, new ArrayList<Service>());
                        System.out.println(newEmployee.toMap());
                        newEmployeeAccount.child(email).setValue(newEmployee.toMap());
                    }
                    Toast.makeText(SignupActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


        // permet de switcher entre l'ecran de sign in et sign up
        switchToSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });


    }
}