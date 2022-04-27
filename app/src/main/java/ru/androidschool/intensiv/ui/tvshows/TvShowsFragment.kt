package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding
import ru.androidschool.intensiv.extensions.applySchedulers
import ru.androidschool.intensiv.network.MovieApiClient
import timber.log.Timber

class TvShowsFragment() : Fragment() {
    private var _binding: TvShowsFragmentBinding? = null

    private val binding get() = _binding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

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

        val allTV = MovieApiClient.apiClient.getPopularTV()

        val disposable: Disposable = allTV
            .applySchedulers()
            .subscribe(
                { it ->
                    val tv = it.movies
                    val moviesList = tv?.map { TVItem(it) { movies -> } }?.toList()
                    binding.tvShowsRv.adapter = adapter.apply {
                        if (moviesList != null) {
                            addAll(moviesList)
                        }
                    }
                }, { error ->
                Timber.e(error.toString())
            }
            )

        compositeDisposable.add(disposable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        compositeDisposable.clear()
    }

    companion object {
        private val TAG = "TvShowsFragment"
    }
}
