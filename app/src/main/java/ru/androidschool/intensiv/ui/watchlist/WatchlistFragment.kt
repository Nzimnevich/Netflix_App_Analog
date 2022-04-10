package ru.androidschool.intensiv.ui.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.data.MyMovie
import ru.androidschool.intensiv.databinding.FragmentWatchlistBinding
import ru.androidschool.intensiv.db.MovieDao
import ru.androidschool.intensiv.db.MovieDatabase

class WatchlistFragment : Fragment() {

    private var _binding: FragmentWatchlistBinding? = null
    private lateinit var db: MovieDao
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        db = context?.let { MovieDatabase.get(it).movies() }!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, 4)
        binding.moviesRecyclerView.adapter = adapter.apply { addAll(listOf()) }

//        val moviesList =
//            MockRepository.getMovies().map {
//                MoviePreviewItem(
//                    it
//                ) { movie -> }
//            }.toList()

        var result = db?.getMovies()?.map {
            MyMovie.convertToMovie(it)
        }?.map {
            MoviePreviewItem(
                it
            ) { movie -> }
        }?.toList()

        binding.moviesRecyclerView.adapter = adapter.apply {
            if (result != null) {
                addAll(result)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = WatchlistFragment()
    }
}
