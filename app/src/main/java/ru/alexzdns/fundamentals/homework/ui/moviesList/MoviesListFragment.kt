package ru.alexzdns.fundamentals.homework.ui.moviesList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.domain.models.Movie

class MoviesListFragment : androidx.fragment.app.Fragment() {
    private var listenerMovieList: MovieListClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listenerMovieList = context as? MovieListClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_lists, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pager = view.findViewById<ViewPager2>(R.id.mlf_pager)
        val pageAdapter = TabAdapter(this)
        pager.adapter = pageAdapter

        val tabLayout = view.findViewById<TabLayout>(R.id.mlf_tab_layout)
        val tabLayoutMediator = TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = TabAdapter.itemList[position].nameList
        }
        tabLayoutMediator.attach()
    }


    override fun onDetach() {
        super.onDetach()
        listenerMovieList = null
    }


    interface MovieListClickListener {
        fun openMovieDetailsFragment(movie: Movie)
    }
}