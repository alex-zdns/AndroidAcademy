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

class MoviesListFragment : androidx.fragment.app.Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private val viewModel: MoviesListViewModel by viewModels { MoviesListViewModelFactory() }

    private var listenerMovieList: MovieListClickListener? = null
    private var recycler: RecyclerView? = null
    private var loader: SwipeRefreshLayout? = null

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
        loader = view.findViewById(R.id.fml_swipe_container)
        loader?.setOnRefreshListener(this)

        viewModel.state.observe(this.viewLifecycleOwner, this::setState)

        if (viewModel.state.value is MoviesListViewModel.State.Default) viewModel.getMovies()
    }

    private fun setState(state: MoviesListViewModel.State) =
        when (state) {
            is MoviesListViewModel.State.Default -> {
                setLoading(false)
            }
            is MoviesListViewModel.State.Loading -> {
                setLoading(true)
            }
            is MoviesListViewModel.State.Error -> {
                setLoading(false)
                Toast.makeText(context, getString(R.string.loading_movies_error_message), Toast.LENGTH_LONG).show()
            }
            is MoviesListViewModel.State.Success -> {
                setupRecycler(state.movies)
                setLoading(false)
            }
        }

    private fun setLoading(loading: Boolean) {
        loader?.isRefreshing = loading
    }

    private fun setupRecycler(movies: List<Movie>) {
        val adapter = MoviesAdapter(movies, clickListener)
        recycler?.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        listenerMovieList = null
    }

    private val clickListener = object : MoviesAdapter.OnRecyclerMovieItemClicked {
        override fun onBannerClick(movie: Movie) {
            listenerMovieList?.openMovieDetailsFragment(movie)
        }
    }

    override fun onRefresh() {
        viewModel.getMovies()
    }


    interface MovieListClickListener {
        fun openMovieDetailsFragment(movie: Movie)
    }
}