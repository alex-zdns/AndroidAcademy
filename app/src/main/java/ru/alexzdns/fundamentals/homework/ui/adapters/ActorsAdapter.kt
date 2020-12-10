package ru.alexzdns.fundamentals.homework.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.data.models.Actor_old


class ActorsAdapter(
    var actorOlds: List<Actor_old>
) : RecyclerView.Adapter<ActorsAdapter.ActorsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder =
        ActorsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_holder_actor,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        holder.bind(actorOlds[position])
    }

    override fun getItemCount(): Int = actorOlds.size


    class ActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatar: ImageView = itemView.findViewById(R.id.vha_avatar)
        private val name: TextView = itemView.findViewById(R.id.vha_cast_name)

        fun bind(actorOld: Actor_old) {
            avatar.setImageResource(actorOld.avatar)
            name.text = actorOld.name
        }
    }
}