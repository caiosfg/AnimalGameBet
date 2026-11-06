package com.animalgame.ui.viewmodel

import androidx.lifecycle.ViewModel
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

    init {
        loadSampleData()
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
                    OrderBookItem(64328.50, 1.20),
                    OrderBookItem(64330.00, 0.85),
                    OrderBookItem(64335.20, 2.10),
                    OrderBookItem(64340.00, 0.30),
                    OrderBookItem(64345.50, 1.75)
                ),
                buyOrders = listOf(
                    OrderBookItem(64318.00, 0.65),
                    OrderBookItem(64315.50, 1.10),
                    OrderBookItem(64310.00, 2.40),
                    OrderBookItem(64305.00, 0.95),
                    OrderBookItem(64300.00, 3.50),
                    OrderBookItem(64295.00, 1.15)
                ),
                isBookmarked = true
            ),
            CryptoModel(
                id = "eth",
                name = "Ethereum",
                symbol = "ETH",
                logoName = "etherium",
                currentPrice = 3480.20,
                changePercentage = 2.15,
                highPrice = 3520.00,
                lowPrice = 3410.50,
                openPrice = 3407.00,
                closePrice = 3480.20,
                dailyChange = 73.20,
                dailyVolume = "$14.2B",
                chartData1H = listOf(3470f, 3472f, 3475f, 3478f, 3480.2f),
                chartData1D = listOf(3407f, 3420f, 3445f, 3460f, 3480.2f),
                chartData1W = listOf(3200f, 3300f, 3350f, 3420f, 3480.2f),
                chartData1M = listOf(2900f, 3100f, 3250f, 3380f, 3480.2f),
                chartData1Y = listOf(1600f, 2100f, 2800f, 3200f, 3480.2f),
                chartDataAll = listOf(10f, 200f, 1200f, 2500f, 3480.2f),
                sellOrders = listOf(
                    OrderBookItem(3481.00, 2.5),
                    OrderBookItem(3482.50, 5.1),
                    OrderBookItem(3484.00, 1.8),
                    OrderBookItem(3486.00, 8.4),
                    OrderBookItem(3488.50, 3.2),
                    OrderBookItem(3490.00, 12.0)
                ),
                buyOrders = listOf(
                    OrderBookItem(3479.50, 3.2),
                    OrderBookItem(3478.00, 6.4),
                    OrderBookItem(3476.00, 2.1),
                    OrderBookItem(3474.50, 10.5),
                    OrderBookItem(3472.00, 4.8),
                    OrderBookItem(3470.00, 15.2)
                ),
                isBookmarked = true
            ),
            CryptoModel(
                id = "bnb",
                name = "Binance",
                symbol = "BNB",
                logoName = "binance",
                currentPrice = 580.75,
                changePercentage = -1.20,
                highPrice = 595.00,
                lowPrice = 575.10,
                openPrice = 587.80,
                closePrice = 580.75,
                dailyChange = -7.05,
                dailyVolume = "$2.1B",
                chartData1H = listOf(582f, 581.5f, 581f, 580.8f, 580.75f),
                chartData1D = listOf(587.8f, 592f, 588f, 584f, 580.75f),
                chartData1W = listOf(560f, 575f, 590f, 585f, 580.75f),
                chartData1M = listOf(520f, 545f, 570f, 585f, 580.75f),
                chartData1Y = listOf(240f, 310f, 420f, 550f, 580.75f),
                chartDataAll = listOf(1f, 40f, 250f, 450f, 580.75f),
                sellOrders = listOf(
                    OrderBookItem(581.00, 12.4),
                    OrderBookItem(581.50, 25.0),
                    OrderBookItem(582.00, 18.2),
                    OrderBookItem(583.00, 45.1),
                    OrderBookItem(584.50, 30.0),
                    OrderBookItem(585.00, 60.5)
                ),
                buyOrders = listOf(
                    OrderBookItem(580.50, 15.0),
                    OrderBookItem(580.00, 32.1),
                    OrderBookItem(579.50, 22.4),
                    OrderBookItem(578.00, 55.0),
                    OrderBookItem(577.50, 40.2),
                    OrderBookItem(576.00, 80.0)
                ),
                isBookmarked = false
            ),
            CryptoModel(
                id = "shib",
                name = "Shiba Inu",
                symbol = "SHIB",
                logoName = "shiba",
                currentPrice = 0.0000245,
                changePercentage = 8.60,
                highPrice = 0.0000260,
                lowPrice = 0.0000220,
                openPrice = 0.00002255,
                closePrice = 0.0000245,
                dailyChange = 0.00000195,
                dailyVolume = "$980M",
                chartData1H = listOf(0.0000240f, 0.0000242f, 0.0000245f),
                chartData1D = listOf(0.0000225f, 0.0000232f, 0.0000240f, 0.0000245f),
                chartData1W = listOf(0.0000200f, 0.0000215f, 0.0000230f, 0.0000245f),
                chartData1M = listOf(0.0000180f, 0.0000200f, 0.0000220f, 0.0000245f),
                chartData1Y = listOf(0.0000080f, 0.0000120f, 0.0000180f, 0.0000245f),
                chartDataAll = listOf(0.0000001f, 0.000005f, 0.00003f, 0.0000245f),
                sellOrders = listOf(
                    OrderBookItem(0.00002455, 1500000.0),
                    OrderBookItem(0.00002460, 4200000.0),
                    OrderBookItem(0.00002470, 8500000.0),
                    OrderBookItem(0.00002480, 12000000.0)
                ),
                buyOrders = listOf(
                    OrderBookItem(0.00002445, 2100000.0),
                    OrderBookItem(0.00002440, 5400000.0),
                    OrderBookItem(0.00002430, 9100000.0),
                    OrderBookItem(0.00002420, 15000000.0)
                ),
                isBookmarked = false
            ),
            CryptoModel(
                id = "trx",
                name = "TRON",
                symbol = "TRX",
                logoName = "trox",
                currentPrice = 0.1350,
                changePercentage = -0.45,
                highPrice = 0.1380,
                lowPrice = 0.1330,
                openPrice = 0.1356,
                closePrice = 0.1350,
                dailyChange = -0.0006,
                dailyVolume = "$420M",
                chartData1H = listOf(0.1355f, 0.1352f, 0.1350f),
                chartData1D = listOf(0.1356f, 0.1370f, 0.1360f, 0.1350f),
                chartData1W = listOf(0.1300f, 0.1340f, 0.1360f, 0.1350f),
                chartData1M = listOf(0.1220f, 0.1280f, 0.1320f, 0.1350f),
                chartData1Y = listOf(0.0750f, 0.0950f, 0.1150f, 0.1350f),
                chartDataAll = listOf(0.0100f, 0.0500f, 0.1000f, 0.1350f),
                sellOrders = listOf(
                    OrderBookItem(0.1352, 45000.0),
                    OrderBookItem(0.1355, 120000.0),
                    OrderBookItem(0.1360, 250000.0),
                    OrderBookItem(0.1365, 500000.0)
                ),
                buyOrders = listOf(
                    OrderBookItem(0.1348, 62000.0),
                    OrderBookItem(0.1345, 140000.0),
                    OrderBookItem(0.1340, 310000.0),
                    OrderBookItem(0.1335, 650000.0)
                ),
                isBookmarked = false
            )
        )

        _cryptoList.value = sampleList
        _selectedCrypto.value = sampleList.first()
    }

    fun selectCryptoById(id: String) {
        val found = _cryptoList.value.find { it.id.equals(id, ignoreCase = true) }
        if (found != null) {
            _selectedCrypto.value = found
            // Reset order amount dynamically based on price
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
        if (_selectedCrypto.value?.id.equals(cryptoId, ignoreCase = true)) {
            _selectedCrypto.value = _selectedCrypto.value?.let { it.copy(isBookmarked = !it.isBookmarked) }
        }
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
}
