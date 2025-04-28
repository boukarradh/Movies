// Fichier: build.gradle.kts (niveau projet)
plugins {
    alias(libs.plugins.android.application) apply false // Ou com.android.library si vous avez des modules librairie
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false // <--- AJOUTEZ CETTE LIGNE
    alias(libs.plugins.hilt) apply false // <--- Ajoutez aussi si vous utilisez Hilt
}