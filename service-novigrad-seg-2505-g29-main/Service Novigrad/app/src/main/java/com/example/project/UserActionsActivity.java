package com.example.project;

// UserActionsActivity.java
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
// (Existing imports...)

public class UserActionsActivity extends AppCompatActivity {

    private static final int REQUEST_FILE_PICKER = 3;

    private Service selectedService;
    private String employeeEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_actions);

        Button buttonSubmitRequest = findViewById(R.id.buttonSubmitRequest);

        // Assuming you have some way of getting the selected service
        // Replace the following with your actual logic to get the selected service
        selectedService = getIntent().getParcelableExtra("SELECTED_SERVICE");
        employeeEmail = getIntent().getStringExtra("EMPLOYEE_EMAIL");

        // Display service details in the form
        displayServiceDetails();

        buttonSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Process the user's service request
                processServiceRequest();
            }
        });
    }

    private void displayServiceDetails() {
        // Display service details in the form for the user to fill out
        EditText editTextServiceName = findViewById(R.id.editTextServiceName);
        editTextServiceName.setText(selectedService.getName());

        LinearLayout layoutFormFields = findViewById(R.id.linearLayoutFormFields);
        for (String formFieldLabel : selectedService.getFormFields()) {
            addNewField(layoutFormFields, formFieldLabel);
        }

        LinearLayout layoutDocuments = findViewById(R.id.linearLayoutDocuments);
        for (String document : selectedService.getRequiredDocuments()) {
            addNewDocumentField(layoutDocuments, document);
        }
    }

    private void addNewField(LinearLayout layout, String label) {
        // Create a new LinearLayout to hold the label and the user input field
        LinearLayout fieldLayout = new LinearLayout(this);
        fieldLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        fieldLayout.setOrientation(LinearLayout.VERTICAL);

        // Create a TextView for the label
        TextView labelTextView = new TextView(this);
        labelTextView.setText(label);

        // Create an EditText for user input
        EditText editText = new EditText(this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Add the label and EditText to the fieldLayout
        fieldLayout.addView(labelTextView);
        fieldLayout.addView(editText);

        // Add the fieldLayout to the main layout
        layout.addView(fieldLayout);
    }

    private void addNewDocumentField(LinearLayout layout, String label) {
        // Create a new LinearLayout to hold the label and the document EditText
        LinearLayout fieldLayout = new LinearLayout(this);
        fieldLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        fieldLayout.setOrientation(LinearLayout.VERTICAL);

        // Create a TextView for the label
        TextView labelTextView = new TextView(this);
        labelTextView.setText(label);

        // Create an EditText for entering document names
        EditText documentEditText = new EditText(this);
        documentEditText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Add the label and document EditText to the fieldLayout
        fieldLayout.addView(labelTextView);
        fieldLayout.addView(documentEditText);

        // Add the fieldLayout to the main layout
        layout.addView(fieldLayout);
    }


    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*"); // Allow all file types
        startActivityForResult(intent, REQUEST_FILE_PICKER);
    }

    // Inside UserActionsActivity.java
    private void processServiceRequest() {
        // Retrieve user-entered data
        String serviceName = ((EditText) findViewById(R.id.editTextServiceName)).getText().toString();
        List<String> formFieldValues = getFieldValues(findViewById(R.id.linearLayoutFormFields));
        List<String> documentValues = getFieldValues(findViewById(R.id.linearLayoutDocuments));

        if (serviceName.trim().isEmpty() || formFieldValues.isEmpty() || containsEmptyField(formFieldValues) || containsEmptyField(documentValues)) {
            // Show an error message if any of the fields is empty
            Toast.makeText(
                    UserActionsActivity.this,
                    "Veuillez remplir tous les champs du formulaire",
                    Toast.LENGTH_LONG
            ).show();
            return;
        }

        // Get the employee's identifier (replace "employeeIdentifier" with the actual identifier)
        String employeeIdentifier = employeeEmail; // Replace this with the actual identifier

        if (!isRequestAlreadyExists(employeeIdentifier, serviceName)) {
            // Show an error message
            Toast.makeText(
                    UserActionsActivity.this,
                    "You have already submitted this Service",
                    Toast.LENGTH_LONG
            ).show();
            return;
        }

        // Create a ServiceRequest object with the entered details
        ServiceRequest serviceRequest = new ServiceRequest(selectedService, formFieldValues, documentValues, "Pending", employeeIdentifier);

        // Send the service request to the server or store it in the database
        DatabaseReference serviceRequestsRef = FirebaseDatabase.getInstance().getReference("ServiceRequests");
        String requestId = serviceRequestsRef.push().getKey();
        serviceRequestsRef.child(requestId).setValue(serviceRequest);

        // Show a success message
        Toast.makeText(
                UserActionsActivity.this,
                "Service Request Submitted Successfully\nName: " + serviceName + "\nForm Fields: " + formFieldValues + "\nDocuments: " + documentValues,
                Toast.LENGTH_LONG
        ).show();

        // Finish the activity after processing the request
        finish();
    }


    private List<String> getFieldValues(LinearLayout layout) {
        List<String> fieldValues = new ArrayList<>();

        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof LinearLayout) {
                LinearLayout fieldLayout = (LinearLayout) child;
                View innerChild = fieldLayout.getChildAt(1); // Assuming the EditText or Button is the second child
                if (innerChild instanceof EditText) {
                    String value = ((EditText) innerChild).getText().toString();
                    fieldValues.add(value);
                }
            }
        }

        return fieldValues;
    }

    private boolean containsEmptyField(List<String> fieldValues) {
        for (String value : fieldValues) {
            if (value.isEmpty()) {
                return true; // There is an empty field
            }
        }
        return false; // All fields are non-empty
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FILE_PICKER && resultCode == RESULT_OK && data != null) {
            // Handle the selected document here
            Uri selectedDocumentUri = data.getData();
            // You can use the selectedDocumentUri to get the file path or perform other operations
            // For now, let's just show a Toast with the selected document URI
            Toast.makeText(this, "Selected Document: " + selectedDocumentUri.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean isRequestAlreadyExists(String employeeId, String serviceName) {
        DatabaseReference serviceRequestsRef = FirebaseDatabase.getInstance().getReference("ServiceRequests");
        final boolean[] flag = new boolean[1];
        serviceRequestsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ServiceRequest serviceRequest = snapshot.getValue(ServiceRequest.class);
                    // Check if the service request matches the selected service and employee
                    if (serviceRequest != null && serviceRequest.getEmployeeIdentifier().equals(employeeId) && serviceRequest.getService().getName().equals(serviceName)) {
                        // Update the status of the service request directly in the database
                            // Update the status of the service request directly in the database
                            flag[0] = false;
                            return;

                        }else if (serviceRequest == null){flag[0] = true;return;}
                    else {
                            flag[0] = true;
                            return;
                        }
                    }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
                Toast.makeText(
                        UserActionsActivity.this,
                        "Error: " + databaseError.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
        
        return flag[0];
    }


}




