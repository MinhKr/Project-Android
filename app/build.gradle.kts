
plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
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
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation("com.hbb20:ccp:2.5.1")
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.database)
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation(libs.play.services.maps)
    implementation("com.mapbox.maps:android:11.4.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.circleimageview)





//    implementation 'androidx.recyclerview:recyclerview:1.1.0'
//    implementation 'androidx.appcompat:appcompat:1.3.1'
//    implementation 'com.google.android.material:material:1.4.0'
//    implementation 'android.constraintlayout:constraintlayout:2.0.4'
//    testImplementation 'junit:junit:4.+'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
}