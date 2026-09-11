package co.adityarajput.notifilter.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech.*
import android.speech.tts.TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import android.speech.tts.TextToSpeech as TTS

object TextToSpeech {
    private lateinit var tts: TTS

    private var status: Int? = null

    suspend fun initialize(context: Context) {
        if (ready)
            return

        tts = TTS(context.applicationContext) { status = it }

        try {
            withTimeout(5.seconds) {
                while (status == null)
                    delay(10.milliseconds)
            }
        } catch (e: Exception) {
            tts.shutdown()
            status = null
            throw e
        }
    }

    val ready: Boolean get() = status == SUCCESS

    val available: Boolean
        get() = try {
            ready && tts.isLanguageAvailable(Locale.getDefault()) in listOf(
                LANG_COUNTRY_VAR_AVAILABLE,
                LANG_COUNTRY_AVAILABLE,
                LANG_AVAILABLE,
            )
        } catch (e: Exception) {
            Logger.e("TextToSpeech", "Failed to check TTS availability", e)
            false
        }

    fun openInstallationScreen(context: Context) =
        context.startActivity(Intent(ACTION_INSTALL_TTS_DATA))

    fun speak(text: String, id: Int) {
        tts.speak(
            text.also { Logger.i("TextToSpeech", "Speaking '$it'") },
            QUEUE_ADD,
            Bundle.EMPTY,
            id.toString(),
        )
    }
}
