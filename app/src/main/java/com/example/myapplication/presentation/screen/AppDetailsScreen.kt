package com.example.myapplication.presentation.screen

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.domain.model.AppItem
import com.example.myapplication.presentation.component.AppIcon
import com.example.myapplication.presentation.theme.MyApplicationTheme
import com.example.myapplication.presentation.viewmodel.AppDetailsViewModel
import kotlinx.coroutines.launch

data class AppDetailsUiState(
    val appItem: AppItem? = null,
    val isLoading: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailsScreen(
    viewModel: AppDetailsViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appItem by viewModel.appItem.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        AppDetailsContent(
            uiState = AppDetailsUiState(
                appItem = appItem,
                isLoading = isLoading
            ),
            onBackClick = onBackClick,
            onShareClick = {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Данный функционал в разработке",
                        duration = SnackbarDuration.Short
                    )
                }
            },
            onInstallClick = {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Данный функционал в разработке",
                        duration = SnackbarDuration.Short
                    )
                }
            },
            onScreenshotClick = {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Данный функционал в разработке",
                        duration = SnackbarDuration.Short
                    )
                }
            },
            onDeveloperClick = {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Данный функционал в разработке",
                        duration = SnackbarDuration.Short
                    )
                }
            },
            onReadMoreClick = {
                println("Читать ещё")
            },
            snackbarHostState = snackbarHostState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailsContent(
    uiState: AppDetailsUiState,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onInstallClick: () -> Unit,
    onScreenshotClick: () -> Unit,
    onDeveloperClick: () -> Unit,
    onReadMoreClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            uiState.appItem?.let { item ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .verticalScroll(rememberScrollState())
                ) {
                    Toolbar(
                        onBackClick = onBackClick,
                        onShareClick = onShareClick
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    AppDetailsHeader(
                        appItem = item,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    InstallButton(
                        onClick = onInstallClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (item.screenshotUrls.isNotEmpty()) {
                        ScreenshotsList(
                            screenshotUrlList = item.screenshotUrls,
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            onScreenshotClick = onScreenshotClick
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    AppDescription(
                        description = item.description,
                        onReadMoreClick = onReadMoreClick
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Developer(
                        name = "Разработчик",
                        onClick = onDeveloperClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            } ?: run {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Приложение не найдено",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = "",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                    contentDescription = "Назад",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        actions = {
            IconButton(onClick = onShareClick) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_share),
                    contentDescription = "Поделиться",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
    )
}

@Composable
fun AppDetailsHeader(
    appItem: AppItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.size(80.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                AppIcon(
                    iconString = appItem.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = appItem.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = appItem.category,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "4.5 ★",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "• 12+",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "• 120 МБ",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun InstallButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = "Установить",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun ScreenshotsList(
    screenshotUrlList: List<String>,
    contentPadding: PaddingValues,
    onScreenshotClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Скриншоты (${screenshotUrlList.size})",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            contentPadding = contentPadding,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(screenshotUrlList) { url ->
                ScreenshotItem(
                    url = url,
                    onClick = onScreenshotClick
                )
            }
        }
    }
}

@Composable
fun ScreenshotItem(
    url: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = modifier
            .size(width = 160.dp, height = 260.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = url,
                contentDescription = "Скриншот приложения",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun AppDescription(
    description: String,
    onReadMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Описание",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        var isCollapsed by remember { mutableStateOf(true) }

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = if (isCollapsed) 3 else Int.MAX_VALUE,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 22.sp
        )

        if (description.length > 100 && isCollapsed) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Ещё",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        isCollapsed = false
                        onReadMoreClick()
                    }
            )
        }
    }
}

@Composable
fun Developer(
    name: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_myplaces),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Разработчик",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_more),
            contentDescription = "Перейти",
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppDetailsScreenPreview() {
    MyApplicationTheme {
        AppDetailsContent(
            uiState = AppDetailsUiState(
                appItem = AppItem(
                    id = "1",
                    icon = "check_circle_unread_24px",
                    title = "СберБанк Онлайн",
                    description = "Больше чем банк. Управляйте финансами, платите, копите и инвестируйте. Мгновенные переводы, оплата услуг без комиссии, вклады под высокий процент, кредиты за 2 минуты. Кэшбэк бонусами Спасибо и биометрическая авторизация.",
                    category = "Финансы",
                    screenshotUrls = listOf(
                        "https://apk28.ru/wp-content/uploads/f919ef6f-2715-4118-b006-5339bd596c2f-699x1024.jpg",
                        "https://apk28.ru/wp-content/uploads/f919ef6f-2715-4118-b006-5339bd596c2f-699x1024.jpg",
                        "https://apk28.ru/wp-content/uploads/f919ef6f-2715-4118-b006-5339bd596c2f-699x1024.jpg"
                    )
                ),
                isLoading = false
            ),
            onBackClick = {},
            onShareClick = {},
            onInstallClick = {},
            onScreenshotClick = {},
            onDeveloperClick = {},
            onReadMoreClick = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppDetailsScreenLoadingPreview() {
    MyApplicationTheme {
        AppDetailsContent(
            uiState = AppDetailsUiState(
                appItem = null,
                isLoading = true
            ),
            onBackClick = {},
            onShareClick = {},
            onInstallClick = {},
            onScreenshotClick = {},
            onDeveloperClick = {},
            onReadMoreClick = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}