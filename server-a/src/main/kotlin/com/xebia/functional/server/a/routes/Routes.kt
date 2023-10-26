package com.xebia.functional.server.a.routes

import io.github.oshai.kotlinlogging.KLogger
import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.routes(client: HttpClient, logger: KLogger) {
  get("/hello") {
    logger.info { "Trace header: ' ${(call.request.headers.getAll("traceparent")?.joinToString(",") ?: "")}'" }
    call.respondText("Hello World!")
  }
}
