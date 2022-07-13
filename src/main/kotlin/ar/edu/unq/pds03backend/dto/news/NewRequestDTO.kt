package ar.edu.unq.pds03backend.dto.news

import org.hibernate.validator.constraints.URL
import javax.validation.constraints.NotBlank

class NewRequestDTO(
    @field:NotBlank(message = "'title' must not be blank")
    val title: String,
    @field:NotBlank(message = "'imageSource' must not be blank")
    @field:URL(message = "invalid 'imageSource' url")
    val imageSource: String,
    @field:NotBlank(message = "'imageAlt' must not be blank")
    val imageAlt: String,
    @field:NotBlank(message = "'message' must not be blank")
    val message: String,
    @field:NotBlank(message = "'writer' must not be blank")
    val writer: String,
    @field:NotBlank(message = "'email' must not be blank")
    val email: String,
)