package com.example.projetjee;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class TimeTable implements Observable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Lien avec un cours spécifique (SchoolClass)
    @ManyToOne
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;

    // Liste des observateurs
    @Transient
    private List<Observateur> observateurs = new ArrayList<>();

    // Attributs supplémentaires (par exemple, le jour de la semaine, etc.)
    private String jour;
    public TimeTable() {}
    public void ajouterObservateur(Observateur observateur) {

    }

    public void supprimerObservateur(Observateur observateur) {

    }

    public void notifierObservateur(String message) {

    }
}
