package co.adityarajput.notifilter

object Constants {
    const val CRASH_REPORT_EMAIL = "mail@adityarajput.co"

    const val ALERT_NOTIFICATION_ID = 1000
    const val ALERT_NOTIFICATION_CHANNEL_ID = "notifilter_alert"
    const val FOREGROUND_NOTIFICATION_ID = 1001
    const val FOREGROUND_NOTIFICATION_CHANNEL_ID = "notifilter_foreground"
    fun getReplaceNotificationId(filterId: Int) = 1002 + filterId
    fun getReplaceNotificationChannelId(filterId: Int) = "notifilter_replace_$filterId"

    const val ACTION_DISMISS_STALE = "co.adityarajput.notifilter.DISMISS_STALE"
    const val EXTRA_SBN_KEY = "extra_sbn_key"
    const val EXTRA_SBN_IS_CLEARABLE = "extra_sbn_is_clearable"

    const val HISTORY_SIZE = 200
    const val LOG_SIZE = 100
}
