# Gestion des Terrains de Football

"Gestion des Terrains de Football" est une application web développée avec Spring Boot pour la gestion des réservations de terrains de football. Elle permet aux utilisateurs de consulter les terrains disponibles et de faire des réservations, tandis que les administrateurs peuvent gérer les terrains, les utilisateurs, les villes et les réservations.

## Fonctionnalités

*   Inscription et authentification des utilisateurs (Connexion/Déconnexion).
*   Contrôle d'accès basé sur les rôles (Admin, Utilisateur).
*   **Tableau de Bord Administrateur** :
    *   Vue d'ensemble des statistiques du système (nombre total d'utilisateurs, terrains, réservations, villes).
    *   Affichage des dernières réservations.
*   **Gestion par l'Administrateur** :
    *   Opérations CRUD (Créer, Lire, Mettre à jour, Supprimer) pour les Utilisateurs.
    *   Opérations CRUD pour les Terrains de football.
    *   Opérations CRUD pour les Villes.
    *   Opérations CRUD pour les Réservations.
*   **Pages Publiques** :
    *   Page d'accueil.
    *   Consultation des terrains de football disponibles.
    *   Possibilité de faire des réservations (pour les utilisateurs inscrits).
*   **Initialisation des Données** :
    *   Pré-remplissage de la base de données avec des données d'exemple (villes marocaines, terrains, et comptes administrateur/utilisateur par défaut) via la classe `DataInitializer`.

## Technologies Utilisées

*   **Backend** :
    *   Java 17
    *   Spring Boot 3.4.4
    *   Spring Data JPA (avec Hibernate comme implémentation)
    *   Spring Security
*   **Frontend** :
    *   Thymeleaf
    *   Thymeleaf Layout Dialect
    *   Bootstrap 5
    *   HTML, CSS, JavaScript (via [src/main/resources/static/css/style.css](src/main/resources/static/css/style.css))
*   **Base de données** :
    *   MySQL
*   **Outil de Build** :
    *   Apache Maven
*   **Outils de Développement** :
    *   Spring Boot DevTools (pour le rechargement automatique)

## Prérequis

*   JDK 17 ou une version ultérieure.
*   Apache Maven 3.6.x ou une version ultérieure.
*   Serveur MySQL.

## Configuration et Installation

1.  **Cloner le dépôt :**
    ```bash
    git clone <url-du-depot>
    cd gestionterrains
    ```

2.  **Configuration de la base de données :**
    *   Assurez-vous que votre serveur MySQL est en cours d'exécution.
    *   Créez une base de données MySQL nommée `gestionterrains` (ou le nom que vous souhaitez utiliser).
    *   Mettez à jour les informations de connexion à la base de données dans le fichier `src/main/resources/application.properties` si nécessaire. Créez ce fichier s'il n'existe pas :
        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/gestionterrains
        spring.datasource.username=votre_utilisateur_mysql
        spring.datasource.password=votre_mot_de_passe_mysql

        spring.jpa.hibernate.ddl-auto=update
        # Pour le développement, 'create-drop' peut être utilisé pour recréer la base de données à chaque redémarrage
        # spring.jpa.hibernate.ddl-auto=create-drop

        spring.jpa.show-sql=true
        spring.jpa.properties.hibernate.format_sql=true

        # Configuration du port du serveur (optionnel, par défaut 8080)
        # server.port=8080
        ```

3.  **Compiler le projet :**
    Utilisez le wrapper Maven fourni :
    *   Sur Linux/macOS :
        ```bash
        ./mvnw clean install
        ```
    *   Sur Windows :
        ```bash
        mvnw.cmd clean install
        ```

4.  **Lancer l'application :**
    *   Sur Linux/macOS :
        ```bash
        ./mvnw spring-boot:run
        ```
    *   Sur Windows :
        ```bash
        mvnw.cmd spring-boot:run
        ```
    L'application démarrera généralement sur `http://localhost:8080`.

## Utilisation

*   Accédez à l'application en naviguant vers `http://localhost:8080` dans votre navigateur web.

*   **Pages publiques :**
    *   Accueil : `http://localhost:8080/public/index` (ou la racine `/` si configurée pour rediriger)
    *   Terrains : `http://localhost:8080/public/terrains`

*   **Connexion :**
    *   Accédez à la page de connexion via `http://localhost:8080/login`.
    *   Les identifiants par défaut sont initialisés par [`DataInitializer.java`](src/main/java/com/gestionterrains/gestionterrains/config/DataInitializer.java) :
        *   **Administrateur :**
            *   Email : `admin@example.com`
            *   Mot de passe : `admin123`
        *   **Utilisateur standard :**
            *   Email : `user@example.com`
            *   Mot de passe : `user123`

*   **Tableau de bord Administrateur :**
    *   Après connexion en tant qu'administrateur, accédez à `http://localhost:8080/admin`.

## Structure du Projet (Répertoires Clés)

```
gestionterrains/
├── .mvn/                     # Wrapper Maven
├── src/
│   ├── main/
│   │   ├── java/             # Code source Java
│   │   │   └── com/gestionterrains/gestionterrains/
│   │   │       ├── config/       # Configuration Spring (Sécurité, Initialisation des données)
│   │   │       ├── controllers/  # Contrôleurs Spring MVC (Authentification, Public, Admin)
│   │   │       ├── enums/        # Énumérations (Role, StatutReservation, TypeSurface)
│   │   │       ├── models/       # Entités JPA (Utilisateur, Terrain, Ville, etc.)
│   │   │       ├── repositories/ # Répertoires Spring Data JPA
│   │   │       └── services/     # Couche de service (logique métier)
│   │   └── resources/        # Ressources
│   │       ├── static/       # Ressources statiques (CSS, JS, images)
│   │       ├── templates/    # Modèles Thymeleaf (vues HTML)
│   │       └── application.properties # Configuration de l'application
│   └── test/                 # Code source des tests
├── pom.xml                   # Fichier de configuration Maven (dépendances, build)
└── README.md                 # Ce fichier
```

## Initialisation des Données

La classe [`DataInitializer.java`](src/main/java/com/gestionterrains/gestionterrains/config/DataInitializer.java) est exécutée au démarrage de l'application. Elle s'assure que :
*   Un utilisateur `ADMIN` et un utilisateur `UTILISATEUR` par défaut sont créés si la table des utilisateurs est vide.
*   Plusieurs villes marocaines et des terrains de football associés sont créés si les tables des villes et des terrains sont vides. Cela fournit un jeu de données initial pour tester et utiliser l'application.


## Licence

Ce projet est sous licence MIT .
