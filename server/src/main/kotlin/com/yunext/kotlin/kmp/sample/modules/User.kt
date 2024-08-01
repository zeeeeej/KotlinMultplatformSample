package com.yunext.kotlin.kmp.sample.modules

import yunext.kotlin.domain.User

//@Serializable
//data class User(
//    val username: String,
//    val password: String,
//    val nickname: String,
//    val age: Int,
//)

internal val MyUserStorage: MutableList<User> = buildList {
    add(User("zeej", "123456", "zeej_kotlin", 18))
}.toMutableList()

val userStorage: List<User>
    get() = MyUserStorage