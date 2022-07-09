package ar.edu.unq.pds03backend.model

data class QuoteRequestWithAdditionalInfo(
    val quoteRequest: QuoteRequest,
    val warnings: List<Warning>,
    val availableQuotes: Int,
    val requestedQuotes: Int,
)