package ru.alexzdns.fundamentals.homework.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.data.models.Movie_old

class MovieAdapter(
    var movieOlds: List<Movie_old>,
    private val clickListener: OnRecyclerMovieItemClicked
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private fun getItem(position: Int): Movie_old = movieOlds[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onBannerClick(position)
        }

        holder.like.setOnClickListener {
            clickListener.onLikeClick(movieOlds[position], position)
        }
    }

    override fun getItemCount(): Int = movieOlds.size

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val banner: ImageView = itemView.findViewById(R.id.vhm_iv_movie_banner)
        private val title: TextView = itemView.findViewById(R.id.vhm_tv_movie_title)
        private val ageRating: TextView = itemView.findViewById(R.id.vhm_tv_age_rating)
        val like: ImageView = itemView.findViewById(R.id.vhm_iv_like)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.vhm_rating_bar)
        private val reviewsCount: TextView = itemView.findViewById(R.id.vhm_tv_reviews_count)
        private val genres: TextView = itemView.findViewById(R.id.vhm_tv_movie_genres)
        private val runningTime: TextView = itemView.findViewById(R.id.vhm_tv_movie_running_time)

        fun bind(movieOld: Movie_old) {
            banner.setImageResource(movieOld.banner)
            title.text = movieOld.title
            ageRating.text = itemView.resources.getString(R.string.movie_age_rating, movieOld.ageRating)
            like.setImageResource(if (movieOld.isLike) R.drawable.ic_like_fill else R.drawable.ic_like_empty)
            ratingBar.rating = movieOld.rating
            reviewsCount.text = itemView.resources.getQuantityString(R.plurals.reviews_count, movieOld.reviewsCount, movieOld.reviewsCount)
            genres.text = movieOld.genres
            runningTime.text = itemView.resources.getString(R.string.movie_duration, movieOld.runningTimeInMin)
        }
    }

    interface OnRecyclerMovieItemClicked {
        fun onBannerClick(position: Int)
        fun onLikeClick(movieOld: Movie_old, position: Int)
    }
}