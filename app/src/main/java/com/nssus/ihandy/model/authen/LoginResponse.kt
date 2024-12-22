package com.nssus.ihandy.model.authen

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("accessToken") val accessToken: String? = null,
    @SerializedName("refreshToken") val refreshToken: String? = null,
    @SerializedName("session") val session: String? = null,
    @SerializedName("prefix") val prefix: String? = null
)
