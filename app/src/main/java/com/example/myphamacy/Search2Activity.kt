package com.example.myphamacy

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_search2.*
import java.util.EnumSet.range
import java.util.jar.Manifest
import kotlin.collections.ArrayList
import android.speech.*
import androidx.core.net.ConnectivityManagerCompat


class Search2Activity : AppCompatActivity() {


    private val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search2)


        if (Build.VERSION.SDK_INT >= 23)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.INTERNET, android.Manifest.permission.RECORD_AUDIO), REQUEST_CODE)

        search_button.setOnClickListener {

            if (isConnected()) {
                val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                sttIntent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "찾는 제품을 말씀하세요")

                try {
                    startActivityForResult(sttIntent, REQUEST_CODE)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Your device does not support STT.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            else{
                Toast.makeText(applicationContext,"Please connect to Internet",Toast.LENGTH_SHORT).show()
            }
        }

        init()
    }
    fun init(){


        search_b.setOnClickListener {
            val intent= Intent(this,SearchActivity::class.java)
            val data = item_search.text
            Log.d("음성인식 data", data.toString())
            val  put = data.toString()
            Log.d("음성인식 data String", put)
            intent.putExtra("putdata","게보린")
            startActivity(intent)
        }
    }


    fun isConnected():Boolean{
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val net = connectivityManager.activeNetworkInfo
        if(net!=null && net.isAvailable && net.isConnected)
        {
            return true
        }else
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE ->{
                if(resultCode == Activity.RESULT_OK && data !=null){
                    val result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS
                    )
                    result?.let {
                        val recognizedText = it[0]
                        item_search.setText(recognizedText)
                    }
                }
            }
        }
    }




}



