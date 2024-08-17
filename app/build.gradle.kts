plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.fpoly.shoes_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fpoly.shoes_app"
        minSdk = 24
        targetSdk = 33
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
//quy
    //service location
    //imagePicaso
    implementation ("com.squareup.picasso:picasso:2.71828")

    //coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    //map
    implementation ("com.google.maps:google-maps-services:2.2.0")
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    //stdlib
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:2.0.0")
    //toast
    implementation ("io.github.muddz:styleabletoast:2.4.0")
    //codeHistory
    implementation ("com.google.zxing:core:3.4.1")
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")

    //end quy


    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
    implementation("androidx.fragment:fragment-ktx:1.8.2")
    implementation("com.google.android.libraries.places:places:3.5.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Hilt
    val hiltVersion = "2.51.1"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    // Dots Indicator
    implementation("com.tbuonomo:dotsindicator:5.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Coil
    implementation("io.coil-kt:coil:2.6.0")

    // Retrofit HTTP
    val retrofit = "2.11.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit")
    implementation("com.google.code.gson:gson:$retrofit")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}