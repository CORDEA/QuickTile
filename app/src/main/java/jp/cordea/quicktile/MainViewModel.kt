package jp.cordea.quicktile

import android.service.quicksettings.Tile
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class MainViewModel @ViewModelInject constructor(
    private val repository: TileStatusRepository
) : ViewModel() {
    fun onActiveClick() {
        repository.update(Tile.STATE_ACTIVE)
    }

    fun onInactiveClick() {
        repository.update(Tile.STATE_INACTIVE)
    }

    fun onUnavailableClick() {
        repository.update(Tile.STATE_UNAVAILABLE)
    }
}
