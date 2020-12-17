package com.example.myphamacy

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.AlarmClock
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.search_pharmacy
import kotlinx.android.synthetic.main.activity_phamacy.*

class MainActivity : AppCompatActivity() {

    var fusedLocationClient: FusedLocationProviderClient? = null
    var locationCallback: LocationCallback? = null
    var locationRequest: LocationRequest? = null

    var loc = LatLng(37.541, 126.986) // 서울 시청으로 초기 값 지정
    var result: String? = null
    var log:Double? = null
    var lan:Double? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLocation()
        init()
    }


    private fun init() {
        search_pharmacy.setOnClickListener {
            val i  = Intent(this,PhamacyActivity::class.java)
            i.putExtra("location",result)
            i.putExtra("lan",lan)
            i.putExtra("log",log)
            startActivity(i)
        }
        search_pill.setOnClickListener {
            val i  = Intent(this,PillSearchActivity::class.java)
            startActivity(i)
        }
        alarm_layout.setOnClickListener {
            val intent = Intent(AlarmClock.ACTION_SET_ALARM)
            startActivity(intent)
        }
    }

    fun getAddress(loc: LatLng): String { //경도 위도를 바탕으로 주소를 보여주는 함수
        val geocoder = Geocoder(this)
        val list = geocoder.getFromLocation(loc.latitude, loc.longitude, 1)
        Log.i("Plz", list[0].getAddressLine(0))
        return list[0].getAddressLine(0)


    }

    fun initLocation() {  //권한정보체크
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            getuserlocation()
            startLocationUpdates()

        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ), 100
            )
        }
    }

    fun getuserlocation() { // 사용자 위치 정보
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient?.lastLocation?.addOnSuccessListener {
            loc = LatLng(it.latitude, it.longitude)
            Log.i("현 데이터", loc.toString())
            result = getAddress(loc)
            log = loc.longitude
            lan = loc.latitude


        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                getuserlocation()
                startLocationUpdates()


            } else {
                Toast.makeText(this, "기본 위치로 설정합니다.", Toast.LENGTH_SHORT).show()
                userLocationTxt.setText(getAddress(loc))
            }
        }
    }

    fun startLocationUpdates() { //사용자 위치 정보 갱신
        locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 50000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationReuslt: LocationResult?) {
                locationReuslt ?: return
                for (location in locationReuslt.locations) {
                    loc = LatLng(location.latitude, location.longitude)
                    Log.i("갱신 데이터", loc.toString())
                    result = getAddress(loc)
                    log = loc.longitude
                    lan = loc.latitude


                }
            }
        }
        fusedLocationClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

    }


}
