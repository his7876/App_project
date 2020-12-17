package com.example.englishquiz

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import com.example.englishquiz.ItemFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_item.view.*
import org.w3c.dom.Text


class MyItemRecyclerViewAdapter(
    //list에서 넘어온 data 전달
    private val mValues: List<MyData>,
    private val mListener: OnListFragmentInteractionListener?

) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {

        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as MyData
            mListener?.onListFragmentInteraction(item)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mContentView.text = item.meaning
        var flag = true
        with(holder.mView) {
            tag = item
            setOnClickListener{
                mListener?.onListFragmentInteraction(item)
                if(flag) {
                    holder.mContentView.setBackgroundColor(Color.parseColor("#5A673AB7"))
                    flag = false
                }
                else{
                    holder.mContentView.setBackgroundColor(Color.WHITE)
                    flag = true

                }
            }



        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView = mView.itemText


        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
