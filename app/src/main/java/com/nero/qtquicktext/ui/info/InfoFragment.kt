package com.nero.qtquicktext.ui.info

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.view.View
import androidx.fragment.app.Fragment
import com.nero.qtquicktext.R
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.fragment_info.*


class InfoFragment : Fragment(R.layout.fragment_info) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvShare.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND

                putExtra(
                    Intent.EXTRA_TEXT,
                    " \n https://play.google.com/store/apps/details?id=com.nero.qtquicktext"
                )
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, null)
            startActivity(shareIntent)
        }
    }
}