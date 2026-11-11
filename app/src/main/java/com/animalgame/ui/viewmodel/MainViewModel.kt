package com.animalgame.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.animalgame.data.model.ApostaModel
import com.animalgame.data.model.BichoDrawResult
import com.animalgame.data.model.BichoItemResult
import com.animalgame.data.model.BichoRecommendation
import com.animalgame.data.model.CryptoModel
import com.animalgame.data.model.OrderBookItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class MainViewModel : ViewModel() {

    private val _cryptoList = MutableStateFlow<List<CryptoModel>>(emptyList())
    val cryptoList: StateFlow<List<CryptoModel>> = _cryptoList.asStateFlow()

    private val _selectedCrypto = MutableStateFlow<CryptoModel?>(null)
    val selectedCrypto: StateFlow<CryptoModel?> = _selectedCrypto.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Trade Panel State
    private val _isBuySelected = MutableStateFlow(true)
    val isBuySelected: StateFlow<Boolean> = _isBuySelected.asStateFlow()

    private val _selectedOrderType = MutableStateFlow("Limit")
    val selectedOrderType: StateFlow<String> = _selectedOrderType.asStateFlow()

    private val _orderAmount = MutableStateFlow("1.0")
    val orderAmount: StateFlow<String> = _orderAmount.asStateFlow()

    private val _selectedTimeInterval = MutableStateFlow("1D")
    val selectedTimeInterval: StateFlow<String> = _selectedTimeInterval.asStateFlow()

    private val _tradeStatusMessage = MutableStateFlow<String?>(null)
    val tradeStatusMessage: StateFlow<String?> = _tradeStatusMessage.asStateFlow()

    // Jogo do Bicho State
    private val _currentBichoDate = MutableStateFlow("10/11/2026")
    val currentBichoDate: StateFlow<String> = _currentBichoDate.asStateFlow()

    private val _isRefreshingBicho = MutableStateFlow(false)
    val isRefreshingBicho: StateFlow<Boolean> = _isRefreshingBicho.asStateFlow()

    private val _bichoDraws = MutableStateFlow<List<BichoDrawResult>>(emptyList())
    val bichoDraws: StateFlow<List<BichoDrawResult>> = _bichoDraws.asStateFlow()

    private val _selectedBichoDrawCode = MutableStateFlow("FED")
    val selectedBichoDrawCode: StateFlow<String> = _selectedBichoDrawCode.asStateFlow()

    private val _bichoRecommendation = MutableStateFlow<BichoRecommendation?>(null)
    val bichoRecommendation: StateFlow<BichoRecommendation?> = _bichoRecommendation.asStateFlow()

    // Daily Bankroll & Bets State (Starts at 0.00)
    private val _valorInicialDia = MutableStateFlow(0.0)
    val valorInicialDia: StateFlow<Double> = _valorInicialDia.asStateFlow()

    private val _apostas = MutableStateFlow<List<ApostaModel>>(emptyList())
    val apostas: StateFlow<List<ApostaModel>> = _apostas.asStateFlow()

    init {
        loadSampleData()
        loadBichoData()
    }

    private fun loadSampleData() {
        val sampleList = listOf(
            CryptoModel(
                id = "btc",
                name = "Bitcoin",
                symbol = "BTC",
                logoName = "bitcoin",
                currentPrice = 64320.50,
                changePercentage = 3.45,
                highPrice = 65120.00,
                lowPrice = 63100.25,
                openPrice = 62175.00,
                closePrice = 64320.50,
                dailyChange = 2145.50,
                dailyVolume = "$28.4B",
                chartData1H = listOf(64100f, 64150f, 64200f, 64180f, 64250f, 64320.5f),
                chartData1D = listOf(62175f, 62500f, 63100f, 63800f, 63500f, 64320.5f),
                chartData1W = listOf(59800f, 60500f, 61200f, 62000f, 63400f, 64320.5f),
                chartData1M = listOf(54000f, 56500f, 58000f, 61000f, 62500f, 64320.5f),
                chartData1Y = listOf(29000f, 35000f, 44000f, 52000f, 60000f, 64320.5f),
                chartDataAll = listOf(100f, 1000f, 10000f, 20000f, 40000f, 64320.5f),
                sellOrders = listOf(
                    OrderBookItem(64325.00, 0.45),
                    OrderBookItem(64328.50, 1.20)
                ),
                buyOrders = listOf(
                    OrderBookItem(64318.00, 0.65),
                    OrderBookItem(64315.50, 1.10)
                ),
                isBookmarked = true
            )
        )
        _cryptoList.value = sampleList
        _selectedCrypto.value = sampleList.first()
    }

    fun loadBichoData() {
        val todayDate = _currentBichoDate.value
        val sampleDraws = listOf(
            BichoDrawResult(
                drawName = "Federal",
                drawCode = "FED",
                time = "19:00",
                date = todayDate,
                items = listOf(
                    BichoItemResult("1º", "7896", 24, "Veado"),
                    BichoItemResult("2º", "4321", 6, "Cabra"),
                    BichoItemResult("3º", "9012", 3, "Burro"),
                    BichoItemResult("4º", "5678", 20, "Peru"),
                    BichoItemResult("5º", "1234", 9, "Cobra"),
                    BichoItemResult("6º", "8765", 17, "Macaco"),
                    BichoItemResult("7º", "0143", 11, "Cavalo")
                )
            ),
            BichoDrawResult(
                drawName = "PT Rio",
                drawCode = "PT",
                time = "14:30",
                date = todayDate,
                items = listOf(
                    BichoItemResult("1º", "3588", 22, "Tigre"),
                    BichoItemResult("2º", "9112", 3, "Burro"),
                    BichoItemResult("3º", "4255", 14, "Gato"),
                    BichoItemResult("4º", "1867", 17, "Macaco"),
                    BichoItemResult("5º", "7320", 5, "Cachorro"),
                    BichoItemResult("6º", "6202", 1, "Avestruz"),
                    BichoItemResult("7º", "0044", 11, "Cavalo")
                )
            ),
            BichoDrawResult(
                drawName = "PTV Rio",
                drawCode = "PTV",
                time = "16:30",
                date = todayDate,
                items = listOf(
                    BichoItemResult("1º", "6147", 12, "Elefante"),
                    BichoItemResult("2º", "8436", 9, "Cobra"),
                    BichoItemResult("3º", "2974", 19, "Pavão"),
                    BichoItemResult("4º", "0582", 21, "Touro"),
                    BichoItemResult("5º", "1309", 3, "Burro"),
                    BichoItemResult("6º", "9448", 12, "Elefante"),
                    BichoItemResult("7º", "0490", 23, "Urso")
                )
            ),
            BichoDrawResult(
                drawName = "PTM Rio",
                drawCode = "PTM",
                time = "11:30",
                date = todayDate,
                items = listOf(
                    BichoItemResult("1º", "4095", 24, "Veado"),
                    BichoItemResult("2º", "8240", 10, "Coelho"),
                    BichoItemResult("3º", "5092", 23, "Urso"),
                    BichoItemResult("4º", "6724", 6, "Cabra"),
                    BichoItemResult("5º", "1487", 22, "Tigre"),
                    BichoItemResult("6º", "4659", 15, "Jacaré"),
                    BichoItemResult("7º", "0618", 5, "Cachorro")
                )
            ),
            BichoDrawResult(
                drawName = "PTN Rio",
                drawCode = "PTN",
                time = "18:30",
                date = todayDate,
                items = listOf(
                    BichoItemResult("1º", "8911", 3, "Burro"),
                    BichoItemResult("2º", "5444", 11, "Cavalo"),
                    BichoItemResult("3º", "4785", 22, "Tigre"),
                    BichoItemResult("4º", "4803", 1, "Avestruz"),
                    BichoItemResult("5º", "6716", 4, "Borboleta"),
                    BichoItemResult("6º", "0659", 15, "Jacaré"),
                    BichoItemResult("7º", "0031", 8, "Camelo")
                )
            ),
            BichoDrawResult(
                drawName = "Corujinha",
                drawCode = "COR",
                time = "21:20",
                date = todayDate,
                items = listOf(
                    BichoItemResult("1º", "9234", 9, "Cobra"),
                    BichoItemResult("2º", "1562", 16, "Leão"),
                    BichoItemResult("3º", "8371", 18, "Porco"),
                    BichoItemResult("4º", "4095", 24, "Veado"),
                    BichoItemResult("5º", "7812", 3, "Burro"),
                    BichoItemResult("6º", "3104", 1, "Avestruz"),
                    BichoItemResult("7º", "0578", 20, "Peru")
                )
            )
        )

        _bichoDraws.value = sampleDraws

        _bichoRecommendation.value = BichoRecommendation(
            animalGroup = 24,
            animalName = "Veado",
            dozenas = listOf(93, 94, 95, 96),
            confidencePercentage = 96,
            reasoning = "Análise Deu no Poste (10/11/2026): O Veado (Grupo 24) saiu no 1º Prêmio da PTM e Federal. A probabilidade de repetição na Corujinha e PTV é altíssima conforme a tabela estatística de frequência.",
            suggestedMilhar = "4095",
            suggestedCentena = "095",
            puxadas = listOf("Cobra (09)", "Leão (16)", "Porco (18)", "Vaca (25)")
        )

        conferirApostas()
    }

    fun recarregarDeuNoPoste(onFinish: () -> Unit = {}) {
        _isRefreshingBicho.value = true
        _currentBichoDate.value = "10/11/2026"
        loadBichoData()
        _isRefreshingBicho.value = false
        onFinish()
    }

    fun setValorInicialDia(valor: Double) {
        _valorInicialDia.value = valor.coerceAtLeast(0.0)
    }

    fun resetarDia() {
        _valorInicialDia.value = 0.0
        _apostas.value = emptyList()
    }

    fun adicionarAposta(aposta: ApostaModel) {
        val newList = _apostas.value + aposta
        _apostas.value = newList
        conferirApostas()
    }

    fun removerAposta(id: String) {
        _apostas.value = _apostas.value.filter { it.id != id }
    }

    fun conferirApostas() {
        val draws = _bichoDraws.value
        val currentBets = _apostas.value

        val updatedBets = currentBets.map { aposta ->
            val draw = draws.find { it.drawCode.equals(aposta.extracaoCode, ignoreCase = true) }
            if (draw != null && draw.items.isNotEmpty()) {
                val maxPosIndex = when (aposta.colocacao) {
                    "1º Prêmio" -> 1
                    "1º ao 5º Prêmio" -> 5
                    "1º ao 7º Prêmio" -> 7
                    else -> 5
                }
                val checkItems = draw.items.take(maxPosIndex)
                val numApostado = aposta.numeroApostado.trim()
                val grupoApostado = numApostado.toIntOrNull()

                var matches = 0
                val matchingPositions = mutableListOf<String>()

                checkItems.forEach { item ->
                    val isMatch = when (aposta.modalidade) {
                        "Grupo" -> (grupoApostado != null && item.grupo == grupoApostado) || item.animalName.equals(aposta.nomeBicho, ignoreCase = true)
                        "Dezena" -> item.dezena == numApostado || item.milhar.endsWith(numApostado)
                        "Centena" -> item.centena == numApostado || item.milhar.endsWith(numApostado)
                        "Milhar" -> item.milhar == numApostado
                        else -> item.grupo == grupoApostado
                    }
                    if (isMatch) {
                        matches++
                        matchingPositions.add(item.position)
                    }
                }

                if (matches > 0) {
                    val baseMultiplier = when (aposta.modalidade) {
                        "Grupo" -> 18.0
                        "Dezena" -> 60.0
                        "Centena" -> 600.0
                        "Milhar" -> 4000.0
                        "Duque de Grupo" -> 18.5
                        "Terno de Grupo" -> 130.0
                        else -> 18.0
                    }
                    val divFactor = if (maxPosIndex > 1) maxPosIndex.toDouble() else 1.0
                    val ganhoTotal = (aposta.valorApostado * (baseMultiplier / divFactor)) * matches
                    val pctLucro = ((ganhoTotal - aposta.valorApostado) / aposta.valorApostado) * 100.0

                    aposta.copy(
                        status = "GANHOU",
                        valorGanho = ganhoTotal,
                        porcentagemRetorno = pctLucro,
                        detalheResultado = "Deu no Poste! Sorteado no ${matchingPositions.joinToString(", ")}"
                    )
                } else {
                    aposta.copy(
                        status = "PERDEU",
                        valorGanho = 0.0,
                        porcentagemRetorno = -100.0,
                        detalheResultado = "Não deu no poste nesta extração"
                    )
                }
            } else {
                aposta.copy(
                    status = "PENDENTE",
                    valorGanho = 0.0,
                    porcentagemRetorno = 0.0,
                    detalheResultado = "Aguardando sorteio no Deu no Poste"
                )
            }
        }

        _apostas.value = updatedBets
    }

    fun selectCryptoById(id: String) {
        val found = _cryptoList.value.find { it.id.equals(id, ignoreCase = true) }
        if (found != null) {
            _selectedCrypto.value = found
            val defaultAmount = if (found.currentPrice > 100) "0.1" else "1.0"
            _orderAmount.value = defaultAmount
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleBuySell(isBuy: Boolean) {
        _isBuySelected.value = isBuy
    }

    fun setOrderType(type: String) {
        _selectedOrderType.value = type
    }

    fun setOrderAmount(amount: String) {
        _orderAmount.value = amount
    }

    fun incrementAmount(step: Double = 0.1) {
        val current = _orderAmount.value.toDoubleOrNull() ?: 0.0
        val updated = current + step
        _orderAmount.value = String.format(Locale.US, "%.3f", updated)
    }

    fun decrementAmount(step: Double = 0.1) {
        val current = _orderAmount.value.toDoubleOrNull() ?: 0.0
        val updated = (current - step).coerceAtLeast(0.01)
        _orderAmount.value = String.format(Locale.US, "%.3f", updated)
    }

    fun setTimeInterval(interval: String) {
        _selectedTimeInterval.value = interval
    }

    fun toggleBookmark(cryptoId: String) {
        val updatedList = _cryptoList.value.map { crypto ->
            if (crypto.id.equals(cryptoId, ignoreCase = true)) {
                crypto.copy(isBookmarked = !crypto.isBookmarked)
            } else {
                crypto
            }
        }
        _cryptoList.value = updatedList
    }

    fun executeTrade() {
        val crypto = _selectedCrypto.value ?: return
        val side = if (_isBuySelected.value) "BUY" else "SELL"
        val amt = _orderAmount.value
        val type = _selectedOrderType.value
        _tradeStatusMessage.value = "Successfully placed $side $type order for $amt ${crypto.symbol}!"
    }

    fun clearTradeStatusMessage() {
        _tradeStatusMessage.value = null
    }

    fun selectBichoDrawCode(code: String) {
        _selectedBichoDrawCode.value = code
    }

    fun generateNewBichoPalpite() {
        val animals = listOf(
            Triple(24, "Veado", listOf(93, 94, 95, 96)),
            Triple(11, "Cavalo", listOf(41, 42, 43, 44)),
            Triple(9, "Cobra", listOf(33, 34, 35, 36)),
            Triple(16, "Leão", listOf(61, 62, 63, 64)),
            Triple(22, "Tigre", listOf(85, 86, 87, 88)),
            Triple(15, "Jacaré", listOf(57, 58, 59, 60)),
            Triple(3, "Burro", listOf(9, 10, 11, 12)),
            Triple(18, "Porco", listOf(69, 70, 71, 72))
        )
        val selected = animals.random()
        val randomCentena = (100..999).random()
        val randomMilhar = (1000..9999).random()

        _bichoRecommendation.value = BichoRecommendation(
            animalGroup = selected.first,
            animalName = selected.second,
            dozenas = selected.third,
            confidencePercentage = (85..98).random(),
            reasoning = "Palpite Recalculado (${_currentBichoDate.value}): Alta tendência estatística identificada para o Grupo ${String.format(Locale.US, "%02d", selected.first)} (${selected.second}) com base na frequência das dezenas ${selected.third.joinToString(", ")}.",
            suggestedMilhar = randomMilhar.toString(),
            suggestedCentena = randomCentena.toString(),
            puxadas = listOf("Avestruz (01)", "Camelo (08)", "Gato (14)", "Urso (23)").shuffled().take(3)
        )
    }
}
