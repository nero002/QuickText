package com.nero.qtquicktext

import com.nero.qtquicktext.ui.saveStatus.recyclerView.StatusModel

interface OnItemClick {

    fun share(statusModel: StatusModel)
}