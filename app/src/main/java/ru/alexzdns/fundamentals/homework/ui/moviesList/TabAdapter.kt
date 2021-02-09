package ru.alexzdns.fundamentals.homework.ui.moviesList

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun createFragment(position: Int): Fragment {
        return MoviesListItemFragment.newInstance(itemList[position].path)
    }

    companion object {
        val itemList = MovieLists.values()
    }
}