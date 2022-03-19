package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.CastResponse
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding
import ru.androidschool.intensiv.network.MovieApiClient

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {
    private var _binding: MovieDetailsFragmentBinding? = null
    private val fotoFirstIV: ImageView by lazy { binding.fotoFirstCircleV }
    private val fotoSecondIV: ImageView by lazy { binding.fotoSecondCircleV }
    private val fotoThirdIV: ImageView by lazy { binding.fotoThirdCircleV }
    private val fotoForthIV: ImageView by lazy { binding.fotoForthCircleV }
    var ss: String? = null

    private val binding get() = _binding!!

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

        val allActors = MovieApiClient.apiClient.getMoviesCrewDetails()

        allActors.enqueue(object : Callback<CastResponse> {

            override fun onFailure(call: Call<CastResponse>, error: Throwable) {
                Log.e(TAG, error.toString())
            }

            override fun onResponse(
                call: Call<CastResponse>,
                response: Response<CastResponse>
            ) {

                val actors = response.body()?.cast
                ss = actors?.get(1)?.image
                Picasso.get()
                    .load(ss)
                    .transform(CropCircleTransformation())
                    .into(fotoFirstIV)
            }
        }
        )
//
//        Picasso.get()
//            .load(ss)
//            .transform(CropCircleTransformation())
//            .into(fotoFirstIV)

        Picasso.get()
            .load(R.drawable.iv_amber_heard)
            .transform(CropCircleTransformation())
            .into(fotoSecondIV)

        Picasso.get()
            .load(R.drawable.iv_patrik_wilson)
            .transform(CropCircleTransformation())
            .into(fotoThirdIV)

        Picasso.get()
            .load(R.drawable.iv_nicole_kidman)
            .transform(CropCircleTransformation())
            .into(fotoForthIV)
    }

    companion object {

        private val TAG = MovieDetailsFragment::class.java.simpleName
    }
}
