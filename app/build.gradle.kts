plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.map_chat_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.map_chat_app"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.annotation)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
<<<<<<< Updated upstream
=======
    implementation(libs.firebase.auth)
    implementation("com.hbb20:ccp:2.7.3")
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.database)
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))
>>>>>>> Stashed changes
    implementation(libs.play.services.maps)
    implementation(libs.play.services.ads)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
