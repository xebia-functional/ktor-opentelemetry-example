package com.xebia.functional.server.a

import arrow.continuations.SuspendApp
import arrow.continuations.ktor.server
import arrow.fx.coroutines.resourceScope
import com.xebia.functional.server.a.routes.routes
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.Resources
import io.ktor.server.routing.*
import io.opentelemetry.instrumentation.ktor.v2_0.client.KtorClientTracing
import io.opentelemetry.instrumentation.ktor.v2_0.server.KtorServerTracing
import kotlinx.coroutines.awaitCancellation

object Server {
  @JvmStatic
  fun main(args: Array<String>) = SuspendApp {
    resourceScope {
      val logger = KotlinLogging.logger {}
      val openTelemetry = OpenTelemetryConfig.config("server-a").newInstance()

      val ktorClient =
        HttpClient(CIO) {
          engine {
            requestTimeout = 0 // disabled
          }
          install(Logging) { level = LogLevel.INFO }
          install(ClientContentNegotiation)
          install(KtorClientTracing) { setOpenTelemetry(openTelemetry) }
        }
      server(factory = Netty, port = 8081, host = "0.0.0.0") {
        install(ContentNegotiation) { json() }
        install(Resources)
        install(KtorServerTracing) { setOpenTelemetry(openTelemetry) }
        routing { routes(ktorClient, logger) }
      }
      awaitCancellation()
    }
  }
}
