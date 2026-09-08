package co.adityarajput.notifilter

import android.app.Application
import co.adityarajput.notifilter.data.AppContainer
import co.adityarajput.notifilter.utils.setWidgetPreviews
import co.adityarajput.notifilter.utils.subscribeWidgetsToFlows
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.acra.ACRA
import org.acra.config.dialog
import org.acra.config.mailSender
import org.acra.data.StringFormat
import org.acra.ktx.initAcra

class NotiFilterApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        if (ACRA.isACRASenderServiceProcess())
            return

        initAcra {
            buildConfigClass = BuildConfig::class.java
            reportFormat = StringFormat.JSON

            mailSender {
                mailTo = Constants.CRASH_REPORT_EMAIL
                subject = "NotiFilter Crash Report"
                additionalSharedPreferences = listOf(Constants.STATE, Constants.SETTINGS)
            }

            dialog {
                title = getString(R.string.crash_dialog_title)
                text = getString(R.string.crash_dialog_text)
                commentPrompt = getString(R.string.crash_dialog_comment_prompt)
                positiveButtonText = getString(R.string.crash_dialog_positive_button)
            }
        }

        container = AppContainer(this)

        if (BuildConfig.DEBUG) {
            // INFO: While debugging, populate database with demo data for screenshots
            container.seedDemoData()
        }

        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            this@NotiFilterApplication.setWidgetPreviews()
            subscribeWidgetsToFlows(this@NotiFilterApplication)
        }
    }
}
