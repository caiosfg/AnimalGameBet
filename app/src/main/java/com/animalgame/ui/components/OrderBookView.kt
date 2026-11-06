package com.animalgame.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animalgame.data.model.OrderBookItem
import com.animalgame.ui.theme.CryptoGreen
import com.animalgame.ui.theme.CryptoGreenBg
import com.animalgame.ui.theme.CryptoRed
import com.animalgame.ui.theme.CryptoRedBg
import java.util.Locale

@Composable
fun OrderBookView(
    buyOrders: List<OrderBookItem>,
    sellOrders: List<OrderBookItem>,
    cryptoSymbol: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        // Section Title
        Text(
            text = "Order Book",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Split Layout: Sell Orders (Left) vs Buy Orders (Right)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Sell Orders Column (Asks - Red)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Sell (Price)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CryptoRed
                    )
                    Text(
                        text = "Amount",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                sellOrders.take(6).forEach { order ->
                    OrderRow(
                        price = order.price,
                        amount = order.amount,
                        isBuy = false,
                        maxAmount = sellOrders.maxOfOrNull { it.amount } ?: 1.0
                    )
                }
            }

            // Divider line
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(180.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            // Buy Orders Column (Bids - Green)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Buy (Price)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CryptoGreen
                    )
                    Text(
                        text = "Amount",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                buyOrders.take(6).forEach { order ->
                    OrderRow(
                        price = order.price,
                        amount = order.amount,
                        isBuy = true,
                        maxAmount = buyOrders.maxOfOrNull { it.amount } ?: 1.0
                    )
                }
            }
        }
    }
}

@Composable
private fun OrderRow(
    price: Double,
    amount: Double,
    isBuy: Boolean,
    maxAmount: Double
) {
    val priceColor = if (isBuy) CryptoGreen else CryptoRed
    val depthBg = if (isBuy) CryptoGreenBg else CryptoRedBg
    val fillFraction = (amount / maxAmount).coerceIn(0.05, 1.0).toFloat()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .padding(vertical = 2.dp)
    ) {
        // Horizontal depth bar fill from right or left
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fillFraction)
                .align(if (isBuy) Alignment.CenterStart else Alignment.CenterEnd)
                .background(depthBg, shape = RoundedCornerShape(4.dp))
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formatPrice(price),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = priceColor
            )
            Text(
                text = formatAmount(amount),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.End
            )
        }
    }
}

private fun formatPrice(price: Double): String {
    return if (price < 1.0) {
        String.format(Locale.US, "%.4f", price)
    } else {
        String.format(Locale.US, "%.2f", price)
    }
}

private fun formatAmount(amount: Double): String {
    return if (amount < 10) {
        String.format(Locale.US, "%.3f", amount)
    } else {
        String.format(Locale.US, "%.1f", amount)
    }
}
