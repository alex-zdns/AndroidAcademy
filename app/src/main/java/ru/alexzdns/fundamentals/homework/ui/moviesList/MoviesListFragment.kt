package ru.alexzdns.fundamentals.homework.ui.moviesList

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.data.loadMovies
import ru.alexzdns.fundamentals.homework.data.models.Movie

class MoviesListFragment : androidx.fragment.app.Fragment() {
    private var listenerMovieList: MovieListClickListener? = null
    private var recycler: RecyclerView? = null

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        Log.e("MovieListFragment", "CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + exceptionHandler)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listenerMovieList = context as? MovieListClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById<RecyclerView>(R.id.mlf_movie_list)

        scope.launch {
            val movies = loadMovies(view.context)
            setupRecycler(movies)
        }
    }

    private suspend fun setupRecycler(movies: List<Movie>) = withContext(Dispatchers.Main) {
        val adapter = MoviesAdapter(movies, clickListener)
        recycler?.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        listenerMovieList = null
        scope.cancel()
    }

    private val clickListener = object : MoviesAdapter.OnRecyclerMovieItemClicked {
        override fun onBannerClick(movie: Movie) {
            listenerMovieList?.openMovieDetailsFragment(movie)
        }
    }

    interface MovieListClickListener {
        fun openMovieDetailsFragment(movie: Movie)
    }
}
