package com.nssus.ihandy.data.constant

object ValueConstant {

    // User Roles
    const val USER_ROLE_ADMIN = "SYSTEM DEPT"
    const val USER_ROLE_PACKING = "PACKING"
    const val USER_ROLE_SHIPPING = "SHIPPING YARD"
    const val USER_ROLE_PRODUCTION = "PRODUCTION"
    const val USER_ROLE_HOT_COIL = "LOGISTIC"
    const val USER_ROLE_TEST_LAB = "TECHNOLOGY & QUALITY CONTROL"

    // Network Timeout
    const val TIME_OUT = 120L

    // Network Request Header Key
    const val REQ_HEADER_KEY_CONTENT_TYPE = "Content-Type"
    const val REQ_HEADER_LANGUAGE = "Language"
    const val REQ_HEADER_AUTHORIZATION = "Authorization"
    const val REQ_HEADER_USER_AGENT = "User-Agent"

    // Network Request Header Value
    const val REQ_HEADER_VALUE_CONTENT_TYPE = "application/json; charset=UTF-8"

    //
    const val PREFIX_TOKEN = "Bearer"

    // Network Response Status Code
    const val STATUS_CODE_SUCCESS = "S"
    const val STATUS_CODE_FAILED = "F"
    const val STATUS_CODE_TOKEN_EXPIRED = "STATUS_CODE_TOKEN_EXPIRED"
    const val STATUS_CODE_NO_INTERNET = "STATUS_CODE_NO_INTERNET"
    const val STATUS_CODE_SOCKET_TIME_OUT = "STATUS_CODE_SOCKET_TIME_OUT"
    const val STATUS_CODE_SERVER_NOT_FOUND = "STATUS_CODE_SERVER_NOT_FOUND"
    const val STATUS_CODE_ERROR = "999"

//    Text Pattern
     const val PATTERN_NUMBER = "^[0-9]*$"

}