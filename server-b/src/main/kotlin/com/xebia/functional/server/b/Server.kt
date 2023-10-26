package com.xebia.functional.server.b

import arrow.continuations.SuspendApp
import arrow.continuations.ktor.server
import arrow.fx.coroutines.resourceScope
import com.xebia.functional.server.a.OpenTelemetryConfig
import com.xebia.functional.server.b.routes.routes
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.Resources
import io.ktor.server.routing.*
import io.opentelemetry.instrumentation.ktor.v2_0.server.KtorServerTracing
import kotlinx.coroutines.awaitCancellation
import org.slf4j.event.Level

object Server {
  @JvmStatic
  fun main(args: Array<String>) = SuspendApp {
    resourceScope {
      val logger = KotlinLogging.logger {}
      val openTelemetry = OpenTelemetryConfig.config("server-b").newInstance()
      server(factory = Netty, port = 8082, host = "0.0.0.0") {
        install(ContentNegotiation) { json() }
        install(Resources)
        install(CallLogging) { level = Level.DEBUG }
        install(KtorServerTracing) { setOpenTelemetry(openTelemetry) }
        routing { routes(logger) }
      }
      awaitCancellation()
    }
  }
}
