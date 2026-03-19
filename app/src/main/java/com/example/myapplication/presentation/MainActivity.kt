package com.example.myapplication.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.presentation.di.DependencyContainer
import com.example.myapplication.presentation.navigation.Screen
import com.example.myapplication.presentation.screen.AppDetailsScreen
import com.example.myapplication.presentation.screen.AppListScreen
import com.example.myapplication.presentation.theme.MyApplicationTheme
import com.example.myapplication.presentation.viewmodel.AppDetailsViewModel
import com.example.myapplication.presentation.viewmodel.AppListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .statusBarsPadding()
                        .navigationBarsPadding()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                            .background(Color.White)
                    ) {
                        AppNavHost(
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
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

            // Исправлено: ViewModel получает данные через UseCase, а не прямой доступ к data слою
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