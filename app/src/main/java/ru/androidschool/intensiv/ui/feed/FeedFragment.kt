package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MyMovie
import ru.androidschool.intensiv.databinding.FeedFragmentBinding
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.extensions.extensionsForObservable
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.afterTextChanged
import timber.log.Timber

class FeedFragment : Fragment(R.layout.feed_fragment) {

    private var _binding: FeedFragmentBinding? = null
    private var _searchBinding: FeedHeaderBinding? = null
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val searchBinding get() = _searchBinding!!

    private val adapter1 by lazy {
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
            if (it.toString().length > MIN_LENGTH) { // todo тут стоит переделать на лисенер кнопки
                openSearch(it.toString())
            }
        }

        val allMovies = MovieApiClient.apiClient.getAllMovies()

        val disposable = allMovies.extensionsForObservable()
            .subscribe(
                { it ->
                    val movies = it.movies
                    var items = movies?.let { getMovieForUI(it, R.string.recommended) }
                    binding.moviesRecyclerView.adapter = adapter1.apply {
                        if (items != null) {
                            addAll(items)
                        }
                    }
                }, { error ->
                Timber.e(error.toString())
            }
            )

        compositeDisposable.add(disposable)

        // Популярные
        val popularsMovies = MovieApiClient.apiClient.getPopularMovies()

        val popularMoviesDisposable = popularsMovies
            .extensionsForObservable()
            .subscribe(
                { it ->
                    val movies = it.movies
                    // Передаем результат в adapter и отображаем элементы
                    var items = movies?.let { getMovieForUI(it, R.string.popular) }
                    binding.moviesRecyclerView.adapter = adapter1.apply {
                        if (items != null) {
                            addAll(items)
                        }
                    }
                }, { error ->
                Timber.e(error.toString())
            }

            )
        compositeDisposable.add(popularMoviesDisposable)
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
        bundle.putString(KEY_ID, movie.id.toString())
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun openSearch(searchText: String) {
        val bundle = Bundle()
        bundle.putString(KEY_SEARCH, searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    override fun onStop() {
        super.onStop()
        adapter1.clear()
        searchBinding.searchToolbar.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
        _binding = null
        _searchBinding = null
    }

    companion object {
        const val MIN_LENGTH = 3
        const val KEY_TITLE = "title"
        const val KEY_SEARCH = "search"
        const val KEY_ID = "1"

        private val TAG = FeedFragment::class.java.simpleName
    }
}
