package ru.alexzdns.fundamentals.homework.background

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.domain.models.Movie
import ru.alexzdns.fundamentals.homework.ui.MainActivity
import java.util.*

class MovieNotifications(private val context: Context) : Notifications {
    private val notificationManagerCompat: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

        init {
            initialize()
        }

    private fun initialize() {
        if (notificationManagerCompat.getNotificationChannel(CHANNEL_NEW_MOVIES) == null) {
            val channel = NotificationChannelCompat.Builder(
                CHANNEL_NEW_MOVIES,
                NotificationManagerCompat.IMPORTANCE_HIGH
            )
                .setName(context.getString(R.string.channel_new_movie))
                .setDescription(context.getString(R.string.channel_new_movies_description))
                .build()

            notificationManagerCompat.createNotificationChannel(channel)
        }
    }

    override fun showNotification(movie: Movie) {
        val contentUri = "https://www.themoviedb.org/movie/${movie.id}".toUri()

        val builder = NotificationCompat.Builder(context, CHANNEL_NEW_MOVIES)
            .setContentTitle(movie.title)
            .setSmallIcon(R.drawable.ic_the_movie_app)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setWhen(Date().time)
            .setStyle(NotificationCompat.BigTextStyle().bigText(movie.overview))
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_CONTENT,
                    Intent(context, MainActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(contentUri),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )

        notificationManagerCompat.notify(MOVIE_TAG, movie.id.toInt(), builder.build())
    }

    companion object {
        private const val CHANNEL_NEW_MOVIES = "new_movies"
        private const val REQUEST_CONTENT = 1
        private const val MOVIE_TAG = "movie"
    }
}