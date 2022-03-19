package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.FakeActorRepository
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {
    private var _binding: MovieDetailsFragmentBinding? = null

    //    private val fotoFirstIV: ImageView by lazy { binding.fotoFirstCircleV }
//    private val fotoSecondIV: ImageView by lazy { binding.fotoSecondCircleV }
//    private val fotoThirdIV: ImageView by lazy { binding.fotoThirdCircleV }
//    private val fotoForthIV: ImageView by lazy { binding.fotoForthCircleV }
    private val actorsRecyclerView: RecyclerView by lazy { binding.actorsRecyclerView }
    var ss: String? = null

    private val binding get() = _binding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieDetailsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actorsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.actorsRecyclerView.adapter = adapter.apply { addAll(listOf()) }

        val actorList =
            FakeActorRepository().getActors().map {
                ActorPreviewItem(
                    it
                ) { actor -> }
            }.toList()

        binding.actorsRecyclerView.adapter = adapter.apply { addAll(actorList) }
    }

    companion object {
        private val TAG = MovieDetailsFragment::class.java.simpleName
    }
}
