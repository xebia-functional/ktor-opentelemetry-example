package com.xebia.functional.client

import arrow.continuations.SuspendApp
import arrow.fx.coroutines.resourceScope
import com.xebia.functional.server.a.OpenTelemetryConfig
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.opentelemetry.instrumentation.ktor.v2_0.client.KtorClientTracing
import io.github.oshai.kotlinlogging.KotlinLogging

object Client {
    @JvmStatic
    fun main(args: Array<String>) = SuspendApp {
        resourceScope {
            val logger = KotlinLogging.logger {}
            val openTelemetry = OpenTelemetryConfig.config("client").newInstance()
            val ktorClient =
                HttpClient(CIO) {
                    engine {
                        requestTimeout = 0 // disabled
                    }
                    install(Logging) { level = LogLevel.INFO }
                    install(ContentNegotiation)
                    install(KtorClientTracing) { setOpenTelemetry(openTelemetry) }
                }
            val response = ktorClient.get("http://0.0.0.0:8081/hello")
            val body = response.bodyAsText()
            logger.info { "Got response from server: '$body'" }
        }
    }
}