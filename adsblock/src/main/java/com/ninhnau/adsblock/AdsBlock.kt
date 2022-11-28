package com.ninhnau.adsblock

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.text.TextUtils
import android.webkit.WebResourceResponse
import androidx.annotation.WorkerThread
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL

class AdsBlock {
    private val AD_HOSTS_FILE = "host.txt"
    private val AD_HOSTS: HashSet<String> = HashSet()
    private var isEnableAds = true

    @SuppressLint("StaticFieldLeak")
    fun initAdsBlock(context: Context) {
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                try {
                    loadFromAssets(context)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                return null
            }
        }.execute()
    }

    @WorkerThread
    @Throws(IOException::class)
    private fun loadFromAssets(context: Context) {
        val stream = context.assets.open(AD_HOSTS_FILE)
        val inputStreamReader = InputStreamReader(stream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            line?.let { AD_HOSTS.add(it) }
        }
        bufferedReader.close()
        inputStreamReader.close()
        stream.close()
    }

    private fun isAdHost(host: String): Boolean {
        if (TextUtils.isEmpty(host)) {
            return false
        }
        val index = host.indexOf(".")
        return index >= 0 && (AD_HOSTS.contains(host) ||
                index + 1 < host.length && isAdHost(host.substring(index + 1)))
    }

    @Throws(MalformedURLException::class)
    private fun getHost(url: String?): String {
        return URL(url).host
    }

    fun isAd(url: String?): Boolean {
        return if (isEnableAds) {
            try {
                isAdHost(getHost(url)) || AD_HOSTS.contains(Uri.parse(url).lastPathSegment)
            } catch (e: MalformedURLException) {
                false
            }
        } else {
            false
        }
    }

    fun isEnableBlock(enable: Boolean) {
        isEnableAds = enable
    }

    fun createEmptyResource(): WebResourceResponse {
        return WebResourceResponse("text/plain", "utf-8", ByteArrayInputStream("".toByteArray()))
    }

    companion object {
        private var INSTANCE: AdsBlock? = null
        val instance: AdsBlock
            get() {
                if (INSTANCE == null) {
                    synchronized(AdsBlock::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = AdsBlock()
                        }
                    }
                }
                return INSTANCE!!
            }
    }
}