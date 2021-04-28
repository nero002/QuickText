package com.nero.qtquicktext.ui.saveStatus.recyclerView

import android.net.Uri

class StatusModel {
    private var path: String? = null
    private var filename: String? = null
    private var uri: Uri? = null

    fun StatusModel() {

    }

    fun StatusModel(name: String?, path: String?, filename: String?, uri: Uri?) {
        this.path = path
        this.filename = filename
        this.uri = uri
    }


    fun getPath(): String? {
        return path
    }

    fun setPath(path: String?) {
        this.path = path
    }

    fun getFilename(): String? {
        return filename
    }

    fun setFilename(filename: String?) {
        this.filename = filename
    }

    fun getUri(): Uri? {
        return uri
    }

    fun setUri(uri: Uri?) {
        this.uri = uri
    }
}