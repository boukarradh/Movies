# Movies App - Explorateur de Films

## Description

Ce projet est une application Android native développée dans le cadre du module **DEVE301 - Développement environnement mobile, L'objectif est de créer une application permettant aux utilisateurs d'explorer des films, de rechercher des titres, de voir les détails et de gérer une liste de favoris.

L'application met en œuvre les concepts modernes de développement Android, notamment Jetpack Compose pour l'interface utilisateur, une architecture MVVM avec des éléments de Clean Architecture, la gestion des données locales avec Room et la récupération de données depuis une API externe (TheMovieDB).

## Fonctionnalités Principales

* **Authentification Locale Simplifiée :**
    * Écran d'inscription (un seul utilisateur à la fois).
    * Écran de connexion.
    * Persistance de l'utilisateur via Room.
    * Gestion de l'état de connexion (potentiellement via DataStore).
    * Déconnexion (suppression des données utilisateur).
* **Exploration de Films :**
    * Affichage des films populaires récupérés depuis l'API TMDB.
    * Mise en cache des données des films dans la base de données Room.
* **Recherche de Films :**
    * Écran permettant de rechercher des films par titre via l'API TMDB.
* **Détails du Film :**
    * Affichage des informations détaillées d'un film sélectionné (synopsis, note, date de sortie, affiche, etc.).
* **Gestion des Favoris :**
    * Possibilité d'ajouter/retirer des films à une liste de favoris (stockée localement).
    * Écran dédié pour afficher les films favoris.
* **Navigation :**
    * Navigation basée sur Jetpack Compose Navigation.
    * Utilisation d'une Bottom Navigation Bar pour les sections principales (Accueil, Recherche, Favoris).

## Technologies Utilisées

* **Langage :** Kotlin
* **UI Toolkit :** Jetpack Compose
* **Architecture :** MVVM (Model-View-ViewModel) avec principes de Clean Architecture (couches Présentation/Données)
* **Asynchronisme :** Kotlin Coroutines & Flows
* **Réseau :** Retrofit (pour les appels API REST) & OkHttp (client HTTP)
* **API Externe :** TheMovieDB (TMDB) API v3
* **Base de Données Locale :** Jetpack Room (pour le cache et les données utilisateur/favoris)
* **Préférences :** Jetpack DataStore (potentiellement pour l'état de connexion)
* **Injection de Dépendances :** Manuelle (via Factories) ou Hilt (si activé)
* **Chargement d'Images :** Coil (bibliothèque Kotlin-first pour le chargement d'images)
* **Build Tool :** Gradle (avec Kotlin Script - kts)
* **Contrôle de Version :** Git & GitHub

## Configuration et Lancement

### Prérequis

* Android Studio (dernière version stable recommandée)
* JDK 11 ou supérieur
* Un compte TheMovieDB et une **Clé API (v3 auth)**

### Configuration

1.  **Cloner le dépôt :**
    ```bash
    git clone [https://github.com/boukarradh/Movies.git](https://github.com/boukarradh/Movies.git)
    cd Movies
    ```
2.  **Obtenir une Clé API TMDB :**
    * Créez un compte sur [https://www.themoviedb.org/](https://www.themoviedb.org/).
    * Allez dans les paramètres de votre compte -> API.
    * Demandez une clé API (v3 auth).
3.  **Ajouter la Clé API au projet :**
    * Ouvrez le fichier `app/src/main/java/com/epsi/movies/data/network/MoviesApiService.kt` (adaptez le chemin si nécessaire).
    * Trouvez la ligne : `const val API_KEY_VALUE = "VOTRE_CLE_API_TMDB"`
    * Remplacez `"VOTRE_CLE_API_TMDB"` par la clé API (v3) que vous avez obtenue.
    * **Important :** Ne commitez pas votre clé API réelle sur un dépôt public ! Pour un projet partagé, utilisez des méthodes plus sécurisées (variables d'environnement, fichier `local.properties` ignoré par Git, etc.).

### Lancement

1.  Ouvrez le projet dans Android Studio.
2.  Laissez Gradle synchroniser les dépendances.
3.  Sélectionnez un émulateur ou connectez un appareil physique Android (avec le débogage USB activé).
4.  Cliquez sur le bouton "Run 'app'" (l'icône de lecture verte).

## Structure du Projet (Simplifiée)

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/epsi/movies/
│   │   │   ├── data/                # Couche Données
│   │   │   │   ├── database/        # Room (DAO, Entity, Database)
│   │   │   │   ├── mapper/          # Fonctions de conversion (Mappers)
│   │   │   │   ├── network/         # Retrofit (ApiService, Models Réseau)
│   │   │   │   └── repository/      # Implémentation des Repositories
│   │   │   ├── di/                  # Modules Hilt (si utilisé)
│   │   │   ├── domain/              # Couche Domaine (Interfaces Repository, Modèles UI/Domaine) - Optionnel
│   │   │   ├── presentation/        # Couche Présentation
│   │   │   │   ├── features/        # Écrans spécifiques (login, home, detail...)
│   │   │   │   │   └── ... (ViewModel et UI Composable par feature)
│   │   │   │   ├── model/           # Modèles spécifiques à l'UI (ex: MovieUiModel)
│   │   │   │   └── navigation/      # Code lié à la navigation (Screen.kt, AppNavigation.kt)
│   │   │   ├── ui/                  # Éléments UI partagés, Thème
│   │   │   │   └── theme/
│   │   │   └── MainActivity.kt      # Point d'entrée principal
│   │   ├── res/
│   │   │   ├── drawable/            # Images, icônes (dont le logo)
│   │   │   └── ...
│   │   └── AndroidManifest.xml
│   ├── test/                    # Tests unitaires
│   └── androidTest/             # Tests instrumentalisés
│
├── build.gradle.kts             # Configuration Gradle du module app
└── ...
build.gradle.kts                 # Configuration Gradle du projet
settings.gradle.kts              # Paramètres Gradle
libs.versions.toml               # Catalogue de dépendances Gradle
```

## Améliorations Possibles

* Gestion d'erreurs plus robuste (avec des états d'erreur spécifiques dans l'UI).
* Implémentation complète du cache pour la recherche et les détails.
* Stratégie de cache plus avancée (ex: expiration basée sur le temps).
* Pagination pour le chargement des listes de films.
* Utilisation de Hilt pour l'injection de dépendances.
* Ajout de tests unitaires et instrumentalisés.
* Amélioration de l'UI/UX (animations, transitions...).
* Stockage sécurisé du mot de passe (hash + salt).

