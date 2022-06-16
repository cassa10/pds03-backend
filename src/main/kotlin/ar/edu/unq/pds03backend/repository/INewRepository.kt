package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.New
import org.springframework.data.jpa.repository.JpaRepository

interface INewRepository: JpaRepository<New, Long>