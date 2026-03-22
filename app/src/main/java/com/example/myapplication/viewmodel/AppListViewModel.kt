package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.AppItem
import com.example.myapplication.data.MockData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface SnackbarEvent {
    data class Show(val message: String) : SnackbarEvent
    object Consumed : SnackbarEvent
}

class AppListViewModel : ViewModel() {

    private val _appList = MutableStateFlow<List<AppItem>>(emptyList())
    val appList: StateFlow<List<AppItem>> = _appList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _snackbarEvent = MutableStateFlow<SnackbarEvent>(SnackbarEvent.Consumed)
    val snackbarEvent: StateFlow<SnackbarEvent> = _snackbarEvent.asStateFlow()

    init {
        loadAppList()
    }

    private fun loadAppList() {
        viewModelScope.launch {
            _isLoading.update { true }
            delay(500)
            _appList.update { MockData.appList }
            _isLoading.update { false }
        }
    }

    fun onLogoClick() {
        viewModelScope.launch {
            _snackbarEvent.update {
                SnackbarEvent.Show("Данный функционал в разработке")
            }
        }
    }

    fun onAppItemClick(appItem: AppItem) {
    }

    fun onSnackbarShown() {
        _snackbarEvent.update { SnackbarEvent.Consumed }
    }
}