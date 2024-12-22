package com.nssus.ihandy.model.authen

import com.google.gson.annotations.SerializedName
data class GetUserInfoRequest(
    @SerializedName("authPrefixToken") val authPrefixToken: String? = null,
    @SerializedName("accessToken") val accessToken: String? = null,
    @SerializedName("deviceId") val deviceId: String? = null
)
