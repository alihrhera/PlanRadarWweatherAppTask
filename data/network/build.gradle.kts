plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.devtools.ksp)
}

android {
    namespace = "hrhera.ali.network"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        externalNativeBuild {
            cmake {
                arguments("-DANDROID_STL=c++_shared")
                cppFlags += "-std=c++17"
                cppFlags("-fexceptions", "-frtti")

            }
        }
    }
    externalNativeBuild {
        cmake {
            path = file("CMakeLists.txt")
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "BASE_URL", "\"https://api.openweathermap.org/\"")

        }
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.openweathermap.org/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Retrofit
    api(libs.retrofit)
    api(libs.converter.gson)
    api(libs.retrofit2.kotlin.coroutines.adapter)
    api(libs.okhttp)
    api(libs.logging.interceptor)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}