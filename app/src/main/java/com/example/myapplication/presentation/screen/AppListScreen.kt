package com.example.myapplication.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.R
import com.example.myapplication.domain.model.AppItem
import com.example.myapplication.presentation.component.AppIcon
import com.example.myapplication.presentation.theme.MyApplicationTheme
import com.example.myapplication.presentation.viewmodel.AppListViewModel
import com.example.myapplication.presentation.viewmodel.SnackbarEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AppListUiState(
    val items: List<AppItem> = emptyList(),
    val isLoading: Boolean = false
)

@Composable
fun AppListScreen(
    onItemClick: (AppItem) -> Unit,
    viewModel: AppListViewModel,
    modifier: Modifier = Modifier
) {
    val appList by viewModel.appList.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbarEvent.collectLatest { event ->
            when (event) {
                is SnackbarEvent.Show -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    AppListContent(
        uiState = AppListUiState(
            items = appList,
            isLoading = isLoading
        ),
        onItemClick = onItemClick,
        onLogoClick = { viewModel.onLogoClick() },
        snackbarHostState = snackbarHostState,
        modifier = modifier
    )
}

@Composable
fun AppListContent(
    uiState: AppListUiState,
    onItemClick: (AppItem) -> Unit,
    onLogoClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rustore_color_logo),
                    contentDescription = "RuStore Logo",
                    modifier = Modifier
                        .width(100.dp)
                        .height(42.dp)
                        .clickable { onLogoClick() },
                    contentScale = ContentScale.Fit
                )
            }

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 24.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.items) { appItem ->
                        AppListItem(
                            appItem = appItem,
                            onClick = { onItemClick(appItem) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun AppListItem(
    appItem: AppItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                AppIcon(
                    iconString = appItem.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = appItem.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = appItem.description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                    maxLines = 2,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = appItem.category,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppListScreenPreview() {
    val previewItems = listOf(
        AppItem(
            id = "1",
            icon = "check_circle_unread_24px",
            title = "СберБанк Онлайн",
            description = "Удобный и безопасный онлайн-банк",
            category = "Финансы",
            screenshotUrls = emptyList()
        ),
        AppItem(
            id = "2",
            icon = "y_circle_24px",
            title = "Яндекс.Браузер",
            description = "Быстрый и безопасный браузер",
            category = "Инструменты",
            screenshotUrls = emptyList()
        ),
        AppItem(
            id = "3",
            icon = "alternate_email_24px",
            title = "Почта Mail.ru",
            description = "Почтовый клиент",
            category = "Инструменты",
            screenshotUrls = emptyList()
        )
    )

    MyApplicationTheme {
        AppListContent(
            uiState = AppListUiState(
                items = previewItems,
                isLoading = false
            ),
            onItemClick = {},
            onLogoClick = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppListScreenLoadingPreview() {
    MyApplicationTheme {
        AppListContent(
            uiState = AppListUiState(
                items = emptyList(),
                isLoading = true
            ),
            onItemClick = {},
            onLogoClick = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}