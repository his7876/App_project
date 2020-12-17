package com.example.englishquiz

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import com.example.englishquiz.VocaFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_voca.*

import kotlinx.android.synthetic.main.fragment_voca.view.*
import kotlinx.android.synthetic.main.fragment_voca.view.meaningText
import kotlinx.android.synthetic.main.fragment_voca.view.wordText


class MyVocaRecyclerViewAdapter2(
    private val mValues: List<MyData>,
    private val mListener: OnListFragmentInteractionListener?

) : RecyclerView.Adapter<MyVocaRecyclerViewAdapter2.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener


    init {

        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as MyData
            mListener?.onListFragmentInteraction(item)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_voca, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.word
        holder.mContentView.text = item.meaning
        var flag = true

        with(holder.mView) {
            tag = item

            setOnClickListener {
                mListener?.onListFragmentInteraction(item)
                if(flag){
                    holder.mContentView.visibility = View.VISIBLE
                    flag = false
                }
                else{
                    holder.mContentView.visibility = View.GONE
                    flag = true
                }
            }

        }


    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.wordText
        val mContentView: TextView = mView.meaningText


            override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
