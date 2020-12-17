package com.example.englishword

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var words = mutableMapOf<String,String>()
    var array = ArrayList<MyData>()
    lateinit var adapter:MyAdapter
    lateinit var tts:TextToSpeech
    var isTtsReady = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }




    private fun init() {
        tts = TextToSpeech(this,TextToSpeech.OnInitListener {
            isTtsReady = true
            tts.language = Locale.US
        })

        readFile()
        recyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        adapter = MyAdapter(array)

        adapter.itemClickListener =object :MyAdapter.OnItemClickListener{

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun OnItemClick(
                holder: MyAdapter.MyViewHolder,
                view: View,
                data: String,
                position: Int
            ) {
                if(isTtsReady)
                    tts.speak(data,TextToSpeech.QUEUE_ADD,null , null)

            }



        }
        recyclerView.adapter = adapter
        val simpleCallback = object:ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN or ItemTouchHelper.UP, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.moveItem(viewHolder.adapterPosition ,target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeItem(viewHolder.adapterPosition)
            }

        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    fun readFileScan(scan: Scanner){
        while(scan.hasNextLine()){
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            words[word] = meaning
            array.add(MyData(word,meaning))

        }
        scan.close()
    }

    fun readFile(){
        val scan2 = Scanner(openFileInput("out.txt"))
        readFileScan(scan2)
        val scan = Scanner(resources.openRawResource(R.raw.words))
        readFileScan(scan)
    }

}
