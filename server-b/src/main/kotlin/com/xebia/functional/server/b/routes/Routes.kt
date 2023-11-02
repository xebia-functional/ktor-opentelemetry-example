package com.xebia.functional.server.b.routes

import com.xebia.functional.opentelemetry.readCurrentSpan
import io.github.oshai.kotlinlogging.KLogger
import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.trace.Span
import io.opentelemetry.context.Context

fun Routing.routes(logger: KLogger, provider: OpenTelemetry) {
  get("/hello") {
    logger.info {
      "Headers: \n${(call.request.headers.entries().joinToString("\n") { "  * ${it.key}: ${it.value.joinToString(", ")}" })}'"
    }

    readCurrentSpan("Server B", logger)

    val tracer = provider.getTracer("xef.ai")
    val span = tracer
      .spanBuilder("new-span")
      .setParent(Context.current())
      .startSpan()
    span.setAttribute("attribute_a", "value_a")
    span.setAttribute("attribute_b", "value_b")
    Thread.sleep(500)

    call.response.headers.append("CUSTOM_HEADER", System.currentTimeMillis().toString() + "ms")
    call.respondText("Server B")

    span.end()
  }
}
