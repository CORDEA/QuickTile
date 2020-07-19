package jp.cordea.quicktile

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

private const val PREFERENCE_NAME = "jp.cordea.quicktile.pref"

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {
    @Provides
    @Reusable
    fun providePreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
}
