package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MyMovie
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding
import ru.androidschool.intensiv.db.MovieDatabase
import ru.androidschool.intensiv.db.MovieEntity
import ru.androidschool.intensiv.extensions.extensionsForAnySingle
import ru.androidschool.intensiv.extensions.extensionsForDB
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.feed.FeedFragment
import timber.log.Timber

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {
    private var _binding: MovieDetailsFragmentBinding? = null

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val imageMovieIv by lazy { binding.imageMovieIv }
    private val descTv by lazy { binding.descTv }
    private val titleMovieIv by lazy { binding.titleMovieIv }
    private val valueYearTv by lazy { binding.valueYearTv }
    private val valueGenreTv by lazy { binding.valueGenreTv }
    private val ratingBarRb by lazy { binding.ratingBarRb }

    private val favoriteCh by lazy { binding.favoriteCheckbox }

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

        binding.actorsRv.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        binding.actorsRv.adapter = adapter.apply { addAll(listOf()) }
        var db = context?.let { MovieDatabase.get(it).movies() }

        val movieId = arguments?.getString(FeedFragment.KEY_ID)?.toInt()

        val movieDetails = MovieApiClient.apiClient.getMoviesDetails(movie_id = movieId ?: 1)
        var movieDb: List<MovieEntity> = listOf()

        val disposable1: Disposable = movieDetails.extensionsForAnySingle().subscribe({
            movieDb = listOf(MyMovie.convertToMovieEntity(it)) as List<MovieEntity>
            descTv.text = it.description
            titleMovieIv.text = it.title
            if (it.releaseDate != null) {
                valueYearTv.text =
                    if (it.releaseDate?.length!! >= 4) it.releaseDate?.substring(
                        0,
                        4
                    ) else R.string.year_unknown.toString()
            } else valueYearTv.text = R.string.year_unknown.toString()

            ratingBarRb.rating = it.rating
            valueGenreTv.text = it.genres.toString().removePrefix("[").removeSuffix("]")
            Picasso.get()
                .load(it.backdrop_image)
                .fit()
                .centerCrop()
                .into(imageMovieIv)
        }
        ) { error ->
            Timber.e(error.toString())
        }

        val movieCastDetails =
            MovieApiClient.apiClient.getMoviesCrewDetails(movie_id = movieId ?: 1)

        val disposable: Disposable =
            movieCastDetails.extensionsForAnySingle()
                .subscribe({ it ->
                    val cast = it.castGroup
                    val actorList = cast.map {
                        ActorPreviewItem(
                            it
                        )
                    }.toList()
                    binding.actorsRv.adapter = adapter.apply { addAll(actorList) }
                }
                ) { error ->
                    Timber.e(error.toString())
                }
        compositeDisposable.add(disposable)
        compositeDisposable.add(disposable1)

        if (db != null) {
            val disposableDb = db.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    var myFavoriteMovie = MyMovie.convertListMovieEntityToMovie(it)
                    myFavoriteMovie.forEach {
                        if (it.id == movieId) {
                            favoriteCh.isChecked = true
                        }
                    }
                }
            compositeDisposable.add(disposableDb)
        }

        favoriteCh.setOnCheckedChangeListener { buttonView, isChecked ->
            if (db != null) {
                val disposableDb: Disposable
                if (isChecked) {
                    disposableDb = db.save(movieDb)
                        .extensionsForDB()
                        .subscribe()
                } else {
                    disposableDb = db.delete(movieDb)
                        .extensionsForDB()
                        .subscribe()
                }
                compositeDisposable.add(disposableDb)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        compositeDisposable.dispose()
        adapter.clear()
    }

    companion object {
        private val TAG = "MovieDetailsFragment"
    }
}
