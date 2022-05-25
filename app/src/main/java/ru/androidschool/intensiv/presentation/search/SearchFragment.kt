package ru.androidschool.intensiv.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.dto.MyMovie
import ru.androidschool.intensiv.data.mappers.MovieMapper
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.databinding.FragmentSearchBinding
import ru.androidschool.intensiv.extensions.applySchedulers
import ru.androidschool.intensiv.extensions.setLoaderForObservable
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.presentation.feed.FeedFragment
import ru.androidschool.intensiv.presentation.feed.FeedFragment.Companion.KEY_SEARCH
import timber.log.Timber

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private var _searchBinding: FeedHeaderBinding? = null
    private val progressBar by lazy { binding.progressBar }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val searchBinding get() = _searchBinding!!
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        _searchBinding = FeedHeaderBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchTerm = requireArguments().getString(KEY_SEARCH)
        if (searchTerm != null) {
            searchBinding.searchToolbar.setText(searchTerm)
        }
        val disposable: Disposable =
            searchBinding.searchToolbar.onTextChangedWithOperatorObservable
                .setLoaderForObservable(progressBar).subscribe(
                    {
                        val searchMovies = MovieApiClient.apiClient.getSearchMovies(query = it)
                        val searchMovieDisposable: Disposable =
                            searchMovies.applySchedulers().subscribe({ it ->
                                val movies = it.movies
                                val items = movies?.let {
                                    movieMapper.getMovieForUI(
                                        it,
                                        R.string.we_find,
                                        ::openMovieDetails
                                    )
                                }
                                binding.actorsRv.adapter = adapter.apply {
                                    if (items != null) {
                                        addAll(items)
                                    }
                                }
                            }
                            ) { error ->
                                Timber.e(error.toString())
                            }

                        compositeDisposable.add(searchMovieDisposable)
                        Timber.d(it.toString())
                    },
                    { error ->
                        Timber.e("$TAG:$error")
                    }
                )

        compositeDisposable.add(disposable)
    }

    private fun openMovieDetails(movie: MyMovie) {
        val bundle = Bundle()

        bundle.putString(FeedFragment.KEY_ID, movie.id.toString())
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _searchBinding = null
        compositeDisposable.clear()
        adapter.clear()
    }

    companion object {
        private val movieMapper = MovieMapper
        private val TAG = "TvShowsFragment"
    }
}
