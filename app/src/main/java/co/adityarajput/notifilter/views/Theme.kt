package co.adityarajput.notifilter.views

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import co.adityarajput.notifilter.R

enum class Brightness(val icon: Int, val description: Int) {
    LIGHT(R.drawable.light_mode, R.string.light),
    SYSTEM(R.drawable.brightness_medium, R.string.system),
    DARK(R.drawable.dark_mode, R.string.dark),
}

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF7C70FF), // Purple
    secondary = Color(0xFF70FFA4), // Green
    tertiary = Color(0xFFFF7070), // Red
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF7C70FF), // Purple
    secondary = Color(0xFF70FFA4), // Green
    tertiary = Color(0xFFFF7070), // Red
)

val Orange = Color(0xFFffad7d)

private val Typography = Typography().run {
    val firaMono = FontFamily(
        Font(R.font.firamono_regular, FontWeight.Normal),
        Font(R.font.firamono_medium, FontWeight.Medium),
        Font(R.font.firamono_bold, FontWeight.Bold),
    )

    Typography(
        displayLarge.copy(fontFamily = firaMono),
        displayMedium.copy(fontFamily = firaMono),
        displaySmall.copy(fontFamily = firaMono),
        headlineLarge.copy(fontFamily = firaMono),
        headlineMedium.copy(fontFamily = firaMono),
        headlineSmall.copy(fontFamily = firaMono),
        titleLarge.copy(fontFamily = firaMono),
        titleMedium.copy(fontFamily = firaMono),
        titleSmall.copy(fontFamily = firaMono),
        bodyLarge.copy(fontFamily = firaMono),
        bodyMedium.copy(fontFamily = firaMono),
        bodySmall.copy(fontFamily = firaMono),
        labelLarge.copy(fontFamily = firaMono),
        labelMedium.copy(fontFamily = firaMono),
        labelSmall.copy(fontFamily = firaMono),
    )
}

@Composable
fun Theme(brightness: Brightness = Brightness.SYSTEM, content: @Composable () -> Unit) =
    MaterialTheme(
        if (brightness == Brightness.LIGHT || (brightness == Brightness.SYSTEM && !isSystemInDarkTheme())) LightColorScheme else DarkColorScheme,
        MaterialTheme.shapes, Typography, content,
    )
