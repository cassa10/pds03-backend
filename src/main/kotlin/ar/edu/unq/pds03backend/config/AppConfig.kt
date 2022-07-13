package ar.edu.unq.pds03backend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.concurrent.Executor
import springfox.documentation.service.ApiKey
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.service.SecurityReference
import springfox.documentation.service.AuthorizationScope
import com.google.common.collect.Lists

@Configuration
@EnableSwagger2
@EnableAsync
class AppConfig(
    @Value("\${task_executor.core_pool_size}")
    private val corePoolSize:Int,
    @Value("\${task_executor.max_pool_size}")
    private val maxPoolSize:Int,
    @Value("\${task_executor.queue_capacity}")
    private val queueCapacity:Int,
) {

    @Bean("threadPoolTaskExecutor")
    fun taskExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = corePoolSize
        executor.maxPoolSize = maxPoolSize
        executor.setQueueCapacity(queueCapacity)
        executor.initialize()
        return executor
    }

    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
        .securityContexts(Lists.newArrayList(securityContext()))
        .securitySchemes(Lists.newArrayList(apiKey()))
        .select()
        .apis(RequestHandlerSelectors.basePackage("ar.edu.unq.pds03backend.api"))
        .paths(PathSelectors.any())
        .build()


    private fun apiKey(): ApiKey {
        return ApiKey("JWT","Authorization", "header")
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build()
    }

    fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls(1)
        authorizationScopes[0] = authorizationScope
        return Lists.newArrayList(
                SecurityReference("JWT", authorizationScopes))
    }

    @Bean
    fun forwardToIndex(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addViewControllers(registry: ViewControllerRegistry) {
                registry.addViewController("/").setViewName("redirect:/swagger-ui.html")
            }
        }
    }

}