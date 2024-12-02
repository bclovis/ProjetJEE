CREATE DATABASE IF NOT EXISTS gestion_scolarite;
USE gestion_scolarite;

CREATE TABLE Etudiant (
    email VARCHAR(255) PRIMARY KEY,  -- L'email comme clé primaire
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    dateNaissance DATE NOT NULL,
    mdp VARCHAR(255) NOT NULL,  -- Mot de passe crypté
    filiere VARCHAR(255) DEFAULT 'AUCUNE' NOT NULL  -- Filière avec valeur par défaut 'AUCUNE'
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

-- Insertion des enseignants dans la table Enseignant sans doublon
INSERT INTO Enseignant (email, nom, prenom, dateNaissance, mdp)
VALUES 
    ('alaya.ines@gmail.com', 'Alaya', 'Ines', '1990-01-01', 'motdepassecrypté1'),
    ('ali.muhammad@gmail.com', 'Ali', 'Muhammad', '1985-02-15', 'motdepassecrypté2'),
    ('alktar.yacin@gmail.com', 'Alktar', 'Yacin', '1987-03-10', 'motdepassecrypté3'),
    ('bourhattas.abderrahim@gmail.com', 'Bourhattas', 'Abderrahim', '1983-04-20', 'motdepassecrypté4'),
    ('forest.jeanpaul@gmail.com', 'Forest', 'Jean-Paul', '1975-05-25', 'motdepassecrypté5'),
    ('haddache.mohamed@gmail.com', 'Haddache', 'Mohamed', '1980-06-15', 'motdepassecrypté6'),
    ('lefrioux.romuald@gmail.com', 'Lefrioux', 'Romuald', '1992-07-11', 'motdepassecrypté7'),
    ('majdoub.michelle@gmail.com', 'Majdoub', 'Michelle', '1988-08-08', 'motdepassecrypté8'),
    ('maublanc.francois@gmail.com', 'Maublanc', 'François', '1981-09-09', 'motdepassecrypté9'),
    ('mercadal.julien@gmail.com', 'Mercadal', 'Julien', '1989-10-30', 'motdepassecrypté10'),
    ('moronenko.natalia@gmail.com', 'Moronenko', 'Natalia', '1984-11-12', 'motdepassecrypté11'),
    ('nguyenthihien@gmail.com', 'Nguyen', 'Thi Hien', '1991-12-01', 'motdepassecrypté12'),
    ('pereira.luc@gmail.com', 'Pereira', 'Luc', '1983-02-22', 'motdepassecrypté13'),
    ('picod.aurelia@gmail.com', 'Picod', 'Aurelia', '1990-03-18', 'motdepassecrypté14'),
    ('senoussi.houcine@gmail.com', 'Senoussi', 'Houcine', '1986-04-04', 'motdepassecrypté15');

-- Table DemandeFiliere
CREATE TABLE DemandeFiliere (
    id INT AUTO_INCREMENT PRIMARY KEY,
    etudiant_email VARCHAR(255) NOT NULL, -- Email de l'étudiant (clé étrangère vers Etudiant.email)
    filiere VARCHAR(50) NOT NULL, -- Utilisation de VARCHAR pour les filières
    statut VARCHAR(20) DEFAULT 'EN_ATTENTE', -- Statut de la demande (EN_ATTENTE, ACCEPTE, REFUSE)
    date_demande TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Date de la demande
    commentaire_admin TEXT, -- Commentaire facultatif pour l'administrateur
    FOREIGN KEY (etudiant_email) REFERENCES Etudiant(email)
);


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

-- Insertion des données dans la table ProfesseurMatiere sans doublons
INSERT IGNORE INTO ProfesseurMatiere (professeur_email, matiere_id)
VALUES 
    ('alaya.ines@gmail.com', 3),
    ('ali.muhammad@gmail.com', 14),
    ('ali.muhammad@gmail.com', 26),
    ('alktar.yacin@gmail.com', 3),
    ('alktar.yacin@gmail.com', 12),
    ('bourhattas.abderrahim@gmail.com', 6),
    ('bourhattas.abderrahim@gmail.com', 21),
    ('forest.jeanpaul@gmail.com', 5),
    ('haddache.mohamed@gmail.com', 7),
    ('haddache.mohamed@gmail.com', 23),
    ('haddache.mohamed@gmail.com', 28),
    ('lefrioux.romuald@gmail.com', 11),
    ('majdoub.michelle@gmail.com', 8),
    ('maublanc.francois@gmail.com', 19),
    ('maublanc.francois@gmail.com', 29),
    ('mercadal.julien@gmail.com', 16),
    ('mercadal.julien@gmail.com', 24),
    ('mercadal.julien@gmail.com', 25),
    ('moronenko.natalia@gmail.com', 10),
    ('moronenko.natalia@gmail.com', 20),
    ('nguyenthihien@gmail.com', 1),
    ('nguyenthihien@gmail.com', 13),
    ('pereira.luc@gmail.com', 18),
    ('picod.aurelia@gmail.com', 9),
    ('picod.aurelia@gmail.com', 17),
    ('senoussi.houcine@gmail.com', 2),
    ('senoussi.houcine@gmail.com', 15);

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
DELETE FROM enseignant where email = "maublans.francois@gmail.com";
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

DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `sender` VARCHAR(255) NOT NULL,
  `recipient` VARCHAR(255) NOT NULL,
  `subject` VARCHAR(255) NOT NULL,
  `content` VARCHAR(1000) NOT NULL,
  `sent_at` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

-- Insertion des données dans la table `messages`
INSERT INTO `messages` (`id`, `sender`, `recipient`, `subject`, `content`, `sent_at`) VALUES
(1, 'etudiant.test@gmail.com', 'etudiant.test@gmail.com', 'test', 'Hello World !', '2024-11-18 11:47:12'),
(2, 'melaimiani@cy-tech.fr', 'etudiant.test@gmail.com', 'test envoi', 'Hello', '2024-11-18 12:06:51'),
(3, 'etudiant.test@gmail.com', 'melaimiani@cy-tech.fr', 'test2', 'test2', '2024-11-18 12:12:36'),
(4, 'melaimiani@cy-tech.fr', 'test.etudiant@gmail.com', 'test3', '3', '2024-11-18 12:14:45'),
(5, 'etudiant.test@gmail.com', 'melaimiani@cy-tech.fr', 'test4', '44', '2024-11-18 12:19:32'),
(6, 'etudiant.test@gmail.com', 'melaimiani@cy-tech.fr', 'test5', '5.5.5.5.55.', '2024-11-18 12:21:23'),
(7, 'melaimiani@cy-tech.fr', 'etudiant.test@gmail.com', 'test6', '6.6.66.6.6.6.6', '2024-11-18 12:23:09'),
(8, 'etudiant.test@gmail.com', 'melaimiani@cy-tech.fr', 'testn', 'nnnnnnnnn', '2024-11-18 12:34:49'),
(9, 'etudiant.test@gmail.com', 'melaimiani@cy-tech.fr', 'testn', 'n*n', '2024-11-18 12:38:20'),
(10, 'etudiant.test@gmail.com', 'melaimiani@cy-tech.fr', 'testnn', '0', '2024-11-18 12:44:27'),
(11, 'etudiant.test@gmail.com', 'melaimiani@cy-tech.fr', 'test9498654', 't', '2024-11-18 12:50:32'),
(12, 'etudiant.test@gmail.com', 'melaimiani@cy-tech.fr', 'test94986541', '0.1', '2024-11-18 13:03:59'),
(13, 'melaimiani@cy-tech.fr', 'etudiant.test@gmail.com', 'dtest', 'dtest', '2024-11-18 13:24:33'),
(14, 'etudiant.test@gmail.com', 'melaimiani@cy-tech.fr', 'testn', 'nnn', '2024-11-21 15:11:51');

-- Création de la table `note`
DROP TABLE IF EXISTS `note`;
CREATE TABLE `note` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `note` DECIMAL(4,2) DEFAULT NULL CHECK (`note` BETWEEN 0 AND 20),
  `etudiant` VARCHAR(255) DEFAULT NULL,
  `matiere` INT DEFAULT NULL,
  `date` DATE DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `etudiant` (`etudiant`),
  KEY `matiere` (`matiere`),
  CONSTRAINT `note_ibfk_1` FOREIGN KEY (`etudiant`) REFERENCES `etudiant` (`email`),
  CONSTRAINT `note_ibfk_2` FOREIGN KEY (`matiere`) REFERENCES `matiere` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- Insertion des données dans la table `note`
INSERT INTO `note` (`id`, `note`, `etudiant`, `matiere`, `date`) VALUES
(1, 15.50, 'etudiant.test@gmail.com', 23, '2024-11-10'),
(2, 17.50, 'etudiant.test@gmail.com', 11, '2024-11-15'),
(3, 18.00, 'etudiant.test@gmail.com', 3, '2024-11-21'),
(4, 20.00, 'etudiant.test@gmail.com', 3, '2024-11-21'),
(5, 19.00, 'etudiant.test@gmail.com', 3, '2024-11-21'),
(6, 19.00, 'etudiant.test@gmail.com', 3, '2024-11-21'),
(7, 15.00, 'etudiant.test@gmail.com', 3, '2024-11-23');


select * from messages;
Select* from etudiant;
Select* from demandefiliere;