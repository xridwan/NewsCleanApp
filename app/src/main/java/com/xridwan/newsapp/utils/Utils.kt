package com.xridwan.newsapp.utils

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    inline fun <reified T : Parcelable> Fragment.parcelable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> arguments?.getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") arguments?.getParcelable(key) as? T
    }

    fun formatDate(dateStringUTC: String?): String? {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'", Locale.US)
        var dateObject: Date? = null
        try {
            dateObject = dateStringUTC?.let { simpleDateFormat.parse(it) }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val df = SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH)
        val formattedDateUTC = dateObject?.let { df.format(it) }

        df.timeZone = TimeZone.getTimeZone("UTC")
        var date: Date? = null
        try {
            date = formattedDateUTC?.let { df.parse(it) }
            df.timeZone = TimeZone.getDefault()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date?.let { df.format(it) }
    }
}