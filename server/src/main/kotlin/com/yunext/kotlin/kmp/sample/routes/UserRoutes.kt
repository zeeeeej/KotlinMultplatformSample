package com.yunext.kotlin.kmp.sample.routes

import com.yunext.kotlin.kmp.sample.domain.fail
import com.yunext.kotlin.kmp.sample.domain.success
import com.yunext.kotlin.kmp.sample.modules.MyDeviceStorage
import com.yunext.kotlin.kmp.sample.modules.MyUserStorage
import com.yunext.kotlin.kmp.sample.modules.userStorage
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import yunext.kotlin.domain.User

fun Route.userRouting() {
    route("/user") {

        get {
            if (userStorage.isNotEmpty()) {
                call.success {
                    userStorage to "获取成功！"
                }
            } else {
                call.fail {
                    "没有用户！"
                }
            }
        }

        get("{username?}") {
//            val username = call.parameters["username"] ?: return@get call.fail {
//                "请输入username！"
//            }
            val username = call.parameters["username"] ?: return@get call.respond(
                HttpStatusCode.Unauthorized,
                "没有username参数"
            )

            val user = userStorage.singleOrNull {
                it.username == username
            } ?: return@get call.fail {
                "没有此用户:$username"
            }

            call.success {
                user to "获取成功！"
            }
        }

        post {
            val user = call.receive<User>()
            val has = userStorage.any {
                user.username == it.username
            }
            if (has) {
                call.fail {
                    "已经存在相同的username:${user.username}"
                }
            } else {
                MyUserStorage.add(user)
                call.success {
                    true to "注册成功"
                }
            }
        }

        delete("{username?}") {
            val username = call.parameters["username"]
                ?: return@delete call.fail { "不存在username" }

            if (MyUserStorage.removeIf { it.username == username }) {
                val deviceList = MyDeviceStorage.map {
                    if (it.owner == username) it.copy(owner = null) else it
                }
                MyDeviceStorage.clear()
                MyDeviceStorage.addAll(deviceList)
                call.success {
                    true to "删除成功！"
                }
            } else {
                call.success {
                    true to "删除失败！"
                }
            }
        }
    }
}

val helloWorld = object {
    val a:String
        get() = ""
    val hello = "Hello"
    val world = "World"
    // object expressions extend Any, so `override` is required on `toString()`
    override fun toString() = "$hello $world"
}

class C {
    // 去掉private 下面getObject().x报错：Unresolved reference: x
    private fun getObject() = object {
        val x: String = "x"
    }

    fun printX() {
        println(getObject().x)
    }
}

