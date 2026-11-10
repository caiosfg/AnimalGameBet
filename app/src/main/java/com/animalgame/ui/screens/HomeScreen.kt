package com.animalgame.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animalgame.ui.theme.CryptoGreen
import com.animalgame.ui.theme.CryptoGreenBg
import com.animalgame.ui.theme.CryptoRed
import com.animalgame.ui.theme.CryptoRedBg
import com.animalgame.ui.viewmodel.MainViewModel
import java.util.Locale

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onNavigateToRegistrar: () -> Unit,
    onNavigateToResultados: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val valorInicial by viewModel.valorInicialDia.collectAsState()
    val apostas by viewModel.apostas.collectAsState()

    var editingValorText by remember { mutableStateOf(if (valorInicial > 0) String.format(Locale.US, "%.2f", valorInicial) else "0.00") }
    var isEditingValor by remember { mutableStateOf(false) }

    val totalApostado = apostas.sumOf { it.valorApostado }
    val totalGanho = apostas.sumOf { it.valorGanho }
    val lucroLiquido = totalGanho - totalApostado
    val saldoAtual = valorInicial + lucroLiquido

    val totalInvestidoBase = if (valorInicial > 0) valorInicial else totalApostado
    val porcentagemRetornoTotal = if (totalInvestidoBase > 0) {
        (lucroLiquido / totalInvestidoBase) * 100.0
    } else {
        0.0
    }

    val winsCount = apostas.count { it.status == "GANHOU" }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Gerenciador do Bicho",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Controle diário de banca & conferência Deu no Poste",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(20.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "HOJE (0,00)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // 1. BANCA & VALOR INICIAL DO DIA
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Saldo Atual Hoje",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (lucroLiquido >= 0) CryptoGreenBg else CryptoRedBg)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${if (porcentagemRetornoTotal >= 0) "+" else ""}${String.format(Locale.US, "%.1f", porcentagemRetornoTotal)}%",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (lucroLiquido >= 0) CryptoGreen else CryptoRed
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "R$ ${String.format(Locale.US, "%,.2f", saldoAtual)}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo para informar/editar o Valor Inicial do Dia (Sempre inicia com 0.00)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Valor Inicial do Dia (Abre em R$ 0,00):",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = "R$ ${String.format(Locale.US, "%,.2f", valorInicial)}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = editingValorText,
                            onValueChange = { editingValorText = it },
                            placeholder = { Text("0.00") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface
                            )
                        )

                        Button(
                            onClick = {
                                val novoVal = editingValorText.toDoubleOrNull() ?: 0.0
                                viewModel.setValorInicialDia(novoVal)
                                Toast.makeText(context, "Valor inicial do dia atualizado!", Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("Salvar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                viewModel.resetarDia()
                                editingValorText = "0.00"
                                Toast.makeText(context, "Dia reiniciado para R$ 0,00!", Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CryptoRed
                            )
                        ) {
                            Text("Reset", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Resumo de Entradas & Saídas
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Total Apostado Hoje", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("R$ ${String.format(Locale.US, "%,.2f", totalApostado)}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text("Total Ganho Hoje", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("R$ ${String.format(Locale.US, "%,.2f", totalGanho)}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = CryptoGreen)
                    }
                }
            }
        }

        // 2. ATALHOS RÁPIDOS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigateToRegistrar() },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("✍️", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Registrar Jogo", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Text("Conferir Aposta", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigateToResultados() },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("📜", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Deu no Poste", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Text("Ver Sorteios", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        // 3. RESUMO DAS APOSTAS DO DIA
        Text(
            text = "Resumo de Apostas do Dia ($winsCount Ganhas / ${apostas.size} Total)",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        if (apostas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Nenhuma aposta cadastrada hoje.", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            apostas.take(3).forEach { aposta ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("${aposta.extracaoName} • ${aposta.modalidade}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            Text("Palpite: ${aposta.numeroApostado} (${aposta.nomeBicho})", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        val isWin = aposta.status == "GANHOU"
                        val isPending = aposta.status == "PENDENTE"
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isWin) CryptoGreenBg else if (isPending) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else CryptoRedBg)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (isWin) "+R$ ${String.format(Locale.US, "%.2f", aposta.valorGanho)}" else if (isPending) "Pendente" else "Não deu",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isWin) CryptoGreen else if (isPending) MaterialTheme.colorScheme.primary else CryptoRed
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}
