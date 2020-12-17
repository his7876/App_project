package com.example.refugeapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_call.*

import kotlinx.android.synthetic.main.activity_refuge.*

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.Buffer


class CallActivity : AppCompatActivity() {

    val CALL_REQUEST = 100
    var num :String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        init()
    }

    fun callAlertDlg(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("신고 접수시 권한이 허용되어야 합니다.")
            .setTitle("권한 허용")
        builder.setPositiveButton("OK"){
                _,_->             ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CALL_PHONE),
            CALL_REQUEST
        )
        }
        val dlg = builder.create()
        dlg.show()
    }
    fun callAction(num:String) { //call_phone에 대한 permission 허용 체크
        val number = Uri.parse(num)
        val callIntent = Intent(Intent.ACTION_CALL, number)
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.CALL_PHONE
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) { //체크 안된 경우
            callAlertDlg()
        } else
            startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CALL_REQUEST-> { //승인 했을 경우
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"권한이 승인되었습니다.", Toast.LENGTH_SHORT).show()
                    Log.i("SSS",num)
                    callAction(num)
                }
                else{
                    Toast.makeText(this,"권한 승인이 거부 되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun setNumber(number:String):String{
        num = number
        return num
    }

    private fun init() {
        var loc = intent.getStringExtra("userlocation")


        pM.setOnClickListener {
            val message = Uri.parse("sms:112")
            val messageIntent = Intent(Intent.ACTION_SENDTO,message)
            messageIntent.putExtra("sms_body",loc)
            startActivity(messageIntent)
        }
        pC.setOnClickListener {
            setNumber("tel:112")
            Log.i("SSS1",num)
            callAction(num)
        }
        fM.setOnClickListener {
            val message = Uri.parse("sms:119")
            val messageIntent = Intent(Intent.ACTION_SENDTO,message)
            messageIntent.putExtra("sms_body",loc)
            startActivity(messageIntent)
        }
        fC.setOnClickListener {
            setNumber("tel:119")
            Log.i("SSS2",num)
            callAction(num)
        }

        internet.setOnClickListener { //안전 신문고 연결
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.safetyreport.go.kr/#main"))
            startActivity(intent)
        }
      }







    }
















