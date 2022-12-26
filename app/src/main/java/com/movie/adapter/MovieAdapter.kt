package com.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.DataBindingUtil
import com.movie.databinding.MovieCardViewItemBinding
import com.movie.model.Genre
import com.movie.model.Result

class MovieAdapter(
    var movieList: List<Result>,
    var genreList: List<Genre>,
    var onClick: (id: Int) -> Unit
) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(var item: MovieCardViewItemBinding) :
        RecyclerView.ViewHolder(item.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding: MovieCardViewItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            com.movie.R.layout.movie_card_view_item,
            parent,
            false
        )
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        movieList.get(position).let {
            holder.item.movie = it
            holder.item.rvGenreList.adapter = it.genre_ids?.flatMap { genreIds ->
                genreList.filter { genreIds == it.id }
            }?.mapNotNull { genre -> genre.name }
                ?.let { list -> GenreAdapter(list) }
        }
        holder.item.movieCardItem.setOnClickListener {
            movieList.get(position).id?.let { id -> onClick.invoke(id) }
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}