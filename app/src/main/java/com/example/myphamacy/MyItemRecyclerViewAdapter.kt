package com.example.myphamacy

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import com.example.myphamacy.PharmacyFragment.OnListFragmentInteractionListener


import kotlinx.android.synthetic.main.fragment_pharmacy.view.*


class MyItemRecyclerViewAdapter(
    private val mValues: List<Pharmacy>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Pharmacy
            mListener?.onListFragmentInteraction(item)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_pharmacy, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.name
        holder.mContentView.text ="주소 :"+ item.address+ "\n" + "번호 : "+item.phone
        holder.mdistance.text = item.distance.toString()+"m"


        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.pharmacy_name
        val mContentView: TextView = mView.phar_Info
        val mdistance:TextView = mView.distanceText

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
