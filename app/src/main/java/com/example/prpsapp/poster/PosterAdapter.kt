package com.example.prpsapp.poster

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.prpsapp.database.Session
import com.example.prpsapp.databinding.ListFilmItemBinding


class PosterAdapter(val clickListener: SessionClickListener): ListAdapter<Session, PosterAdapter.ViewHolder>(DiffCallback()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListFilmItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: SessionClickListener, item: Session) {
            binding.session = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListFilmItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}


class DiffCallback : DiffUtil.ItemCallback<Session>() {
    override fun areItemsTheSame(oldItem: Session, newItem: Session): Boolean {
        return oldItem.idSession == newItem.idSession
    }

    override fun areContentsTheSame(oldItem: Session, newItem: Session): Boolean {
        return oldItem == newItem
    }
}

class SessionClickListener(val clickListener: (sleepId: Long) -> Unit) {
    fun onClick(session: Session) = clickListener(session.idSession)
}