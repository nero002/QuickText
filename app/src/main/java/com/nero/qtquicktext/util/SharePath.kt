package com.nero.qtquicktext.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.Intent
import android.os.TransactionTooLargeException
import com.nero.qtquicktext.R
import com.simplemobiletools.commons.extensions.getFinalUriFromPath
import com.simplemobiletools.commons.extensions.getUriMimeType
import com.simplemobiletools.commons.extensions.showErrorToast
import com.simplemobiletools.commons.extensions.toast
import com.simplemobiletools.commons.helpers.ensureBackgroundThread


fun Activity.sharePath(path: String, applicationId: String) {
    ensureBackgroundThread {
        val newUri =
            getFinalUriFromPath(path, applicationId) ?: return@ensureBackgroundThread
        Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, newUri)
            putExtra(
                Intent.EXTRA_TEXT,
                "Shared using Quick Text \n https://play.google.com/store/apps/details?id=com.nero.qtquicktext"
            );
            clipData = ClipData.newRawUri(null, newUri)
            type = getUriMimeType(path, newUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            try {
                startActivity(Intent.createChooser(this, getString(R.string.share_via)))
            } catch (e: ActivityNotFoundException) {
                toast(R.string.no_app_found)
            } catch (e: RuntimeException) {
                if (e.cause is TransactionTooLargeException) {
                    toast(R.string.maximum_share_reached)
                } else {
                    showErrorToast(e)
                }
            } catch (e: Exception) {
                showErrorToast(e)
            }
        }
    }
}
