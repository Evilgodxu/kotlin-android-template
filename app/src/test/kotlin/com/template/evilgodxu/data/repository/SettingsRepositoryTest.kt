package com.template.evilgodxu.data.repository

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.template.evilgodxu.data.settings.AppLanguage
import com.template.evilgodxu.data.settings.ThemeMode
import java.io.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class SettingsRepositoryTest {

    @get:Rule
    val temporaryFolder = TemporaryFolder()

    private val testDispatcher = StandardTestDispatcher()

    private fun newRepository(): SettingsRepository {
        val dataStore = PreferenceDataStoreFactory.create(
            scope = CoroutineScope(testDispatcher),
        ) {
            File(temporaryFolder.root, "test-${System.nanoTime()}.preferences_pb")
        }
        return DataStoreSettingsRepository(dataStore)
    }

    @Test
    fun `未写入时主题与语言返回默认值`() = runTest(testDispatcher) {
        val repository = newRepository()

        assertEquals(ThemeMode.SYSTEM, repository.settings.first().themeMode)
        assertEquals(AppLanguage.SYSTEM, repository.appLanguage.first())
    }

    @Test
    fun `写入主题后流中可读到新值`() = runTest(testDispatcher) {
        val repository = newRepository()

        repository.saveThemeMode(ThemeMode.DARK)

        assertEquals(ThemeMode.DARK, repository.settings.first().themeMode)
    }

    @Test
    fun `写入语言后流中可观察到`() = runTest(testDispatcher) {
        val repository = newRepository()

        repository.setAppLanguage(AppLanguage.CHINESE)

        assertEquals(AppLanguage.CHINESE, repository.appLanguage.first())
    }
}