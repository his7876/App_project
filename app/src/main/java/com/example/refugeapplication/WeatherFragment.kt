package com.example.refugeapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [WeatherFragment.OnListFragmentInteractionListener] interface.
 */
class WeatherFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    var array= ArrayList<WeatherData>()
    private var listener: OnListFragmentInteractionListener? = null


    companion object{
        fun newWeatherFragment(product:ArrayList<WeatherData>):WeatherFragment{
            val weatherFragment = WeatherFragment()
            weatherFragment.array = product
            return  weatherFragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                setData(array)
                adapter = MyWeatherRecyclerViewAdapter(array, listener)
            }
        }
        return view
    }

    fun setData(data:ArrayList<WeatherData>){
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
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: WeatherData?)
    }

}
