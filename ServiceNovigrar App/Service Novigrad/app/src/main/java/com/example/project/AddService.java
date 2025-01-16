package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

    public class AddService extends AppCompatActivity {

        private ArrayList<Service> availableServices = new ArrayList<>();


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_service);

            // Assuming you pass the employee object from the previous activity
            Intent intent = getIntent();
            String email = intent.getStringExtra("EMAIL");


            // Populate the available services from Firebase
            DatabaseReference servicesRef = FirebaseDatabase.getInstance().getReference("services");
            servicesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    availableServices.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Service service = snapshot.getValue(Service.class);
                        if (service != null) {
                            availableServices.add(service);
                        }
                    }

                    // Update the ListView
                    updateListView();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(AddService.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            // Handle item click on the ListView
            ListView listViewAvailableServices = findViewById(R.id.listViewAvailableServices);
            listViewAvailableServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Add the selected service to the employee's offered services
                    Service selectedService = availableServices.get(position);
                    DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference("Employee accounts/");

                    Query query = employeeRef.orderByChild("email").equalTo(email);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                EmployeeAccount employee = snapshot.getValue(EmployeeAccount.class);
                                // Check if the employee is found
                                if (employee != null) {
                                    // Add the selected service to the employee's offered services
                                    if (!employee.getOfferedServicesNames().contains(selectedService.getName())) {
                                        employee.addOfferedService(selectedService);
                                        // Notify the user or perform additional actions if needed
                                        Toast.makeText(AddService.this, "Service added: " + selectedService.getName(), Toast.LENGTH_SHORT).show();
                                        // Update the employee data in the database
                                        snapshot.getRef().setValue(employee);
                                        Intent resultIntent = new Intent();
                                        resultIntent.putExtra("ADDED_SERVICE", selectedService);
                                        setResult(RESULT_OK, resultIntent);
                                        finish();
                                    } else {
                                        // Service is already offered by the employee
                                        Toast.makeText(AddService.this, "You already offer this service.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(AddService.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        private void updateListView() {
            ListView listViewAvailableServices = findViewById(R.id.listViewAvailableServices);

            // Create a list of service names
            ArrayList<String> serviceNames = new ArrayList<>();
            for (Service service : availableServices) {
                serviceNames.add(service.getName());
            }

            // Use the list of service names to create the ArrayAdapter
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, serviceNames);

            // Set the adapter for the ListView
            listViewAvailableServices.setAdapter(adapter);
        }
    }

