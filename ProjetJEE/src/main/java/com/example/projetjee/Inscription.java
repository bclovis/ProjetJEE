package com.example.projetjee;

import java.util.Date;
import jakarta.persistence.*;

@Entity
public class Inscription {
    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student etudiant;

    @Id
    @ManyToOne
    @JoinColumn(name = "school_class_id")
    private SchoolClass cours;
    private Date date;

    // Constructeurs, getters et setters
    public Inscription() {}

    public Inscription(Student etudiant, SchoolClass cours, Date date) {
        this.etudiant = etudiant;
        this.cours = cours;
        this.date = date;
    }
}
