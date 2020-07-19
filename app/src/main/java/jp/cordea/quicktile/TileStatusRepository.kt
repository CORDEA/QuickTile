package jp.cordea.quicktile

import android.content.SharedPreferences
import dagger.Reusable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@Reusable
class TileStatusRepository @Inject constructor(
    private val preferences: SharedPreferences
) {
    companion object {
        private const val STATUS_KEY = "status"
    }

    @ExperimentalCoroutinesApi
    fun observe() = callbackFlow<Int> {
        val listener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                if (key == STATUS_KEY) {
                    sendBlocking(find())
                }
            }
        preferences.registerOnSharedPreferenceChangeListener(listener)
        sendBlocking(find())
        awaitClose { preferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    fun find() = preferences.getInt(STATUS_KEY, 0)

    fun update(status: Int) =
        preferences
            .edit()
            .putInt(STATUS_KEY, status)
            .apply()
}
