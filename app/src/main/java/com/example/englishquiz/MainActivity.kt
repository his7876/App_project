package com.example.englishquiz

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_item.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity()
    , ItemFragment.OnListFragmentInteractionListener {
    var quizNum = 0
    var array = ArrayList<MyData>()
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        readFile()
        makeQuiz(quizNum)
        makeItem(quizNum)

        nextBtn.setOnClickListener {
            quizNum = randomMake()
            changeItem(quizNum)
            changeQuiz(quizNum)
        }
        scoreBtn.setOnClickListener {
            val intent = Intent(this,ScoreActivity::class.java)
            intent.putExtra("score",count)
            startActivity(intent)
        }

    }


    fun makeQuiz(num: Int) {  //시작 시 quiz 만듦
        val fragment = supportFragmentManager.findFragmentById(R.id.quizframe)
        if(fragment == null){
            val quizTranscation = supportFragmentManager.beginTransaction()
            val quizFragment = QuizFragment.newQuizFragment(num)
            quizTranscation.replace(R.id.quizframe,quizFragment,"quizFrag")
            quizTranscation.addToBackStack(null)
            quizTranscation.commit()
        }
        else {
            (fragment as QuizFragment).setActiveQuiz(num)
        }
    }
    fun makeItem(num: Int) {
        val fragment = supportFragmentManager.findFragmentById(R.id.itemframe)
        if(fragment == null){
            val itemTransaction = supportFragmentManager.beginTransaction()
            val itemFragment = ItemFragment.newItemFragment(num)
            itemTransaction.replace(R.id.itemframe , itemFragment,"itemFrag")
            itemTransaction.addToBackStack(null)
            itemTransaction.commit()
        }
        else{
            (fragment as ItemFragment).setActiveQuiz(num)
        }

    }


    fun changeQuiz(num:Int){ //버튼 클릭시 fragment 새 정보로 replace

        val fragment = supportFragmentManager.findFragmentByTag("quizFrag")
        if(fragment != null){
            val quizTranscation = supportFragmentManager.beginTransaction()
            val quizFragment = QuizFragment.newQuizFragment(num)
            quizTranscation.replace(R.id.quizframe,quizFragment,"quizFrag")
            quizTranscation.addToBackStack(null)
            quizTranscation.commit()
        }

    }
    fun changeItem(num:Int){
        val fragment = supportFragmentManager.findFragmentByTag("itemFrag")
        if(fragment!=null){
            val itemTransaction = supportFragmentManager.beginTransaction()
            val itemFragment = ItemFragment.newItemFragment(num)
            itemTransaction.replace(R.id.itemframe , itemFragment,"itemFrag")
            itemTransaction.addToBackStack(null)
            itemTransaction.commit()
        }
    }


    fun readFile() {
        val scan = Scanner(resources.openRawResource(R.raw.words))
        while (scan.hasNextLine()) {
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            array.add(MyData(word, meaning))
        }
        scan.close()

    }

    fun randomMake(): Int {
        val rand = Random().nextInt(array.size - 4)
        return rand
    }

    override fun onListFragmentInteraction(item: MyData?) {
        if (array[quizNum].meaning == item?.meaning) {
            count++
            Toast.makeText(this,"정답",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,"오답",Toast.LENGTH_SHORT).show()
        }

    }




}