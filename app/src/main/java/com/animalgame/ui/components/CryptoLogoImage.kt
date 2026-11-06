package com.animalgame.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animalgame.R

@Composable
fun CryptoLogoImage(
    logoName: String,
    symbol: String,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp
) {
    val context = LocalContext.current
    val resourceId = context.resources.getIdentifier(
        logoName.lowercase().trim(),
        "drawable",
        context.packageName
    )

    if (resourceId != 0) {
        Image(
            painter = painterResource(id = resourceId),
            contentDescription = "$logoName logo",
            contentScale = ContentScale.Fit,
            modifier = modifier
                .size(size)
                .clip(CircleShape)
        )
    } else {
        // Safe fallback for missing resources to prevent app crashes
        Box(
            modifier = modifier
                .size(size)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = symbol.take(2).uppercase(),
                color = MaterialTheme.colorScheme.primary,
                fontSize = (size.value * 0.38).sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
