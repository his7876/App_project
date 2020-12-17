package com.example.refugeapplication

import android.content.Intent
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_refuge.*
import kotlinx.android.synthetic.main.fragment_item.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.json.JSONArray

class RefugeActivity : AppCompatActivity() ,ItemFragment.OnListFragmentInteractionListener
{

    var array = ArrayList<Product>()
    var findarray = ArrayList<Product>()

    val items = arrayOf("민방위 대피소","민방위 급수시설", "홍수 대피소", "지진해일 대피소", "소방서")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refuge)
        init()
        initspinner()

    }


    private fun init() {

        make(array)

        if(intent.hasExtra("userlocation")){
            var loc = intent.getStringExtra("userlocation")

            locationText.setText(loc)
        }




        searchimage.setOnClickListener {

            var findText =searchText.text
            var new = findData(findText.toString())

            changeData(new)  //검색된 내용으로 새로운 데이터 넣어줌
            searchText.text.clear()
        }


    }




    fun getAddress(loc: LatLng): String { //경도 위도를 바탕으로 주소를 보여주는 함수
        val geocoder = Geocoder(this)
        val list = geocoder.getFromLocation(loc.latitude, loc.longitude, 1)
        Log.i("Plz", list[0].getAddressLine(0))
        return list[0].getAddressLine(0)


    }


    fun findData(text:String):ArrayList<Product>{ // 대피소 검색 보여주는 함수
        findarray.clear()

        for(i in 0 until array.size-1){
            if(array[i].name == text){

                findarray.add(
                    Product(array[i].name,array[i].address,array[i].distance,
                        array[i].lat,array[i].lng)
                )
            }
        }
        return findarray
    }

    fun checkDistance(refuge:LatLng):Float{  // 현위치와 대피소 간의 거리 구하기
        val lat = intent.getDoubleExtra("LatData",1.0)
        val lng = intent.getDoubleExtra("LngData",1.0)
        val user = LatLng(lat,lng)

        val locationA =Location("A")
        locationA.latitude = user.latitude
        locationA.longitude = user.longitude

        val locationB = Location("B")
        locationB.latitude = refuge.latitude
        locationB.longitude = refuge.longitude

        var distance= locationA.distanceTo(locationB)

        return distance
    }





   fun initspinner(){
       val myAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,items)
       spinner.adapter = myAdapter
       //spinner.prompt =" 대피소 "
       spinner.setSelection(0)
       spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
           override fun onNothingSelected(parent: AdapterView<*>?) {
              //
           }

           override fun onItemSelected(
               parent: AdapterView<*>?,
               view: View?,
               position: Int,
               id: Long
           ) {
               when(position){  // 선택된 부분의 대피소만 보여줌
                   0 ->{
                       array = inputEvacuationData()
                       changeData(array)
                   }
                   1 ->{
                       array = inputWaterData()
                       changeData(array)
                   }
                   2 ->{
                       array = inputFloodData()
                       changeData(array)
                   }
                   3 ->{
                       array = inputQuakerData()
                       changeData(array)
                   }
                   4 ->{
                       array = inputFireSationData()
                       changeData(array)
                   }
                   else ->{

                   }
               }
           }

       }
   }





    fun make(product:ArrayList<Product>){
        val fragment = supportFragmentManager.findFragmentById(R.id.frameLayout)
        if(fragment == null){
            val itemTransaction = supportFragmentManager.beginTransaction()
            val itemFragment = ItemFragment.newItemFragment(product)
            itemTransaction.replace(R.id.frameLayout, itemFragment, "item")
            itemTransaction.commit()
        }
        else{
            (fragment as ItemFragment).setdata(product)
        }
    }

    fun changeData(product:ArrayList<Product>){
        val fragment = supportFragmentManager.findFragmentByTag("item")
        if(fragment != null){
            val itemTranscation = supportFragmentManager.beginTransaction()
            val itemFragment = ItemFragment.newItemFragment(product)
            itemTranscation.replace(R.id.frameLayout,itemFragment,"item")
            itemTranscation.commit()
        }
    }


    //-----------Data Input을 위한 function-------------------------
    fun inputFloodData():ArrayList<Product>{  //홍수 대피소 정보 넣기
        val inputStream = assets.open("floodrefuge.json")
        val strData = inputStream.bufferedReader().use{
            it.readText() }
        val jsonArrayData = JSONArray(strData)

        val array = ArrayList<Product>()

        for( i in 0 until (jsonArrayData.length()-1)){
            val json = jsonArrayData.getJSONObject(i)
            val name =  json.getString("대피소명칭")
            val address = json.getString("소재지")
            val lat = json.getString("위도")
            val lng = json.getString("경도")

            val latLng = LatLng(lat.toDouble(),lng.toDouble())
            val distance = checkDistance(latLng)

            array.add(Product(name,address,distance,lat,lng))

        }
        array.sortBy { data->data.distance}
        return  array
    }

    fun inputQuakerData():ArrayList<Product>{ // 지진 대피소 정보 넣기
        val inputStream = assets.open("earthquakerefuge.json")
        val strData = inputStream.bufferedReader().use{
            it.readText() }
        val jsonArrayData = JSONArray(strData)

        val array = ArrayList<Product>()

        for( i in 0 until (jsonArrayData.length()-1)){
            val json = jsonArrayData.getJSONObject(i)
            val name =  json.getString("지진옥외대피장소명")
            val address = json.getString("소재지지번주소")
            val lat = json.getString("위도")
            val lng = json.getString("경도")

            val latLng = LatLng(lat.toDouble(),lng.toDouble())
            val distance = checkDistance(latLng)

            array.add(Product(name,address,distance,lat,lng))
        }
        array.sortBy { data->data.distance}
        return  array
    }
    fun inputEvacuationData():ArrayList<Product>{ // 민방위 대피소
        val inputStream = assets.open("ev.json")
        val strData = inputStream.bufferedReader().use{
            it.readText() }
        val jsonArrayData = JSONArray(strData)

        val array = ArrayList<Product>()

        for( i in 1 until (jsonArrayData.length()-1)){
            val json = jsonArrayData.getJSONObject(i)
            val name =  json.getString("X")
            val address = json.getString("Y")
            val lat = json.getString("위도")
            val lng = json.getString("경도")

            val latLng = LatLng(lat.toDouble(),lng.toDouble())
            val distance = checkDistance(latLng)

            array.add(Product(name,address,distance,lat,lng))
        }
        array.sortBy { data->data.distance}
        return  array
    }

    fun inputWaterData():ArrayList<Product>{ // 민방위 급수시설
        val inputStream = assets.open("waterplace.json")
        val strData = inputStream.bufferedReader().use{
            it.readText() }
        val jsonArrayData = JSONArray(strData)

        val array = ArrayList<Product>()

        for( i in 1 until (jsonArrayData.length()-1)){
            val json = jsonArrayData.getJSONObject(i)
            val name =  json.getString("X")
            val address = json.getString("Y")
            val lat = json.getString("위도")
            val lng = json.getString("경도")

            val latLng = LatLng(lat.toDouble(),lng.toDouble())
            val distance = checkDistance(latLng)

            array.add(Product(name,address,distance,lat,lng))

        }
        array.sortBy { data->data.distance}
        return  array
    }
    fun inputFireSationData():ArrayList<Product>{ // 민방위 대피소
        val inputStream = assets.open("firestation.json")
        val strData = inputStream.bufferedReader().use{
            it.readText() }
        val jsonArrayData = JSONArray(strData)

        val array = ArrayList<Product>()

        for( i in 0 until (jsonArrayData.length()-1)){
            val json = jsonArrayData.getJSONObject(i)
            val name =  json.getString("소방서명")
            val address = json.getString("위치")

            val lat = json.getString("위도")
            val lng = json.getString("경도")
            val latLng = LatLng(lat.toDouble(),lng.toDouble())
            val distance = checkDistance(latLng)

            array.add(Product(name,address,distance,lat,lng))

        }
        array.sortBy { data->data.distance}
        return  array
    }


    override fun onListFragmentInteraction(item: Product?) {

        var address = item!!.address
        var name = item!!.name
        var lat = item!!.lat
        var lng = item!!.lng

        val intent = Intent(this,MapActivity::class.java)
        intent.putExtra("refugeName",name)
        intent.putExtra("refugeAddress",address)
        intent.putExtra("refugeLat",lat)
        intent.putExtra("refugeLng",lng)
        startActivity(intent)





    }








}
