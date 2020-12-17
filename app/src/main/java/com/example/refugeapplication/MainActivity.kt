package com.example.refugeapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainActivity : AppCompatActivity()
,NavigationView.OnNavigationItemSelectedListener{

    var fusedLocationClient: FusedLocationProviderClient? = null
    var locationCallback: LocationCallback? = null
    var locationRequest: LocationRequest? = null

    var loc = LatLng(37.541, 126.986) // 서울 시청으로 초기 값 지정

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_layout_toolbar) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false)


        nav_view.setNavigationItemSelectedListener(this)

        init()
        initLocation()

    }





    fun getAddress(loc: LatLng): String { //경도 위도를 바탕으로 주소를 보여주는 함수
        val geocoder = Geocoder(this)
        val list = geocoder.getFromLocation(loc.latitude, loc.longitude, 1)
        Log.i("Plz", list[0].getAddressLine(0))
        return list[0].getAddressLine(0)


    }

    fun init() {
        refugeImg.setOnClickListener {
            // 재난 대피소 액티비티로 화면 전환
            val i = Intent(this, RefugeActivity::class.java)
            var location = userLocationTxt.text
            i.putExtra("userlocation", location)
            i.putExtra("LatData", loc.latitude)
            i.putExtra("LngData", loc.longitude)
            startActivity(i)

        }
        infoImg.setOnClickListener {
            // 재난 대피 요령을 알려주는 액티비티로 화면 전환
            val i = Intent(this, InfoActivity::class.java)
            startActivity(i)

        }
        weatherImg.setOnClickListener {
            // 긴급전화 화면으로 전환
            val i = Intent(this, CallActivity::class.java)
            var location = userLocationTxt.text
            i.putExtra("userlocation", location)
            startActivity(i)
        }

        userLocationImage.setOnClickListener {
            getuserlocation()
        }

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
            var result = getAddress(loc)
            userLocationTxt.setText("")
            userLocationTxt.setText(result)

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
                    userLocationTxt.setText("")
                    var result = getAddress(loc)
                    userLocationTxt.setText(result)


                }
            }
        }
        fusedLocationClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{ // 메뉴 버튼
                drawer_layout.openDrawer(GravityCompat.START)    // 네비게이션 드로어 열기
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.nav_home-> {
                //Toast.makeText(this,"home",Toast.LENGTH_SHORT).show()

            }

            R.id.nav_info-> {
               // Toast.makeText(this,"기상 정보",Toast.LENGTH_SHORT).show()
                val i = Intent(this, WeatherActivity::class.java)
                startActivity(i)

            }
            R.id.nav_setting-> {
                //Toast.makeText(this,"설정",Toast.LENGTH_SHORT).show()
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)

            }

        }
        return false

    }



    override fun onBackPressed() { //뒤로가기 처리
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawers()

        } else{
            super.onBackPressed()
        }
    }

}



