package com.example.refugeapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.text.Typography.section

class WeatherActivity : AppCompatActivity(),WeatherFragment.OnListFragmentInteractionListener {

    var array =ArrayList<WeatherData>()
    lateinit var data:String
    lateinit var notifData:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        yesterday()
        AsyncTaskClass().execute()

    }

    inner class AsyncTaskClass : AsyncTask<Unit, Unit, String>() {
        override fun doInBackground(vararg params: Unit?): String {
            var reqParam = URLEncoder.encode("serviceKey", "UTF-8") + "=" +
                    "1NvKefm2fOrfb395qHYCmXF13MDkSMGUXqRR9Nxuo%2B8kAeiFzaI7%2FzaBa7mzym8ahgIg1AmD%2FsdRaRZGurwGGg%3D%3D"

            reqParam += "&" + URLEncoder.encode(
                "pageNo",
                "UTF-8"
            ) + "=" + URLEncoder.encode("1", "UTF-8")
            reqParam += "&" + URLEncoder.encode(
                "numOfRows",
                "UTF-8"
            ) + "=" + URLEncoder.encode("10", "UTF-8")
            reqParam += "&" + URLEncoder.encode(
                "dataType",
                "UTF-8"
            ) + "=" + URLEncoder.encode("JSON", "UTF-8")
            reqParam += "&" + URLEncoder.encode(
                "stnId",
                "UTF-8"
            ) + "=" + URLEncoder.encode("108", "UTF-8")
            reqParam += "&" + URLEncoder.encode(
                "fromTmFc",
                "UTF-8"
            ) + "=" + URLEncoder.encode(yesterday(), "UTF-8")
            reqParam += "&" + URLEncoder.encode(
                "toTmFc",
                "UTF-8"
            ) + "=" + URLEncoder.encode("20210618", "UTF-8")

            val mURL =
                URL("http://apis.data.go.kr/1360000/WthrWrnInfoService/getWthrWrnMsg?"+reqParam)
                    .openConnection() as HttpURLConnection
            Log.i("EEE",mURL.toString())
            if (mURL.responseCode == HttpURLConnection.HTTP_OK) {
                Log.i("EEE", mURL.responseCode.toString())
                Log.i("EEE",mURL.responseMessage)
                try {
                    val stream = BufferedInputStream(mURL.inputStream)
                     data = readStream(inputStream = stream)
                    Log.i("EEE",data)
                    return data
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    mURL.disconnect()
                }
            } else {
                Log.i("EEE", mURL.responseCode.toString())
            }
            return " "
        }

        fun readStream(inputStream: BufferedInputStream): String {
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            bufferedReader.forEachLine {
                stringBuilder.append(it)
            }
            return stringBuilder.toString()
        }

        override fun onPostExecute(result: String?) {
            val json = JSONObject(data)
            val obj = (json["response"] as JSONObject)
            val parse_header = obj.getJSONObject("header")

            if(parse_header.get("resultCode") != "10") {
                val parse_body = obj.getJSONObject("body")
                val parse_items = parse_body.getJSONObject("items")
                val upperArray: JSONArray = parse_items.getJSONArray("item")

                for (i in 0..3) {
                    val upperObject = upperArray.getJSONObject(i)
                    val title = upperObject.getString("t1")
                    val section = upperObject.getString("t2")
                    val time = upperObject.getString("t3")
                    val contents = upperObject.getString("t4")
                    array.add(WeatherData(title, section, time, contents))
                }
                make(array)
                notifData = array[0].title + array[0].section
                if(intent.hasExtra("Yes")) {
                    makeNotification()
                }
            }
            else{
                array.add(WeatherData(" ", " ", " ", ""))
            }
            super.onPostExecute(result)

        }



    }

    fun make(product:ArrayList<WeatherData>){
        val fragment = supportFragmentManager.findFragmentById(R.id.weatherFrameLayout)
        if(fragment == null){
            val weatherTransaction = supportFragmentManager.beginTransaction()
            val weatherFragment = WeatherFragment.newWeatherFragment(product)
            weatherTransaction.replace(R.id.weatherFrameLayout, weatherFragment, "item")
            weatherTransaction.commit()
        }
        else{
            (fragment as WeatherFragment).setData(product)
        }

    }

    override fun onListFragmentInteraction(item: WeatherData?) {
        Toast.makeText(this,"click",Toast.LENGTH_SHORT).show()
    }



    fun makeNotification(){  // 알람기능
        val chanelID = "MyChannel"
        val channelName = "WeatherChannel"
        val notificationChannel = NotificationChannel(chanelID,channelName
            ,NotificationManager.IMPORTANCE_DEFAULT)
        notificationChannel.enableVibration(true)
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val builder = NotificationCompat.Builder(this,chanelID)
            .setSmallIcon(R.drawable.runningman)
            .setContentTitle("새로운 기상 속보 확인하기")
            .setContentText(notifData)
            .setAutoCancel(true)


        val intent = Intent(this, WeatherActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(this, 1,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.createNotificationChannel(notificationChannel)
        val notification = builder.build()
        manager.notify(10, notification)
    }




    fun yesterday():String{  // 이틀 전 날짜 가져오는 함수
        var date = Calendar.getInstance()
        date.add(Calendar.DAY_OF_MONTH,-2)
        var yester = date.time
        var getdate = SimpleDateFormat("yyyyMMdd").format(yester)
        return getdate

    }
}
