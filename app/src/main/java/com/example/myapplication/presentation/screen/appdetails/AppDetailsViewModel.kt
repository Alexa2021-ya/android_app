package com.example.myapplication.presentation.screen.appdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.appdetails.AppDetails
import com.example.myapplication.domain.usecase.GetAppDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AppDetailsUiState(
    val appDetails: AppDetails? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    private val getAppDetailsUseCase: GetAppDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val appId: String = checkNotNull(savedStateHandle["appId"])

    private val _uiState = MutableStateFlow(AppDetailsUiState(isLoading = true))
    val uiState: StateFlow<AppDetailsUiState> = _uiState.asStateFlow()

    init {
        observeAppDetails()
    }

    private fun observeAppDetails() {
        viewModelScope.launch {
            getAppDetailsUseCase.observe(appId)
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { appDetails ->
                    _uiState.update {
                        it.copy(
                            appDetails = appDetails,
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }
    }

    fun toggleWishlist() {
        viewModelScope.launch {
            getAppDetailsUseCase.toggleWishlist(appId)
        }
    }
}