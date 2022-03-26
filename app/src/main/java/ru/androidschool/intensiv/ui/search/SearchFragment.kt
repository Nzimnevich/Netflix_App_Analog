package ru.androidschool.intensiv.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Transformations.map
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.databinding.FragmentSearchBinding
import ru.androidschool.intensiv.ui.feed.FeedFragment.Companion.KEY_SEARCH
import ru.androidschool.intensiv.ui.tvshows.TvShowsFragment
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private var _searchBinding: FeedHeaderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val searchBinding get() = _searchBinding!!
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

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
//        searchBinding.searchToolbar.setText(searchTerm)

        val disposable: Disposable = searchBinding.searchToolbar.onTextChangedObservable
            .subscribeOn(Schedulers.io())
            .map { it.trim() }
            .filter { it> 3.toString() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it
                Timber.d(it.toString())
            }, {
                Log.e(TAG, it.toString())
            })

        searchBinding.searchToolbar.setText(disposable.toString())
        compositeDisposable.add(disposable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _searchBinding = null
        compositeDisposable.dispose()
    }

    companion object {
        private val TAG = TvShowsFragment::class.java.simpleName
    }
}
