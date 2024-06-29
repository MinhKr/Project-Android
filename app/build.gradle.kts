

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
//    implementation fileTree(dir: "libs",include: ["*.jar"])
//    implementation(libs.appcompat)
//    implementation(libs.material)
//    implementation(libs.activity)
//    implementation(libs.constraintlayout)
//    implementation(libs.annotation)
//    implementation(libs.lifecycle.livedata.ktx)
//    implementation(libs.lifecycle.viewmodel.ktx)
//    implementation(libs.firebase.auth)
//    implementation(libs.firebase.database)
//    implementation("com.hbb20:ccp:2.5.1")
//    implementation(libs.firebase.firestore)
//    implementation(libs.firebase.database)
//    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
//    implementation(platform("com.google.firebase:firebase-database:23.1.0"))
//    implementation(platform("com.google.firebase:firebase-auth:23.1.0"))
//    implementation(platform("com.google.firebase:firebase-core:17.3.0"))
//    implementation(platform("com.google.firebase:firebase-storage:19.1.1"))
//    implementation(platform("com.firebaseui:firebase-ui-database:8.0.1"))
//    implementation(libs.play.services.maps)
//    implementation("com.mapbox.maps:android:11.4.1")
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.ext.junit)
//    androidTestImplementation(libs.espresso.core)
//    implementation(libs.circleimageview)
//    implementation 'com.firebaseui:firebase-ui-database:8.0.2';


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
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))
    implementation(libs.play.services.maps)
    implementation("com.mapbox.maps:android:11.4.1")
    implementation("com.firebaseui:firebase-ui-firestore:8.0.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.circleimageview)
    implementation("com.google.firebase:firebase-database:20.0.5")
    apply(plugin = "com.google.gms.google-services")
    implementation("com.firebaseui:firebase-ui-firestore:8.0.0")
    implementation("com.google.maps.android:android-maps-utils:0.5+")

}
