package com.utad.rickandmortyapp.ui.views

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import com.utad.rickandmortyapp.R
import com.utad.rickandmortyapp.data.remote.RetrofitClient
import com.utad.rickandmortyapp.data.repository.CharactersRepository
import com.utad.rickandmortyapp.databinding.ActivityDetailBinding
import com.utad.rickandmortyapp.ui.viewModel.CharactersViewModel
import com.utad.rickandmortyapp.ui.viewModel.CharactersViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val charactersViewModel: CharactersViewModel by viewModels {
        CharactersViewModelFactory(CharactersRepository(RetrofitClient.apiService))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val id = intent.getIntExtra("id", 0)
        charactersViewModel.getCharacterById(id)
        charactersViewModel.character.observe(this) { character ->
            this.title = character.name
            Picasso.get().load(character.image).into(binding.ivCharacter)
            binding.tvName.text = character.name
            binding.tvStatus.text = character.status
        }
    }
}