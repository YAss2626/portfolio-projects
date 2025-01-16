package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmployeeManageBranchProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_manage_branch_profile);

        //Variables
        TextView txtBranchName = findViewById(R.id.txtBranchName);
        EditText txtBranchAdresseField = findViewById(R.id.txtBranchAdresseField);
        EditText txtBranchPhoneNumberField = findViewById(R.id.txtBranchPhoneNumberField);
        EditText txtMondayOpenHours = findViewById(R.id.txtMondayOpenHoursField);
        EditText txtTuesdayOpenHours = findViewById(R.id.txtTuesdayOpenHoursField);
        EditText txtWednesdayOpenHours = findViewById(R.id.txtWednesdayOpenHoursField);
        EditText txtThursdayOpenHours = findViewById(R.id.txtThursdayOpenHoursField);
        EditText txtFridayOpenHours = findViewById(R.id.txtFridayOpenHoursField);
        EditText txtSaturdayOpenHours = findViewById(R.id.txtSaturdayOpenHoursField);
        EditText txtSundayOpenHours = findViewById(R.id.txtSundayOpenHoursField);
        EditText txtMondayCloseHours = findViewById(R.id.txtMondayCloseHoursField);
        EditText txtTuesdayCloseHours = findViewById(R.id.txtTuesdayCloseHoursField);
        EditText txtWednesdayCloseHours = findViewById(R.id.txtWednesdayCloseHoursField);
        EditText txtThursdayCloseHours = findViewById(R.id.txtThursdayCloseHoursField);
        EditText txtFridayCloseHours = findViewById(R.id.txtFridayCloseHoursField);
        EditText txtSaturdayCloseHours = findViewById(R.id.txtSaturdayCloseHoursField);
        EditText txtSundayCloseHours = findViewById(R.id.txtSundayCloseHoursField);
        Button btnUpdateInfo = findViewById(R.id.btnUpdateInfo);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        String branchName = getIntent().getStringExtra("EMAIL");
        txtBranchName.setText(branchName);

        String branchAdresse = txtBranchAdresseField.getText().toString();
        String branchPhoneNumber = txtBranchPhoneNumberField.getText().toString();
        String mondayOpenHours = txtMondayOpenHours.getText().toString();
        String tuesdayOpenHours = txtTuesdayOpenHours.getText().toString();
        String wednesdayOpenHours = txtWednesdayOpenHours.getText().toString();
        String thursdayOpenHours = txtThursdayOpenHours.getText().toString();
        String fridayOpenHours = txtFridayOpenHours.getText().toString();
        String saturdayOpenHours = txtSaturdayOpenHours.getText().toString();
        String sundayOpenHours = txtSundayOpenHours.getText().toString();
        String mondayCloseHours = txtMondayCloseHours.getText().toString();
        String tuesdayCloseHours = txtTuesdayCloseHours.getText().toString();
        String wednesdayCloseHours = txtWednesdayCloseHours.getText().toString();
        String thursdayCloseHours = txtThursdayCloseHours.getText().toString();
        String fridayCloseHours = txtFridayCloseHours.getText().toString();
        String saturdayCloseHours = txtSaturdayCloseHours.getText().toString();
        String sundayCloseHours = txtSundayCloseHours.getText().toString();

        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = firebaseDatabase.getReference("User accounts/");
                databaseReference.child(branchName).setValue(branchAdresse);
                databaseReference.child(branchName).setValue(branchPhoneNumber);
                databaseReference.child(branchName).setValue(mondayOpenHours);
                databaseReference.child(branchName).setValue(tuesdayOpenHours);
                databaseReference.child(branchName).setValue(wednesdayOpenHours);
                databaseReference.child(branchName).setValue(thursdayOpenHours);
                databaseReference.child(branchName).setValue(fridayOpenHours);
                databaseReference.child(branchName).setValue(saturdayOpenHours);
                databaseReference.child(branchName).setValue(sundayOpenHours);
                databaseReference.child(branchName).setValue(mondayCloseHours);
                databaseReference.child(branchName).setValue(tuesdayCloseHours);
                databaseReference.child(branchName).setValue(wednesdayCloseHours);
                databaseReference.child(branchName).setValue(thursdayCloseHours);
                databaseReference.child(branchName).setValue(fridayCloseHours);
                databaseReference.child(branchName).setValue(saturdayCloseHours);
                databaseReference.child(branchName).setValue(sundayCloseHours);

                Toast.makeText(EmployeeManageBranchProfile.this, "Details updated successfully", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }
}