package com.example.myphamacy

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


import com.example.myphamacy.PillFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.activity_main.view.*


import kotlinx.android.synthetic.main.fragment_pill.view.*


class MyPillRecyclerViewAdapter(
    private val mValues: List<Pill>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyPillRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Pill
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_pill, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.item_name
        holder.mContentView.text ="효능:"+ item.drug_effect
        holder.mContentView2.text ="제품 이름: "+ item.item_name +"\n"+"제조사:"+item.entp_name
        holder.mContentView4.text = "제품모양:"+item.drug_shape+"\t"+"제품 색상:"+item.drug_color
        holder.mContentView3.text ="제품 특징:"+ item.chart+ item.fom_code_name
        holder.mImageView.setImageResource(item.image_url)
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content
        val mContentView2:TextView = mView.content2
        val mContentView3:TextView = mView.content3
        val mContentView4:TextView = mView.content4
        val mImageView:ImageView =mView.item_image


        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
