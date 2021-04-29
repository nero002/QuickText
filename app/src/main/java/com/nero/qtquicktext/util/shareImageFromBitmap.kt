package com.nero.qtquicktext.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.nero.qtquicktext.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

 fun shareImageFromBitmap(bmp: Bitmap, context: Context) {
    val uri: Uri = getUriImageFromBitmap(bmp, context)
        ?: //Show no URI message
        return
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.putExtra(Intent.EXTRA_TEXT, "")
    shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
    shareIntent.type = "image/png"
    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    context.startActivity(Intent.createChooser(shareIntent, "Share image using"))
}

private fun getUriImageFromBitmap(bmp: Bitmap?, context: Context): Uri? {
    if (bmp == null) return null
    var bmpUri: Uri? = null
    try {
        val file: File = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "IMG_" + System.currentTimeMillis() + ".png"
        )
        val out = FileOutputStream(file)
        bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
        out.flush()
        out.close()
        //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        bmpUri = FileProvider.getUriForFile(
            context,
            BuildConfig.APPLICATION_ID.toString() + ".provider",
            file
        )
        //            else
//                bmpUri = Uri.fromFile(file);
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return bmpUri
}