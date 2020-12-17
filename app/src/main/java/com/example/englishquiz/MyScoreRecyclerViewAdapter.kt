package com.example.englishquiz

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import com.example.englishquiz.ScoreFragment.OnListFragmentInteractionListener


import kotlinx.android.synthetic.main.fragment_score.view.*

class MyScoreRecyclerViewAdapter(
    private val mValues: List<MyData>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyScoreRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as String
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_score, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.word
        holder.mContentView.text = item.meaning

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.user
        val mContentView: TextView = mView.itemText

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
