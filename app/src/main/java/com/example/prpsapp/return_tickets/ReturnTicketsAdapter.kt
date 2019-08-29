package com.example.prpsapp.return_tickets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.prpsapp.database.ReturnTicketsQueryFirst
import com.example.prpsapp.databinding.ReturnTicketListItemBinding

class ReturnTicketsAdapter(val clickListener: ReturnTicketsClickListener):
    ListAdapter<ReturnTicketsQueryFirst, ReturnTicketsAdapter.ViewHolder>(DiffCallback()){

    override fun onBindViewHolder(holder: ReturnTicketsAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReturnTicketsAdapter.ViewHolder {
        return ReturnTicketsAdapter.ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: ReturnTicketListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: ReturnTicketsClickListener, item:ReturnTicketsQueryFirst) {
            binding.viewModel = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =ReturnTicketListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ReturnTicketsClickListener(val clickListener: (idTIcket: Long) -> Unit) {
    fun onClick(item: ReturnTicketsQueryFirst) = clickListener(item.idTicket)
}

private class DiffCallback : DiffUtil.ItemCallback<ReturnTicketsQueryFirst>() {
    override fun areItemsTheSame(oldItem: ReturnTicketsQueryFirst, newItem: ReturnTicketsQueryFirst): Boolean {
        return oldItem.idTicket == newItem.idTicket
    }

    override fun areContentsTheSame(oldItem: ReturnTicketsQueryFirst, newItem: ReturnTicketsQueryFirst): Boolean {
        return oldItem == newItem
    }
}