package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieResponse
import ru.androidschool.intensiv.data.MyMovie
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.feed.MovieItem

class TvShowsFragment : Fragment() {
    private var _binding: TvShowsFragmentBinding? = null

    private val binding get() = _binding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TvShowsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvShowsRv.layoutManager = LinearLayoutManager(context)
        binding.tvShowsRv.adapter = adapter.apply { addAll(listOf()) }

        val allTV = MovieApiClient.apiClient.getPopularTV(BuildConfig.THE_MOVIE_DATABASE_API, "ru")
        allTV.enqueue(object : Callback<MovieResponse> {

            override fun onFailure(call: Call<MovieResponse>, error: Throwable) {
                Log.e(TAG, error.toString())
            }

            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {

                val movies = response.body()?.movies

                var items: List<TvShowContainer>? = null

                if (movies != null) {
                    items = listOf(
                        TvShowContainer(
                            movies.map {
                                MovieItem(it) { movie ->
                                    (
                                        movie
                                    )
                                }
                            }.toList()
                        )
                    )
                }

//                movies?.let {
                binding.tvShowsRv.adapter = adapter.apply {
                    if (items != null) {
                        addAll(items)
                    }
                }

                // SerialsAdapter(movies, R.layout.serials_item)
//                }
            }
        }
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = TvShowsFragment()

        private val TAG = TvShowsFragment::class.java.simpleName
    }
}
