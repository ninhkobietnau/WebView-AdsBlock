package com.ninhnau.adsblock

import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient

abstract class WebViewClientAdsBlock : WebViewClient() {

    private val loadedUrls: MutableMap<String, Boolean> = HashMap()

    private fun isBlockAds(url: String): Boolean {
        val ad: Boolean
        if (!loadedUrls.containsKey(url)) {
            ad = AdsBlock.instance.isAd(url)
            loadedUrls[url] = ad
        } else {
            ad = loadedUrls[url]!!
        }
        return ad
    }

    override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse? {
        val isBlockAds = isBlockAds(url)
        return if (isBlockAds) AdsBlock.instance.createEmptyResource() else super.shouldInterceptRequest(
            view,
            url
        )
    }

}