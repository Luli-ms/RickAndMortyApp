package com.utad.rickandmortyapp.data.model

// Respuesta general de la API para listas
data class CharacterResponse(
    val results: List<CharacterModel>
)