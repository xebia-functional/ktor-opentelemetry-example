plugins {
    id(libs.plugins.kotlin.jvm.get().pluginId)
    id(libs.plugins.kotlinx.serialization.get().pluginId)
    alias(libs.plugins.node.gradle)
    alias(libs.plugins.arrow.gradle.publish)
    alias(libs.plugins.semver.gradle)
    alias(libs.plugins.spotless)
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

dependencies {
    implementation(libs.klogging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.hocon)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.client)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.json)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.contentNegotiation)
    implementation(libs.ktor.server.resources)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.request.validation)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.logback)
    implementation(libs.suspendApp.core)
    implementation(libs.suspendApp.ktor)
    implementation(libs.uuid)
    implementation(libs.opentelemetry.api)
    implementation(libs.opentelemetry.exporter.logging)
    implementation(libs.opentelemetry.sdk)
    implementation(libs.opentelemetry.semconv)
    implementation(libs.opentelemetry.extension.kotlin)
    implementation(libs.opentelemetry.exporter.otlp)
    implementation(libs.opentelemetry.ktor)
    implementation(libs.opentelemetry.logback)
    implementation(projects.opentelemetry)

    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.kotest.property)
    testImplementation(libs.kotest.framework)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.kotest.testcontainers)
    testRuntimeOnly(libs.kotest.junit5)
}

spotless {
    kotlin {
        target("**/*.kt")
        ktfmt().googleStyle().configure {
            it.setRemoveUnusedImport(true)
        }
    }
}

task<JavaExec>("server") {
    dependsOn("compileKotlin")
    group = "Execution"
    description = "Server B application"
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("com.xebia.functional.server.b.Server")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<AbstractPublishToMaven> {
    dependsOn(tasks.withType<Sign>())
}
