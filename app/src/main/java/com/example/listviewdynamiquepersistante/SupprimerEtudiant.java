package com.example.listviewdynamiquepersistante;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;


public class SupprimerEtudiant extends AppCompatActivity {

    // creation des references boutons  que je vais trouver dans le layout
    // déclaration, sans initialisation
    Button boutonRetour;
    ArrayList<Etudiant> listestudiants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* les instructions "classiques" : a la création d'une activité, on appelle le constructeur
        parent et on charge le calque associé */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer_etudiant);

        /**********************************************/
        /*** RECUPERATION DE LA LISTE DES ETUDIANTS ***/
        /**********************************************/
        /** ici on récupere la liste des étudiants qui est sauvegardée dans un endroit du telephone qui s'appelle
         * sharedpreference. c'est comme une base de données, mais propre au telephone.
         * cette zone est accessible n'importe ou dans les activités de l'application :) Le probleme de
         * cette zone ,c'est qu'on ne peut y enregistrer que des types simples : string, int, float etc
         * or on voudrait stocker une arrayList d'étudiants
         * on a donc comme solution alterrative de transformer notre arraylist en chaine json, puis la stocker
         * et apres on la récupere sous forme de chaine, on la retransforme en tableau fixe, puis en arraylist
         * Ca semble un peu tordu et compliqué, mais finalement ce n'est pas si long et ca marche plutot bien
         *
         * On a ci-dessous la portion de code pour charger une liste existante
         * il y aura ailleurs une autre portion de code pour sauvegarder la liste dans sharedpreference, qu'on
         * utilisera quand on aura modifié la liste
         */
        /** chargement de la liste d'étudiants **/
        // on récupère les préférences stockées sous la clé mesPrefs :
        SharedPreferences prefsStockees = getSharedPreferences("mesPrefs", MODE_PRIVATE);
        Gson gson = new Gson(); // on crée un gestionnaire de format json
        // on extrait la liste referencée par le mot cle_listeEtudiants qu'on avait stocké dans les
        // préférences partagées
        String listeEtudiantTxtJson = prefsStockees.getString("cle_listeEtudiants", "");
        // desormais dans listeEtudiantsTxtJson on a tous nos etudiants stockés dans un format json
        // on reconstruit un tableau d'objets de type étudiants grace à al liste au format json
        Etudiant[] tableauEtudiantsTemporaire = gson.fromJson(listeEtudiantTxtJson, Etudiant[].class);
        // reconstitution d'une arrayList a partir du tableau tableauEtudiantsTemporaire
        listestudiants = new ArrayList<Etudiant>(Arrays.asList(tableauEtudiantsTemporaire));

        /*****************************************/
        /*** AFFICHAGE DE LA LISTE D'ETUDIANTS ***/
        /*****************************************/

        /** on va synchroniser  la listView avec notre arrayList listeEtudiants
         *
         * un baseAdapter est un outil puissant mais complexe qui va nous permettre de faire une listView
         * evoluée, dans laquelle chaque item pourra contenir un formatage poussé.
         * ceci nécessite de redéfinir quelques méthodes, notamment getView qui permet de formater l'affichage
         */
        BaseAdapter customBaseAdapter = new BaseAdapter() {
            // Return list view item count.
            @Override
            // a la question "combien d'éléments as-tu ?" on va fournir comme réponse la taille de la listeEtudiants.
            public int getCount() {
                return listestudiants.size();
            }

            @Override
            public Object getItem(int i) {
                // getItem doit renvoyer l'item qui est associé à l'éléméent de liste d'indice i
                // on renvoie simplement le i^eme elemnt de listeEtudiant, car la listview doit etre
                // etre synchronisée avec listeEtudiants
                return listestudiants.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(final int itemIndex, View itemView, ViewGroup viewGroup) {
                /** C'est ici que ca devient difficile : on va construire un affichage pour chaque item
                 * on a la fonction getView qui indique comment on doit constuire l'affichage de l'item
                 * numero 'itemIndex', en construisant la vue 'itemView'. Avec l'info du premier paramètre, on va
                 * construire le second. On considere donc ici qu'on ne construit l'affichage que pour 1 item
                 **/
                if (itemView == null) {   // on va creer une case réponse (une ligne du listview ) avec un modele défini dans le fichier
                    // xml main_activity_base_adapter
                    itemView = LayoutInflater.from(SupprimerEtudiant.this).inflate(R.layout.cadre_item_de_liste, null);
                }

                // On récupere les 3 cases (image + zone identite + zone age de ce modele)
                // on va les remplir par la suite avec les valeurs à affcher pour cette ligne
                // ImageView imageView = (ImageView) itemView.findViewById(R.id.baseUserImage);
                TextView txt_etudiant_nomprenom  = (TextView) itemView.findViewById(R.id.txt_nom_prenom);
                TextView txt_etudiant_annee  = (TextView) itemView.findViewById(R.id.txt_annee);

                // on alterne la couleur du fond
                int colorPos = itemIndex % 2;
                if (colorPos == 0) {
                    itemView.setBackgroundColor(Color.parseColor("#FFEEEE"));
                } else {
                    itemView.setBackgroundColor(Color.parseColor("#DDBBBB"));
                }

                // on lit les valeur des ressources par rapport à listeetudiants
                Etudiant etudiantAafficher = (Etudiant) listestudiants.get(itemIndex);
                final String nom = etudiantAafficher.nom;
                final String prenom = etudiantAafficher.prenom;
                final String annee = etudiantAafficher.annee;

                // on les insère dans les champs correspondants
                txt_etudiant_nomprenom.setText(prenom + " " + nom);
                txt_etudiant_annee.setText("Annee : "+ annee);

                /** que se passe-t'il si on click sur l'item en entier (itemView)?
                 * on va simplement supprimer cet item de la liste des étudiants
                 * */
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // on récupere l'étudiant cliqué
                        Etudiant etudiantSupprime =  (Etudiant) getItem(itemIndex);
                        // on affiche un petit message de type Toast, qui annonce l'étudaint supprimé
                        Toast.makeText(SupprimerEtudiant.this, "vous avez supprimé " + etudiantSupprime.prenom + " " + etudiantSupprime.nom, Toast.LENGTH_SHORT).show();
                        // on enleve l'étudiant de la liste;
                        listestudiants.remove(etudiantSupprime);
                        /* important : on annonce a la listview que la liste d'étudiants a partir de laquelle
                        elle avait été construire a changé. ca va permettre de raffraichir l'affichage */
                        notifyDataSetChanged();
                    }
                });
                // on termine la méthode surchargée en renvoyant la view créée
                return itemView;
            }
        };

        // de retour dans la methode onCreate :  on récupere enfin la listView pour affichage
        ListView lv_Etudiants  = (ListView)findViewById(R.id.listView_etudiants);
        // on l'associe au customAdapter. et voila
        lv_Etudiants.setAdapter(customBaseAdapter);

        /*********************************/
        /*** GESTION DU BOUTON  RETOUR ***/
        /*********************************/
        /** IMPORTANT  :  contriarement aux autres activites : ici quand on quitte l'activite il ne faut pas
         * oublier de sauvegarder la liste listeEtudiants car elle a ete modifiée **/
        // recherche du bouton retour dans le layout
        boutonRetour = findViewById(R.id.bouton_retour);
        // ajout de l'écouteur sur boutonAjouterEtudiant
        boutonRetour.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                /** quand on clique sur le bouton retour :
                 * on se connecte aux preferences stockées sur le teléphone, et on les mets à jour
                 * avec la liste d'étudiants actuelle (dans laquelle on a oté des éléments)  */

                // on récupère les préférences stockées sous la clé mesPrefs
                SharedPreferences prefsStockees = getSharedPreferences("mesPrefs", MODE_PRIVATE);
                // on cree un éditeur de préferences, pour mettre à jour "mesPrefs" :
                Editor prefsEditor = prefsStockees.edit();
                Gson gson = new Gson(); // on crée un gestionnaire de format json
                // on transforme la liste d'étudiant en format json :
                String ListeEtudiantsEnJson = gson.toJson(listestudiants);
                // on envoie la liste (json) dans la clé cle_listeEtudiants de mesPrefs :
                 prefsEditor.putString("cle_listeEtudiants", ListeEtudiantsEnJson);
                prefsEditor.commit(); // on enregistre les préférences
                finish();
            }
        });



    }
}
