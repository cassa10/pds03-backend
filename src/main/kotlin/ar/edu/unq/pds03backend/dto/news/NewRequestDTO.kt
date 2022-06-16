package ar.edu.unq.pds03backend.dto.news

import javax.validation.constraints.NotBlank

class NewRequestDTO(
    @field:NotBlank val title: String,
    @field:NotBlank val imageSource: String,
    @field:NotBlank val imageAlt: String,
    @field:NotBlank val message: String,
)