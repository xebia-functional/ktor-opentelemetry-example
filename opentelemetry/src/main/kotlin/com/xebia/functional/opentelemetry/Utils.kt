package com.xebia.functional.opentelemetry

import io.github.oshai.kotlinlogging.KLogger
import io.opentelemetry.api.trace.Span
import io.opentelemetry.context.Context

fun readCurrentSpan(tag: String, logger: KLogger): Span? {
  val context = Context.current()
  val currentSpan = Span.fromContext(context)
  if (currentSpan != null && currentSpan.spanContext.isValid) {
    val spanContext = currentSpan.spanContext
    logger.info {
      """$tag
          |Trace Id: ${spanContext.traceId}
          |Span Id: ${spanContext.spanId}
          |Trace flags: ${spanContext.traceFlags.asHex()}
        """
        .trimMargin()
    }
  } else logger.info { "No valid context found!" }
  return currentSpan
}
