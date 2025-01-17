package com.codinginflow.mvvm_guests.ui.guests

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.codinginflow.mvvm_guests.data.GuestRecyclerViewItem
import mvvm_guests.R
import mvvm_guests.databinding.ItemGuestBinding
import mvvm_guests.databinding.ItemTitleBinding
import java.lang.IllegalArgumentException


//class GuestsAdapter : ListAdapter<GuestRecyclerViewItem.Guest, GuestsAdapter.GuestRecyclerViewHolder.GuestViewHolder>(DiffCallback()) {
class GuestsAdapter : RecyclerView.Adapter<GuestsAdapter.GuestRecyclerViewHolder>() {

    var items = listOf<GuestRecyclerViewItem>()
        set(value) {
            field= value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: GuestRecyclerViewHolder, position: Int) {
        when(holder) {
            is GuestRecyclerViewHolder.GuestViewHolder -> holder.bind(items[position] as GuestRecyclerViewItem.Guest)
            is GuestRecyclerViewHolder.TitleViewHolder -> holder.bind(items[position] as GuestRecyclerViewItem.Title)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    //use constants here
    override fun getItemViewType(position: Int): Int {
        return when(items[position]) {
            is GuestRecyclerViewItem.Guest -> R.layout.item_guest
            is GuestRecyclerViewItem.Title -> R.layout.item_title
        }
    }


    //override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestRecyclerViewHolder.GuestViewHolder {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestRecyclerViewHolder {
        return when (viewType) {
            R.layout.item_guest -> {
                val binding = ItemGuestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return GuestRecyclerViewHolder.GuestViewHolder(binding)
            }

            R.layout.item_title -> {
                val binding = ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return GuestRecyclerViewHolder.TitleViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid View Type Provided")
        }

    }

    /*override fun onBindViewHolder(holder: GuestRecyclerViewHolder.GuestViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }*/


    sealed class GuestRecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        class GuestViewHolder(private val binding: ItemGuestBinding) : GuestRecyclerViewHolder(binding) {

            private val itemCbCallback: ((GuestRecyclerViewItem.Guest, Boolean)-> Unit)? = null

            //puts data in views inside layout
            fun bind(guest: GuestRecyclerViewItem.Guest) {
                binding.apply {
                    checkboxGuest.isChecked = false //always false to begin
                    checkboxGuest.text = guest.guestName
                }
            }
        }


        class TitleViewHolder(private val binding: ItemTitleBinding) : GuestRecyclerViewHolder(binding) {
            fun bind(title: GuestRecyclerViewItem.Title) {
                binding.tvGuestReservationLabel.text = title.title
            }
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<GuestRecyclerViewItem.Guest>() {
        override fun areItemsTheSame(oldItem: GuestRecyclerViewItem.Guest, newItem: GuestRecyclerViewItem.Guest) = (oldItem.id == newItem.id)
        override fun areContentsTheSame(oldItem: GuestRecyclerViewItem.Guest, newItem: GuestRecyclerViewItem.Guest) = (oldItem == newItem)
    }

}