package com.animalgame.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animalgame.data.model.CryptoModel
import com.animalgame.ui.components.CryptoChartView
import com.animalgame.ui.viewmodel.MainViewModel

@Composable
fun ChartScreen(
    viewModel: MainViewModel,
    onCryptoSelected: (CryptoModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val cryptoList by viewModel.cryptoList.collectAsState()
    val topCryptos = cryptoList.take(3)
    var selectedInterval by remember { mutableStateOf("1D") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Market Charts Dashboard",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Overview of key market pairs",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        topCryptos.forEach { crypto ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                onClick = { onCryptoSelected(crypto) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${crypto.name} (${crypto.symbol}/USDT)",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "$${crypto.currentPrice}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    CryptoChartView(
                        dataPoints = crypto.chartData1D,
                        isPositive = crypto.isPositive,
                        selectedInterval = selectedInterval,
                        onIntervalSelected = { selectedInterval = it }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}
