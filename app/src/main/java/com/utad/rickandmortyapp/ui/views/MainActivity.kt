package com.utad.rickandmortyapp.ui.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.utad.rickandmortyapp.R
import com.utad.rickandmortyapp.data.remote.RetrofitClient
import com.utad.rickandmortyapp.data.repository.CharactersRepository
import com.utad.rickandmortyapp.databinding.ActivityMainBinding
import com.utad.rickandmortyapp.ui.viewModel.CharactersViewModel
import com.utad.rickandmortyapp.ui.viewModel.CharactersViewModelFactory
import com.utad.rickandmortyapp.ui.views.adapters.CharactersAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val charactersViewModel: CharactersViewModel by viewModels {
        CharactersViewModelFactory(CharactersRepository(RetrofitClient.apiService))
    }
    private val adapter = CharactersAdapter { navigateToDetail(it) }
    private var categoriaSeleccionada = "todos"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupUi()
        loadSelectedCharacters()
        setupObservers()
    }

    override fun onRestart() {
        super.onRestart()
        loadSelectedCharacters()
    }

    private fun loadSelectedCharacters() {
        if (categoriaSeleccionada == "todos") {
            charactersViewModel.getAllCharacters()
            this.title = "Personajes"
        } else {
            charactersViewModel.getCharactersByStatus(categoriaSeleccionada)
            this.title = when (categoriaSeleccionada) {
                "alive" -> "Vivos"
                "dead" -> "Muertos"
                "unknown" -> "Desconocido"
                else -> "Personajes"
            }
        }
    }

    private fun setupUi() {
        binding.rvCharacters.adapter = adapter
        binding.rvCharacters.layoutManager = LinearLayoutManager(this)
    }

    private fun setupObservers() {
        charactersViewModel.characters.observe(this) { characters ->
            adapter.submitList(characters)
        }
        charactersViewModel.error.observe(this) { e ->
            showSnackBarWithMessage(e)
        }
        charactersViewModel.loading.observe(this) {
            binding.progressBar.visibility = if (it) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.todos -> {
                categoriaSeleccionada = "todos"
            }
            R.id.alive -> {
                categoriaSeleccionada = "alive"
            }
            R.id.dead -> {
                categoriaSeleccionada = "dead"
            }
            R.id.unknown -> {
                categoriaSeleccionada = "unknown"
            }
            else -> return false
        }
        loadSelectedCharacters()
        return true
    }

    private fun navigateToDetail(id: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun showSnackBarWithMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}