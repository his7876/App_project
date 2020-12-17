package com.example.myphamacy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_phamacy.*
import kotlinx.android.synthetic.main.fragment_pharmacy.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import org.xml.sax.InputSource
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory


class PhamacyActivity : AppCompatActivity(),PharmacyFragment.OnListFragmentInteractionListener {

    var array = ArrayList<Pharmacy>()
    lateinit var data:String
    var lan:Double? = null
    var log:Double? = null


    private lateinit var retrofit: Retrofit
    private lateinit var retrofitService: RetrofitAPI
    val ServiceKey:String = "1NvKefm2fOrfb395qHYCmXF13MDkSMGUXqRR9Nxuo+8kAeiFzaI7/zaBa7mzym8ahgIg1AmD/sdRaRZGurwGGg=="


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phamacy)
        init()
        setRetrofitInit()
        callProduct()

    }




    private fun init() {
        if (intent.hasExtra("location")) {
            var loc = intent.getStringExtra("location")
            userLocationTxt.setText("현위치 :"+ loc)
            log = intent.getDoubleExtra("log",0.1)
            lan = intent.getDoubleExtra("lan",0.1)
            Log.i("lan,log",lan.toString()+log.toString())

        }


    }

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
        return builder.build()
    }

    fun setRetrofitInit() {
        retrofit = Retrofit.Builder()
            .baseUrl("http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(createOkHttpClient())

            .build()
        retrofitService = retrofit.create(RetrofitAPI::class.java)

    }

    fun callProduct() {
        retrofitService.getPharmacy(ServiceKey,log!!,lan!!)
            .enqueue(object : retrofit2.Callback<SearchResponseWrapper> {


                override fun onResponse(
                    call: Call<SearchResponseWrapper>,
                    response: retrofit2.Response<SearchResponseWrapper>
                ) {
                    val body = response.body()
                    Log.e("TTTTPharmcy", Gson().toJson(body))

                    data = Gson().toJson(body)
                    val json = JSONObject(data)
                    val obj = (json["body"] as JSONObject)
                    val parser_header = obj.getJSONObject("items")

                    val upperArray = parser_header.getJSONArray("item")

                    for (i in 0..5) {
                        val upperObject = upperArray.getJSONObject(i)
                        val distance = upperObject.getDouble("distance")
                        val address = upperObject.getString("dutyAddr")
                        val name = upperObject.getString("dutyName")
                        val tel = upperObject.getString("dutyTel1")
                        val lat = upperObject.getDouble("latitude")
                        val lon = upperObject.getDouble("longitude")

                        array.add(Pharmacy(name, tel, address, (distance * 1000).toInt(),lat,lon))

                    }
                    make(array)
                    Log.d("TTT",array.toString())


                }

                override fun onFailure(call: Call<SearchResponseWrapper>, t: Throwable) {
                    Log.d("fail", t.message.toString())
                }
            })


    }




    fun make(product:ArrayList<Pharmacy>){  //fragment 부착
        val fragment = supportFragmentManager.findFragmentById(R.id.frame_pharmacy)
        if(fragment == null){
            val pharmacyrTransaction = supportFragmentManager.beginTransaction()
            val pharmacyFragment = PharmacyFragment.newPharmacyFragment(product)
            pharmacyrTransaction.replace(R.id.frame_pharmacy, pharmacyFragment, "item")
            pharmacyrTransaction.commit()
        }
        else{
            (fragment as PharmacyFragment).setData(product)
        }

    }

    override fun onListFragmentInteraction(item: Pharmacy?) {

        Toast.makeText(this,"click",Toast.LENGTH_SHORT).show()
        Log.d(" this api",item.toString())
        val item = item
        var address = item?.address
        var name = item?.name
        var lat = item?.lat
        var log = item?.log
        var phone = item?.phone

        val intent = Intent(this,MapActivity::class.java)
        intent.putExtra("name",name)
        intent.putExtra("address",address)
        intent.putExtra("lat",lat)
        intent.putExtra("log",log)
        intent.putExtra("phone",phone)
        Log.d("putExtra",lat.toString()+log.toString())
        startActivity(intent)

    }


}
