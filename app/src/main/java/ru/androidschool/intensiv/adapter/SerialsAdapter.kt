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

class SerialsAdapter(
    private val movies: List<MyMovie>,
    private val rowLayout: Int
) : RecyclerView.Adapter<SerialsAdapter.SerialViewHolder>() {

    class SerialViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var serialsTitle: TextView = v.findViewById(R.id.title_for_serials_tv)
        internal var posterPath: ImageView = v.findViewById(R.id.image_iv)
        internal var rating: RatingBar = v.findViewById(R.id.ratingBar_rb)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SerialViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return SerialViewHolder(view)
    }

    override fun onBindViewHolder(holder: SerialViewHolder, position: Int) {
        val current = movies[position]
        holder.serialsTitle.text = current.title
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