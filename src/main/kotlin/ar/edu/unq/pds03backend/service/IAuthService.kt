package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.LoginRequestDTO
import ar.edu.unq.pds03backend.dto.LoginResponseDTO

interface IAuthService {
    fun login(loginRequestDTO: LoginRequestDTO): LoginResponseDTO
}