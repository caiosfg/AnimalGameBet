package com.animalgame.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import com.animalgame.data.model.ApostaModel
import com.animalgame.ui.theme.CryptoGreen
import com.animalgame.ui.theme.CryptoGreenBg
import com.animalgame.ui.theme.CryptoRed
import com.animalgame.ui.theme.CryptoRedBg
import com.animalgame.ui.viewmodel.MainViewModel
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarJogoScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val apostas by viewModel.apostas.collectAsState()

    var modalidade by remember { mutableStateOf("Grupo") }
    var extracaoCode by remember { mutableStateOf("PT") }
    var extracaoName by remember { mutableStateOf("PT Rio (14:30)") }
    var colocacao by remember { mutableStateOf("1º ao 5º Prêmio") }
    var numeroApostado by remember { mutableStateOf("22") }
    var nomeBicho by remember { mutableStateOf("Tigre") }
    var valorText by remember { mutableStateOf("10.00") }

    var modalidadeDropdown by remember { mutableStateOf(false) }
    var extracaoDropdown by remember { mutableStateOf(false) }
    var colocacaoDropdown by remember { mutableStateOf(false) }

    val modalidades = listOf("Grupo", "Dezena", "Centena", "Milhar", "Duque de Grupo", "Terno de Grupo")
    val extracoes = listOf(
        Pair("PTM", "PTM Rio (11:30)"),
        Pair("PT", "PT Rio (14:30)"),
        Pair("PTV", "PTV Rio (16:30)"),
        Pair("PTN", "PTN Rio (18:30)"),
        Pair("FED", "Federal (19:00)"),
        Pair("COR", "Corujinha (21:20)")
    )
    val colocacoes = listOf("1º Prêmio", "1º ao 5º Prêmio", "1º ao 7º Prêmio")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Registrar Aposta",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Informe seu jogo para conferência automática com o Deu no Poste",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Card Formulário de Registro
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Dropdowns Row 1: Modalidade & Extração
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Modalidade Dropdown
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Modalidade", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
                        ExposedDropdownMenuBox(
                            expanded = modalidadeDropdown,
                            onExpandedChange = { modalidadeDropdown = !modalidadeDropdown }
                        ) {
                            Box(
                                modifier = Modifier
                                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(horizontal = 10.dp, vertical = 10.dp)
                            ) {
                                Text(modalidade, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            }
                            ExposedDropdownMenu(
                                expanded = modalidadeDropdown,
                                onDismissRequest = { modalidadeDropdown = false }
                            ) {
                                modalidades.forEach { m ->
                                    DropdownMenuItem(
                                        text = { Text(m) },
                                        onClick = {
                                            modalidade = m
                                            modalidadeDropdown = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Extração Dropdown
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Horário / Extração", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
                        ExposedDropdownMenuBox(
                            expanded = extracaoDropdown,
                            onExpandedChange = { extracaoDropdown = !extracaoDropdown }
                        ) {
                            Box(
                                modifier = Modifier
                                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(horizontal = 10.dp, vertical = 10.dp)
                            ) {
                                Text(extracaoCode, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            }
                            ExposedDropdownMenu(
                                expanded = extracaoDropdown,
                                onDismissRequest = { extracaoDropdown = false }
                            ) {
                                extracoes.forEach { ex ->
                                    DropdownMenuItem(
                                        text = { Text(ex.second) },
                                        onClick = {
                                            extracaoCode = ex.first
                                            extracaoName = ex.second
                                            extracaoDropdown = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // Row 2: Colocação & Número/Bicho
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Colocação Dropdown
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Colocação", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
                        ExposedDropdownMenuBox(
                            expanded = colocacaoDropdown,
                            onExpandedChange = { colocacaoDropdown = !colocacaoDropdown }
                        ) {
                            Box(
                                modifier = Modifier
                                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(horizontal = 10.dp, vertical = 10.dp)
                            ) {
                                Text(colocacao, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            }
                            ExposedDropdownMenu(
                                expanded = colocacaoDropdown,
                                onDismissRequest = { colocacaoDropdown = false }
                            ) {
                                colocacoes.forEach { c ->
                                    DropdownMenuItem(
                                        text = { Text(c) },
                                        onClick = {
                                            colocacao = c
                                            colocacaoDropdown = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Número / Grupo Campo
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Número / Grupo", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
                        OutlinedTextField(
                            value = numeroApostado,
                            onValueChange = { numeroApostado = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.background,
                                unfocusedContainerColor = MaterialTheme.colorScheme.background
                            )
                        )
                    }
                }

                // Row 3: Nome do Bicho / Valor Apostado
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Nome do Bicho", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
                        OutlinedTextField(
                            value = nomeBicho,
                            onValueChange = { nomeBicho = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.background,
                                unfocusedContainerColor = MaterialTheme.colorScheme.background
                            )
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Valor Apostado (R$)", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
                        OutlinedTextField(
                            value = valorText,
                            onValueChange = { valorText = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.background,
                                unfocusedContainerColor = MaterialTheme.colorScheme.background
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Botão de Adicionar Aposta
                Button(
                    onClick = {
                        val valor = valorText.toDoubleOrNull() ?: 5.0
                        if (numeroApostado.isNotBlank()) {
                            val novaAposta = ApostaModel(
                                id = UUID.randomUUID().toString(),
                                modalidade = modalidade,
                                extracaoCode = extracaoCode,
                                extracaoName = extracaoName,
                                numeroApostado = numeroApostado,
                                nomeBicho = nomeBicho.ifBlank { "Grupo $numeroApostado" },
                                colocacao = colocacao,
                                valorApostado = valor
                            )
                            viewModel.adicionarAposta(novaAposta)
                            Toast.makeText(context, "Aposta registrada e conferida com Deu no Poste!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Informe o número ou grupo!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "➕ CADASTRAR E CONFERIR APOSTA",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Minhas Apostas do Dia (${apostas.size})",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Lista de Apostas Cadastradas com Conferência
        if (apostas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nenhuma aposta cadastrada hoje.\nPreencha o formulário acima para conferir!",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(apostas, key = { it.id }) { aposta ->
                    CardApostaItem(
                        aposta = aposta,
                        onDelete = { viewModel.removerAposta(aposta.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CardApostaItem(
    aposta: ApostaModel,
    onDelete: () -> Unit
) {
    val isWin = aposta.status == "GANHOU"
    val isPending = aposta.status == "PENDENTE"

    val statusBg = when {
        isWin -> CryptoGreenBg
        isPending -> MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
        else -> CryptoRedBg
    }

    val statusColor = when {
        isWin -> CryptoGreen
        isPending -> MaterialTheme.colorScheme.primary
        else -> CryptoRed
    }

    val statusText = when {
        isWin -> "🎉 GANHOU!"
        isPending -> "⏳ AGUARDANDO SORTEIO"
        else -> "❌ NÃO DEU"
    }

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
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(statusBg)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = statusText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (isWin) {
                        Text(
                            text = "+R$ ${String.format(Locale.US, "%,.2f", aposta.valorGanho)} (+${String.format(Locale.US, "%.0f", aposta.porcentagemRetorno)}%)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = CryptoGreen
                        )
                    } else if (!isPending) {
                        Text(
                            text = "-R$ ${String.format(Locale.US, "%,.2f", aposta.valorApostado)} (-100%)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = CryptoRed
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable { onDelete() }
                            .padding(4.dp)
                    ) {
                        Text("🗑️", fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${aposta.extracaoName} • ${aposta.modalidade} (${aposta.numeroApostado} - ${aposta.nomeBicho})",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Colocação: ${aposta.colocacao}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Apostado: R$ ${String.format(Locale.US, "%,.2f", aposta.valorApostado)}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Detalhe do Resultado Conferido no Deu no Poste
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(8.dp)
            ) {
                Text(
                    text = "Conferência Deu no Poste: ${aposta.detalheResultado}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
