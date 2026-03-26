package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserSearchByAdresse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_by_adresse);

        //Variables
        EditText searchedAdresse = findViewById(R.id.txtSearchField);
        Button btnSearch = findViewById(R.id.btnSearch);
        Button btnBack = findViewById(R.id.btnBack);

        String adresseQuery = searchedAdresse.getText().toString();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSearchByAdresse.this, UserAdresseSearchResultActivity.class);
                intent.putExtra("adresse", adresseQuery);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}