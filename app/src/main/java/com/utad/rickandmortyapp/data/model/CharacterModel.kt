package com.utad.rickandmortyapp.data.model

// Modelo del Personaje
data class CharacterModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String, // URL de la imagen
    val location: LocationModel
)