package com.epsi.movies.presentation.ui.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.epsi.movies.R
import com.epsi.movies.presentation.ui.theme.MoviesTheme

/**
 * Composable affichant une carte pour un film dans une liste.
 * Affiche l'affiche à gauche et les informations principales à droite,
 * ainsi qu'une icône étoile pour indiquer l'état de favori.
 * Reçoit les données via des paramètres individuels.
 *
 * @param movieId L'ID unique du film (pour l'action de clic).
 * @param title Le titre du film à afficher.
 * @param posterUrl L'URL complète de l'affiche du film (peut être null).
 * @param releaseDateFormatted La date de sortie déjà formatée pour l'affichage.
 * @param voteAverageFormatted La note moyenne déjà formatée pour l'affichage.
 * @param isFavorite Indique si le film est marqué comme favori.
 * @param onCardClick Lambda appelé lorsque la carte est cliquée, passant l'ID du film.
 * @param onFavoriteClick Lambda appelé lorsque l'icône étoile est cliquée, passant l'ID du film.
 * @param modifier Modificateur Compose pour personnaliser l'apparence ou le layout.
 */
@Composable
fun MovieCard(
    movieId: Int,
    title: String,
    posterUrl: String?,
    releaseDateFormatted: String,
    voteAverageFormatted: String,
    isFavorite: Boolean, //  paramètre pour l'état de favori
    onCardClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit, //  lambda pour le clic sur l'étoile
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { onCardClick(movieId) }, // Clic sur la carte entière
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // --- Affiche du Film (Gauche) ---
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(posterUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.img)
                    .error(R.drawable.img)
                    .build(),
                contentDescription = "Affiche de $title",
                modifier = Modifier
                    .size(width = 80.dp, height = 120.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // --- Détails du Film (Centre) ---
            Column(
                modifier = Modifier.weight(1f) // Prend l'espace disponible au centre
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = releaseDateFormatted,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Note: $voteAverageFormatted",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // --- Icône Favori (Droite) ---
            IconButton(
                onClick = { onFavoriteClick(movieId) }, // Appelle le lambda spécifique au clic sur l'étoile
                modifier = Modifier.align(Alignment.Top) // Aligne l'icône en haut de la Row
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                    contentDescription = if (isFavorite) "Retirer des favoris" else "Ajouter aux favoris",
                    tint = if (isFavorite) Color.Yellow else Color.Gray // Couleur jaune si favori, gris sinon
                )
            }
        }
    }
}

// --- Preview ---
@Preview(showBackground = true, name = "Movie Card - Not Favorite")
@Composable
fun MovieCardPreviewNotFavorite() {
    MoviesTheme {
        MovieCard(
            movieId = 1,
            title = "Exemple de Film Pas Favori",
            posterUrl = null,
            releaseDateFormatted = "3 mai 2025",
            voteAverageFormatted = "6.5/10",
            isFavorite = false, // Test état non favori
            onCardClick = { },
            onFavoriteClick = { }
        )
    }
}

@Preview(showBackground = true, name = "Movie Card - Favorite")
@Composable
fun MovieCardPreviewFavorite() {
    MoviesTheme {
        MovieCard(
            movieId = 2,
            title = "Autre Film Qui Est Favori Avec un Titre Assez Long",
            posterUrl = null,
            releaseDateFormatted = "10 juin 2025",
            voteAverageFormatted = "8.2/10",
            isFavorite = true, // Test état favori
            onCardClick = { },
            onFavoriteClick = { }
        )
    }
}