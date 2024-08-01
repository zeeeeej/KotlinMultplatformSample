package com.yunext.kotlin.kmp.sample.modules

import yunext.kotlin.domain.Device

//@Serializable
//data class Device(
//    val deviceId: String,
//    val deviceName: String,
//    val productKey: String,
//    val owner: String?,
//)

internal val MyDeviceStorage: MutableList<Device> = (1..5).map {
    Device(
        deviceId = "deviceId-$it",
        deviceName = "deviceName-$it",
        productKey = "productKey-$it",
        null
    )
}.toMutableList()

val deviceStorage: List<Device>
    get() = MyDeviceStorage