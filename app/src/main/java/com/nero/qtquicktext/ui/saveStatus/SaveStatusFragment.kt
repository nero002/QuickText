package com.nero.qtquicktext.ui.saveStatus

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nero.qtquicktext.OnItemClick
import com.nero.qtquicktext.R
import com.nero.qtquicktext.constant.Constant
import com.nero.qtquicktext.ui.saveStatus.recyclerView.StatusModel
import com.nero.qtquicktext.ui.saveStatus.recyclerView.StatusSaverAdapter
import kotlinx.android.synthetic.main.fragment_save_status.*
import java.io.File
import java.util.*

class SaveStatusFragment : Fragment(R.layout.fragment_save_status), OnItemClick {

    lateinit var statusSaverAdapter: StatusSaverAdapter
    lateinit var files: Array<File>
    var fileList = ArrayList<StatusModel>()
    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.SrSwipeRefreshLayout)

        setUpRefreshLayoutAdapter()

        swipeRefreshLayout.setOnRefreshListener {

//            tvPullDown?.visibility = View.GONE
//            swipeRefreshLayout.visibility = View.VISIBLE
//            fileList.clear()
            setUpRefreshLayoutAdapter()
            swipeRefreshLayout.isRefreshing = false

        }
        fileList.clear()
        Toast.makeText((context)!!, "Refreshing!", Toast.LENGTH_SHORT).show()
    }

    private fun setUpRefreshLayoutAdapter() {
        rvStatusViews.setHasFixedSize(true)
        rvStatusViews.layoutManager = GridLayoutManager(context, 2)
        statusSaverAdapter = context?.let { StatusSaverAdapter(getData(), it, this) }!!
        rvStatusViews.adapter = statusSaverAdapter
        statusSaverAdapter.notifyDataSetChanged()

    }

    private fun getData(): ArrayList<StatusModel> {
        var statusModel: StatusModel
        val targetPath =
            Environment.getExternalStorageDirectory().absolutePath + Constant.FOLDER_NAME.toString() + "Media/.Statuses/"
        val targetDir = File(targetPath)
        files = targetDir.listFiles()
        for (i in 1 until files.size) {

            val file = files[i]

            statusModel = StatusModel()
            statusModel.setUri(Uri.fromFile(file))
            statusModel.setPath(file.absolutePath)
            statusModel.setFilename(file.name)
            fileList.add(statusModel)
        }
        return fileList
    }



    override fun share(statusModel: StatusModel) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                statusModel.getUri()
            )
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
