package com.nero.qtquicktext.ui.messageWithoutSavingNumber

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.nero.qtquicktext.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val NIGHT_MODE = "night_mode"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnContactOnWhatsApp.setOnClickListener {
            if (etPhoneNumber.text.toString().isEmpty()) {
                AlertDialogBlankInput()
            } else {
                //  CODE FOR COUNTRY CODE SPINNER

                cpp.registerCarrierNumberEditText(etPhoneNumber)
                val phoneNumber = cpp.fullNumber
                val installed: Boolean = appInstalledOrNot("com.whatsapp")
                if (installed) {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber")
                    )
                    startActivity(browserIntent)
                } else {
                    Toast.makeText(
                        context,
                        "WhatsApp is not installed on your device",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun appInstalledOrNot(uri: String): Boolean {
        return try {
            context?.packageManager?.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun AlertDialogBlankInput() {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setCancelable(true)
        builder?.setTitle("Error")
        builder?.setMessage("Please Enter A Number")
        val alert = builder?.create()
        alert?.window!!.setGravity(Gravity.CENTER)
        alert.show()
    }
}