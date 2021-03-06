package ru.alexzdns.fundamentals.homework.ui.moviesList.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.domain.models.Movie

class MoviesAdapter(
    private val clickListener: OnRecyclerMovieItemClicked
) : ListAdapter<Movie, MoviesAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onBannerClick(getItem(position))
        }


        holder.like.setOnClickListener {
            clickListener.onLikeClick(getItem(position))
            notifyItemChanged(position)
        }

    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val banner: ImageView = itemView.findViewById(R.id.vhm_iv_movie_banner)
        private val title: TextView = itemView.findViewById(R.id.vhm_tv_movie_title)
        private val ageRating: TextView = itemView.findViewById(R.id.vhm_tv_age_rating)

        val like: ImageView = itemView.findViewById(R.id.vhm_iv_like)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.vhm_rating_bar)
        private val reviewsCount: TextView = itemView.findViewById(R.id.vhm_tv_reviews_count)
        private val genres: TextView = itemView.findViewById(R.id.vhm_tv_movie_genres)

        fun bind(movie: Movie) {
            Glide.with(itemView.context)
                .load(movie.poster)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(imageOption)
                .into(banner)

            title.text = movie.title
            ageRating.text =
                itemView.resources.getString(R.string.movie_age_rating, movie.minimumAge)
            like.setImageResource(if (movie.isFavorite) R.drawable.ic_like_fill else R.drawable.ic_like_empty)
            ratingBar.rating = movie.ratings
            reviewsCount.text = itemView.resources.getQuantityString(
                R.plurals.reviews_count,
                movie.numberOfRatings,
                movie.numberOfRatings
            )
            genres.text = movie.genres
        }

        companion object {
            private val imageOption = RequestOptions()
                .placeholder(R.drawable.mlf_poster_holder)
                .fallback(R.drawable.mlf_poster_holder)
                .centerCrop()
        }
    }

    private class MovieDiffCallback(): DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem

    }

    interface OnRecyclerMovieItemClicked {
        fun onBannerClick(movie: Movie)
        fun onLikeClick(movie: Movie)
    }
}