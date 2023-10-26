package com.xebia.functional.server.b.routes

import io.github.oshai.kotlinlogging.KLogger
import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.routes(logger: KLogger) {
  get("/hello") {
    logger.info {
      "Headers: ' ${(call.request.headers.entries().joinToString("\n") { "  * ${it.key}: ${it.value.joinToString(", ")}" })}'"
    }
    call.respondText("Server B")
  }
}
