plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
    alias(libs.plugins.kotlinx.serialization)
}

group = "com.yunext.kotlin.kmp.sample"
version = "1.0.0"
application {
    mainClass.set("com.yunext.kotlin.kmp.sample.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.serialization.kotlinx.json.jvm)
    implementation(libs.ktor.server.content.negotiation.jvm)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
}