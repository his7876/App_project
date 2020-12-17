package com.example.englishquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_score.*
import java.io.PrintStream
import java.util.*
import kotlin.collections.ArrayList

class ScoreActivity : AppCompatActivity(),ScoreFragment.OnListFragmentInteractionListener {

    var array = ArrayList<MyData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        init()
    }
    private fun init() {
        makeScore(array)
        if( intent.hasExtra("score")) {
            Log.i("getScore?",intent.getIntExtra("score",-1).toString())
            scoreText.text = intent.getIntExtra("score",0).toString()+"점"
        }else{
            scoreText.text = "0점"
        }

        InputBtn.setOnClickListener {
            val user = usereditText.text.toString()
            writeFile(user, intent.getIntExtra("score",0).toString()+"점")
            readFile()
            usereditText.text.clear()
            changeScoreFrag(array)
        }

    }


    fun makeScore(array:ArrayList<MyData>) {  //초기 frag 부착
        val fragment = supportFragmentManager.findFragmentById(R.id.scoreframe)
        if(fragment == null){
            val scoreTranscation = supportFragmentManager.beginTransaction()
            val scoreFragment = ScoreFragment.newScoreFragment(array)
            scoreTranscation.replace(R.id.scoreframe,scoreFragment,"scoreFrag")
            scoreTranscation.commit()
        }

    }
    fun changeScoreFrag(array: ArrayList<MyData>){ //score frag 갱신
        val fragment = supportFragmentManager.findFragmentByTag("scoreFrag")
        if(fragment != null){
            val scoreTranscation = supportFragmentManager.beginTransaction()
            val scoreFragment = ScoreFragment.newScoreFragment(array)
            scoreTranscation.replace(R.id.scoreframe,scoreFragment,"scoreFrag")
            scoreTranscation.addToBackStack(null)
            scoreTranscation.commit()
        }
    }

    private fun writeFile(user: String, intExtra: String) {
        val input = PrintStream(openFileOutput("user.txt", Context.MODE_APPEND))
        input.println(user)
        input.println(intExtra)
        input.close()
    }


    fun readFile() {
        val scan = Scanner(openFileInput("user.txt"))
        while(scan.hasNextLine()){
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            array.add(MyData(word,meaning))

        }
        scan.close()
    }

    override fun onListFragmentInteraction(item: String) {
       Log.i("TTT",item)
    }
}
