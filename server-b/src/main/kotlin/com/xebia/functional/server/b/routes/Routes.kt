package com.xebia.functional.server.b.routes

import com.xebia.functional.opentelemetry.readCurrentSpan
import io.github.oshai.kotlinlogging.KLogger
import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.routes(logger: KLogger) {
  get("/hello") {
    logger.info {
      "Headers: \n${(call.request.headers.entries().joinToString("\n") { "  * ${it.key}: ${it.value.joinToString(", ")}" })}'"
    }

    readCurrentSpan("Server B", logger)

    call.response.headers.append("CUSTOM_HEADER", System.currentTimeMillis().toString() + "ms")
    call.respondText("Server B")
  }
}
