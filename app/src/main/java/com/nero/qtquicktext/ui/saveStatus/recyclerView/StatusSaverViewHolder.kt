package com.nero.qtquicktext.ui.saveStatus.recyclerView

import android.content.Context
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nero.qtquicktext.OnItemClick
import com.nero.qtquicktext.constant.Constant
import com.nero.qtquicktext.util.copyFileOrDirectory
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

class StatusSaverViewHolder(private val view: View, private val onItemClick: OnItemClick) :
    RecyclerView.ViewHolder(view) {

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

        itemView.ivShare.setOnClickListener {
            onItemClick.share(statusModel)
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