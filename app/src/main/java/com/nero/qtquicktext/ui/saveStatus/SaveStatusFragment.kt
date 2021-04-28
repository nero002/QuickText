package com.nero.qtquicktext.ui.saveStatus

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nero.qtquicktext.R
import com.nero.qtquicktext.constant.Constant
import com.nero.qtquicktext.ui.saveStatus.recyclerView.StatusModel
import com.nero.qtquicktext.ui.saveStatus.recyclerView.StatusSaverAdapter
import kotlinx.android.synthetic.main.fragment_save_status.*
import java.io.File
import java.util.*

class SaveStatusFragment : Fragment(R.layout.fragment_save_status) {

    lateinit var statusSaverAdapter: StatusSaverAdapter
    lateinit var files: Array<File>
    var fileList = ArrayList<StatusModel>()
    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.SrSwipeRefreshLayout)
        setUpRefreshLayout()

        swipeRefreshLayout.setOnRefreshListener {
            setUpRefreshLayout()

            Handler().postDelayed({
                swipeRefreshLayout.isRefreshing = false
                Toast.makeText(context, "Refreshing!", Toast.LENGTH_SHORT).show()
            }, 2000)
        }

    }

    private fun setUpRefreshLayout() {
        rvStatusViews.setHasFixedSize(true)
        rvStatusViews.layoutManager = LinearLayoutManager(context)
        statusSaverAdapter = context?.let { StatusSaverAdapter(getData(), it) }!!
        rvStatusViews.adapter = statusSaverAdapter
        statusSaverAdapter.notifyDataSetChanged()
    }

    private fun getData(): ArrayList<StatusModel> {
        var statusModel: StatusModel
        val targetPath =
            Environment.getExternalStorageDirectory().absolutePath + Constant.FOLDER_NAME.toString() + "Media/.Statuses/"
        val targetDir = File(targetPath)
        files = targetDir.listFiles()
        for (i in files.size - 1 downTo 1) {

            val file = files[i]

            statusModel = StatusModel()
            statusModel.setUri(Uri.fromFile(file))
            statusModel.setPath(file.absolutePath)
            statusModel.setFilename(file.name)
            fileList.add(statusModel)
        }
        return fileList
    }
}
