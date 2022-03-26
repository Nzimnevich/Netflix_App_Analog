package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding
import ru.androidschool.intensiv.network.MovieApiClient

class TvShowsFragment() : Fragment() {
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

        val allTV = MovieApiClient.apiClient.getPopularTV()

        allTV
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
                // Логируем ошибку
                Log.e(TAG, error.toString())
            }
            )

//        allTV.enqueue(object : Callback<MovieResponse> {
//
//            override fun onFailure(call: Call<MovieResponse>, error: Throwable) {
//                Log.e(TAG, error.toString())
//            }
//
//            override fun onResponse(
//                call: Call<MovieResponse>,
//                response: Response<MovieResponse>
//            ) {
//
//                val movies = response.body()?.movies
//
//                val moviesList = movies?.map { TVItem(it) { movies -> } }?.toList()
//                binding.tvShowsRv.adapter = adapter.apply {
//                    if (moviesList != null) {
//                        addAll(moviesList)
//                    }
//                }
//            }
//        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAG = TvShowsFragment::class.java.simpleName
    }
}
