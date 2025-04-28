package com.epsi.movies.data.network

import com.epsi.movies.common.Common.API_KEY_PARAM
import com.epsi.movies.common.Common.API_KEY_VALUE
import com.epsi.movies.common.Common.BASE_URL
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object NetworkModule {
    // Intercepteur OkHttp pour ajouter la clé API à chaque requête
    private val apiKeyInterceptor: Interceptor = Interceptor { chain ->
        val originalRequest: Request = chain.request()
        val originalHttpUrl: HttpUrl = originalRequest.url

        // Ajoute le paramètre 'api_key' à l'URL
        val urlWithApiKey: HttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(API_KEY_PARAM, API_KEY_VALUE)
            .build()

        // Construit la nouvelle requête avec l'URL modifiée
        val requestBuilder: Request.Builder = originalRequest.newBuilder()
            .url(urlWithApiKey)

        val newRequest: Request = requestBuilder.build()
        chain.proceed(newRequest) // Exécute la nouvelle requête
    }

    // Intercepteur OkHttp pour le logging (affiche les détails dans Logcat)
    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        // Mettre Level.BODY en développement pour tout voir,
        // Mettre Level.NONE ou BASIC en production.
        level = HttpLoggingInterceptor.Level.BODY
    }
    // Instance unique (Singleton) du service API
    // Utilisation de 'lazy' pour créer l'instance seulement lors du premier accès.
    val moviesApiService: MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }

    // Configuration du client OkHttp
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        // Ajout de l'intercepteur pour la clé API (voir ci-dessous)
        .addInterceptor(apiKeyInterceptor)
        // Ajout de l'intercepteur pour logger les requêtes/réponses (très utile en debug)
        .addInterceptor(loggingInterceptor)
        // Définition de timeouts (bonne pratique)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Configuration de Retrofit
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL) // URL de base de l'API TMDB
        .client(okHttpClient) // Utilisation du client OkHttp configuré ci-dessous
        .addConverterFactory(GsonConverterFactory.create()) // Convertisseur JSON -> Objets Kotlin
        .build()





}