package com.animalgame.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animalgame.ui.theme.CryptoGreen
import com.animalgame.ui.theme.CryptoRed
import java.util.Locale

@Composable
fun StatsSection(
    openPrice: Double,
    closePrice: Double,
    highPrice: Double,
    lowPrice: Double,
    dailyChange: Double,
    dailyVolume: String,
    modifier: Modifier = Modifier
) {
    val statsList = listOf(
        StatItem("Open", "$${formatPrice(openPrice)}"),
        StatItem("Close", "$${formatPrice(closePrice)}"),
        StatItem("High", "$${formatPrice(highPrice)}"),
        StatItem("Low", "$${formatPrice(lowPrice)}"),
        StatItem(
            "Daily Change",
            "${if (dailyChange >= 0) "+" else ""}${String.format(Locale.US, "%.2f", dailyChange)}%",
            valueColor = if (dailyChange >= 0) CryptoGreen else CryptoRed
        ),
        StatItem("Daily Volume", dailyVolume)
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(
            text = "Market Statistics",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Vertical List
        statsList.forEachIndexed { index, stat ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stat.label,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = stat.value,
                    fontSize = 14.sp,
                    color = stat.valueColor ?: MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }

            if (index < statsList.size - 1) {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    thickness = 1.dp
                )
            }
        }
    }
}

private data class StatItem(
    val label: String,
    val value: String,
    val valueColor: Color? = null
)

private fun formatPrice(price: Double): String {
    return if (price < 1.0) {
        String.format(Locale.US, "%.4f", price)
    } else {
        String.format(Locale.US, "%,.2f", price)
    }
}
