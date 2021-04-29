package com.nero.qtquicktext.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.net.URLConnection


fun loadBitmap(url: String?): Bitmap? {
    var bm: Bitmap? = null
    var `is`: InputStream? = null
    var bis: BufferedInputStream? = null
    try {
        val conn: URLConnection = URL(url).openConnection()
        conn.connect()
        `is` = conn.getInputStream()
        bis = BufferedInputStream(`is`, 8192)
        bm = BitmapFactory.decodeStream(bis)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        if (bis != null) {
            try {
                bis.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (`is` != null) {
            try {
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    return bm
}