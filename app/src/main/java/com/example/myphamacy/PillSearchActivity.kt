package com.example.myphamacy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_pill_search.*

class PillSearchActivity : AppCompatActivity() {
    var cate:Int? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_search)
        init()
    }

    fun init() {

        ItemLayout1.setOnClickListener {
           Toast.makeText(this,"의약품 검색",Toast.LENGTH_SHORT).show()
            cate = 0
        }

        ItemLayout2.setOnClickListener {
            Toast.makeText(this,"알약 검색",Toast.LENGTH_SHORT).show()
            cate = 1
        }

       click_image.setOnClickListener {
           if (cate == 1) {
               val int = Intent(this, Search_3Activity::class.java)
               startActivity(int)
           } else {
               val textdata = editText.text
               val intent = Intent(this, SearchActivity::class.java)
               intent.putExtra("string", textdata.toString())
               intent.putExtra("category", cate)
               Log.d("putdata", textdata.toString())
               startActivity(intent)
           }
       }
        micro_imageView.setOnClickListener {
            val intent = Intent(this,Search2Activity::class.java)
            startActivity(intent)
        }
    }


}
