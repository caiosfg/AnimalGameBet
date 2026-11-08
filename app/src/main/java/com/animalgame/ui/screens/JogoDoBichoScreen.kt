package com.animalgame.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animalgame.data.model.BichoDrawResult
import com.animalgame.data.model.BichoRecommendation
import com.animalgame.ui.theme.CryptoGreen
import com.animalgame.ui.theme.CryptoGreenBg
import com.animalgame.ui.theme.CryptoPrimary
import com.animalgame.ui.theme.CryptoRed
import com.animalgame.ui.theme.CryptoRedBg
import com.animalgame.ui.viewmodel.MainViewModel

@Composable
fun JogoDoBichoScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val draws by viewModel.bichoDraws.collectAsState()
    val selectedDrawCode by viewModel.selectedBichoDrawCode.collectAsState()
    val recommendation by viewModel.bichoRecommendation.collectAsState()

    val currentDraw = draws.find { it.drawCode == selectedDrawCode } ?: draws.firstOrNull()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Jogo do Bicho",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Deu no Poste (ojogodobicho.com) & Palpites",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(CryptoGreenBg)
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "LIVE 08/11",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = CryptoGreen
                )
            }
        }

        // 1. BEST ANIMAL RECOMMENDATION CARD ("Melhor Animal para Aposta")
        recommendation?.let { rec ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🎯 MELHOR ANIMAL PARA APOSTA",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(CryptoGreenBg)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${rec.confidencePercentage}% Confiança",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = CryptoGreen
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Recommended Animal Focus Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(CryptoPrimary.copy(alpha = 0.2f))
                                .border(2.dp, CryptoPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "G${rec.formattedGroup}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${rec.animalName} (Grupo ${rec.formattedGroup})",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Dezenas: ${rec.dozenas.joinToString(" • ")}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Suggested Betting Numbers Card
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "MILHAR SUGERIDA",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = rec.suggestedMilhar,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = CryptoGreen
                            )
                        }

                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(30.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "CENTENA SUGERIDA",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = rec.suggestedCentena,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Reasoning / Algorithmic Justification Box
                    Text(
                        text = rec.reasoning,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Recommended Puxadas
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Puxadas:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        rec.puxadas.forEach { pux ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = pux,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Recalculate Lucky Guess Button
                    Button(
                        onClick = {
                            viewModel.generateNewBichoPalpite()
                            Toast.makeText(context, "Novo palpite gerado com sucesso!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = "🎲 GERAR NOVO PALPITE DA SORTE",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // 2. DRAW RESULTS SECTION ("DEU NO POSTE - HOJE")
        Text(
            text = "Resultados - Deu no Poste",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        // Draw Tabs (Federal, PT, PTV, PTM, PTN, COR)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(draws, key = { it.drawCode }) { draw ->
                val isSelected = draw.drawCode == selectedDrawCode
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.selectBichoDrawCode(draw.drawCode) },
                    label = {
                        Text(
                            text = "${draw.drawName} (${draw.time})",
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        // Selected Draw Result Table Card
        currentDraw?.let { draw ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Extração ${draw.drawName} - ${draw.time}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = draw.date,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Table Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Prêmio", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(0.8f))
                        Text("Milhar", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1.2f))
                        Text("Grupo", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
                        Text("Bicho / Animal", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1.6f))
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, thickness = 1.dp)

                    // Draw Items Rows
                    draw.items.forEachIndexed { index, item ->
                        val isFirstPlace = index == 0
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (isFirstPlace) CryptoGreenBg.copy(alpha = 0.5f)
                                    else Color.Transparent,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(vertical = 10.dp, horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Position
                            Text(
                                text = item.position,
                                fontSize = 13.sp,
                                fontWeight = if (isFirstPlace) FontWeight.ExtraBold else FontWeight.Bold,
                                color = if (isFirstPlace) CryptoGreen else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(0.8f)
                            )

                            // Milhar
                            Text(
                                text = item.milhar,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (isFirstPlace) CryptoGreen else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1.2f)
                            )

                            // Grupo
                            Text(
                                text = String.format(java.util.Locale.US, "%02d", item.grupo),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.weight(1f)
                            )

                            // Animal Name
                            Text(
                                text = item.animalName,
                                fontSize = 14.sp,
                                fontWeight = if (isFirstPlace) FontWeight.Bold else FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1.6f)
                            )
                        }

                        if (index < draw.items.size - 1) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), thickness = 0.5.dp)
                        }
                    }
                }
            }
        }

        // 3. TABELA COMPLETA DOS 25 BICHOS
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Tabela dos 25 Bichos",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                val bichoTable = listOf(
                    Triple(1, "Avestruz", "01-02-03-04"),
                    Triple(2, "Águia", "05-06-07-08"),
                    Triple(3, "Burro", "09-10-11-12"),
                    Triple(4, "Borboleta", "13-14-15-16"),
                    Triple(5, "Cachorro", "17-18-19-20"),
                    Triple(6, "Cabra", "21-22-23-24"),
                    Triple(7, "Carneiro", "25-26-27-28"),
                    Triple(8, "Camelo", "29-30-31-32"),
                    Triple(9, "Cobra", "33-34-35-36"),
                    Triple(10, "Coelho", "37-38-39-40"),
                    Triple(11, "Cavalo", "41-42-43-44"),
                    Triple(12, "Elefante", "45-46-47-48"),
                    Triple(13, "Galo", "49-50-51-52"),
                    Triple(14, "Gato", "53-54-55-56"),
                    Triple(15, "Jacaré", "57-58-59-60"),
                    Triple(16, "Leão", "61-62-63-64"),
                    Triple(17, "Macaco", "65-66-67-68"),
                    Triple(18, "Porco", "69-70-71-72"),
                    Triple(19, "Pavão", "73-74-75-76"),
                    Triple(20, "Peru", "77-78-79-80"),
                    Triple(21, "Touro", "81-82-83-84"),
                    Triple(22, "Tigre", "85-86-87-88"),
                    Triple(23, "Urso", "89-90-91-92"),
                    Triple(24, "Veado", "93-94-95-96"),
                    Triple(25, "Vaca", "97-98-99-00")
                )

                bichoTable.chunked(2).forEach { pair ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        pair.forEach { bicho ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column {
                                        Text(
                                            text = "G${String.format(java.util.Locale.US, "%02d", bicho.first)} • ${bicho.second}",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = bicho.third,
                                            fontSize = 10.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}
