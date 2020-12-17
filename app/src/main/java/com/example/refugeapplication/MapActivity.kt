package com.example.refugeapplication

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_refuge.*

class MapActivity : AppCompatActivity(),OnMapReadyCallback{
    lateinit var  mMap : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        findmap()
    }

    override fun onMapReady(googleMap: GoogleMap){
        mMap =googleMap
        findmap()
    }



    fun findmap(){ //위도, 경도 들어오면 대피소의 위치를 화면에 보여주는 함수
        // 화면 이동은 movecamera 기능 사용하기
        var name = intent.getStringExtra("refugeName")
        var address = intent.getStringExtra("refugeAddress")
        var lat = intent.getStringExtra("refugeLat")!!.toDouble()
        var lng = intent.getStringExtra("refugeLng")!!.toDouble()
        var latLng =LatLng (lat,lng)
        Log.i("ㅁㅁa",latLng.toString())
        Log.i("ㅁㅁa",name)
        Log.i("ㅁㅁa",address)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            mMap = it
            val option = MarkerOptions()
            option.position(latLng)
            option.title(name)
            option.snippet(address)
            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            mMap.addMarker(option)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f))


        }
    }
}
