package com.example.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.presentation.di.DependencyContainer
import com.example.myapplication.presentation.screen.AppDetailsScreen
import com.example.myapplication.presentation.screen.AppListScreen
import com.example.myapplication.presentation.viewmodel.AppDetailsViewModel
import com.example.myapplication.presentation.viewmodel.AppListViewModel

enum class Screen(val route: String) {
    APP_LIST("app_list"),
    APP_DETAILS("app_details/{appId}");

    companion object {
        const val ARG_APP_ID = "appId"
    }

    fun withAppId(appId: String): String =
        "app_details/$appId"
}

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.APP_LIST.route,
        modifier = modifier
    ) {
        composable(Screen.APP_LIST.route) {
            val viewModel: AppListViewModel = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AppListViewModel(
                        getAppListUseCase = DependencyContainer.getAppListUseCase
                    ) as T
                }
            }.let { factory ->
                androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)
            }

            AppListScreen(
                onItemClick = { appItem ->
                    navController.navigate(Screen.APP_DETAILS.withAppId(appItem.id))
                },
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.APP_DETAILS.route,
            arguments = listOf(navArgument(Screen.ARG_APP_ID) { type = NavType.StringType })
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString(Screen.ARG_APP_ID) ?: ""

            val viewModel: AppDetailsViewModel = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AppDetailsViewModel(
                        savedStateHandle = SavedStateHandle(mapOf("appId" to appId)),
                        getAppByIdUseCase = DependencyContainer.getAppByIdUseCase
                    ) as T
                }
            }.let { factory ->
                androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)
            }

            AppDetailsScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}