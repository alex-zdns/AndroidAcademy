package ru.alexzdns.fundamentals.homework.ui.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.data.models.Movie

class MovieAdapter(
    var movies: List<Movie>,
    private val clickListener: OnRecyclerMovieItemClicked
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val holder = MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false))

        val margin = parent.resources.getDimensionPixelSize(R.dimen.mlf_margin_side)
        val marginBetweenCards = parent.resources.getDimensionPixelSize(R.dimen.mlf_margin_between_cards)
        val itemCount = parent.resources.getInteger(R.integer.movies_list_span_count)
        val side = (Resources.getSystem().displayMetrics.widthPixels - margin * 2 - marginBetweenCards * (itemCount - 1)) / itemCount

        holder.cardView.layoutParams.width = side

        return holder
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.itemView.setOnClickListener {
            clickListener.onBannerClick(movies[position])
        }

        /*
        holder.like.setOnClickListener {
            clickListener.onLikeClick(movies[position], position)
        }
         */
    }

    override fun getItemCount(): Int = movies.size

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: ConstraintLayout = itemView.findViewById(R.id.vhm_cl_film_item)
        private val banner: ImageView = itemView.findViewById(R.id.vhm_iv_movie_banner)
        private val title: TextView = itemView.findViewById(R.id.vhm_tv_movie_title)
        private val ageRating: TextView = itemView.findViewById(R.id.vhm_tv_age_rating)
        //val like: ImageView = itemView.findViewById(R.id.vhm_iv_like)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.vhm_rating_bar)
        private val reviewsCount: TextView = itemView.findViewById(R.id.vhm_tv_reviews_count)
        private val genres: TextView = itemView.findViewById(R.id.vhm_tv_movie_genres)
        private val runningTime: TextView = itemView.findViewById(R.id.vhm_tv_movie_running_time)

        fun bind(movie: Movie) {
            val imageOption = RequestOptions()
                .placeholder(R.drawable.mlf_poster_holder)
                .fallback(R.drawable.mlf_poster_holder)
                .centerCrop()

            Glide.with(itemView.context)
                .load(movie.poster)
                .apply(imageOption)
                .into(banner)

            title.text = movie.title
            ageRating.text = itemView.resources.getString(R.string.movie_age_rating, movie.minimumAge)
            //like.setImageResource(if (movie.isLike) R.drawable.ic_like_fill else R.drawable.ic_like_empty)
            ratingBar.rating = movie.ratings / 2.0f
            reviewsCount.text = itemView.resources.getQuantityString(R.plurals.reviews_count, movie.numberOfRatings, movie.numberOfRatings)
            genres.text = movie.genres.joinToString(separator = ", ") { it.name }
            runningTime.text = itemView.resources.getString(R.string.movie_duration, movie.runtime)
        }
    }

    interface OnRecyclerMovieItemClicked {
        fun onBannerClick(movie: Movie)
        //fun onLikeClick(movie: Movie, position: Int)
    }
}
