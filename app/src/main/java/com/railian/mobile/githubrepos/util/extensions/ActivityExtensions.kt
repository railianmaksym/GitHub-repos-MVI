package com.railian.mobile.githubrepos.util.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat

fun Activity.openUrlInBrowser(url: String) {
    try {
        val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        ContextCompat.startActivity(this, myIntent, null)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(
            this, "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG
        ).show()
        e.printStackTrace()
    }
}