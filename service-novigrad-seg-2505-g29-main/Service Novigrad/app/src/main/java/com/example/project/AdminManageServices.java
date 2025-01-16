package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminManageServices extends AppCompatActivity {

    public ArrayList<Service> serviceList = new ArrayList<>();
    private static final int REQUEST_CREATE_SERVICE = 1;
    private static final int REQUEST_UPDATE_SERVICE = 1;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_services);

        // Liste des services (exemple)
        databaseReference = FirebaseDatabase.getInstance().getReference("services");


        // ListView
        ListView listViewServices = findViewById(R.id.listViewServices);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                // Clear the existing list
                serviceList.clear();

                // Iterate through the dataSnapshot and add services to the list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Service service = snapshot.getValue(Service.class);
                    if (service != null) {
                        serviceList.add(service);
                    }
                }

                // Update your ListView adapter or any other UI logic here
                // For example, if you are using a custom adapter:
                ServiceAdapter adapter = new ServiceAdapter(AdminManageServices.this, serviceList);
                ListView listViewServices = findViewById(R.id.listViewServices);
                listViewServices.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors, if any
                Toast.makeText(AdminManageServices.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Gestionnaire d'événements pour la sélection d'un service
        listViewServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: Implémentez le code pour ouvrir la page de détails du service
                ArrayList<String> serviceNames = new ArrayList<>();
                for(Service service : serviceList){
                    serviceNames.add(service.getName());
                }
                Service selectedService = serviceList.get(position);
                serviceList.remove(selectedService);
                // Vous pouvez envoyer des données supplémentaires à la page suivante (par exemple, le nom du service sélectionné)
                Intent intent = new Intent(AdminManageServices.this, ServiceDetail.class);
                intent.putExtra("SERVICE", selectedService);
                intent.putExtra("SERVICENAMES", serviceNames);
                startActivityForResult(intent, REQUEST_CREATE_SERVICE);

            }
        });

        // Gestionnaire d'événements pour le bouton "Créer un Service"
        Button buttonCreateService = findViewById(R.id.buttonCreateService);

        buttonCreateService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir la page de création de service
                Intent intent = new Intent(AdminManageServices.this, ServiceManager.class);
                intent.putExtra("SERVICE_LIST" , serviceList);
                startActivityForResult(intent, REQUEST_CREATE_SERVICE);

            }
        });
    }



    public class ServiceAdapter extends ArrayAdapter<Service> {

        public ServiceAdapter(Context context, List<Service> services) {
            super(context, 0, services);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Service service = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            TextView serviceNameTextView = convertView.findViewById(android.R.id.text1);
            serviceNameTextView.setText(service.getName());

            return convertView;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CREATE_SERVICE) {
                // ServiceManager has finished for creating, update the serviceList
                if (data != null && data.hasExtra("UPDATED_SERVICE_LIST")) {
                    serviceList = data.getParcelableArrayListExtra("UPDATED_SERVICE_LIST");
                    if (isServiceNameExist(serviceList.get(serviceList.size()-1).getName())) {
                        serviceList.get(serviceList.size()-1).setName(appendCounterToServiceName(serviceList.get(serviceList.size()-1).getName()));
                    }
                    updateFirebase(serviceList);
                } else if (data != null && data.hasExtra("UPDATED_SERVICE")) {
                    Service createdService = data.getParcelableExtra("UPDATED_SERVICE");
                    // Ajoutez le service créé à la liste existante
                    serviceList.add(createdService);

                }
            }  if (requestCode == REQUEST_UPDATE_SERVICE) {
                // ServiceDetail has finished for updating, update the serviceList
                if (data != null && data.hasExtra("UPDATED_SERVICE_LIST")) {
                    serviceList = data.getParcelableArrayListExtra("UPDATED_SERVICE_LIST");

                } else if (data != null && data.hasExtra("UPDATED_SERVICE")) {
                    Service updatedService = data.getParcelableExtra("UPDATED_SERVICE");
                    // Mettez à jour le service dans la liste existante
                    int position = findServicePosition(updatedService);
                    if (position != -1) {
                        serviceList.set(position, updatedService);
                    }
                    updateFirebase(serviceList);

                }else if (data != null && data.hasExtra("DELETE_SERVICE")) {
                    Service deleteService = data.getParcelableExtra("DELETE_SERVICE");
                    serviceList.remove(deleteService);
                    updateFirebase(serviceList);

                }
            }

            // Update your ListView adapter or any other UI logic here
            // For example, if you are using a custom adapter:
            ServiceAdapter adapter = new ServiceAdapter(AdminManageServices.this, serviceList);
            ListView listViewServices = findViewById(R.id.listViewServices);
            listViewServices.setAdapter(adapter);
        }
    }

    private void updateFirebase(List<Service> serviceList) {
        // Get a reference to the "services" node in the Firebase database
        DatabaseReference servicesRef = FirebaseDatabase.getInstance().getReference("services");;

        // Clear existing data in the "services" node
        servicesRef.removeValue();

        // Iterate through the serviceList and add each service to the "services" node
        for (Service service : serviceList) {
            // Generate a unique key for each service using push().getKey()
            String serviceKey = servicesRef.push().getKey();

            // Set the data for the new child node using the unique key
            servicesRef.child(serviceKey).setValue(service.toMap());
        }
    }

    private int findServicePosition(Service service) {
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).getName().equals(service.getName())) {
                return i;
            }
        }
        return -1;
    }


    private boolean isServiceNameExist(String serviceName) {
        int i = 0;
        for (Service service : serviceList) {
            if (service.getName().equals(serviceName) && i ==1) {
                return true;
            } else if (service.getName().equals(serviceName) && i ==0){
                i++;
            }
        }
        return false;
    }

    private String appendCounterToServiceName(String serviceName) {
        int counter = 1;
        while (isServiceNameExist(serviceName + " (" + counter + ")")) {
            counter++;
        }
        return serviceName + " (" + counter + ")";
    }
}