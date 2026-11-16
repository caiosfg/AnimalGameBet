package com.animalgame.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
    var numeroApostado by remember { mutableStateOf("2581") }
    var valorText by remember { mutableStateOf("10.00") }

    // Multi-selection for Grupos & Dezenas
    val selectedGrupos = remember { mutableStateListOf<Int>() }
    val selectedDezenas = remember { mutableStateListOf<String>() }

    var modalidadeDropdown by remember { mutableStateOf(false) }
    var extracaoDropdown by remember { mutableStateOf(false) }
    var colocacaoDropdown by remember { mutableStateOf(false) }

    val modalidades = listOf(
        "Grupo",
        "Dupla de Grupo",
        "Terno de Grupo",
        "Dezena",
        "Duque de Dezenas",
        "Terno de Dezenas",
        "Centena",
        "Milhar",
        "Invertido"
    )

    val extracoes = listOf(
        Pair("PTM", "PTM Rio (11:30)"),
        Pair("PT", "PT Rio (14:30)"),
        Pair("PTV", "PTV Rio (16:30)"),
        Pair("PTN", "PTN Rio (18:30)"),
        Pair("FED", "Federal (19:00)"),
        Pair("COR", "Corujinha (21:20)")
    )

    val colocacoes = listOf(
        "1º Prêmio",
        "1º ao 5º Prêmio",
        "1º ao 7º Prêmio",
        "1º e 2º Prêmio",
        "2º e 3º Prêmio",
        "3º e 4º Prêmio",
        "4º e 5º Prêmio"
    )

    // Tabela dos 25 Bichos com foto PNG oficial no res/drawable
    val bichoList = listOf(
        BichoSelectionItem(1, "Avestruz", listOf("01", "02", "03", "04"), "avestruz"),
        BichoSelectionItem(2, "Águia", listOf("05", "06", "07", "08"), "aguia"),
        BichoSelectionItem(3, "Burro", listOf("09", "10", "11", "12"), "burro"),
        BichoSelectionItem(4, "Borboleta", listOf("13", "14", "15", "16"), "borboleta"),
        BichoSelectionItem(5, "Cachorro", listOf("17", "18", "19", "20"), "cachorro"),
        BichoSelectionItem(6, "Cabra", listOf("21", "22", "23", "24"), "cabra"),
        BichoSelectionItem(7, "Carneiro", listOf("25", "26", "27", "28"), "carneiro"),
        BichoSelectionItem(8, "Camelo", listOf("29", "30", "31", "32"), "camelo"),
        BichoSelectionItem(9, "Cobra", listOf("33", "34", "35", "36"), "cobra"),
        BichoSelectionItem(10, "Coelho", listOf("37", "38", "39", "40"), "coelho"),
        BichoSelectionItem(11, "Cavalo", listOf("41", "42", "43", "44"), "cavalo"),
        BichoSelectionItem(12, "Elefante", listOf("45", "46", "47", "48"), "elefante"),
        BichoSelectionItem(13, "Galo", listOf("49", "50", "51", "52"), "galo"),
        BichoSelectionItem(14, "Gato", listOf("53", "54", "55", "56"), "gato"),
        BichoSelectionItem(15, "Jacaré", listOf("57", "58", "59", "60"), "jacare"),
        BichoSelectionItem(16, "Leão", listOf("61", "62", "63", "64"), "leao"),
        BichoSelectionItem(17, "Macaco", listOf("65", "66", "67", "68"), "macaco"),
        BichoSelectionItem(18, "Porco", listOf("69", "70", "71", "72"), "porco"),
        BichoSelectionItem(19, "Pavão", listOf("73", "74", "75", "76"), "pavao"),
        BichoSelectionItem(20, "Peru", listOf("77", "78", "79", "80"), "peru"),
        BichoSelectionItem(21, "Touro", listOf("81", "82", "83", "84"), "touro"),
        BichoSelectionItem(22, "Tigre", listOf("85", "86", "87", "88"), "tigre"),
        BichoSelectionItem(23, "Urso", listOf("89", "90", "91", "92"), "urso"),
        BichoSelectionItem(24, "Veado", listOf("93", "94", "95", "96"), "veado"),
        BichoSelectionItem(25, "Vaca", listOf("97", "98", "99", "00"), "vaca")
    )

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
            text = "Selecione o bicho pelas fotos oficiais (Galo, Touro, Tigre, Águia...)",
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
                                            selectedGrupos.clear()
                                            selectedDezenas.clear()
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

                // Row 2: Colocação & Valor
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Colocação Dropdown
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Colocação / Posição", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
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

                    // Valor Apostado (R$)
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

                // SELEÇÃO VISUAL DOS 25 ANIMAIS (COM AS FOTOS DO RES/DRAWABLE)
                Text(
                    text = "Selecione o Bicho na Tabela Ilustrada (${selectedGrupos.size} selecionado/s):",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(bichoList, key = { it.group }) { item ->
                        val isSelected = selectedGrupos.contains(item.group)
                        val resId = context.resources.getIdentifier(
                            item.photoName.lowercase().trim(),
                            "drawable",
                            context.packageName
                        )

                        Box(
                            modifier = Modifier
                                .width(115.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (isSelected) CryptoGreenBg else MaterialTheme.colorScheme.background)
                                .border(
                                    2.dp,
                                    if (isSelected) CryptoGreen else MaterialTheme.colorScheme.surfaceVariant,
                                    RoundedCornerShape(14.dp)
                                )
                                .clickable {
                                    if (modalidade.contains("Dupla")) {
                                        if (isSelected) selectedGrupos.remove(item.group)
                                        else if (selectedGrupos.size < 2) selectedGrupos.add(item.group)
                                    } else if (modalidade.contains("Terno de Grupo")) {
                                        if (isSelected) selectedGrupos.remove(item.group)
                                        else if (selectedGrupos.size < 3) selectedGrupos.add(item.group)
                                    } else {
                                        selectedGrupos.clear()
                                        selectedGrupos.add(item.group)
                                        selectedDezenas.clear()
                                        selectedDezenas.addAll(item.dezenas)
                                        numeroApostado = item.dezenas.first()
                                    }
                                }
                                .padding(8.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                if (resId != 0) {
                                    Image(
                                        painter = painterResource(id = resId),
                                        contentDescription = item.name,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("G${String.format(Locale.US, "%02d", item.group)}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "G${String.format(Locale.US, "%02d", item.group)} • ${item.name}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) CryptoGreen else MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1
                                )

                                Text(
                                    text = item.dezenas.joinToString("-"),
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                // CAMPO NÚMERO / MILHAR / CENTENA / INVERTIDO
                if (modalidade in listOf("Centena", "Milhar", "Invertido", "Dezena", "Duque de Dezenas", "Terno de Dezenas")) {
                    Column {
                        Text(
                            text = "Número Apostado ($modalidade):",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        OutlinedTextField(
                            value = numeroApostado,
                            onValueChange = { numeroApostado = it },
                            placeholder = { Text(if (modalidade == "Centena") "581" else "2581") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.background,
                                unfocusedContainerColor = MaterialTheme.colorScheme.background
                            )
                        )

                        if (modalidade == "Invertido" && numeroApostado.length >= 3) {
                            val perms = viewModel.generatePermutations(numeroApostado)
                            Text(
                                text = "Combinações Invertidas (${perms.size}): ${perms.take(8).joinToString(", ")}${if (perms.size > 8) "..." else ""}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Botão de Cadastrar Aposta
                Button(
                    onClick = {
                        val valor = valorText.toDoubleOrNull() ?: 10.0
                        val perms = if (modalidade == "Invertido") viewModel.generatePermutations(numeroApostado) else emptyList()

                        val nomeBichoMain = if (selectedGrupos.isNotEmpty()) {
                            selectedGrupos.mapNotNull { g -> bichoList.find { it.group == g }?.name }.joinToString(" + ")
                        } else {
                            "Grupo $numeroApostado"
                        }

                        val novaAposta = ApostaModel(
                            id = UUID.randomUUID().toString(),
                            modalidade = modalidade,
                            extracaoCode = extracaoCode,
                            extracaoName = extracaoName,
                            gruposSelecionados = selectedGrupos.toList(),
                            dezenasSelecionadas = selectedDezenas.toList(),
                            numeroApostado = numeroApostado,
                            combinacoesInvertidas = perms,
                            colocacao = colocacao,
                            valorApostado = valor,
                            nomeBicho = nomeBichoMain
                        )

                        viewModel.adicionarAposta(novaAposta)
                        Toast.makeText(context, "Aposta ($modalidade) registrada e conferida com Deu no Poste!", Toast.LENGTH_SHORT).show()
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
                        text = "➕ CADASTRAR E CONFERIR COM DEU NO POSTE",
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

private data class BichoSelectionItem(
    val group: Int,
    val name: String,
    val dezenas: List<String>,
    val photoName: String
)

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
                text = "${aposta.extracaoName} • ${aposta.modalidade} (${aposta.descricaoAposta})",
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
