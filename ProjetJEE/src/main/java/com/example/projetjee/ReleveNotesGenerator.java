package com.example.projetjee;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

public class ReleveNotesGenerator {

    public static void genererReleve(String filePath, String etudiant, Map<Matiere, List<Note>> notesParMatiere, double moyenneGenerale) {
        try {
            // Initialisation du document PDF
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Titre et informations générales
            document.add(new Paragraph("Relevé de Notes").setBold().setFontSize(16));
            document.add(new Paragraph("Étudiant : " + etudiant).setFontSize(12));
            document.add(new Paragraph("Moyenne générale : " + moyenneGenerale + " / 20").setFontSize(12).setMarginBottom(20));

            // Parcourir les matières et les notes
            for (Map.Entry<Matiere, List<Note>> entry : notesParMatiere.entrySet()) {
                Matiere matiere = entry.getKey();
                List<Note> notes = entry.getValue();

                // Moyenne par matière
                double moyenneMatiere = notes.stream()
                        .mapToDouble(Note::getNote)
                        .average()
                        .orElse(0);
                document.add(new Paragraph(matiere + " (Moyenne : " + moyenneMatiere + " / 20)").setBold().setMarginBottom(10));

                // Créer une table
                Table table = new Table(new float[]{1, 1});
                table.addHeaderCell("Note (/20)");
                table.addHeaderCell("Date");

                // Remplir la table avec les notes
                for (Note note : notes) {
                    table.addCell(String.valueOf(note.getNote()));
                    table.addCell(note.getDate().toString());
                }
                document.add(table.setMarginBottom(20));
            }

            document.close();
            System.out.println("Relevé généré : " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
