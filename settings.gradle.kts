dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "opentelemetry-poc"

include("detekt-rules")
project(":detekt-rules").projectDir = file("detekt-rules")

include("opentelemetry")
project(":opentelemetry").projectDir = file("opentelemetry")

include("client")
project(":client").projectDir = file("client")

include("server-a")
project(":server-a").projectDir = file("server-a")
