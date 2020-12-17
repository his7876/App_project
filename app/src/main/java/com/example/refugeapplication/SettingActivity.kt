package com.example.refugeapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.app_bar_main.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setSupportActionBar(main_layout_toolbar)
        init()
    }

    private fun init() {
        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                val intent = Intent(this,WeatherActivity::class.java)
                intent.putExtra("Yes",1)
                startActivity(intent)
                Toast.makeText(this,"설정 되었습니다.",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"설정 취소되었습니다.",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
