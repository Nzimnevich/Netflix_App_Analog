package ru.androidschool.intensiv.presentation.tvshows

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
        view.tvShowsRv.adapter = GroupAdapter<GroupieViewHolder>().apply { addAll(items) }
    }

    override fun initializeViewBinding(v: View): TvShowsFragmentBinding =
        TvShowsFragmentBinding.bind(v)
}
