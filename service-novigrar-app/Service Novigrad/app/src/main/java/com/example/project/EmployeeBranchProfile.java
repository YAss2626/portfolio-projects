package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeBranchProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_branch_profile);

        //Variables
        TextView txtBranchName = findViewById(R.id.txtBranchName);
        TextView txtBranchAdresseDisplay = findViewById(R.id.txtBranchAdresseDisplay);
        TextView txtBranchPhoneNumberDisplay = findViewById(R.id.txtBranchPhoneNumberDisplay);
        TextView txtMondayOpenHoursDisplay = findViewById(R.id.txtMondayOpenHoursDisplay);
        TextView txtTuesdayOpenHoursDisplay = findViewById(R.id.txtTuesdayOpenHoursDisplay);
        TextView txtWednesdayOpenHoursDisplay = findViewById(R.id.txtWednesdayOpenHoursDisplay);
        TextView txtThursdayOpenHoursDisplay = findViewById(R.id.txtThursdayOpenHoursDisplay);
        TextView txtFridayOpenHoursDisplay = findViewById(R.id.txtFridayOpenHoursDisplay);
        TextView txtSaturdayOpenHoursDisplay = findViewById(R.id.txtSaturdayOpenHoursDisplay);
        TextView txtSundayOpenHoursDisplay = findViewById(R.id.txtSundayOpenHoursDisplay);
        TextView txtMondayCloseHoursDisplay = findViewById(R.id.txtMondayCloseHoursDisplay);
        TextView txtTuesdayCloseHoursDisplay = findViewById(R.id.txtTuesdayCloseHoursDisplay);
        TextView txtWednesdayCloseHoursDisplay = findViewById(R.id.txtWednesdayCloseHoursDisplay);
        TextView txtThursdayCloseHoursDisplay = findViewById(R.id.txtThursdayCloseHoursDisplay);
        TextView txtFridayCloseHoursDisplay = findViewById(R.id.txtFridayCloseHoursDisplay);
        TextView txtSaturdayCloseHoursDisplay = findViewById(R.id.txtSaturdayCloseHoursDisplay);
        TextView txtSundayCloseHoursDisplay = findViewById(R.id.txtSundayCloseHoursDisplay);
        Button btnReturnToEmployeeHomepage = findViewById(R.id.btnReturnToHomepage);
        Button btnEditBranchProfile = findViewById(R.id.btnEditBranchProfile);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        //gets and displays the name of the branch
        String branchName = getIntent().getStringExtra("EMAIL");
        txtBranchName.setText(branchName);


        //move to page to edit branch profile
        btnEditBranchProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeBranchProfile.this, EmployeeManageBranchProfile.class);
                intent.putExtra("EMAIL", branchName);
                startActivity(intent);
            }
        });

        //return to employee homepage
        btnReturnToEmployeeHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //retrieve branch information to display
                    String branchAdresse = snapshot.child("/Employee accounts").child("EMAIL").child("adresse").getValue(String.class);
                    String branchPhoneNumber = snapshot.child("/Employee accounts").child("EMAIL").child("phoneNumber").getValue(String.class);
                    String mondayOpenHours = snapshot.child("/Employee accounts").child("EMAIL").child("mondayHours").getValue(String.class);
                    String tuesdayOpenHours = snapshot.child("/Employee accounts").child("EMAIL").child("tuesdayHours").getValue(String.class);
                    String wednesdayOpenHours = snapshot.child("/Employee accounts").child("EMAIL").child("wednesdayHours").getValue(String.class);
                    String thursdayOpenHours = snapshot.child("/Employee accounts").child("EMAIL").child("thursdayHours").getValue(String.class);
                    String fridayOpenHours = snapshot.child("/Employee accounts").child("EMAIL").child("fridayHours").getValue(String.class);
                    String saturdayOpenHours = snapshot.child("/Employee accounts").child("EMAIL").child("saturdayHours").getValue(String.class);
                    String sundayOpenHours = snapshot.child("/Employee accounts").child("EMAIL").child("sundayHours").getValue(String.class);
                    String mondayCloseHours = snapshot.child("/Employee accounts").child("EMAIL").child("mondayHours").getValue(String.class);
                    String tuesdayCloseHours = snapshot.child("/Employee accounts").child("EMAIL").child("tuesdayHours").getValue(String.class);
                    String wednesdayCloseHours = snapshot.child("/Employee accounts").child("EMAIL").child("wednesdayHours").getValue(String.class);
                    String thursdayCloseHours = snapshot.child("/Employee accounts").child("EMAIL").child("thursdayHours").getValue(String.class);
                    String fridayCloseHours = snapshot.child("/Employee accounts").child("EMAIL").child("fridayHours").getValue(String.class);
                    String saturdayCloseHours = snapshot.child("/Employee accounts").child("EMAIL").child("saturdayHours").getValue(String.class);
                    String sundayCloseHours = snapshot.child("/Employee accounts").child("EMAIL").child("sundayHours").getValue(String.class);

                    //display branch information
                    txtBranchAdresseDisplay.setText(branchAdresse);
                    txtBranchPhoneNumberDisplay.setText(branchPhoneNumber);
                    txtMondayOpenHoursDisplay.setText(mondayOpenHours);
                    txtTuesdayOpenHoursDisplay.setText(tuesdayOpenHours);
                    txtWednesdayOpenHoursDisplay.setText(wednesdayOpenHours);
                    txtThursdayOpenHoursDisplay.setText(thursdayOpenHours);
                    txtFridayOpenHoursDisplay.setText(fridayOpenHours);
                    txtSaturdayOpenHoursDisplay.setText(saturdayOpenHours);
                    txtSundayOpenHoursDisplay.setText(sundayOpenHours);
                    txtMondayCloseHoursDisplay.setText(mondayCloseHours);
                    txtTuesdayCloseHoursDisplay.setText(tuesdayCloseHours);
                    txtWednesdayCloseHoursDisplay.setText(wednesdayCloseHours);
                    txtThursdayCloseHoursDisplay.setText(thursdayCloseHours);
                    txtFridayCloseHoursDisplay.setText(fridayCloseHours);
                    txtSaturdayCloseHoursDisplay.setText(saturdayCloseHours);
                    txtSundayCloseHoursDisplay.setText(sundayCloseHours);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }
}