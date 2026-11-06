package com.animalgame.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animalgame.ui.theme.CryptoGreen
import com.animalgame.ui.theme.CryptoRed
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TradePanel(
    isBuy: Boolean,
    onToggleBuySell: (Boolean) -> Unit,
    selectedOrderType: String,
    onOrderTypeSelected: (String) -> Unit,
    amount: String,
    onAmountChanged: (String) -> Unit,
    onIncrementAmount: () -> Unit,
    onDecrementAmount: () -> Unit,
    cryptoSymbol: String,
    currentPrice: Double,
    onExecuteTrade: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var dropdownExpanded by remember { mutableStateOf(false) }
    val orderTypes = listOf("Limit", "Market", "Stop")
    val accentColor = if (isBuy) CryptoGreen else CryptoRed

    val addResId = context.resources.getIdentifier("add", "drawable", context.packageName)
    val minusResId = context.resources.getIdentifier("minus", "drawable", context.packageName)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        // Section Title
        Text(
            text = "Trade Controls",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // 1. Buy / Sell Operation Toggle Segmented Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(4.dp)
        ) {
            // Buy Toggle Button
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isBuy) CryptoGreen else Color.Transparent)
                    .clickable { onToggleBuySell(true) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "BUY",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isBuy) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Sell Toggle Button
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (!isBuy) CryptoRed else Color.Transparent)
                    .clickable { onToggleBuySell(false) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SELL",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (!isBuy) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Order Type Dropdown Menu (Spinner Equivalent)
        Text(
            text = "Order Type",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = { dropdownExpanded = !dropdownExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(10.dp)
                    )
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$selectedOrderType Order",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "▼",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            ExposedDropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                orderTypes.forEach { type ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "$type Order",
                                fontWeight = if (type == selectedOrderType) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        onClick = {
                            onOrderTypeSelected(type)
                            dropdownExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Amount Input Field with Increment (+) and Decrement (-) Buttons
        Text(
            text = "Amount ($cryptoSymbol)",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Decrement (-) Button
            IconButton(
                onClick = onDecrementAmount,
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(10.dp)
                    )
            ) {
                if (minusResId != 0) {
                    Image(
                        painter = painterResource(id = minusResId),
                        contentDescription = "Decrement Amount",
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = "-",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Input Field
            OutlinedTextField(
                value = amount,
                onValueChange = { newValue ->
                    if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                        onAmountChanged(newValue)
                    }
                },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accentColor,
                    unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background
                )
            )

            // Increment (+) Button
            IconButton(
                onClick = onIncrementAmount,
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(10.dp)
                    )
            ) {
                if (addResId != 0) {
                    Image(
                        painter = painterResource(id = addResId),
                        contentDescription = "Increment Amount",
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = "+",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Percentage Selector Buttons (25%, 50%, 75%, 100%)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(0.25, 0.50, 0.75, 1.00).forEach { pct ->
                val label = "${(pct * 100).toInt()}%"
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .clickable {
                            val baseAmount = if (currentPrice > 0) 1000.0 / currentPrice else 1.0
                            val calculated = baseAmount * pct
                            onAmountChanged(String.format(Locale.US, "%.4f", calculated))
                        }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Total Estimate Summary
        val amountNum = amount.toDoubleOrNull() ?: 0.0
        val estTotal = amountNum * currentPrice
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Estimated Total:",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "$${String.format(Locale.US, "%,.2f", estTotal)} USDT",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Action Submit Button
        Button(
            onClick = onExecuteTrade,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = accentColor
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "${if (isBuy) "BUY" else "SELL"} $cryptoSymbol",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
