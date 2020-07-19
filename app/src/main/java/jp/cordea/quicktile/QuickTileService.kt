package jp.cordea.quicktile

import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@AndroidEntryPoint
class QuickTileService : TileService(), CoroutineScope by MainScope() {
    @Inject
    lateinit var repository: TileStatusRepository

    @ExperimentalCoroutinesApi
    override fun onStartListening() {
        super.onStartListening()
        launch {
            repository.observe()
                .flowOn(Dispatchers.IO)
                .collect {
                    qsTile.state = it
                    qsTile.icon = Icon.createWithResource(
                        applicationContext,
                        when (it) {
                            Tile.STATE_ACTIVE -> R.drawable.ic_hourglass_full
                            Tile.STATE_INACTIVE -> R.drawable.ic_hourglass_empty
                            Tile.STATE_UNAVAILABLE -> R.drawable.ic_hourglass_disabled
                            else -> throw IllegalArgumentException()
                        }
                    )
                    qsTile.updateTile()
                }
        }
    }

    override fun onClick() {
        super.onClick()
        when (repository.find()) {
            Tile.STATE_INACTIVE -> repository.update(Tile.STATE_ACTIVE)
            Tile.STATE_ACTIVE -> repository.update(Tile.STATE_INACTIVE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
