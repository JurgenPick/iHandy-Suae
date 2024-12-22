package com.nssus.ihandy.model.authen

import com.google.gson.annotations.SerializedName

data class GetUserInfoResponse(
    @SerializedName("userName") val userName: String? = null,
    @SerializedName("department") val department: String? = null,

)
