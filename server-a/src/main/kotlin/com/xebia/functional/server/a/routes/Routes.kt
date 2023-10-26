package com.xebia.functional.server.a.routes

import io.github.oshai.kotlinlogging.KLogger
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.routes(client: HttpClient, logger: KLogger) {
  get("/hello") {
    logger.info {
      "Headers: ' ${(call.request.headers.entries().joinToString("\n") { "  * ${it.key}: ${it.value.joinToString(", ")}" })}'"
    }
    val response = client.get("http://0.0.0.0:8082/hello")
    val body = response.bodyAsText()
    call.respondText("Server A + $body")
  }
}
