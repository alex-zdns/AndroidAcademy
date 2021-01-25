package ru.alexzdns.fundamentals.homework.ui.moviesList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.domain.models.Movie
import ru.alexzdns.fundamentals.homework.ui.moviesList.MoviesListViewModel.State

class MoviesListFragment : androidx.fragment.app.Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private val viewModel: MoviesListViewModel by viewModels { MoviesListViewModelFactory() }

    private var listenerMovieList: MovieListClickListener? = null
    private var recycler: RecyclerView? = null
    private var loader: SwipeRefreshLayout? = null

    private val adapter = MoviesAdapter(object : MoviesAdapter.OnRecyclerMovieItemClicked {
        override fun onBannerClick(movie: Movie) {
            listenerMovieList?.openMovieDetailsFragment(movie)
        }

        override fun onLikeClick(movie: Movie) {
            viewModel.onLikeHandle(movie)
        }
    })


    override fun onAttach(context: Context) {
        super.onAttach(context)
        listenerMovieList = context as? MovieListClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.mlf_movie_list)
        recycler?.adapter = adapter

        loader = view.findViewById(R.id.fml_swipe_container)
        loader?.setOnRefreshListener(this)

        viewModel.state.observe(this.viewLifecycleOwner, this::setState)
        viewModel.moviesList.observe(this.viewLifecycleOwner, this::updateMoviesList)

        if (viewModel.state.value is State.Default) viewModel.getMoviesFromDbAndServer()
    }

    private fun setState(state: State) =
        when (state) {
            is State.Default,
            is State.Success-> {
                setLoading(false)
            }
            is State.Loading -> {
                setLoading(true)
            }
            is State.Error -> {
                setLoading(false)
                Toast.makeText(context, getString(R.string.loading_movies_error_message), Toast.LENGTH_LONG).show()
            }
        }

    private fun setLoading(loading: Boolean) {
        loader?.isRefreshing = loading
    }

    private fun updateMoviesList(movies: List<Movie>) {
        adapter.submitList(movies)
    }

    override fun onDetach() {
        super.onDetach()
        listenerMovieList = null
    }


    override fun onRefresh() {
        viewModel.updateMovieFromServer()
    }


    interface MovieListClickListener {
        fun openMovieDetailsFragment(movie: Movie)
    }
}