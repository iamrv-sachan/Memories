package com.rajdroid.memories.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rajdroid.memories.R
import kotlinx.android.synthetic.main.item_memories.view.*

open class MemoriesAdapter(val context: Context, val memory:List<Memory>) : RecyclerView.Adapter<MemoriesAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemoriesAdapter.MyViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.item_memories,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return memory.size
    }

    override fun onBindViewHolder(holder: MemoriesAdapter.MyViewHolder, position: Int) {
        val mem=memory[position]
        holder.setData(mem,position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun setData(mem:Memory?,position: Int)
        {
            itemView.tv_title.text=mem!!.title
            itemView.tv_description.text=mem!!.description
        }
    }

}