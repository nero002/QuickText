package com.nero.qtquicktext.util

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection


fun shareVideo(title: String?, path: String, context: Context) {
    MediaScannerConnection.scanFile(
        context, arrayOf(path),
        null
    ) { path, uri ->
        val shareIntent = Intent(
            Intent.ACTION_SEND
        )
        shareIntent.type = "video/*"
        shareIntent.putExtra(
            Intent.EXTRA_SUBJECT, title
        )
        shareIntent.putExtra(
            Intent.EXTRA_TITLE, title
        )
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        val clipData = ClipData.newRawUri(null, uri)

        shareIntent
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
        context.startActivity(
            Intent.createChooser(
                shareIntent,
                "123"
            )
        )
    }
}