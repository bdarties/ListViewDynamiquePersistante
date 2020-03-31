package com.example.listviewdynamiquepersistante;


public class Etudiant {
    // attributs personnalisés pour la classe Etudiant
    public String nom;
    public String prenom;
    public String annee;

    // constructeur classique (3 parametres affectant les attributs)
    public Etudiant (String p_nom,  String p_prenom, String p_annee ) {
        nom = p_nom;
        prenom = p_prenom;
        annee = p_annee;
    }

    // la méthode toString. Classique mais inutilisée ici
    // employée seulement à des fins de debug
    @Override
    public String toString() {
        return "Hello : "+ prenom + " "+ nom + ", enchanté!\n";
    }
}
