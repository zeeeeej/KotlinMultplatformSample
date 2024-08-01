package com.yunext.kotlin.kmp.sample.plugins

import com.yunext.kotlin.kmp.sample.routes.addDeviceRoute
import com.yunext.kotlin.kmp.sample.routes.deleteDeviceRoute
import com.yunext.kotlin.kmp.sample.routes.deviceListByUserRoute
import com.yunext.kotlin.kmp.sample.routes.deviceListRoute
import com.yunext.kotlin.kmp.sample.routes.userRouting
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting(){
    routing {
        userRouting()
        deviceListRoute()
        deviceListByUserRoute()
        addDeviceRoute()
        deleteDeviceRoute()
    }
}