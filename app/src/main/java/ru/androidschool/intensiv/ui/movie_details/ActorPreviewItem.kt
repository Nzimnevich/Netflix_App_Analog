package ru.androidschool.intensiv.ui.movie_details

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.FakeActor
import ru.androidschool.intensiv.databinding.ActorInfoBinding

class ActorPreviewItem(
    private val content: FakeActor,
    param: (FakeActor) -> Unit
) : BindableItem<ActorInfoBinding>() {

    override fun getLayout() = R.layout.actor_info

    override fun bind(view: ActorInfoBinding, position: Int) {
        view.firstNameActorTv.text = content.title
        view.fotoFirstCircleV.setImageResource(content.resId)
    }
    override fun initializeViewBinding(v: View): ActorInfoBinding = ActorInfoBinding.bind(v)
}
