package com.example.englishword

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {
    val ADDVOC_REQUEST = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        init()
    }

    private fun init() {
        button.setOnClickListener {
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }
        button2.setOnClickListener {
            val i = Intent(this, AddVocActivity::class.java)
            //결과를 받음
            startActivityForResult(i ,ADDVOC_REQUEST)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            ADDVOC_REQUEST->{
                if(resultCode== Activity.RESULT_OK){
                    val str = data?.getSerializableExtra("voc")as MyData
                    Toast.makeText(this,str.word+"단어 추가 됨",Toast.LENGTH_SHORT).show()
                }

            }
        }

    }
}
