package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF5F9EA0))
                        .statusBarsPadding()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color.White,
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                    topStart = 32.dp,
                                    topEnd = 32.dp
                                )
                            )
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
        startDestination = "app_list",
        modifier = modifier
    ) {
        composable("app_list") {
            AppListScreen(
                onItemClick = { appItem ->
                    navController.navigate("app_details/${appItem.id}")
                }
            )
        }

        composable(
            route = "app_details/{appId}",
            arguments = listOf(navArgument("appId") { type = NavType.StringType })
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId") ?: ""

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