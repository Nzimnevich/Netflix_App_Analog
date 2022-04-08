package ru.androidschool.intensiv.ui.movie_details

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.actorDetail
import ru.androidschool.intensiv.databinding.ActorInfoBinding
import ru.androidschool.intensiv.utils.CircleTransform

class ActorPreviewItem(
    private val content: actorDetail
) : BindableItem<ActorInfoBinding>() {

    override fun getLayout() = R.layout.actor_info

    override fun bind(view: ActorInfoBinding, position: Int) {
        view.firstNameActorTv.text = content.name

        Picasso.get()
            .load(content.profilePath)
            .transform(CircleTransform())
            .into(view.fotoFirstCircleV)
    }

    override fun initializeViewBinding(v: View): ActorInfoBinding = ActorInfoBinding.bind(v)
}
