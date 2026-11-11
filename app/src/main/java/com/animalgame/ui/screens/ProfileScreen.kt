package com.animalgame.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animalgame.ui.theme.CryptoGreen
import com.animalgame.ui.theme.CryptoRed
import com.animalgame.ui.viewmodel.MainViewModel
import java.util.Locale

@Composable
fun ProfileScreen(
    viewModel: MainViewModel? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val profileResId = context.resources.getIdentifier("profile", "drawable", context.packageName)
    val walletResId = context.resources.getIdentifier("wallet", "drawable", context.packageName)

    val valorInicial = viewModel?.valorInicialDia?.collectAsState()?.value ?: 0.0
    val apostas = viewModel?.apostas?.collectAsState()?.value ?: emptyList()

    val totalApostado = apostas.sumOf { it.valorApostado }
    val totalGanho = apostas.sumOf { it.valorGanho }
    val lucroLiquido = totalGanho - totalApostado
    val saldoAtual = valorInicial + lucroLiquido

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Meu Perfil",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(CryptoGreen.copy(alpha = 0.15f))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Apostador VIP",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = CryptoGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // User Profile Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (profileResId != 0) {
                        Image(
                            painter = painterResource(id = profileResId),
                            contentDescription = "User Avatar",
                            modifier = Modifier.size(36.dp)
                        )
                    } else {
                        Text("👤", fontSize = 28.sp)
                    }
                }

                Column {
                    Text(
                        text = "Apostador Pro",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "apostador@deunoposte.com",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(CryptoGreen.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Conta Verificada",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = CryptoGreen
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Resumo da Banca
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (walletResId != 0) {
                        Image(
                            painter = painterResource(id = walletResId),
                            contentDescription = "Banca",
                            modifier = Modifier.size(28.dp)
                        )
                    } else {
                        Text("👛", fontSize = 20.sp)
                    }

                    Column {
                        Text(
                            text = "Saldo Atual Hoje",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "R$ ${String.format(Locale.US, "%,.2f", saldoAtual)}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, thickness = 1.dp)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Valor Inicial", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("R$ ${String.format(Locale.US, "%,.2f", valorInicial)}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    }

                    Column {
                        Text("Total Apostado", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("R$ ${String.format(Locale.US, "%,.2f", totalApostado)}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text("Total Ganho", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("R$ ${String.format(Locale.US, "%,.2f", totalGanho)}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = CryptoGreen)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botão para Resetar Dia
        Button(
            onClick = {
                viewModel?.resetarDia()
                Toast.makeText(context, "Dia reiniciado e banca zerada!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CryptoRed
            )
        ) {
            Text(
                text = "🔄 REINICIAR BANCA DO DIA (ZERAR R$ 0,00)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = androidx.compose.ui.graphics.Color.White
            )
        }
    }
}
