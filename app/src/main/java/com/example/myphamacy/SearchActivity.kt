package com.example.myphamacy

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.net.URL
import java.net.URLEncoder
import java.util.concurrent.TimeUnit


class SearchActivity : AppCompatActivity(),PillFragment.OnListFragmentInteractionListener {

    private lateinit var retrofit: Retrofit
    private lateinit var retrofitService: PillSearchAPI
    val ServiceKey: String =
        "1NvKefm2fOrfb395qHYCmXF13MDkSMGUXqRR9Nxuo+8kAeiFzaI7/zaBa7mzym8ahgIg1AmD/sdRaRZGurwGGg=="
    lateinit var data:String
    var array = ArrayList<Pill>()


    var string: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setRetrofitInit()
        init()


    }

    fun init() {
        string = intent.getStringExtra("string")

        Log.d("putdata", string.toString())
        val encoderStr = URLEncoder.encode(string, "UTF-8")
        itemText.setText(string)
        callProduct(encoderStr)
        Log.d("putdata", encoderStr)
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
            .baseUrl("http://apis.data.go.kr/1470000/MdcinGrnIdntfcInfoService/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(createOkHttpClient())
            .build()
        retrofitService = retrofit.create(PillSearchAPI::class.java)

    }

    fun callProduct(item: String) {
        retrofitService.getPill(ServiceKey, 3, 1, item)
            .enqueue(object : retrofit2.Callback<SearchResponseWrapperPill> {
                override fun onResponse(
                    call: Call<SearchResponseWrapperPill>,
                    response: Response<SearchResponseWrapperPill>
                ) {
                    val body = response.body()
                    Log.i("TTT1", body.toString())

                    data = Gson().toJson(body)
                    Log.d("TTT2", data)

                    val json = JSONObject(data)
                    val obj = (json["body"] as JSONObject)
                    val count = obj.get("totalCount")
                    Log.d("TTTCount", count.toString())

                    countText.setText(count.toString() + "건")
                    val c = count.toString().toInt()
                    val parser_header = obj.getJSONObject("items")

                    for (i in 0..4) {
                        val upperArray = parser_header.getJSONArray("item")
                        Log.d("TTT3", upperArray.toString())
                        val upperObject = upperArray.getJSONObject(0)
                        val chart = upperObject.getString("CHART")
                        Log.d("TTT4", chart)
                        val class_name = upperObject.getString("CLASS_NAME")
                        val item_color = upperObject.getString("COLOR_CLASS1")
                        val drug_shape = upperObject.getString("DRUG_SHAPE")

                        val entp_name = upperObject.getString("ENTP_NAME")
                        val form_code_name = upperObject.getString("FORM_CODE_NAME")


                        val item_name = upperObject.getString("ITEM_NAME")


                        val item_image="image"+i
                        val reid = resources.getIdentifier(item_image,"drawable",packageName)

                        array.add(
                            Pill(
                                item_name, entp_name, chart,
                                drug_shape, item_color, class_name, form_code_name, reid
                            )
                        )

                    }
                    Log.d("TTT5", array.toString())
                    make(array)

                }


                override fun onFailure(call: Call<SearchResponseWrapperPill>, t: Throwable) {
                    Log.d("fail", t.message.toString())
                }

            }
            )


    }

    fun make(product: ArrayList<Pill>){  //fragment 부착
        val fragment = supportFragmentManager.findFragmentById(R.id.frame_pill)
        if(fragment == null){
            val pillTransaction = supportFragmentManager.beginTransaction()
            val pillFragment = PillFragment.newPillFragment(product)
            pillTransaction.replace(R.id.frame_pill, pillFragment, "item")
            pillTransaction.commit()
        }
        else{
            (fragment as PillFragment).setData(product)
        }

    }

    override fun onListFragmentInteraction(item: Pill?) {
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
    }




}

