package com.template.evilgodxu.screens.settings

import com.template.evilgodxu.data.repository.SettingsRepository
import com.template.evilgodxu.data.settings.AppLanguage
import com.template.evilgodxu.data.settings.SettingsState
import com.template.evilgodxu.data.settings.ThemeMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `初始状态包含版本号与默认主题语言`() = runTest(testDispatcher) {
        val viewModel = SettingsViewModel(FakeSettingsRepository(), "1.0.0")

        assertEquals("1.0.0", viewModel.uiState.value.version)
        assertEquals(ThemeMode.SYSTEM, viewModel.uiState.value.themeMode)
        assertEquals(AppLanguage.SYSTEM, viewModel.uiState.value.language)
    }

    @Test
    fun `切换主题后状态跟随数据源更新`() = runTest(testDispatcher) {
        val viewModel = SettingsViewModel(FakeSettingsRepository(), "1.0.0")

        viewModel.setThemeMode(ThemeMode.DARK)
        advanceUntilIdle()

        assertEquals(ThemeMode.DARK, viewModel.uiState.value.themeMode)
    }

    @Test
    fun `切换语言后状态跟随数据源更新`() = runTest(testDispatcher) {
        val viewModel = SettingsViewModel(FakeSettingsRepository(), "1.0.0")

        viewModel.setLanguage(AppLanguage.CHINESE)
        advanceUntilIdle()

        assertEquals(AppLanguage.CHINESE, viewModel.uiState.value.language)
    }

    private class FakeSettingsRepository : SettingsRepository {
        private val themeMode = MutableStateFlow(ThemeMode.SYSTEM)
        private val language = MutableStateFlow(AppLanguage.SYSTEM)

        override val settings: Flow<SettingsState> = themeMode.map { SettingsState(it) }
        override val appLanguage: Flow<AppLanguage> = language
        override suspend fun saveThemeMode(mode: ThemeMode) {
            themeMode.value = mode
        }

        override suspend fun setAppLanguage(language: AppLanguage) {
            this.language.value = language
        }
    }
}