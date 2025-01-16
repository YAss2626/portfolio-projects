package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class AdminManageBranches extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_branches);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("branches");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Account> accountList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Account account = snapshot.getValue(Account.class);
                    accountList.add(account);
                }

                // Maintenant, vous avez la liste des comptes. Vous pouvez les afficher sur votre layout.
                // Appelez une méthode pour afficher les comptes sur le layout ici.
                displayAccountsOnLayout(accountList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Gestion des erreurs de lecture des données Firebase
            }

            public void displayAccountsOnLayout(List<Account> accountList) {
                // utilisation de la mise en page(lay-out) pour l'affichage
                // Créez un adaptateur pour afficher les comptes
                // Assurez-vous que cet adaptateur est attaché à votre RecyclerView ou ListView
            }

            //3eme class Suppression d'un Compte avec Mise à Jour sur Firebase et Actualisation de l'Affichage:
// Supprimez un compte lorsque nécessaire, par exemple, lorsqu'un élément de la liste est cliqué
// Assurez-vous de supprimer également le compte de Firebase
// Après la suppression, réaffichez la liste mise à jour sur votre layout

            public void deleteAccount(Account accountToDelete) {
                DatabaseReference accountRef = FirebaseDatabase.getInstance().getReference("branches")
                        .child(accountToDelete.getFirstName());
                accountRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // La suppression sur Firebase est réussie, maintenant mettez à jour l'affichage
                            // Appelez à nouveau la méthode pour lire les données et afficher les comptes sur le layout
                        } else {
                            // Gestion des erreurs lors de la suppression sur Firebase
                        }
                    }
                });
            }

        });

        //Affichage des Comptes sur le Layout:





    }


}
/*Lecture des données Firebase et Transformation en Liste de Comptes */
