package com.example.englishquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.activity_main.*


class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        init()
    }
    private fun init() {
        button.setOnClickListener {
            val intent = Intent(this, VocaActivity::class.java)
            startActivity(intent)

        }
        button2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
        button3.setOnClickListener {
            val intent = Intent(this, ScoreActivity::class.java)
            startActivity(intent)

        }
    }
}
