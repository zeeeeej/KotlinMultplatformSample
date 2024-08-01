package com.yunext.kotlin.kmp.sample.routes

import com.yunext.kotlin.kmp.sample.modules.MyDeviceStorage
import com.yunext.kotlin.kmp.sample.modules.deviceStorage
import com.yunext.kotlin.kmp.sample.modules.userStorage
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import yunext.kotlin.domain.Device

fun Route.deviceListRoute() {
    get("/device") {
        if (deviceStorage.isNotEmpty()) {
            call.respond(deviceStorage)
        } else {
            call.respondText(status = HttpStatusCode.InternalServerError) { "没有设备" }
        }
    }
}

fun Route.deviceListByUserRoute() {
    get("/device/{username?}") {
        val username = call.parameters["username"]
        val list = deviceStorage.filter {
            username == null || it.owner == username
        }
        if (list.isNotEmpty()) {
            call.respond(list)
        } else {
            call.respondText(status = HttpStatusCode.InternalServerError) { "没有设备" }
        }
    }
}

fun Route.addDeviceRoute() {
    post("/device/add") {
        val device = call.receive<Device>()
        val owner = device.owner
        if (MyDeviceStorage.any { it.deviceId == device.deviceId }) {
            return@post call.respondText(status = HttpStatusCode.BadRequest) { "重复的deviceId:$device" }
        }
        if (owner == null || userStorage.any { it.username == owner }) {
            MyDeviceStorage.add(device)
            call.respondText(status = HttpStatusCode.Accepted) { "添加成功！"}
        } else {
            call.respondText(status = HttpStatusCode.InternalServerError) { "添加失败！" }
        }
    }
}

fun Route.deleteDeviceRoute() {
    post("/device/delete/{deviceId?}") {
        val deviceId = call.parameters["deviceId"]
            ?: return@post call.respondText(status = HttpStatusCode.BadRequest) { "输入设备id" }
        if (MyDeviceStorage.removeIf {
                it.deviceId == deviceId
            }) {
            call.respondText(status = HttpStatusCode.Accepted) { "删除成功！" }
        }else{
            call.respondText(status = HttpStatusCode.Accepted) { "设备不存在！" }
        }
    }
}

