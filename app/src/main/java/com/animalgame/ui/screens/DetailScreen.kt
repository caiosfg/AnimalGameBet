package com.animalgame.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animalgame.ui.components.*
import com.animalgame.ui.theme.CryptoGreen
import com.animalgame.ui.theme.CryptoGreenBg
import com.animalgame.ui.theme.CryptoRed
import com.animalgame.ui.theme.CryptoRedBg
import com.animalgame.ui.viewmodel.MainViewModel
import java.util.Locale

@Composable
fun DetailScreen(
    viewModel: MainViewModel,
    cryptoId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(cryptoId) {
        viewModel.selectCryptoById(cryptoId)
    }

    val selectedCrypto by viewModel.selectedCrypto.collectAsState()
    val isBuySelected by viewModel.isBuySelected.collectAsState()
    val orderType by viewModel.selectedOrderType.collectAsState()
    val orderAmount by viewModel.orderAmount.collectAsState()
    val timeInterval by viewModel.selectedTimeInterval.collectAsState()
    val tradeStatusMessage by viewModel.tradeStatusMessage.collectAsState()

    LaunchedEffect(tradeStatusMessage) {
        tradeStatusMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            viewModel.clearTradeStatusMessage()
        }
    }

    val crypto = selectedCrypto ?: return

    val backResId = context.resources.getIdentifier("back", "drawable", context.packageName)
    val favoriteResId = context.resources.getIdentifier("favorite", "drawable", context.packageName)
    val shareResId = context.resources.getIdentifier("share", "drawable", context.packageName)

    val isPositive = crypto.isPositive
    val changeColor = if (isPositive) CryptoGreen else CryptoRed
    val changeBg = if (isPositive) CryptoGreenBg else CryptoRedBg

    val currentChartPoints = when (timeInterval) {
        "1H" -> crypto.chartData1H
        "1D" -> crypto.chartData1D
        "1W" -> crypto.chartData1W
        "1M" -> crypto.chartData1M
        "1Y" -> crypto.chartData1Y
        "ALL" -> crypto.chartDataAll
        else -> crypto.chartData1D
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            // Top Bar with Back Button, Symbol & Title, Favorite & Share Actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                            .clickable { onBackClick() }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (backResId != 0) {
                            Image(
                                painter = painterResource(id = backResId),
                                contentDescription = "Back",
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Text("←", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Column {
                        Text(
                            text = crypto.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "${crypto.symbol}/USDT",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Favorite Toggle
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                            .clickable { viewModel.toggleBookmark(crypto.id) }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (favoriteResId != 0) {
                            Image(
                                painter = painterResource(id = favoriteResId),
                                contentDescription = "Favorite",
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Text(if (crypto.isBookmarked) "★" else "☆", fontSize = 18.sp)
                        }
                    }

                    // Share Action
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                            .clickable {
                                Toast.makeText(context, "Share ${crypto.name} details", Toast.LENGTH_SHORT).show()
                            }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (shareResId != 0) {
                            Image(
                                painter = painterResource(id = shareResId),
                                contentDescription = "Share",
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Text("⤤", fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. PRICE SECTION
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        CryptoLogoImage(
                            logoName = crypto.logoName,
                            symbol = crypto.symbol,
                            size = 48.dp
                        )

                        Column {
                            Text(
                                text = "$${formatDetailPrice(crypto.currentPrice)}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Current Price",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(changeBg)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "${if (isPositive) "+" else ""}${String.format(Locale.US, "%.2f", crypto.changePercentage)}%",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = changeColor
                        )
                    }
                }
            }

            // 2. CHART SECTION
            CryptoChartView(
                dataPoints = currentChartPoints,
                isPositive = isPositive,
                selectedInterval = timeInterval,
                onIntervalSelected = { viewModel.setTimeInterval(it) }
            )

            // 3. ORDER BOOK SECTION
            OrderBookView(
                buyOrders = crypto.buyOrders,
                sellOrders = crypto.sellOrders,
                cryptoSymbol = crypto.symbol
            )

            // 4. TRADE PANEL SECTION
            TradePanel(
                isBuy = isBuySelected,
                onToggleBuySell = { viewModel.toggleBuySell(it) },
                selectedOrderType = orderType,
                onOrderTypeSelected = { viewModel.setOrderType(it) },
                amount = orderAmount,
                onAmountChanged = { viewModel.setOrderAmount(it) },
                onIncrementAmount = { viewModel.incrementAmount() },
                onDecrementAmount = { viewModel.decrementAmount() },
                cryptoSymbol = crypto.symbol,
                currentPrice = crypto.currentPrice,
                onExecuteTrade = { viewModel.executeTrade() }
            )

            // 5. STATS SECTION
            StatsSection(
                openPrice = crypto.openPrice,
                closePrice = crypto.closePrice,
                highPrice = crypto.highPrice,
                lowPrice = crypto.lowPrice,
                dailyChange = crypto.dailyChange,
                dailyVolume = crypto.dailyVolume
            )

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

private fun formatDetailPrice(price: Double): String {
    return if (price < 0.01) {
        String.format(Locale.US, "%.7f", price)
    } else if (price < 1.0) {
        String.format(Locale.US, "%.4f", price)
    } else {
        String.format(Locale.US, "%,.2f", price)
    }
}
