package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ServiceDetail extends AppCompatActivity {

    private EditText editTextServiceName;
    private LinearLayout linearLayoutFormFields;
    private LinearLayout linearLayoutDocuments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_service);


        // Retrieve the selected service details (replace with your logic)
        Service selectedService = (Service) getIntent().getParcelableExtra("SERVICE");
        linearLayoutFormFields = findViewById(R.id.linearLayoutFormFields);
        linearLayoutDocuments = findViewById(R.id.linearLayoutDocuments);

        // Initialize views
        Button buttonAddFormField = findViewById(R.id.buttonAddFormField);
        Button buttonAddDocument = findViewById(R.id.buttonAddDocument);
        Button buttonUpdateService = findViewById(R.id.buttonUpdateService);
        Button buttonDeleteService = findViewById(R.id.buttonDeleteService);


        if (selectedService != null) {
            // Example: Update TextViews with service details
            TextView editTextServiceName = findViewById(R.id.editTextServiceName);
            editTextServiceName.setText(selectedService.getName());

            List<String> formFields = selectedService.getFormFields();
            for (String formField : formFields) {
                addFormFieldEditText(formField, linearLayoutFormFields);
            }

            // Add EditText fields for required documents dynamically
            List<String> requiredDocuments = selectedService.getRequiredDocuments();
            for (String requiredDocument : requiredDocuments) {
                addFormFieldEditText(requiredDocument, linearLayoutDocuments);
            }

            // You can similarly populate other views with form fields and required documents
        } else {
            // Handle the case when the selectedService is null
            Toast.makeText(this, "Error: Service details not found.", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity or handle appropriately
        }



        // Button click listeners
        buttonAddFormField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewField(linearLayoutFormFields, "Nouveau champ du formulaire");
            }
        });

        buttonAddDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewField(linearLayoutDocuments, "Nouveau document requis");
            }
        });

        buttonUpdateService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update service logic (replace with your logic)
                if (selectedService != null) {
                    // Update service logic
                    updateService(selectedService);
                } else {
                    // Handle the case when the selectedService is null
                    Toast.makeText(ServiceDetail.this, "Error: Service details not found.", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity or handle appropriately
                }

            }
        });

        buttonDeleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedService != null) {
                    // Update service logic
                    deleteService(selectedService);
                } else {
                    // Handle the case when the selectedService is null
                    Toast.makeText(ServiceDetail.this, "Error: Service details not found.", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity or handle appropriately
                }
            }
        });
    }





    private void addNewField(LinearLayout layout, String hint) {
        EditText editText = new EditText(this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        editText.setHint(hint);
        layout.addView(editText);
    }

    private List<String> getFieldValues(LinearLayout layout) {
        List<String> fieldValues = new ArrayList<>();

        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof EditText) {
                String value = ((EditText) child).getText().toString();
                fieldValues.add(value);
            }
        }

        return fieldValues;
    }

    private void updateService(Service updatedService) {

        String serviceName = ((EditText) findViewById(R.id.editTextServiceName)).getText().toString();
        List<String> formFieldValues = getFieldValues(linearLayoutFormFields);
        List<String> documentValues = getFieldValues(linearLayoutDocuments);

        ArrayList<String>  serviceNames = getIntent().getStringArrayListExtra("SERVICENAMES");

        for(String name : serviceNames){
            if(name.equals(serviceName) && !serviceName.equals(updatedService.getName())){
            Toast.makeText(
                    ServiceDetail.this,
                    "Ce nom existe déjà",
                    Toast.LENGTH_LONG
            ).show();
            return;}
        }

        if (serviceName.trim().isEmpty() || formFieldValues.isEmpty() || documentValues.isEmpty() || containsEmptyField(formFieldValues) || containsEmptyField(documentValues)) {
            // Show an error message if any of the fields is empty
            Toast.makeText(
                    ServiceDetail.this,
                    "Veuillez remplir tous les champs du formulaire",
                    Toast.LENGTH_LONG
            ).show();
            return;}

        // Update the service with new data
        updatedService.setName(serviceName);
        updatedService.setFormFields(formFieldValues);
        updatedService.setRequiredDocuments(documentValues);
        // Display a message (replace with your logic)
        Toast.makeText(
                this,
                "Service updated.\nName: " + updatedService.getName() + "\nForm Fields: " + formFieldValues + "\nDocuments: " + documentValues,
                Toast.LENGTH_LONG
        ).show();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("UPDATED_SERVICE", updatedService);
        setResult(RESULT_OK, new Intent().putExtra("UPDATED_SERVICE", updatedService));
        finish();
    }

    private void deleteService(Service selectedService) {
        // Delete service logic (replace with your logic)
        Toast.makeText(
                this,
                "Service deleted.\nName: " + selectedService.getName(),
                Toast.LENGTH_LONG
        ).show();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("DELETE_SERVICE", selectedService);
        setResult(RESULT_OK, new Intent().putExtra("DELETE_SERVICE", selectedService));
        finish();

        // Finish the activity or navigate back to the service list
    }

    private void addFormFieldEditText(String fieldName, LinearLayout linearLayout) {
        EditText editText = new EditText(this);
        editText.setText(fieldName);
        editText.setHint("Enter " + fieldName);

        linearLayout.addView(editText);
    }

    private boolean containsEmptyField(List<String> fieldValues) {
        for (String value : fieldValues) {
            if (value.isEmpty()) {
                return true; // There is an empty field
            }
        }
        return false; // All fields are non-empty
    }
}