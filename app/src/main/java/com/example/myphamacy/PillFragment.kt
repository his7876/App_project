package com.example.myphamacy

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class PillFragment : Fragment() {

    var array =ArrayList<Pill>()

    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null





    companion object{
        fun newPillFragment(product:ArrayList<Pill>):PillFragment{
            val pillFragment = PillFragment()
            pillFragment.array = product
            return  pillFragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pill_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                setData(array)
                adapter = MyPillRecyclerViewAdapter(array, listener)
            }
        }
        return view
    }

    fun setData(data:ArrayList<Pill>){
        array = data
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {

        fun onListFragmentInteraction(item: Pill?)
    }

}
