package com.example.listviewdynamiquepersistante;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.google.gson.Gson;

public class VoirEtudiant extends AppCompatActivity {

    // creation des references boutons et EditText que je vais trouver dans le layout
    // déclaration, sans initialisation
    Button boutonRetour;
    EditText txt_nom_etudiant;
    EditText txt_prenom_etudiant;
    EditText txt_annee_etudiant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* les instructions "classiques" : a la création d'une activité, on appelle le constructeur
        parent et on charge le calque associé */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_etudiant);

        /*****************************************/
        /*** RECUPERATION DE L'ETUDIANT CLIQUE ***/
        /*****************************************/

        /* ici on a une configuration particulière : la page doit afficher un etudiant en particulier
        cet étudiant a ete désigné sur l'activité précédente. On a envoyé son numero dans la liste
        grace a une propriété "extra" lors de la création de l'activité, nommée indexEtudiantClique
        on récupère la valeur dans indiceEtudiant grace aux deux lignes suivantes
         */
        Intent intent = getIntent();  // on récupere la session d'acitivte
        // on recupere ensuite l'indice passé
        int indiceEtudiant = intent.getIntExtra("indexEtudiantClique", -1);

        // on récupère les préférences stockées sous la clé mesPrefs :
        SharedPreferences prefsStockees = getSharedPreferences("mesPrefs", MODE_PRIVATE);
        Gson gson = new Gson(); // on crée un gestionnaire de format json
        // on extrait la liste referencée par le mot cle_listeEtudiants qu'on avait stocké dans les
        // préférences partagées
        String listeEtudiantTxtJson = prefsStockees.getString("cle_listeEtudiants", "");
        // desormais dans listeEtudiantsTxtJson on a tous nos etudiants stockés dans un format json
        // on reconstruit un tableau d'objets de type étudiants grace à al liste au format json
        Etudiant[] tableauEtudiantsTemporaire = gson.fromJson(listeEtudiantTxtJson, Etudiant[].class);

        // on identifie l'étudiant de la liste qui a l'id qu'on devait afficher.
        // on le stocke dans une reference etudiantVisualite
        Etudiant etudiantVisualise = tableauEtudiantsTemporaire[indiceEtudiant];

        /********************************/
        /*** AFFICHAGE DE L'ETUDIANT  ***/
        /********************************/

        // on a désormais l'objet Etudiant à afficher. le reste est facile :
        // on associe chaque référence au widget qui nous permettra d'afficher l'étudiant
        txt_nom_etudiant = findViewById(R.id.txt_nom);
        txt_prenom_etudiant = findViewById(R.id.txt_prenom);
        txt_annee_etudiant = findViewById(R.id.txt_annee);

        // on met enfin à jour le texte des widget avec les valeurs issues de etudiantVisualise
        txt_prenom_etudiant.setText(etudiantVisualise.prenom);
        txt_nom_etudiant.setText(etudiantVisualise.nom);
        txt_annee_etudiant.setText(etudiantVisualise.annee);

        /*********************************/
        /*** GESTION DU BOUTON  RETOUR ***/
        /*********************************/
        // recherche du bouton retour dans le layout
        boutonRetour = findViewById(R.id.bouton_retour);

        // ajout de l'écouteur sur le widget bouton_retour, pour revenir à l'activite précedente
        boutonRetour.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // on se contente de fermer l'activite. Pas de création d'une activité déja lancée avant
            }
        });
    }
}
