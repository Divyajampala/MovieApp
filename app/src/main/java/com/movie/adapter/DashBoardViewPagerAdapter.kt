package com.movie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.movie.services.MovieCategory
import com.movie.view.MovieListFragment

class DashBoardViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm!!) {

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment
        when (position) {
            0 -> fragment = MovieListFragment.getInstance(MovieCategory.now_playing)
            1 -> fragment = MovieListFragment.getInstance(MovieCategory.popular)
            2 -> fragment = MovieListFragment.getInstance(MovieCategory.top_rated)
            else -> fragment = MovieListFragment.getInstance(MovieCategory.upcoming)
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return MovieCategory.values()[position].title
    }

    override fun getCount(): Int {
        return MovieCategory.values().size
    }
}