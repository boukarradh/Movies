package com.epsi.movies.presentation.ui.designsystem
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.epsi.movies.R
import com.epsi.movies.presentation.ui.theme.MoviesTheme

/**
 * Composable affichant le contenu détaillé d'un film.
 * Reçoit toutes les informations nécessaires via des paramètres individuels.
 *
 * @param title Titre du film.
 * @param overview Synopsis/résumé du film.
 * @param posterUrl URL complète de l'affiche (peut être null).
 * @param backdropUrl URL complète de l'image de fond (peut être null).
 * @param releaseDateFormatted Date de sortie formatée.
 * @param voteAverageFormatted Note moyenne formatée.
 * @param voteCount Nombre de votes (peut être null).
 * @param runtimeFormatted Durée formatée.
 * @param genres Chaîne de caractères des genres formatés.
 * @param tagline Slogan du film (peut être null).
 * @param modifier Modificateur Compose.
 */
@Composable
fun MovieDetailContent(
    title: String,
    overview: String,
    posterUrl: String?,
    backdropUrl: String?,
    releaseDateFormatted: String,
    voteAverageFormatted: String,
    voteCount: Int?,
    runtimeFormatted: String,
    genres: String,
    tagline: String?,
    modifier: Modifier = Modifier
) {
    // Column avec scrolling vertical pour l'ensemble du contenu
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Permet le défilement
    ) {
        // --- Image Backdrop ---
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(backdropUrl) // Utilise le paramètre backdropUrl
                .crossfade(true)
                .placeholder(R.drawable.img)
                .error(R.drawable.img) // Erreur
                .build(),
            contentDescription = "Image de fond pour $title", // Utilise le paramètre title
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f), // Ratio 16:9
            contentScale = ContentScale.Crop // Remplit l'espace
        )

        // --- Section Principale (Affiche + Infos) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.Top // Aligne en haut
        ) {
            // Affiche
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(posterUrl) // Utilise le paramètre posterUrl
                    .crossfade(true)
                    .placeholder(R.drawable.img)
                    .error(R.drawable.img)
                    .build(),
                contentDescription = "Affiche de $title",
                // Utilise le paramètre title
                modifier = Modifier
                    .width(130.dp) // Largeur un peu plus grande pour les détails
                    .aspectRatio(3f / 3f) // Ratio standard d'affiche
                    .clip(MaterialTheme.shapes.medium),

            )

            Spacer(modifier = Modifier.width(16.dp))

            // Colonne pour les infos à côté de l'affiche
            Column(modifier = Modifier.weight(1f)) { // Prend l'espace restant
                // Titre
                Text(
                    text = title, // Utilise le paramètre title
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                // Slogan (Tagline) - Affiché seulement s'il n'est pas null ou vide
                tagline?.takeIf { it.isNotBlank() }?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it, // Utilise le paramètre tagline
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                // Note
                Text(
                    // Utilise voteAverageFormatted et voteCount
                    text = "Note: $voteAverageFormatted (${voteCount ?: 0} votes)",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Date et Durée
                Text(
                    // Utilise releaseDateFormatted et runtimeFormatted
                    text = "$releaseDateFormatted • $runtimeFormatted",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                 // Genres
                Text(
                    text = "Genres: $genres", // Utilise le paramètre genres
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // --- Synopsis ---
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                text = "Synopsis",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = overview.ifBlank { "No data " }, // Utilise le paramètre overview
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espace en bas
    }
}

// --- Preview ---
@Preview(showBackground = true, name = "Movie Detail Content Preview")
@Composable
fun MovieDetailContentPreview() {
    MoviesTheme {
        // Appelle le composable avec des données factices pour chaque paramètre
        MovieDetailContent(
            title = "Titre du Film Exemple Détaillé",
            overview = "Ceci est un long synopsis pour voir comment le texte s'affiche et s'il faut scroller. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            posterUrl = null, // Affichera le placeholder
            backdropUrl = null, // Affichera le placeholder
            releaseDateFormatted = "2 mai 2025",
            voteAverageFormatted = "8.2/10",
            voteCount = 2500,
            runtimeFormatted = "2h 10min",
            genres = "Action, Science-Fiction, Aventure",
            tagline = "Un slogan accrocheur ici."
        )
    }
}