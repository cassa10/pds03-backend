package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.service.HelloWorldService
import org.springframework.stereotype.Service


@Service
class HelloWorldServiceImpl : HelloWorldService {
    override fun hello(): String = "Hello World :D"
}