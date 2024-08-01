package com.yunext.kotlin.kmp.sample

import Greeting
import SERVER_PORT
import com.yunext.kotlin.kmp.sample.plugins.configureRouting
import com.yunext.kotlin.kmp.sample.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args:Array<String>) {
    embeddedServer(Netty, port = SERVER_PORT, host = "127.0.0.1", module = Application::module)
        .start(wait = true)

    // io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module(){
    configureRouting()
    configureSerialization()
}

fun Application.moduleDemo() {
    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}!!!")
        }

        post {

        }
    }
}