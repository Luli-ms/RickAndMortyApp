package com.utad.rickandmortyapp.ui.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.utad.rickandmortyapp.data.model.CharacterModel
import com.utad.rickandmortyapp.databinding.ItemCharacterBinding

class CharactersAdapter(
    private val onClickListener: (Int) -> Unit
) : ListAdapter<CharacterModel, CharacterViewHolder>(CharacterDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CharacterViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), onClickListener)
    }
}

object CharacterDiffCallback : DiffUtil.ItemCallback<CharacterModel>() {

    override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
        return oldItem == newItem
    }
}
