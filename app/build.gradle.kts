// Fichier: app/build.gradle.kts

plugins {
    // Plugins de base
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
     alias(libs.plugins.hilt)
    // alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.epsi.movies" // Gardez votre namespace
    compileSdk = 35 // Assurez-vous que cette version est installée via le SDK Manager

    defaultConfig {
        applicationId = "com.epsi.movies" // Gardez votre applicationId
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Configuration pour les tests Compose
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    // Configuration nécessaire pour Compose (peut déjà être présente)
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.compose.get()
    }
    packaging {
        // Exclusions nécessaires pour certaines dépendances Compose/Lifecycle
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("com.google.dagger:hilt-android:2.48.1") // Replace with latest version
    ksp("com.google.dagger:hilt-compiler:2.48.1") // Replace with latest version
    // --- Core AndroidX & Kotlin ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlin.stdlib) // Ajouté

    // --- Cycle de vie (Lifecycle) ---
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx) // Ajouté
    implementation(libs.androidx.lifecycle.viewmodel.compose) // Ajouté
    implementation(libs.androidx.lifecycle.runtime.compose) // Ajouté

    // --- Jetpack Compose ---
    implementation(platform(libs.androidx.compose.bom)) // BOM Compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose) // Ajouté
    // implementation(libs.androidx.material.icons.core) // Optionnel
    // implementation(libs.androidx.material.icons.extended) // Optionnel

    // --- Coroutines ---
    implementation(libs.kotlinx.coroutines.core) // Ajouté
    implementation(libs.kotlinx.coroutines.android) // Ajouté

    // --- Réseau (Retrofit & OkHttp) ---
    implementation(libs.retrofit.core) // Ajouté
    implementation(libs.retrofit.converter.gson) // Ajouté
    implementation(libs.okhttp.core) // Ajouté
    implementation(libs.okhttp.logging.interceptor) // Ajouté (utile pour le debug)

    // --- Base de données (Room) ---
    implementation(libs.androidx.room.runtime) // Ajouté
    implementation(libs.androidx.room.ktx) // Ajouté
    ksp(libs.androidx.room.compiler) // Ajouté - Utiliser ksp ici

    // --- Préférences (DataStore) ---
    implementation(libs.androidx.datastore.preferences) // Ajouté

    // --- Chargement d'Images (Coil) ---
    implementation(libs.coil.compose) // Ajouté

    // --- Injection de Dépendances (Hilt) - Décommenter si utilisé ---
     implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
    // implementation(libs.androidx.hilt.navigation.compose)

    // --- Tests ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom)) // BOM pour les tests Compose
    androidTestImplementation(libs.androidx.ui.test.junit4)
    // Dépendances de debug pour les outils Compose (Preview, etc.)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// Configuration KSP (si vous utilisez Room ou Hilt)
// Assurez-vous que cette section existe.
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    }

// Assurez-vous que cette section existe si vous avez appliqué le plugin Hilt.
hilt {
   enableAggregatingTask = true
 }

