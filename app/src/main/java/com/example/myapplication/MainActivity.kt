package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.theme.MyApplicationTheme

enum class Screen(val route: String) {
    APP_LIST("app_list"),
    APP_DETAILS("app_details/{appId}");

    companion object {
        const val ARG_APP_ID = "appId"
    }

    fun withAppId(appId: String): String =
        "app_details/$appId"
}

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
                            .background(MaterialTheme.colorScheme.background)
                            .padding(top = 40.dp)
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
            AppListScreen(
                onItemClick = { appItem ->
                    navController.navigate(Screen.APP_DETAILS.withAppId(appItem.id))
                }
            )
        }

        composable(
            route = Screen.APP_DETAILS.route,
            arguments = listOf(navArgument(Screen.ARG_APP_ID) { type = NavType.StringType })
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString(Screen.ARG_APP_ID) ?: ""

            val appItem = MockData.appList.find { it.id == appId }

            if (appItem != null) {
                AppDetailsScreen(
                    appItem = appItem,
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}