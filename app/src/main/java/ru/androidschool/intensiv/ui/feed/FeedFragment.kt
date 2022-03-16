package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.BuildConfig.THE_MOVIE_DATABASE_API
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieResponse
import ru.androidschool.intensiv.data.MyMovie
import ru.androidschool.intensiv.databinding.FeedFragmentBinding
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.afterTextChanged
import timber.log.Timber

class FeedFragment : Fragment(R.layout.feed_fragment) {

    private var _binding: FeedFragmentBinding? = null
    private var _searchBinding: FeedHeaderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val searchBinding get() = _searchBinding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FeedFragmentBinding.inflate(inflater, container, false)
        _searchBinding = FeedHeaderBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBinding.searchToolbar.binding.searchEditText.afterTextChanged {
            Timber.d(it.toString())
            if (it.toString().length > MIN_LENGTH) {
                openSearch(it.toString())
            }
        }

        val allMovies = MovieApiClient.apiClient.getAllMovies(THE_MOVIE_DATABASE_API, "ru")

        allMovies.enqueue(object : Callback<MovieResponse> {

            override fun onFailure(call: Call<MovieResponse>, error: Throwable) {
                Log.e(TAG, error.toString())
            }

            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {

                val movies = response.body()?.movies

                var items = movies?.let { getMovieForUI(it, R.string.recommended) }
                binding.moviesRecyclerView.adapter = adapter.apply {
                    if (items != null) {
                        addAll(items)
                    }
                }
            }
        })

        // Популярные
        val popularsMovies = MovieApiClient.apiClient.getPopularMovies(THE_MOVIE_DATABASE_API, "ru")
        popularsMovies.enqueue(object : Callback<MovieResponse> {

            override fun onFailure(call: Call<MovieResponse>, error: Throwable) {
                Log.e(TAG, error.toString())
            }

            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {

                val movies = response.body()?.movies

                var popularItems = movies?.let { getMovieForUI(it, R.string.popular) }
                binding.moviesRecyclerView.adapter = adapter.apply {
                    if (popularItems != null) {
                        addAll(popularItems)
                    }
                }
            }
        })
    }

    fun getMovieForUI(movies: List<MyMovie>, intResourses: Int): List<MainCardContainer> {
        var items: List<MainCardContainer> = listOf(
            MainCardContainer(
                intResourses,
                movies.map {
                    MovieItem(it) { movie ->
                        openMovieDetails(
                            movie
                        )
                    }
                }.toList()
            )
        )
        return items
    }

    private fun openMovieDetails(movie: MyMovie) {
        val bundle = Bundle()
        bundle.putString(KEY_TITLE, movie.title)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun openSearch(searchText: String) {
        val bundle = Bundle()
        bundle.putString(KEY_SEARCH, searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    override fun onStop() {
        super.onStop()
        searchBinding.searchToolbar.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _searchBinding = null
    }

    companion object {
        const val MIN_LENGTH = 3
        const val KEY_TITLE = "title"
        const val KEY_SEARCH = "search"

        private val TAG = FeedFragment::class.java.simpleName
    }
}
