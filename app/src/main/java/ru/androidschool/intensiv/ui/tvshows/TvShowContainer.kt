package ru.androidschool.intensiv.ui.tvshows

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Movie
import ru.androidschool.intensiv.databinding.SerialsItemBinding

class TvShowContainer(
    private val content: Movie,
    movie: (Movie) -> Unit
) : BindableItem<SerialsItemBinding>() {

    override fun getLayout(): Int = R.layout.serials_item

    override fun bind(view: SerialsItemBinding, position: Int) {
        view.titleTv.text = content.title
        view.ratingBarRb.rating = content.rating

        // TODO Получать из модели
        Picasso.get()
            .load("https://m.media-amazon.com/images/M/MV5BYTk3MDljOWQtNGI2My00OTEzLTlhYjQtOTQ4ODM2MzUwY2IwXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_.jpg")
            .into(view.imageIv)
    }

    override fun initializeViewBinding(v: View) = SerialsItemBinding.bind(v)
}
