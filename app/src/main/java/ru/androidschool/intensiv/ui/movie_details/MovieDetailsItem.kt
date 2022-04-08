package ru.androidschool.intensiv.ui.movie_details

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDetailsResponse
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding

class MovieDetailsItem(
    private val content: MovieDetailsResponse
) : BindableItem<MovieDetailsFragmentBinding>() {

    override fun getLayout() = R.layout.movie_details_fragment

    override fun bind(view: MovieDetailsFragmentBinding, position: Int) {
        view.descTv.text = content.description
        view.titleMovieIv.text = content.title
        view.valueYearTv.text = content.releaseDate
        view.ratingBarRb.rating = content.rating
        view.valueGenreTv.text = content.genres.toString()
        Picasso.get()
            .load(content.backdrop_image)
            .into(view.imageMovieIv)
    }

    override fun initializeViewBinding(v: View): MovieDetailsFragmentBinding = MovieDetailsFragmentBinding.bind(v)
}
