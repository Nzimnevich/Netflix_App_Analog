package ru.androidschool.intensiv.ui.tvshows

import android.view.View
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding

class TvShowContainer(
    private val items: List<BindableItem<*>>
) : BindableItem<TvShowsFragmentBinding>() {

    override fun getLayout(): Int = R.layout.serials_item

    override fun bind(view: TvShowsFragmentBinding, position: Int) {
//        view.titleForSerialsTv.text = view.
//        view.ratingBarRb.rating = content.rating

        view.tvShowsRv.adapter = GroupAdapter<GroupieViewHolder>().apply { addAll(items) }

//        // TODO Получать из модели
//        Picasso.get()
//            .load("https://m.media-amazon.com/images/M/MV5BYTk3MDljOWQtNGI2My00OTEzLTlhYjQtOTQ4ODM2MzUwY2IwXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_.jpg")
//            .into(view.imageIv)
    }

    override fun initializeViewBinding(v: View): TvShowsFragmentBinding = TvShowsFragmentBinding.bind(v)
}
