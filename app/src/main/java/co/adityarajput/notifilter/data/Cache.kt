package co.adityarajput.notifilter.data

import android.content.Intent
import android.content.pm.PackageManager
import co.adityarajput.notifilter.data.models.App
import co.adityarajput.notifilter.data.models.Intents
import co.adityarajput.notifilter.utils.Logger
import java.util.Collections.synchronizedMap

object Cache {
    private var _allPackages: List<App>? = null
    private var _visibleApps: List<App>? = null

    val intents: MutableMap<Int, Intents> = synchronizedMap(mutableMapOf())

    private var _cachedAt = 0L
    private const val APPS_CACHE_TIMEOUT = 10 * 60 * 1000L

    fun getAllPackages(packageManager: PackageManager): List<App> {
        if (_allPackages == null || System.currentTimeMillis() - _cachedAt > APPS_CACHE_TIMEOUT) {
            update(packageManager)
        }

        return _allPackages!!
    }

    fun getVisibleApps(packageManager: PackageManager): List<App> {
        if (_visibleApps == null || System.currentTimeMillis() - _cachedAt > APPS_CACHE_TIMEOUT) {
            update(packageManager)
        }

        return _visibleApps!!
    }

    fun update(packageManager: PackageManager) {
        Logger.d("Cache", "Updating apps")

        val allPackages = packageManager.getInstalledApplications(0)
            .map { App(it.loadLabel(packageManager).toString(), it.packageName) }
            .sortedBy { it.name }

        val visibleApps = packageManager.queryIntentActivities(
            Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER),
            0,
        ).map {
            App(
                it.activityInfo.applicationInfo.loadLabel(packageManager).toString(),
                it.activityInfo.packageName,
            )
        }.sortedBy { it.name }

        _allPackages = allPackages
        _visibleApps = visibleApps

        // INFO: An empty result means the OS withheld the app list, e.g. because the user has
        // not answered the permission prompt yet. Caching that would keep the filter form
        // spinning for the whole timeout even after access is granted, so retry next time.
        _cachedAt = if (allPackages.isEmpty()) 0L else System.currentTimeMillis()

        Logger.d("Cache", "Updated cache")
    }
}
