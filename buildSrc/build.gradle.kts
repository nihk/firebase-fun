plugins {
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.0.0")
    implementation(kotlin("gradle-plugin", "1.5.10"))
}
