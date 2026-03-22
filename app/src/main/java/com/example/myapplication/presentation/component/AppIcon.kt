package com.example.myapplication.presentation.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage

@Composable
fun AppIcon(
    iconString: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val context = LocalContext.current

    if (isLocalResource(iconString)) {
        val resourceId = getResourceId(context, iconString)
        if (resourceId != 0) {
            Image(
                painter = painterResource(id = resourceId),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        } else {
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }
    } else {
        AsyncImage(
            model = iconString,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
    }
}

private fun isLocalResource(iconString: String): Boolean {
    return !iconString.startsWith("http://") && !iconString.startsWith("https://")
}

private fun getResourceId(context: Context, resourceName: String): Int {
    return context.resources.getIdentifier(
        resourceName,
        "drawable",
        context.packageName
    )
}