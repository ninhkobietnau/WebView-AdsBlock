package com.ninhnau.lib

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.ninhnau.adsblock.AdsBlock
import com.ninhnau.adsblock.WebViewClientAdsBlock

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView: WebView = findViewById(R.id.webView)
        AdsBlock.instance.initAdsBlock(this)
        webView.webViewClient = object : WebViewClientAdsBlock() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }
        webView.loadUrl("https://dantri.com.vn/")
    }
}