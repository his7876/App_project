package com.example.englishquiz

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_item.*
import kotlinx.android.synthetic.main.fragment_quiz.*
import kotlinx.android.synthetic.main.fragment_voca.view.*
import java.util.*
import kotlin.collections.ArrayList


class ItemFragment : Fragment() {
    var itemNum =0
    var array = ArrayList<MyData>()
    var itemValue = ArrayList<MyData>()

    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

   companion object{ // 값을 갖는 객체로 객체화
       fun newItemFragment(num:Int):ItemFragment{
           val itemFragment = ItemFragment()
           itemFragment.itemNum = num
           return itemFragment
       }
   }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                    readFile()
                    setActiveQuiz(itemNum)
                    adapter = MyItemRecyclerViewAdapter(itemValue, listener)

                }
            }
        return view
    }




    fun setActiveQuiz(num:Int){
        itemNum = num
        itemValue.clear()
        itemValue.add(array[itemNum+2])
        itemValue.add(array[itemNum])
        itemValue.add(array[itemNum+1])
        itemValue.add(array[itemNum+3])


    }


    fun readFile() {
        val scan = Scanner(resources.openRawResource(R.raw.words))
        while (scan.hasNextLine()) {
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            array.add(MyData(word,meaning))
        }
        scan.close()

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
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: MyData?)

    }

}
