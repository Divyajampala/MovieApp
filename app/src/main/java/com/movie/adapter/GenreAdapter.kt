package com.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.DataBindingUtil
import com.movie.databinding.GenreCardViewItemBinding

class GenreAdapter(var genreList: List<String>) :
    RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    inner class GenreViewHolder(var item: GenreCardViewItemBinding) :
        RecyclerView.ViewHolder(item.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val itemBinding: GenreCardViewItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            com.movie.R.layout.genre_card_view_item,
            parent,
            false
        )
        return GenreViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        genreList.get(position).also { holder.item.genre.text = it }
    }

    override fun getItemCount(): Int {
        return genreList.size
    }
}