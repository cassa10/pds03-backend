package ar.edu.unq.pds03backend.dto.quoteRequest

import javax.validation.constraints.NotNull

data class QuoteRequestRequestDTO(
    @field:NotNull val idStudent: Long,
    @field:NotNull val comment: String
)