package com.example.projetjee;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "subject_id")  // Ajoute cette relation si une SchoolClass a une matière (Subject)
    private Subject matiere;
    @OneToMany(mappedBy = "schoolClass")
    private List<Inscription> inscriptions;
    @ManyToOne
    @JoinColumn(name = "teacher_id")  // Ajoute cette relation si un enseignant enseigne une SchoolClass
    private Teacher enseignant;
    private LocalDateTime debut;
    private LocalDateTime fin;

    private String semestre;

    public SchoolClass() {}
}
