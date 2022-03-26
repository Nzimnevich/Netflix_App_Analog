package ru.androidschool.intensiv.ui.tvshows

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MyMovie
import ru.androidschool.intensiv.databinding.SerialsItemBinding

class TVItem(
    private val content: MyMovie,
    private val onClick: (movie: MyMovie) -> Unit
) : BindableItem<SerialsItemBinding>() {

    override fun getLayout(): Int = R.layout.serials_item

    override fun bind(view: SerialsItemBinding, position: Int) {
        view.titleForSerialsTv.text = content.title
        view.ratingBarRb.rating = content.let { it.rating }

        view.serialItemRl.setOnClickListener {
            onClick.invoke(content)
        }
        var path = content.image.toString()

        Picasso.get()
            .load(path)
            .into(view.imageIv)
    }

    override fun initializeViewBinding(v: View) = SerialsItemBinding.bind(v)
}
