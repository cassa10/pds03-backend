package ar.edu.unq.pds03backend.model

class Warning(
    val type: WarningType,
    val message: String
)

enum class WarningType {
    CRITICAL,
    MEDIUM,
    LOW
}

class QuoteRequestWarningSeeker(
    private val mapper: (QuoteRequest) -> Warning?
) {
    fun apply(quoteRequest: QuoteRequest): Warning? = mapper(quoteRequest)
}