package co.adityarajput.notifilter.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import co.adityarajput.notifilter.services.Preferences
import co.adityarajput.notifilter.views.Brightness

class AppearanceViewModel : ViewModel() {
    var brightness by mutableStateOf(Brightness.entries[Preferences.brightness])
}
