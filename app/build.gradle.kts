import com.android.build.api.variant.BuildConfigField
import com.android.ide.common.repository.GMAVEN_BASE_URL

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "com.nssus.ihandy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nssus.ihandy"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    androidComponents {
        onVariants { variant ->
            val flavorName = "${variant.flavorName}"

            variant.buildConfigFields.put("BASE_URL", BuildConfigField("String", "\"${rootProject.extra["${flavorName}_baseUrl"]}\"", "API URL for ${variant.name}"))

            // Mock-up URL (Not use)
            //variant.buildConfigFields.put("BASE_USER", BuildConfigField("String", "\"${rootProject.extra["${flavorName}_user"]}\"", "API URL for ${variant.name}"))


            // config root for authentication
            variant.buildConfigFields.put("BASE_AUTHEN", BuildConfigField("String", "\"${rootProject.extra["${flavorName}_authen"]}\"", "API URL for Authen Service"))

            // config root for common api
            variant.buildConfigFields.put("BASE_COMMON",BuildConfigField("String", "\"${rootProject.extra["${flavorName}_common"]}\"","API URL for Common Service"))
            // config root for shipping api
            variant.buildConfigFields.put("BASE_SHIPPING",BuildConfigField("String", "\"${rootProject.extra["${flavorName}_shipping"]}\"","API URL for Shipping Service"))
            // config root for yard entry (mockup only yard entry)
            //variant.buildConfigFields.put("BASE_YARD_ENTRY", BuildConfigField("String", "\"${rootProject.extra["${flavorName}_yardEntry"]}\"", "API URL for Yard Entry Service"))

            // config root for common service (variant build config for Common API)
//            variant.buildConfigFields.put("BASE_COMMON", BuildConfigField("String", "\"${rootProject.extra["${flavorName}_common"]}\"", "API URL for Common Service"))

            // config root for production service (variant build config for Production API)
            //variant.buildConfigFields.put("BASE_PRODUCTION", BuildConfigField("String", "\"${rootProject.extra["${flavorName}_production"]}\"", "API URL for Production Service"))

            // config root for hot coil service (variant build config for Hot coil API)
            //variant.buildConfigFields.put("BASE_HOT_COIL", BuildConfigField("String", "\"${rootProject.extra["${flavorName}_hotCoil"]}\"", "API URL for Hot coil Service"))

            // config root for shipping service (variant build config for Shipping API)
            //variant.buildConfigFields.put("BASE_SHIPPING", BuildConfigField("String", "\"${rootProject.extra["${flavorName}_shipping"]}\"", "API URL for Shipping Service"))

            // config root for test lab service (variant build config for Test lab API)
            //variant.buildConfigFields.put("BASE_TEST_LAB", BuildConfigField("String", "\"${rootProject.extra["${flavorName}_testLab"]}\"", "API URL for Test Lab Service"))

            // config root for packing service (variant build config for Packing API)
            //variant.buildConfigFields.put("BASE_PACKING", BuildConfigField("String", "\"${rootProject.extra["${flavorName}_packing"]}\"", "API URL for Packing Service"))
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
            isMinifyEnabled = true
            versionNameSuffix = "-debug"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += "default"

    productFlavors {
        create("${rootProject.extra["sitFlavorName"]}") {
            dimension = "default"
            versionCode = 1
            versionName = "1.0.0"
            applicationIdSuffix = ".sit"
            buildConfigField("String", "ENV", "\"SIT\"")
            resValue("string", "app_name", "iHandy Sit")
        }
//        create("${rootProject.extra["uatFlavorName"]}") {
//            dimension = "default"
//            versionCode = 1
//            versionName = "1.0.0"
//            applicationIdSuffix = ".uat"
//            buildConfigField("String", "ENV", "\"UAT\"")
//            resValue("string", "app_name", "iHandy Uat")
//        }
        create("${rootProject.extra["prodFlavorName"]}") {
            dimension = "default"
            versionCode = 1
            versionName = "2.0.0"
            buildConfigField("String", "ENV", "\"PRODUCTION\"")
            resValue("string", "app_name", "iHandy")
         }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    // Google Material
    implementation("androidx.compose.material3:material3:1.2.0-alpha02")
    implementation("com.google.android.material:material:1.12.0")

    // Unit testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra["lifecycleVersion"]}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${rootProject.extra["lifecycleVersion"]}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${rootProject.extra["lifecycleVersion"]}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${rootProject.extra["lifecycleVersion"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:${rootProject.extra["lifecycleVersion"]}")

    // Navigation
    implementation("androidx.navigation:navigation-compose:${rootProject.extra["navVersion"]}")

    // Dependency Injection
    implementation("io.insert-koin:koin-core:${rootProject.extra["koinVersion"]}")
    implementation("io.insert-koin:koin-android:${rootProject.extra["koinVersion"]}")
    implementation("io.insert-koin:koin-androidx-compose:${rootProject.extra["composeKoinVersion"]}")

    // Network
    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:${rootProject.extra["okHttpVersion"]}")
    implementation("com.squareup.okhttp3:logging-interceptor:${rootProject.extra["okHttpVersion"]}")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:${rootProject.extra["retrofitVersion"]}")
    implementation("com.squareup.retrofit2:converter-gson:${rootProject.extra["retrofitVersion"]}")

    // API Traffic Transaction in-app
    debugImplementation("com.github.chuckerteam.chucker:library:${rootProject.extra["chuckerVersion"]}")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:${rootProject.extra["chuckerVersion"]}")

    // Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Constraint Layout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // Commons Net
     implementation("commons-net:commons-net:3.9.0")

}