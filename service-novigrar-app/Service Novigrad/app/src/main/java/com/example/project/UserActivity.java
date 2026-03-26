package com.example.project;

// UserActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    private ArrayList<EmployeeAccount> employeeAccounts = new ArrayList<>();
    private ArrayList<String> employeeNames = new ArrayList<>();  // To store only names
    private ArrayAdapter<String> employeeAdapter;  // Modified adapter to display names

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Assuming you have a ListView in your layout with the id "listViewEmployees"
        ListView listViewEmployees = findViewById(R.id.listViewEmployees);

        // Adapter for displaying employee names
        employeeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employeeNames);
        listViewEmployees.setAdapter(employeeAdapter);


        // Set up click listener for selecting an employee
        listViewEmployees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch activity to perform actions with the selected employee
                EmployeeAccount selectedEmployee = employeeAccounts.get(position);
                Intent intent = new Intent(UserActivity.this, EmployeeServicesActivity.class);
                intent.putExtra("EMPLOYEE_EMAIL", selectedEmployee.getEmail());
                startActivity(intent);
            }
        });

        // Retrieve employee accounts from Firebase
        DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference("Employee accounts/");
        employeeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                employeeAccounts.clear();
                employeeNames.clear();  // Clear previous names
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EmployeeAccount employee = snapshot.getValue(EmployeeAccount.class);
                    if (employee != null) {
                        employeeAccounts.add(employee);
                        employeeNames.add(employee.getFirstName());
                    }
                }
                // Notify the adapter
                employeeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


