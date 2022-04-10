package ar.edu.unq.pds03backend.service.logger

import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import java.util.UUID

private val logger = KotlinLogging.logger { }

@Aspect
@Component
class LogAspect {

    @Suppress("TooGenericExceptionCaught")
    @Around("@annotation(ar.edu.unq.pds03backend.service.logger.LogExecution)")
    fun logExecutionTime(joinPoint: ProceedingJoinPoint): Any {
        val reqId = randomUUID()
        val start = System.currentTimeMillis()
        val signature = joinPoint.signature.toShortString()
        val reqLog = """{"requestId": "$reqId"}"""
        val result = try {
            with(StringBuilder("$reqLog | start -> Executing $signature, ")) {
                appendParameters(joinPoint.args)
                logger.info(toString())
            }
            joinPoint.proceed()
        } catch (throwable: Throwable) {
            logger.error("$reqLog | *** Exception during executing $signature, ${throwable.javaClass} - message: ${throwable.message}")
            throw throwable
        }
        val duration = System.currentTimeMillis() - start
        logger.info("$reqLog | end -> Finished executing: $signature, returned: '$result', duration: $duration ms")
        return result
    }

    private fun StringBuilder.appendParameters(args: Array<Any>) {
        append("parameters: [")
        args.forEachIndexed { i, p ->
            append(p.javaClass.simpleName).append("(").append(p.toString()).append(")")
            if (i < args.size - 1) append(", ")
        }
        append("]")
    }

    private fun randomUUID() = UUID.randomUUID().toString()

}