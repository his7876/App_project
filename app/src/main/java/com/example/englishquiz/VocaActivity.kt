package com.example.englishquiz

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_voca.*
import kotlinx.android.synthetic.main.fragment_voca.*
import java.io.PrintStream
import java.util.*
import kotlin.collections.ArrayList

class VocaActivity : AppCompatActivity(), VocaFragment.OnListFragmentInteractionListener {

    val textArray = arrayListOf<String>("단어장","단어 검색")

    lateinit var tts: TextToSpeech
    var isTtsReady = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voca)
        init()
    }

    private fun init() {
        viewPager.adapter = MyFragStateAdapter(this)
        TabLayoutMediator(tabLayout, viewPager){
            tab, position ->
            tab.text = textArray[position]
        }.attach()

        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            isTtsReady = true
            tts.language = Locale.US
        })


    }


    override fun onStop() {
        super.onStop()
        tts.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }



    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onListFragmentInteraction(item: MyData?) {
        Log.i("Item?", item.toString())
        if (isTtsReady) {
            tts.speak(item?.word, TextToSpeech.QUEUE_ADD, null, null)

        }

    }
}




