package com.example.englishword

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val items: ArrayList<MyData>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    interface  OnItemClickListener{
        fun OnItemClick(holder: MyViewHolder,view: View, data:String, position: Int)

    }

    var itemClickListener:OnItemClickListener ?= null

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.textView)
        var textView2: TextView = itemView.findViewById(R.id.textView3)
        var flag = true
        init {

            itemView.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition].word, adapterPosition)
                Log.d("SSS","${adapterPosition}")
                if(flag){
                    textView2.visibility = View.VISIBLE
                    flag= false

                }else
                    textView2.visibility= View.GONE
                    flag = true


            }
            
        }


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = items[position].word
        holder.textView2.text = items[position].meaning

    }


    fun moveItem(oldPos:Int, newPos:Int){
        val item  = items.get(oldPos)
        items.removeAt(oldPos)
        items.add(newPos,item)
        notifyItemMoved(oldPos,newPos)
    }

    fun removeItem(pos:Int){
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }
}