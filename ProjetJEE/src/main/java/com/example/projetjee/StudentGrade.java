package com.example.projetjee;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class StudentGrade {

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Id
    private float grade;

    private Date date;

    public StudentGrade() {}

    public StudentGrade(Student student, Subject subject, float grade, Date date) {
        this.student = student;
        this.subject = subject;
        this.grade = grade;
        this.date = date;
    }

    // getters et setters
}
