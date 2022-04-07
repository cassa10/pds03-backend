package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.service.HelloWorldService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/hello")
class HelloWorldController(@Autowired val helloWorldService: HelloWorldService) {

    @GetMapping
    fun hello(): String {
        return helloWorldService.hello()
    }
}
