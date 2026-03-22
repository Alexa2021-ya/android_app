package com.example.myapplication.presentation.navigation

enum class Screen(val route: String) {
    APP_LIST("app_list"),
    APP_DETAILS("app_details/{appId}");

    companion object {
        const val ARG_APP_ID = "appId"
    }

    fun withAppId(appId: String): String = "app_details/$appId"
}