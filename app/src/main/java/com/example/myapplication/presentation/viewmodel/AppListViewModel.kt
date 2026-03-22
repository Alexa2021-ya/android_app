package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.AppItem
import com.example.myapplication.domain.usecase.GetAppListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface SnackbarEvent {
    data class Show(val message: String) : SnackbarEvent
}

class AppListViewModel(
    private val getAppListUseCase: GetAppListUseCase
) : ViewModel() {

    private val _appList = MutableStateFlow<List<AppItem>>(emptyList())
    val appList: StateFlow<List<AppItem>> = _appList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _snackbarEvent = Channel<SnackbarEvent>()
    val snackbarEvent = _snackbarEvent.receiveAsFlow()

    init {
        loadAppList()
    }

    private fun loadAppList() {
        viewModelScope.launch {
            _isLoading.update { true }
            try {
                val list = getAppListUseCase()
                _appList.update { list }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    fun onLogoClick() {
        viewModelScope.launch {
            _snackbarEvent.send(SnackbarEvent.Show("Данный функционал в разработке"))
        }
    }

    fun onAppItemClick(appItem: AppItem) {
        // Логика для клика по элементу
    }
}