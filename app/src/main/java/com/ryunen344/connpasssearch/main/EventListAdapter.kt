package com.ryunen344.connpasssearch.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ryunen344.connpasssearch.R
import com.ryunen344.connpasssearch.data.Event
import com.ryunen344.connpasssearch.databinding.ItemEventBinding
import com.ryunen344.connpasssearch.main.eventList.EventListViewModel
import com.ryunen344.connpasssearch.util.LogUtil

class EventListAdapter(private val eventListViewModel: EventListViewModel) :
    RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    private var eventList: MutableList<Event> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        LogUtil.d()
        return ViewHolder(parent)
    }

    override fun getItemCount(): Int {
        LogUtil.d("item count is " + eventList.size)
        return eventList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        LogUtil.d("position is $position")
        //set click listener
        holder.binding.root.setOnClickListener {
            eventListViewModel.itemClick(eventList[position].event_id)
        }

        holder.binding.item = eventList[position]
        holder.binding.executePendingBindings()
    }

    fun update(eventList: MutableList<Event>) {
        LogUtil.d()
        this.eventList = eventList
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val parent: ViewGroup,
        val binding: ItemEventBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_event,
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            LogUtil.d()
            binding.item = event
        }

    }

    companion object {


    }
}