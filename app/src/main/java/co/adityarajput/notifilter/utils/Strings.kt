package co.adityarajput.notifilter.utils

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import co.adityarajput.notifilter.R
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun Long.toDelta(): String {
    val now = System.currentTimeMillis()
    val delta = now - this

    val seconds = delta / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        days > 1000 -> stringResource(R.string.long_ago)
        days > 0 -> pluralStringResource(R.plurals.day_ago, days.toInt(), days)
        hours > 0 -> pluralStringResource(R.plurals.hour_ago, hours.toInt(), hours)
        minutes > 0 -> pluralStringResource(R.plurals.minute_ago, minutes.toInt(), minutes)
        seconds > 0 -> pluralStringResource(R.plurals.second_ago, seconds.toInt(), seconds)
        else -> stringResource(R.string.just_now)
    }
}

fun Long.toReadableTime(): String {
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDateTime().format(
        if (System.currentTimeMillis() - this > 24 * 60 * 60 * 1000)
            DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
        else
            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT),
    )
}

fun String.getFirst(length: Int): String =
    if (this.length <= length) this else this.take(length - 3) + "..."

fun Int.toHourMinuteString() =
    "${(this / 60).toString().padStart(2, '0')}:${(this % 60).toString().padStart(2, '0')}"

@Composable
fun Boolean.getToggleString(): String =
    stringResource(if (this) R.string.disable else R.string.enable)

@Suppress("DEPRECATION")
val Bundle.printable get() = "Bundle(${keySet().joinToString(", ") { "$it=${get(it)}" }})"
