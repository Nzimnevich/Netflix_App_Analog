package ru.androidschool.intensiv.ui.profile

import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.FragmentProfileBinding
import ru.androidschool.intensiv.db.MovieDao
import ru.androidschool.intensiv.db.MovieDatabase

class ProfileFragment : Fragment() {

    private lateinit var profileTabLayoutTitles: Array<String>

    private var _binding: FragmentProfileBinding? = null
    private lateinit var db: MovieDao
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var profilePageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            Toast.makeText(
                requireContext(),
                "Selected position: $position",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        db = context?.let { MovieDatabase.get(it).movies() }!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Picasso.get()
//            .load(R.drawable.ic_avatar)
//            .transform(CropCircleTransformation())
//            .placeholder(R.drawable.ic_avatar)
//            .into(binding.avatar)

        profileTabLayoutTitles = resources.getStringArray(R.array.tab_titles)

        val profileAdapter = ProfileAdapter(
            this,
            profileTabLayoutTitles.size
        )
        binding.profileViewPager.adapter = profileAdapter

        binding.profileViewPager.registerOnPageChangeCallback(
            profilePageChangeCallback
        )

        TabLayoutMediator(binding.tabLayout, binding.profileViewPager) { tab, position ->

            // Выделение первой части заголовка таба
            // Название таба
            val title = profileTabLayoutTitles[position]
            // Раздеряем название на части. Первый элемент будет кол-во
            val parts = profileTabLayoutTitles[position].split(" ")
            val number = parts[0]
            val spannableStringTitle = SpannableString(title)
            spannableStringTitle.setSpan(RelativeSizeSpan(2f), 0, number.count(), 0)

            tab.text = spannableStringTitle
        }.attach()

//        var result = db?.getMovies()
//        if (result != null) {
//            if (result.size != 0) {
//                Picasso.get()
//                    .load(result[0].path)
//                    .transform(CropCircleTransformation())
//                    .placeholder(R.drawable.ic_avatar)
//                    .into(binding.avatar)
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
