package com.utad.rickandmortyapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utad.rickandmortyapp.data.model.CharacterModel
import com.utad.rickandmortyapp.data.repository.CharactersRepository
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val repository: CharactersRepository
): ViewModel() {
    private val _characters = MutableLiveData<List<CharacterModel>>()
    val characters: LiveData<List<CharacterModel>> = _characters
    private val _character = MutableLiveData<CharacterModel>()
    val character: LiveData<CharacterModel> = _character
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getAllCharacters() {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                _characters.postValue(repository.getAllCharacters())
            } catch (e: Exception) {
                _error.postValue(e.message)
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun getCharactersByStatus(status: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                _characters.postValue(repository.getCharactersByStatus(status))
            } catch (e: Exception) {
                _error.postValue(e.message)
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun getCharacterById(id: Int) {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                _character.postValue(repository.getCharacterById(id))
            } catch (e: Exception) {
                _error.postValue(e.message)
            } finally {
                _loading.postValue(false)
            }
        }
    }
}