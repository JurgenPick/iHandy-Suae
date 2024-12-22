buildscript {
    // Extra Value
    val lifecycleVersion by extra("2.6.1")
    val navVersion by extra("2.6.0")
    val kotlinVersion by extra("1.7.20") // KOTLIN VERSION change(2 lines)
    val koinVersion by extra("3.4.0")
    val composeKoinVersion by extra("3.4.3")
    val okHttpVersion by extra("5.0.0-alpha.11")
    val retrofitVersion by extra("2.9.0")
    val chuckerVersion by extra("4.0.0")

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.gms:google-services:4.4.2")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) version "8.0.0" apply false
    id("com.android.library") version "8.0.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false // KOTLIN VERSION change(2 lines)
}

// All Variant API Path

// Endpoint Service Path
val userSv = "apiProxy/Announcement/"
val authenSv = "api/authen/"
val commonSv = "api/common/"
val shippingSv = "api/shipping/"

// SIT Env
val sitFlavorName = "sitFlavorName"
extra[sitFlavorName] = "iHandySit"
val sitBaseUrl = "${extra.get(sitFlavorName)}_baseUrl"
// extra[sitBaseUrl] = "https://67060c2c031fd46a8311c642.mockapi.io/" // comment this
// extra["${extra.get(sitFlavorName)}_user"] = "${extra.get(sitBaseUrl)}${userSv}" // comment this
// uncomment 3 lines
// Using config from app level
extra[sitBaseUrl] = "http://10.210.3.56:8000/"
extra["${extra.get(sitFlavorName)}_authen"] = "${extra.get(sitBaseUrl)}${authenSv}"
extra["${extra.get(sitFlavorName)}_common"] = "${extra.get(sitBaseUrl)}${commonSv}"
extra["${extra.get(sitFlavorName)}_shipping"] = "${extra.get(sitBaseUrl)}${shippingSv}"

// near future will use this command
//extra["${extra.get(sitFlavorName)}_yardEntry"] = "${extra.get(sitBaseUrl)}${yardEntrySv}"

// PROD Env // comment this if not use
val prodFlavorName = "prodFlavorName"
extra[prodFlavorName] = "iHandyProd"
val prodBaseUrl = "${extra.get(prodFlavorName)}_baseUrl"
//extra[prodBaseUrl] = "https://sit-ebusiness.cdc.ais.th/"
//extra["${extra.get(prodFlavorName)}_user"] = "${extra.get(prodBaseUrl)}${userSv}v1.0/"
//extra["${extra.get(prodFlavorName)}_yardEntry"] = "${extra.get(prodBaseUrl)}${yardEntrySv}v1.0/"
extra[prodBaseUrl] = "http://10.210.3.56:8000/"
extra["${extra.get(prodFlavorName)}_authen"] = "${extra.get(prodBaseUrl)}${authenSv}"
extra["${extra.get(prodFlavorName)}_common"] = "${extra.get(prodBaseUrl)}${commonSv}"
extra["${extra.get(prodFlavorName)}_shipping"] = "${extra.get(prodBaseUrl)}${shippingSv}"