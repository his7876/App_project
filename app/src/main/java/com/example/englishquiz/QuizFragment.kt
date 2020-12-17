package com.example.englishquiz

import android.content.Intent
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_quiz.*
import java.util.*
import kotlin.collections.ArrayList

class QuizFragment : Fragment() {
    var quizNum =0
    var array = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

        companion object{
            fun newQuizFragment(num:Int):QuizFragment{
                val quizFragment = QuizFragment()
                quizFragment.quizNum = num
                return quizFragment
            }
        }


    fun readFile() {
        val scan = Scanner(resources.openRawResource(R.raw.words))
        while (scan.hasNextLine()) {
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            array.add(word)
        }
        scan.close()

    }


    fun setActiveQuiz(num:Int){
        quizNum = num
        quizText.text = array[quizNum]
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        readFile()
        setActiveQuiz(quizNum)

    }

}
