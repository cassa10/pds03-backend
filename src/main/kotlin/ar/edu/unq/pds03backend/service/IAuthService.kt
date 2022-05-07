package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.authentication.LoginRequestDTO
import ar.edu.unq.pds03backend.dto.authentication.LoginResponseDTO

interface IAuthService {
    fun login(loginRequestDTO: LoginRequestDTO): LoginResponseDTO
}