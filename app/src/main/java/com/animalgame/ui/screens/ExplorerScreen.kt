package com.animalgame.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animalgame.data.model.CryptoModel
import com.animalgame.ui.components.CryptoLogoImage
import com.animalgame.ui.theme.CryptoGreen
import com.animalgame.ui.theme.CryptoGreenBg
import com.animalgame.ui.theme.CryptoRed
import com.animalgame.ui.theme.CryptoRedBg
import com.animalgame.ui.viewmodel.MainViewModel
import java.util.Locale

@Composable
fun ExplorerScreen(
    viewModel: MainViewModel,
    onCryptoSelected: (CryptoModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val cryptoList by viewModel.cryptoList.collectAsState()
    val topGainers = cryptoList.sortedByDescending { it.changePercentage }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Market Explorer",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Discover top gainers, categories & market trends",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Categories Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Top Gainers", "DeFi", "Layer 1", "Meme").forEachIndexed { index, cat ->
                val isSelected = index == 0
                FilterChip(
                    selected = isSelected,
                    onClick = { },
                    label = { Text(cat, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(topGainers, key = { it.id }) { crypto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    onClick = { onCryptoSelected(crypto) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            CryptoLogoImage(
                                logoName = crypto.logoName,
                                symbol = crypto.symbol,
                                size = 40.dp
                            )
                            Column {
                                Text(
                                    text = crypto.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Vol ${crypto.dailyVolume}",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "$${String.format(Locale.US, "%,.2f", crypto.currentPrice)}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            val isPos = crypto.isPositive
                            Box(
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .background(
                                        if (isPos) CryptoGreenBg else CryptoRedBg,
                                        shape = RoundedCornerShape(6.dp)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "${if (isPos) "+" else ""}${String.format(Locale.US, "%.2f", crypto.changePercentage)}%",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isPos) CryptoGreen else CryptoRed
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
