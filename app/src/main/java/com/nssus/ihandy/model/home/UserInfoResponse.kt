package com.nssus.ihandy.model.home

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("message") val message: String? = null,
    @SerializedName("result") val result: String? = null,
    @SerializedName("id") val id: String? = null
)