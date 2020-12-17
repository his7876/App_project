package com.example.refugeapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.android.synthetic.main.activity_splash.view.*

class InfoActivity : AppCompatActivity() {
    lateinit var mWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        mWebView = findViewById(R.id.webViewq)
        mWebView.webViewClient
        mWebView.loadUrl("http://www.safekorea.go.kr/idsiSFK/neo/sfk/cs/contents/prevent/prevent01.html?menuSeq=126")
        var mWebViewSetting = mWebView.settings
        mWebViewSetting.javaScriptEnabled = true
        mWebViewSetting.javaScriptCanOpenWindowsAutomatically = false
        mWebViewSetting.useWideViewPort = false
        mWebViewSetting.setSupportMultipleWindows(false)
        mWebViewSetting.setSupportZoom(true)
        initInfo()

    }

    private fun initInfo() {
        button1.setOnClickListener {//자연재난
            mWebView.loadUrl("http://www.safekorea.go.kr/idsiSFK/neo/sfk/cs/contents/prevent/prevent01.html?menuSeq=126")
        }
        button2.setOnClickListener {//사회재난
            mWebView.loadUrl("http://www.safekorea.go.kr/idsiSFK/neo/sfk/cs/contents/prevent/SDIJKM5116_LIST.html?menuSeq=127")
        }
        button3.setOnClickListener {//생활안전
            mWebView.loadUrl("http://www.safekorea.go.kr/idsiSFK/neo/sfk/cs/contents/prevent/SDIJKM5138_LIST.html?menuSeq=128")

        }
        button4.setOnClickListener {//비상대피
            mWebView.loadUrl("http://www.safekorea.go.kr/idsiSFK/neo/sfk/cs/contents/prevent/SDIJKM5103.html?menuSeq=728")

        }

    }


}
//