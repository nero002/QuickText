package com.nero.qtquicktext.ui.saveStatus.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nero.qtquicktext.OnItemClick
import com.nero.qtquicktext.R

class StatusSaverAdapter(
    private val filesList: ArrayList<StatusModel>,
    private val context: Context, private val onItemClick: OnItemClick
) :
    RecyclerView.Adapter<StatusSaverViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusSaverViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_status_saver, parent, false)
        return StatusSaverViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: StatusSaverViewHolder, position: Int) {
        val files = filesList[position]
        holder.setData(files, context)
    }

    override fun getItemCount(): Int {
        return filesList.size
    }
}