package com.example.projetjee;

import java.util.ArrayList;
import java.util.List;

public interface Observable {

    List<Observateur> observateurs = new ArrayList<>();

    default void ajouterObservateur(Observateur observateur) {
        observateurs.add(observateur);
    }

    default void supprimerObservateur(Observateur observateur) {
        observateurs.remove(observateur);
    }

    default void notifierObservateurs(String message) {
        for (Observateur observateur : observateurs) {
            observateur.mettreAJour(message);
        }
    }
}
