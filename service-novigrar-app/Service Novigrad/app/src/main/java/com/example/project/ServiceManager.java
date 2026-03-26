package com.example.project;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.Toast;
        import androidx.appcompat.app.AppCompatActivity;
        import java.util.ArrayList;
        import java.util.List;
        import android.content.Intent;

        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

public class ServiceManager extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_service);


        LinearLayout layoutFormFields = findViewById(R.id.linearLayoutFormFields);
        Button buttonAddFormField = findViewById(R.id.buttonAddFormField);

        LinearLayout layoutDocuments = findViewById(R.id.linearLayoutDocuments);
        Button buttonAddDocument = findViewById(R.id.buttonAddDocument);

        Button buttonCreateService = findViewById(R.id.buttonCreateServiceType);

        buttonAddFormField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewField(layoutFormFields, "Nouveau champ du formulaire");
            }
        });

        buttonAddDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewField(layoutDocuments, "Nouveau document requis");
            }
        });

        buttonCreateService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serviceName = ((EditText) findViewById(R.id.editTextServiceName)).getText().toString();
                java.util.List<String> formFieldValues = getFieldValues(layoutFormFields);
                java.util.List<String> documentValues = getFieldValues(layoutDocuments);

                if (serviceName.trim().isEmpty() || formFieldValues.isEmpty() || documentValues.isEmpty() || containsEmptyField(formFieldValues) || containsEmptyField(documentValues)) {
                    // Show an error message if any of the fields is empty
                    Toast.makeText(
                            ServiceManager.this,
                            "Veuillez remplir tous les champs du formulaire",
                            Toast.LENGTH_LONG
                    ).show();
                return;}

                createService(serviceName, formFieldValues, documentValues);
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

    private void createService(String serviceName, List<String> formFieldValues, List<String> documentValues) {
        // Implémentez la logique de création du type de service
        // Utilisez le nom du service, les valeurs des champs du formulaire et les valeurs des documents requis
        // Par exemple, enregistrez-les dans une base de données, envoyez-les au serveur, etc.
        ArrayList<Service> serviceList = new ArrayList<>();

        // Check if the intent has an existing serviceList
        if (getIntent().hasExtra("SERVICE_LIST")) {
            // If yes, get the existing serviceList
            serviceList = getIntent().getParcelableArrayListExtra("SERVICE_LIST");
        }
        Service newService = new Service(serviceName, formFieldValues, documentValues);

        // Add the new service to the public list
        serviceList.add(newService);

        // Exemple : Affichez un message Toast pour démontrer la réussite
        Toast.makeText(
                this,
                "Type de service créé avec succès.\nNom: " + serviceName + "\nChamps du Formulaire: " + formFieldValues + "\nDocuments Requis: " + documentValues,
                Toast.LENGTH_LONG
        ).show();

        setResult(RESULT_OK, new Intent().putExtra("UPDATED_SERVICE_LIST", serviceList));
        finish();


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