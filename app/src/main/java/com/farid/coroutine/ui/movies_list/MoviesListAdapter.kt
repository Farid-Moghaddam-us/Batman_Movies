package com.farid.coroutine.ui.movies_list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farid.coroutine.R
import com.farid.coroutine.databinding.MoviesRecyclerItemBinding
import com.farid.coroutine.data.model.Movie

class MoviesListAdapter(private val listener: MovieItemListener) :
    RecyclerView.Adapter<MyViewHolder>() {

    private val movies = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val moviesRecyclerItemBinding: MoviesRecyclerItemBinding =
            MoviesRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return MyViewHolder(moviesRecyclerItemBinding, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    private fun getItem(position: Int): Movie {
        return movies[position]
    }

    fun setItems(items: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(items)
        notifyDataSetChanged()
    }

}

class MyViewHolder(
    private val itemBinding: MoviesRecyclerItemBinding,
    private val movieItemListener: MovieItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var movie: Movie

    fun bind(item: Movie) {
        this.movie = item
        itemBinding.model = item
        if (item.type == "movie")
            itemBinding.type.setCardBackgroundColor(itemBinding.root.context.resources.getColor(R.color.movies_card_color))
        else itemBinding.type.setCardBackgroundColor(itemBinding.root.context.resources.getColor(R.color.series_card_color))
    }

    override fun onClick(p0: View?) {
        movieItemListener.onClickMovie(movie.imdbID)
    }

    init {
        itemBinding.root.setOnClickListener(this)
    }

}

interface MovieItemListener {
    fun onClickMovie(imdbId: String)
}