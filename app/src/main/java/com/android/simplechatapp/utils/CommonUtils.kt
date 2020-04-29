package com.android.simplechatapp.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun Timestamp.toReadableDate(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(Date(this.seconds*1000))
}

fun Date.toFirestoreTimestamp():Timestamp{
    return Timestamp(this)
}

fun getCurrentFirestoreTimestamp():Timestamp{
    return Calendar.getInstance().time.toFirestoreTimestamp()
}
