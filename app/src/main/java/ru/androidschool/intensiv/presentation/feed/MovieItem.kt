package ru.androidschool.intensiv.presentation.feed

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.dto.MyMovie
import ru.androidschool.intensiv.databinding.ItemWithTextBinding

class MovieItem(
    private val content: MyMovie,
    private val onClick: (movie: MyMovie) -> Unit
) : BindableItem<ItemWithTextBinding>() {

    override fun getLayout(): Int = R.layout.item_with_text

    override fun bind(view: ItemWithTextBinding, position: Int) {
        view.description.text = content.title
        view.movieRating.rating = content.rating!!
        view.content.setOnClickListener {
            onClick.invoke(content)
        }
        var path = content.image.toString()

        Picasso.get()
            .load(path)
            .into(view.imagePreview)
    }

    override fun initializeViewBinding(v: View) = ItemWithTextBinding.bind(v)
}
