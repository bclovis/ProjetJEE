CREATE TABLE Etudiant (
    email VARCHAR(255) PRIMARY KEY,  -- L'email comme clé primaire
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    dateNaissance DATE NOT NULL,
    mdp VARCHAR(255) NOT NULL  -- Mot de passe crypté
);

CREATE TABLE Enseignant (
    email VARCHAR(255) PRIMARY KEY,  -- L'email comme clé primaire
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    dateNaissance DATE NOT NULL,
    mdp VARCHAR(255) NOT NULL  -- Mot de passe crypté
);

CREATE TABLE Admin (
    email VARCHAR(255) PRIMARY KEY,  -- L'email comme clé primaire
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    dateNaissance DATE NOT NULL,
    mdp VARCHAR(255) NOT NULL  -- Mot de passe crypté
);

INSERT INTO Etudiant (email, nom, prenom, dateNaissance, mdp)
VALUES ('etudiant.test@gmail.com', 'Etudiant', 'Test', '2000-01-01','password123');

INSERT INTO Enseignant (email, nom, prenom, dateNaissance, mdp)
VALUES ('enseignant.test@gmail.com', 'Enseignant', 'Test', '2000-01-01','password123');

INSERT INTO Admin (email, nom, prenom, dateNaissance, mdp)
VALUES ('admin.test@gmail.com', 'Admin', 'Test', '2000-01-01','password123');

SELECT * FROM Etudiant;
SELECT * FROM Enseignant;
SELECT * FROM Admin;

CREATE TABLE Filiere (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL
);
SELECT * FROM filiere;
INSERT INTO filiere(id, nom)
VALUES(1,'Mathématiques');

INSERT INTO filiere(id, nom)
VALUES(2,'Informatique');

SELECT * FROM Filiere;

SELECT * FROM enseignant;

#########################################################

CREATE TABLE Matiere (
    id INT AUTO_INCREMENT PRIMARY KEY,  -- Identifiant unique pour chaque matière
    nom VARCHAR(255) NOT NULL UNIQUE    -- Nom de la matière, doit être unique
);

INSERT INTO Matiere (nom) VALUES
-- Cours Maths Semestre 1
('Modèle Linéaire et Extensions'),
('Datamining 2'),
('Optimisation déterministe'),
('Traitement de signal'),
('Programmation fonctionnelle'),
('Décidabilité et Complexité'),
('Architecture réseau'),
('Anglais'),
('Communication Interculturelle'),
('Ecoute et condition d\'entretien'),
('Economie'),

-- Cours Maths Semestre 2
('Compressive Sensing'),
('Introduction aux séries temporelles'),
('EDP'),
('IA: Applications'),
('Architecture et Programmation Parallèle'),
('Ateliers d\'Intelligence Collective'),
('Méthode agile'),
('Projet MI'),
('Design de la décision'),

-- Cours Info Semestre 1
('Statistiques'),
('IA: Théorie et Algorithmes'),
('Développement Distribué Java EE'),
('Design Patterns'),
('Test et Vérification'),
('Cybersécurité Opérationnelle'),

-- Cours Info Semestre 2
('Programmation C++'),
('Programmation Système et Réseau'),
('Projet GSI');


SELECT * FROM Matiere;

CREATE TABLE ProfesseurMatiere (
    id INT AUTO_INCREMENT PRIMARY KEY,                  -- Clé primaire avec incrémentation automatique
    professeur_email VARCHAR(255) NOT NULL,            -- Email du professeur, clé étrangère
    matiere_id INT NOT NULL,                           -- ID de la matière, clé étrangère
    CONSTRAINT fk_professeur FOREIGN KEY (professeur_email) REFERENCES Enseignant(email) ON DELETE CASCADE,  -- Référence à la table Enseignant
    CONSTRAINT fk_matiere FOREIGN KEY (matiere_id) REFERENCES Matiere(id) ON DELETE CASCADE,                -- Référence à la table Matiere
    UNIQUE (professeur_email, matiere_id)              -- Empêche les doublons pour un couple Professeur-Matière
);

SELECT * FROM ProfesseurMatiere;

CREATE TABLE MatiereFiliere (
    id INT AUTO_INCREMENT PRIMARY KEY,
    matiere_id INT NOT NULL,               -- Référence à la table Matiere
    filiere_id INT NOT NULL,               -- Référence à la table Filiere
    CONSTRAINT fk_matiere_filiere FOREIGN KEY (matiere_id) REFERENCES Matiere(id) ON DELETE CASCADE,
    CONSTRAINT fk_filiere_filiere FOREIGN KEY (filiere_id) REFERENCES Filiere(id) ON DELETE CASCADE,
    UNIQUE (matiere_id, filiere_id)        -- Empêche les doublons (une matière ne peut être liée qu'une fois à une filière donnée)
);

INSERT INTO MatiereFiliere (matiere_id, filiere_id)
VALUES
-- Semestre 1
((SELECT id FROM Matiere WHERE nom = 'Modèle Linéaire et extensions'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'Datamining 2'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'Optimisation déterministe'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'Traitement de signal'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'Programmation fonctionnelle'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'Décidabilité et Complexité'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'Architecture réseau'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'Anglais'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'Communication Interculturelle'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'Ecoute et condition d\'entretien'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'Economie'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),

-- Semestre 2
((SELECT id FROM Matiere WHERE nom = 'Compressive Sensing'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'Introduction aux séries temporelles'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'EDP'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'IA: Applications'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'Architecture et Programmation Parallèle'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'Ateliers d\'Intelligence Collective'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'Méthode agile'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'Projet MI'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques')),
((SELECT id FROM Matiere WHERE nom = 'Design de la décision'), (SELECT id FROM Filiere WHERE nom = 'Mathématiques'));

INSERT INTO MatiereFiliere (matiere_id, filiere_id)
VALUES
-- Semestre 1
((SELECT id FROM Matiere WHERE nom = 'Statistiques'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'IA: Théorie et Algorithmes'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'Développement Distribué Java EE'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'Design Patterns'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'Architecture réseau'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'Test et Vérification'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'Cybersécurité Opérationnelle'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'Anglais'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'Communication Interculturelle'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'Ecoute et Condition d\'Entretien'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'Economie'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),

-- Semestre 2
((SELECT id FROM Matiere WHERE nom = 'IA: Applications'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'Programmation fonctionnelle'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'Architecture et Programmation Parallèle'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'Programmation C++'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'Programmation Système et Réseau'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'Ateliers d\'intelligence collective'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'Méthode agile'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'Projet GSI'), (SELECT id FROM Filiere WHERE nom = 'Informatique')),
((SELECT id FROM Matiere WHERE nom = 'Design de la décision'), (SELECT id FROM Filiere WHERE nom = 'Informatique'));


SELECT * from matierefiliere;

DROP TABLE IF EXISTS EmploiDuTemps;

select * from emploidutemps;

CREATE TABLE EmploiDuTemps (
    id INT AUTO_INCREMENT PRIMARY KEY,          -- Clé primaire avec incrémentation automatique
    jour VARCHAR(50) NOT NULL,                 -- Le jour (ex : Lundi, Mardi)
    heure VARCHAR(20) NOT NULL,                -- Le créneau horaire (ex : "08h-10h")
    matiere_id INT NOT NULL,                   -- ID de la matière (clé étrangère vers Matiere)
    professeur_email VARCHAR(255) NOT NULL,   -- Email du professeur (clé étrangère vers Enseignant)
    filiere_id INT NOT NULL,                  -- ID de la filière (clé étrangère vers Filiere)
    semestre INT NOT NULL,                    -- Numéro du semestre (1 ou 2)
    semaineDebut INT NOT NULL,                -- Semaine de début (ex : 1)
    semaineFin INT NOT NULL,                  -- Semaine de fin (ex : 16),

    -- Contraintes de clé étrangère
    CONSTRAINT fk_matiere_emploi FOREIGN KEY (matiere_id) REFERENCES Matiere(id) ON DELETE CASCADE,
    CONSTRAINT fk_professeur_emploi FOREIGN KEY (professeur_email) REFERENCES Enseignant(email) ON DELETE CASCADE,
    CONSTRAINT fk_filiere_emploi FOREIGN KEY (filiere_id) REFERENCES Filiere(id) ON DELETE CASCADE,

    -- Contrainte unique : un professeur ne peut avoir deux cours à la même heure le même jour
    CONSTRAINT unique_cours_filiere_jour_heure UNIQUE (jour, heure, filiere_id, semaineDebut, semaineFin)
);


INSERT INTO EmploiDuTemps (jour, heure, matiere_id, professeur_email, filiere_id, semestre, semaineDebut, semaineFin)
VALUES
-- Lundi
('Lundi', '08h-10h', 1, 'nguyenthihien@gmail.com', 1, 1, 1, 16), -- Modèle Linéaire et Extensions
('Lundi', '10h-12h', 2, 'senoussi.houcine@gmail.com', 1, 1, 1, 16), -- Datamining 2
('Lundi', '14h-16h', 8, 'majdoub.michelle@gmail.com', 1, 1, 1, 16), -- Anglais

-- Mardi
('Mardi', '08h-10h', 3, 'alktar.yacin@gmail.com', 1, 1, 1, 16), -- Optimisation déterministe
('Mardi', '10h-12h', 4, 'alaya.ines@gmail.com', 1, 1, 1, 16), -- Traitement de Signal
('Mardi', '14h-16h', 9, 'picod.aurelia@gmail.com', 1, 1, 1, 8), -- Communication Interculturelle

-- Mercredi
('Mercredi', '08h-10h', 6, 'bourhattas.abderrahim@gmail.com', 1, 1, 1, 16), -- Décidabilité et Complexité
('Mercredi', '10h-12h', 5, 'forest.jeanpaul@gmail.com', 1, 1, 1, 16), -- Programmation Fonctionnelle
('Mercredi', '14h-16h', 10, 'moronenko.natalia@gmail.com', 1, 1, 9, 16), -- Écoute et Condition d'Entretien

-- Jeudi
('Jeudi', '08h-10h', 11, 'lefrioux.romuald@gmail.com', 1, 1, 9, 16), -- Économie
('Jeudi', '10h-12h', 1, 'nguyenthihien@gmail.com', 1, 1, 1, 16), -- Modèle Linéaire et Extensions
('Jeudi', '14h-16h', 2, 'senoussi.houcine@gmail.com', 1, 1, 1, 16), -- Datamining 2

-- Vendredi
('Vendredi', '08h-10h', 7, 'haddache.mohamed@gmail.com', 1, 1, 1, 16), -- Architecture Réseau
('Vendredi', '10h-12h', 5, 'forest.jeanpaul@gmail.com', 1, 1, 1, 16); -- Programmation Fonctionnelle

INSERT INTO EmploiDuTemps (jour, heure, matiere_id, professeur_email, filiere_id, semestre, semaineDebut, semaineFin)
VALUES
-- Lundi
('Lundi', '08h-10h', 12, 'alktar.yacin@gmail.com', 1, 2, 17, 32), -- Compressive Sensing
('Lundi', '10h-12h', 13, 'nguyenthihien@gmail.com', 1, 2, 17, 32), -- Introduction aux Séries Temporelles
('Lundi', '14h-16h', 8, 'majdoub.michelle@gmail.com', 1, 2, 17, 32), -- Anglais

-- Mardi
('Mardi', '08h-10h', 14, 'ali.muhammad@gmail.com', 1, 2, 17, 32), -- EDP
('Mardi', '10h-12h', 15, 'senoussi.houcine@gmail.com', 1, 2, 17, 32), -- IA : Applications
('Mardi', '14h-16h', 17, 'picod.aurelia@gmail.com', 1, 2, 17, 24), -- Ateliers d'Intelligence Collective

-- Mercredi
('Mercredi', '08h-10h', 16, 'mercadal.julien@gmail.com', 1, 2, 17, 32), -- Architecture et Programmation Parallèle
('Mercredi', '10h-12h', 13, 'nguyenthihien@gmail.com', 1, 2, 17, 32), -- Introduction aux Séries Temporelles
('Mercredi', '14h-16h', 11, 'lefrioux.romuald@gmail.com', 1, 2, 17, 32), -- Économie

-- Jeudi
('Jeudi', '08h-10h', 15, 'senoussi.houcine@gmail.com', 1, 2, 17, 32), -- IA : Applications
('Jeudi', '10h-12h', 18, 'pereira.luc@gmail.com', 1, 2, 25, 32), -- Méthode Agile
('Jeudi', '14h-16h', 19, 'maublanc.francois@gmail.com', 1, 2, 25, 32), -- Projet MI

-- Vendredi
('Vendredi', '08h-10h', 20, 'moronenko.natalia@gmail.com', 1, 2, 25, 32), -- Design de la Décision
('Vendredi', '10h-12h', 8, 'majdoub.michelle@gmail.com', 1, 2, 17, 32); -- Anglais

INSERT INTO EmploiDuTemps (jour, heure, matiere_id, professeur_email, filiere_id, semestre, semaineDebut, semaineFin)
VALUES
-- Lundi
('Lundi', '08h-10h', 21, 'bourhattas.abderrahim@gmail.com', 2, 1, 1, 16), -- Statistiques
('Lundi', '10h-12h', 23, 'haddache.mohamed@gmail.com', 2, 1, 1, 16), -- Développement Distribué Java EE
('Lundi', '16h-18h', 22, 'senoussi.houcine@gmail.com', 2, 1, 1, 16), -- IA Théorie et Algorithmes

-- Mardi
('Mardi', '08h-10h', 24, 'mercadal.julien@gmail.com', 2, 1, 1, 16), -- Design Patterns
('Mardi', '10h-12h', 25, 'forest.jeanpaul@gmail.com', 2, 1, 1, 16), -- Test et Vérification
('Mardi', '16h-18h', 9, 'picod.aurelia@gmail.com', 2, 1, 1, 8), -- Communication Interculturelle

-- Mercredi
('Mercredi', '08h-10h', 26, 'ali.muhammad@gmail.com', 2, 1, 1, 16), -- Cybersécurité Opérationnelle
('Mercredi', '10h-12h', 7, 'haddache.mohamed@gmail.com', 2, 1, 1, 16), -- Architecture Réseau
('Mercredi', '16h-18h', 10, 'moronenko.natalia@gmail.com', 2, 1, 9, 16), -- Écoute et Condition d’Entretien

-- Jeudi
('Jeudi', '08h-10h', 8, 'majdoub.michelle@gmail.com', 2, 1, 1, 16), -- Anglais
('Jeudi', '10h-12h', 11, 'lefrioux.romuald@gmail.com', 2, 1, 9, 16), -- Économie
('Jeudi', '14h-16h', 21, 'bourhattas.abderrahim@gmail.com', 2, 1, 1, 16), -- Statistiques

-- Vendredi
('Vendredi', '08h-10h', 24, 'mercadal.julien@gmail.com', 2, 1, 1, 16), -- Design Patterns
('Vendredi', '10h-12h', 9, 'picod.aurelia@gmail.com', 2, 1, 1, 8); -- Communication Interculturelle


INSERT INTO EmploiDuTemps (jour, heure, matiere_id, professeur_email, filiere_id, semestre, semaineDebut, semaineFin)
VALUES
-- Lundi
('Lundi', '08h-10h', 15, 'senoussi.houcine@gmail.com', 2, 2, 17, 32), -- IA : Applications
('Lundi', '10h-12h', 5, 'forest.jeanpaul@gmail.com', 2, 2, 17, 32), -- Programmation Fonctionnelle
('Lundi', '14h-16h', 16, 'mercadal.julien@gmail.com', 2, 2, 17, 32), -- Architecture et Programmation Parallèle

-- Mardi
('Mardi', '08h-10h', 27, 'alktar.yacin@gmail.com', 2, 2, 17, 32), -- Programmation C++
('Mardi', '10h-12h', 28, 'haddache.mohamed@gmail.com', 2, 2, 17, 32), -- Programmation Système et Réseau
('Mardi', '16h-18h', 9, 'picod.aurelia@gmail.com', 2, 2, 17, 24), -- Communication Interculturelle (Déplacé de 14h-16h)

-- Mercredi
('Mercredi', '08h-10h', 26, 'ali.muhammad@gmail.com', 2, 2, 17, 32), -- Cybersécurité Opérationnelle
('Mercredi', '10h-12h', 18, 'pereira.luc@gmail.com', 2, 2, 25, 32), -- Méthode Agile (Permuté avec Économie)
('Mercredi', '14h-16h', 19, 'maublanc.francois@gmail.com', 2, 2, 25, 32), -- Projet GSI

-- Jeudi
('Jeudi', '08h-10h', 11, 'lefrioux.romuald@gmail.com', 2, 2, 17, 32), -- Économie (Permuté avec Méthode Agile)
('Jeudi', '14h-16h', 20, 'moronenko.natalia@gmail.com', 2, 2, 25, 32), -- Design de la Décision
('Jeudi', '16h-18h', 15, 'senoussi.houcine@gmail.com', 2, 2, 17, 32), -- IA : Applications

-- Vendredi
('Vendredi', '08h-10h', 8, 'majdoub.michelle@gmail.com', 2, 2, 17, 32), -- Anglais
('Vendredi', '10h-12h', 11, 'lefrioux.romuald@gmail.com', 2, 2, 17, 32); -- Économie

select * from emploidutemps;


