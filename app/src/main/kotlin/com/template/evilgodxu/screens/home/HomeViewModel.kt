package com.template.evilgodxu.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.evilgodxu.data.repository.UserPreferencesRepository
import com.template.evilgodxu.screen.home.landscape.main_workspace.sidebar.LandscapeTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        userPreferencesRepository.themeMode
            .onEach { mode -> _state.value = _state.value.copy(themeMode = mode) }
            .launchIn(viewModelScope)

        userPreferencesRepository.language
            .onEach { language -> _state.value = _state.value.copy(language = language) }
            .launchIn(viewModelScope)
    }

    fun setThemeMode(mode: String) {
        viewModelScope.launch {
            userPreferencesRepository.setThemeMode(mode)
        }
    }

    fun setLanguage(language: String) {
        viewModelScope.launch {
            userPreferencesRepository.setLanguage(language)
        }
    }

    fun showSettings() {
        _state.value = _state.value.copy(showSettings = true)
    }

    fun hideSettings() {
        _state.value = _state.value.copy(showSettings = false)
    }

    fun selectTab(tab: LandscapeTab) {
        _state.value = _state.value.copy(selectedTab = tab)
    }
}
