package ru.androidschool.intensiv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MyMovie

class MoviesAdapter(
    private val movies: List<MyMovie>,
    private val rowLayout: Int
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    class MovieViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var movieTitle: TextView = v.findViewById(R.id.description)
        internal var posterPath: ImageView = v.findViewById(R.id.image_preview)
        internal var rating: RatingBar = v.findViewById(R.id.movie_rating)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val current = movies[position]
        holder.movieTitle.text = current.title
        holder.rating.rating = current.rating!!

        Picasso.get()
            .load(current.image)
            .fit()
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.posterPath)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
