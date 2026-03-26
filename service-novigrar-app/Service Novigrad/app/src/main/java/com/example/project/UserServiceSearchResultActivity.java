package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserServiceSearchResultActivity extends AppCompatActivity {

    private ArrayList<EmployeeAccount> searchResults = new ArrayList<>();  //valid search results will be stored in here
    ArrayAdapter<String>  searchResultsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_service_search_result);

        //Variables
        Intent intent = getIntent();
        String searchedService = intent.getStringExtra("service");

        ListView listViewSearchResults = findViewById(R.id.listViewSearchResults);
        Button btnBack = findViewById(R.id.btnBack);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        //adapter to display search results
        searchResultsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listViewSearchResults.setAdapter(searchResultsAdapter);

        databaseReference.child("Employee Accounts/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EmployeeAccount branch = snapshot.getValue(EmployeeAccount.class);
                    if (snapshot.hasChild(searchedService)) {
                        searchResults.add(branch);
                    }
                }
                updateListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //return to search
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void updateListView() {
        ListView listViewSearchResults = findViewById(R.id.listViewSearchResults);
        //create list of branch names
        ArrayList<String> branchNames = new ArrayList<>();
        for (EmployeeAccount branch : searchResults) {
            branchNames.add(branch.getEmail());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, branchNames);

        listViewSearchResults.setAdapter(adapter);

    }

}