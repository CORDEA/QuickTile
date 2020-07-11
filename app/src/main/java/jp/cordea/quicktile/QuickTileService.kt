package jp.cordea.quicktile

import android.content.Context
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class QuickTileService : TileService() {
    companion object {
        private const val PREFERENCE_NAME = "jp.cordea.quicktile.pref"

        private const val ENABLED_KEY = "enabled"
    }

    private val preferences
        get() = applicationContext.getSharedPreferences(
            PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )

    private var isEnabled
        get() = preferences.getBoolean(ENABLED_KEY, false)
        set(value) {
            preferences
                .edit()
                .putBoolean(ENABLED_KEY, value)
                .apply()
        }

    override fun onStartListening() {
        super.onStartListening()
        refresh()
    }

    override fun onClick() {
        super.onClick()
        isEnabled = !isEnabled
        refresh()
    }

    private fun refresh() {
        qsTile.state = if (isEnabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.updateTile()
    }
}
