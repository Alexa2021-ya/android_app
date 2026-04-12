package com.example.myapplication.presentation.screen.appdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.appdetails.AppDetails
import com.example.myapplication.domain.model.AppItem
import com.example.myapplication.domain.usecase.GetAppDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    private val getAppDetailsUseCase: GetAppDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val appId: String = checkNotNull(savedStateHandle["appId"])

    private val _appDetails = MutableStateFlow<AppDetails?>(null)
    val appDetails: StateFlow<AppDetails?> = _appDetails.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadAppDetails()
    }

    private fun loadAppDetails() {
        viewModelScope.launch {
            _isLoading.value = true
            val details = getAppDetailsUseCase(appId)
            _appDetails.value = details
            _isLoading.value = false
        }
    }
}