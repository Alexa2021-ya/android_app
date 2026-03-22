package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.AppItem
import com.example.myapplication.domain.usecase.GetAppByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class AppDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getAppByIdUseCase: GetAppByIdUseCase
) : ViewModel() {

    private val appId: String = checkNotNull(savedStateHandle["appId"])

    private val _appItem = MutableStateFlow<AppItem?>(null)
    val appItem: StateFlow<AppItem?> = _appItem.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadAppDetails()
    }

    private fun loadAppDetails() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val item = getAppByIdUseCase(appId)
                _appItem.value = item
            } finally {
                _isLoading.value = false
            }
        }
    }
}