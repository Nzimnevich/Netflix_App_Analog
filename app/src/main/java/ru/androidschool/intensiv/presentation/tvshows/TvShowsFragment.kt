package ru.androidschool.intensiv.presentation.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import ru.androidschool.intensiv.data.repository.TVShowsRemoteRepository
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding
import ru.androidschool.intensiv.domain.usecase.TVShowsUseCase

class TvShowsFragment : Fragment(), TVShowsPresenter.TVShowsView {
    private var _binding: TvShowsFragmentBinding? = null

    private val binding get() = _binding!!

    private val presenter: TVShowsPresenter by lazy {
        TVShowsPresenter(TVShowsUseCase(TVShowsRemoteRepository()))
    }

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
        presenter.attachView(this)

        binding.tvShowsRv.layoutManager = LinearLayoutManager(context)
        binding.tvShowsRv.adapter = adapter.apply { addAll(listOf()) }

        presenter.getMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        compositeDisposable.clear()
    }

    companion object {
        private val TAG = "TvShowsFragment"
    }

    override fun showMovies(movies: List<TVItem>) {
        binding.tvShowsRv.adapter = adapter.apply {
            if (movies != null) {
                addAll(movies)
            }
        }
    }

    override fun showEmptyMovies() {
        TODO("Not yet implemented")
    }
}
