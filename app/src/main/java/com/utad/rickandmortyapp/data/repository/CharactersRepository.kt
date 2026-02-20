package com.utad.rickandmortyapp.data.repository

import com.utad.rickandmortyapp.data.model.CharacterModel
import com.utad.rickandmortyapp.data.remote.CharactersApiService

class CharactersRepository(
    private val apiService: CharactersApiService
) {
    suspend fun getAllCharacters(): List<CharacterModel> {
        val charactersResponse = apiService.getAllCharacters()
        if (charactersResponse.isSuccessful) {
            return charactersResponse.body()?.results ?: emptyList()
        } else {
            throw Exception("Error al obtener los personajes")
        }
    }

    suspend fun getCharactersByStatus(status: String): List<CharacterModel> {
        val charactersResponse = apiService.getCharactersByStatus(status)
        if (charactersResponse.isSuccessful) {
            return charactersResponse.body()?.results ?: emptyList()
        } else {
            throw Exception("Error al obtener los personajes")
        }
    }

    suspend fun getCharacterById(id: Int): CharacterModel {
        val charactersResponse = apiService.getCharacterById(id)
        if (charactersResponse.isSuccessful) {
            return charactersResponse.body() ?: throw Exception("Personaje no encontrado")
        } else {
            throw Exception("Error al obtener los personajes")
        }
    }
}