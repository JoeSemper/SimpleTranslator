package com.joesemper.history.view.history.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.joesemper.historyscreen.R
import kotlinx.android.synthetic.main.history_fragment_rv_item.view.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var data: List<com.joesemper.model.data.DataModel> = arrayListOf()

    fun setData(data: List<com.joesemper.model.data.DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: com.joesemper.model.data.DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.tv_item_history.text = data.text
                itemView.setOnClickListener {
                    Toast.makeText(itemView.context, "on click: ${data.text}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.history_fragment_rv_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

}