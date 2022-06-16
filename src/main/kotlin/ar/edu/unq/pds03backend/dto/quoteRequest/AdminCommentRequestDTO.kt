package ar.edu.unq.pds03backend.dto.quoteRequest

import javax.validation.constraints.NotNull

data class AdminCommentRequestDTO(
    @field:NotNull(message = "'comment' must not be null") val comment: String
)