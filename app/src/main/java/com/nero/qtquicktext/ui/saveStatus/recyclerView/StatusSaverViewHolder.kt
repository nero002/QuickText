package com.nero.qtquicktext.ui.saveStatus.recyclerView

import android.content.Context
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nero.qtquicktext.constant.Constant
import kotlinx.android.synthetic.main.item_layout_status_saver.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel

class StatusSaverViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun setData(statusModel: StatusModel, context: Context) {

        if (statusModel.getUri().toString().endsWith(".mp4")) {
            itemView.ivPlayIcon.visibility = View.VISIBLE
        } else {
            itemView.ivPlayIcon.visibility = View.INVISIBLE
        }
        Glide.with(view)
            .load(statusModel.getUri())
            .into(itemView.ivStatusSave)

        itemView.ivDownloadID.setOnClickListener {
            download(statusModel, context)
        }
    }

    private fun download(statusModel: StatusModel, context: Context) {

        CoroutineScope(Dispatchers.IO).launch {
            checkFolder()
            val path = statusModel.getPath()
            val file = File(path)
            val destPath =
                "${Environment.getExternalStorageDirectory()}" + Constant.SAVE_FOLDER_NAME


            copyFileOrDirectory(file.path, destPath)
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Save to: " + destPath + statusModel.getFilename(),
                    Toast.LENGTH_LONG
                )
                    .show();
            }
        }

    }

    private fun copyFileOrDirectory(srcDir: String?, dstDir: String?) {
        try {
            val src = File(srcDir)
            val dst = File(dstDir, src.name)
            if (src.isDirectory) {
                val files = src.list()
                val filesLength = files.size
                for (i in 0 until filesLength) {
                    val src1 = File(src, files[i]).path
                    val dst1 = dst.path
                    copyFileOrDirectory(src1, dst1)
                }
            } else {
                copyFile(src, dst)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    fun copyFile(sourceFile: File?, destFile: File) {
        if (!destFile.parentFile.exists()) destFile.parentFile.mkdirs()
        if (!destFile.exists()) {
            destFile.createNewFile()
        }
        var source: FileChannel? = null
        var destination: FileChannel? = null
        try {
            source = FileInputStream(sourceFile).channel
            destination = FileOutputStream(destFile).channel
            destination.transferFrom(source, 0, source.size())
        } finally {
            source?.close()
            destination?.close()
        }
    }

    private fun checkFolder() {
        val dir: File =
            File("${Environment.getExternalStorageDirectory()}" + Constant.SAVE_FOLDER_NAME)
        var isDirCreated = dir.exists()
        if (!isDirCreated) {
            isDirCreated = dir.mkdirs()
        }
        if (isDirCreated) {
            Log.d("Dheeraj", "created")
        }
    }

}