package com.example.listviewdynamiquepersistante;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // creation des references boutons que je vais trouver dans le layout
    // déclaration, sans initialisation
    Button boutonAjouteEtudiant;
    Button boutonListeEtudiants;
    Button boutonEnleveEtudiant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* les instructions "classiques" : a la création d'une activité, on appelle le constructeur
        parent et on charge le calque associé */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // association de nos references avec les objets du layout
        boutonAjouteEtudiant = (Button) findViewById(R.id.btn_ajout_element);
        boutonListeEtudiants = (Button) findViewById(R.id.btn_voir_liste);
        boutonEnleveEtudiant = (Button) findViewById(R.id.btn_supprimer_element);

        // ajout des écouuteurs : quand on va cliqueur sur un bounton on va charger l'activité associée
        // ajout de l'écouteur sur boutonAjouterEtudiant
        boutonAjouteEtudiant.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // ovuerture de l'activité AjoutEtudiant
                Intent intent = new Intent(MainActivity.this, AjoutEtudiant.class);
                startActivity(intent);
            }
        });

        // ajout de l'écouteur sur boutonAjouterEtudiant
        boutonListeEtudiants.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // ovuerture de l'activité AjoutEtudiant
                Intent intent = new Intent(MainActivity.this, ListeEtudiants.class);
                startActivity(intent);
            }
        });

        // ajout de l'écouteur sur boutonAjouterEtudiant
        boutonEnleveEtudiant.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // ovuerture de l'activité AjoutEtudiant
                Intent intent = new Intent(MainActivity.this, SupprimerEtudiant.class);
                startActivity(intent);
            }
        });
    }
}
