package com.joesemper.simpletranslator.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joesemper.simpletranslator.R
import kotlinx.android.synthetic.main.main_fragment_recycler_item.view.*

class MainAdapter(
    private var onListItemClickListener: OnListItemClickListener,
) : RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    private var data: List<com.joesemper.model.data.DataModel> = arrayListOf()

    fun setData(data: List<com.joesemper.model.data.DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: com.joesemper.model.data.DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.tv_item_header.text = data.text
                itemView.tv_item_translation.text = data.meanings?.get(0)?.translation?.translation

                itemView.setOnClickListener { openInNewWindow(data) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_fragment_recycler_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun openInNewWindow(listItemData: com.joesemper.model.data.DataModel) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: com.joesemper.model.data.DataModel)
    }
}