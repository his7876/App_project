package com.example.myphamacy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


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



    fun findmap(){ //위도, 경도 들어오면 약국 위치를 화면에 보여주는 함수
        // 화면 이동은 movecamera 기능 사용하기
        var name = intent.getStringExtra("name")
        var address = intent.getStringExtra("address")
        var phone = intent.getStringExtra("phone")
        Log.d("map",address.toString()+name.toString()+phone)

        var lat = intent.getDoubleExtra("lat",0.0)
        var log = intent.getDoubleExtra("log",0.0)

        var latLng =LatLng (log,lat)
        Log.d("map",latLng.toString())

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_image) as SupportMapFragment
        mapFragment.getMapAsync {
            mMap = it
            val mapdata = "주소 : "+address
            val option = MarkerOptions()
            option.position(latLng)
            option.title(name)
            option.snippet(mapdata)
            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            mMap.addMarker(option)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f))


        }
    }
}
