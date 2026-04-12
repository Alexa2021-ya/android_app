package com.example.myapplication.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.presentation.navigation.Screen
import com.example.myapplication.presentation.screen.appdetails.AppDetailsScreen
import com.example.myapplication.presentation.screen.applist.AppListScreen
import com.example.myapplication.presentation.theme.MyApplicationTheme
import com.example.myapplication.presentation.screen.appdetails.AppDetailsViewModel
import com.example.myapplication.presentation.screen.applist.AppListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding()
                ) {
                    AppNavHost(modifier = Modifier.fillMaxSize())
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
        modifier = modifier,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        composable(Screen.APP_LIST.route) {
            val viewModel: AppListViewModel = hiltViewModel()
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
            val viewModel: AppDetailsViewModel = hiltViewModel(backStackEntry)
            AppDetailsScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}