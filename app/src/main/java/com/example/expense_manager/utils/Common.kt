package com.example.expense_manager.utils
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.widget.Toast
import java.util.Calendar
import java.util.Date

const val DATABASE_NAME = "notes.db"
const val COUNTDOWN_DATABASE_NAME = "countDown.db"
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("dd:M:yyyy hh:mm:ss")
    return sdf.format(Date())
}

fun getTimeZoneMessage(): String {
    val c: Calendar = Calendar.getInstance()

    when (c.get(Calendar.HOUR_OF_DAY)) {
        in 0..11 -> {
            return "Good Morning"
        }

        in 12..15 -> {
            return "Good Afternoon"
        }

        in 16..20 -> {
            return "Good Evening"
        }

        in 21..23 -> {
            return "Good Night"
        }
    }
    return ""
}