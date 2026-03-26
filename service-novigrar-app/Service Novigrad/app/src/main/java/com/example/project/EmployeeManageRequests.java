// EmployeeManageRequestsActivity.java
package com.example.project;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.ServiceRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManageRequests extends AppCompatActivity {

    private List<ServiceRequest> serviceRequests = new ArrayList<>();
    private ArrayAdapter<ServiceRequest> requestsAdapter;

    private String employeeEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_manage_requests);

        // Assuming you have a ListView in your layout with the id "listViewRequestedServices"
        ListView listViewRequestedServices = findViewById(R.id.listViewRequestedServices);
        employeeEmail = getIntent().getStringExtra("EMAIL");

        // Adapter for displaying requested services
        requestsAdapter = new ArrayAdapter<ServiceRequest>(this, android.R.layout.simple_list_item_2, android.R.id.text1, serviceRequests) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ServiceRequest request = (ServiceRequest) getItem(position);
                // Customize the view to display service name and status
                if (request != null) {
                    String displayText = request.getService().getName() + "\nStatus: " + request.getStatus();
                    ((android.widget.TextView) view.findViewById(android.R.id.text1)).setText(displayText);
                }

                return view;
            }
        };

        listViewRequestedServices.setAdapter(requestsAdapter);


        // Set up click listener for selecting a requested service
        listViewRequestedServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch activity to view details of the selected requested service
                ServiceRequest selectedRequest = serviceRequests.get(position);
                Intent intent = new Intent(EmployeeManageRequests.this, ViewRequestedService.class);
                intent.putExtra("SELECTED_REQUEST", selectedRequest);
                intent.putExtra("EMAIL", employeeEmail);
                startActivity(intent);
            }
        });

        // Retrieve service requests from Firebase
        DatabaseReference requestsRef = FirebaseDatabase.getInstance().getReference("ServiceRequests");
        requestsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                serviceRequests.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ServiceRequest request = snapshot.getValue(ServiceRequest.class);
                    if (request != null) {
                        if (request.getEmployeeIdentifier().equals(employeeEmail)) {
                            serviceRequests.add(request);
                        }
                    }
                }
                // Notify the adapter
                requestsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EmployeeManageRequests.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


