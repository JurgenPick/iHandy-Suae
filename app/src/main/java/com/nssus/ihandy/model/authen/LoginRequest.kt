package com.nssus.ihandy.model.authen

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("employeeId") val employeeId: Int? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("deviceId") val deviceId: String? = null
)
