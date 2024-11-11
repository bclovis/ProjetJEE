package com.example.projetjee;

public interface Observable {
    public void ajouterObservateur(Observateur observateur);
    public void supprimerObservateur(Observateur observateur);

    public void notifierObservateur(String message);

}
