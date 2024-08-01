package com.yunext.kotlin.kmp.sample.domain

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import kotlinx.serialization.json.JsonNull
import yunext.kotlin.domain.ApiResponse

internal suspend inline fun <reified T : Any> ApplicationCall.resp(
    status: HttpStatusCode,
    crossinline block: suspend () -> ApiResponse<T>,
) {
    resp(status, block())
}

internal suspend inline fun <reified T : Any> ApplicationCall.resp(
    status: HttpStatusCode,
    response: ApiResponse<T>,
) {
    respond(status = status, message = response)
}

internal suspend inline fun <reified T : Any> ApplicationCall.success(response: ApiResponse<T>) {
    resp(HttpStatusCode.OK, response)
}

internal suspend inline fun <reified T : Any> ApplicationCall.successSuspend(crossinline responseBlock: suspend () -> ApiResponse<T>) {
    resp(HttpStatusCode.OK, responseBlock())
}


internal suspend inline fun <reified T : Any> ApplicationCall.success(crossinline dataAndMessage: suspend () -> Pair<T, String>) {
    return successSuspend {
        val (data, msg) = dataAndMessage()
        ApiResponse(data, ApiResponse.SUCCESS, msg)
    }
}

internal suspend inline fun ApplicationCall.fail(crossinline message: suspend () -> String) {
    resp(HttpStatusCode.OK, ApiResponse(JsonNull, ApiResponse.FAIL, message()))
}





