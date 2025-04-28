package com.epsi.movies.data.network

import com.google.gson.annotations.SerializedName

/**
 * Représente la réponse de l'API pour une liste de films (ex: populaires, recherche).
 * C'est une "enveloppe" contenant la liste réelle des films et les informations de pagination.
 */
data class MovieListResponseNetwork(
    // Le numéro de la page actuelle des résultats.
    @SerializedName("page") val page: Int,

    // La liste des objets ApiMovie pour cette page.
    @SerializedName("results") val results: List<ApiMovie>,

    // Le nombre total de pages disponibles pour cette requête.
    @SerializedName("total_pages") val totalPages: Int,

    // Le nombre total de films trouvés correspondant à la requête sur toutes les pages.
    @SerializedName("total_results") val totalResults: Int
)
