package com.example.refugeapplication

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


import com.example.refugeapplication.ItemFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_item.view.*


class MyItemRecyclerViewAdapter(
    private val mValues: List<Product>,
    private val mListener: ItemFragment.OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Product
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
        holder.mIdView.text =  item.name
        holder.mContentView.text = "주소 :" + item.address +"\n"
        holder.ImageView


        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)


        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.rfgName
        val mContentView: TextView = mView.rfgInfo
        val ImageView: ImageView = mView.locationMap

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
