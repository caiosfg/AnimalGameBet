package com.animalgame.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animalgame.ui.theme.CryptoGreen
import com.animalgame.ui.theme.CryptoRed

@Composable
fun CryptoChartView(
    dataPoints: List<Float>,
    isPositive: Boolean,
    selectedInterval: String,
    onIntervalSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val intervals = listOf("1H", "1D", "1W", "1M", "1Y", "ALL")
    val lineColor = if (isPositive) CryptoGreen else CryptoRed

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        // Time Interval Selectors
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            intervals.forEach { interval ->
                val isSelected = interval == selectedInterval
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary
                            else Color.Transparent
                        )
                        .clickable { onIntervalSelected(interval) }
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = interval,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Price Chart Canvas
        if (dataPoints.isNotEmpty()) {
            val minVal = dataPoints.minOrNull() ?: 0f
            val maxVal = dataPoints.maxOrNull() ?: 1f
            val range = if (maxVal == minVal) 1f else maxVal - minVal

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                val width = size.width
                val height = size.height

                val path = Path()
                val fillPath = Path()

                dataPoints.forEachIndexed { index, value ->
                    val x = (index.toFloat() / (dataPoints.size - 1)) * width
                    val normalizedY = (value - minVal) / range
                    val y = height - (normalizedY * (height - 30.dp.toPx())) - 15.dp.toPx()

                    if (index == 0) {
                        path.moveTo(x, y)
                        fillPath.moveTo(x, height)
                        fillPath.lineTo(x, y)
                    } else {
                        val prevX = ((index - 1).toFloat() / (dataPoints.size - 1)) * width
                        val prevVal = dataPoints[index - 1]
                        val prevNormalizedY = (prevVal - minVal) / range
                        val prevY = height - (prevNormalizedY * (height - 30.dp.toPx())) - 15.dp.toPx()

                        // Cubic Bezier curve for smooth line
                        val controlX1 = prevX + (x - prevX) / 2f
                        val controlY1 = prevY
                        val controlX2 = prevX + (x - prevX) / 2f
                        val controlY2 = y

                        path.cubicTo(controlX1, controlY1, controlX2, controlY2, x, y)
                        fillPath.cubicTo(controlX1, controlY1, controlX2, controlY2, x, y)
                    }
                }

                fillPath.lineTo(width, height)
                fillPath.close()

                // Gradient Area Fill
                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            lineColor.copy(alpha = 0.35f),
                            lineColor.copy(alpha = 0.0f)
                        )
                    )
                )

                // Chart Stroke Line
                drawPath(
                    path = path,
                    color = lineColor,
                    style = Stroke(width = 3.dp.toPx())
                )

                // Draw pulsing dot at the last point
                val lastIndex = dataPoints.size - 1
                val lastX = width
                val lastNormalizedY = (dataPoints[lastIndex] - minVal) / range
                val lastY = height - (lastNormalizedY * (height - 30.dp.toPx())) - 15.dp.toPx()

                drawCircle(
                    color = lineColor.copy(alpha = 0.3f),
                    radius = 8.dp.toPx(),
                    center = Offset(lastX, lastY)
                )
                drawCircle(
                    color = lineColor,
                    radius = 4.dp.toPx(),
                    center = Offset(lastX, lastY)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Chart Data Available",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
