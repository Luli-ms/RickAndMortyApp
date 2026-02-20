package com.utad.rickandmortyapp.ui.views.adapters

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.utad.rickandmortyapp.data.model.CharacterModel
import com.utad.rickandmortyapp.databinding.ItemCharacterBinding

class CharacterViewHolder(
    private val binding: ItemCharacterBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CharacterModel, onClickListener: (id: Int) -> Unit) {
        binding.tvName.text = item.name
        Picasso.get().load(item.image).into(binding.ivCharacter)
        binding.itemX.setOnClickListener {
            onClickListener(item.id)
        }
    }
}