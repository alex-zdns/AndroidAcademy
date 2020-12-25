package ru.alexzdns.fundamentals.homework.ui.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.data.models.Actor


class ActorsAdapter(
    var actors: List<Actor>,
) : RecyclerView.Adapter<ActorsAdapter.ActorsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        val holder = ActorsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        )

        val margin = parent.resources.getDimensionPixelSize(R.dimen.md_margin_side)
        val side = (Resources.getSystem().displayMetrics.widthPixels - margin * 2) / parent.resources.getInteger(R.integer.actors_list_item_count)
        holder.avatar.layoutParams.width = side
        holder.avatar.layoutParams.height = side

        return holder
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        holder.bind(actors[position])
    }

    override fun getItemCount(): Int = actors.size


    class ActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.vha_avatar)
        private val name: TextView = itemView.findViewById(R.id.vha_cast_name)
        private val imageOption: RequestOptions = RequestOptions()
            .placeholder(R.drawable.vha_avatar_placeholder)
            .fallback(R.drawable.vha_avatar_placeholder)
            .transforms(CenterCrop(), RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.vha_corner_radius)))

        fun bind(actor: Actor) {
            Glide.with(itemView.context)
                .load(actor.picture)
                .apply(imageOption)
                .into(avatar)

            name.text = actor.name
        }
    }
}
