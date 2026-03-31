package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.AppDetailsRepository
import com.example.myapplication.domain.appdetails.AppDetails
import javax.inject.Inject

class GetAppDetailsUseCase @Inject constructor(
    private val repository: AppDetailsRepository
) {
    suspend operator fun invoke(id: String): AppDetails? = repository.getAppDetails(id)
}