plugins {
    kotlin("jvm") version "1.8.10"
    id("io.ktor.plugin") version "2.2.3"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

kotlin {
    jvmToolchain(17) // or 11, or 8, etc.
}

application {
    mainClass.set("com.example.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:2.2.3")
    implementation("io.ktor:ktor-server-netty-jvm:2.2.3")
    implementation("io.ktor:ktor-server-host-common-jvm:2.2.3")
    implementation("io.ktor:ktor-server-compression-jvm:2.2.3")
    implementation("io.ktor:ktor-server-freemarker-jvm:2.2.3")
    // For server-side HTML rendering:
    implementation("io.ktor:ktor-server-html-builder-jvm:2.2.3")

    // HTTP client to call the OpenAI API
    implementation("io.ktor:ktor-client-core-jvm:2.2.3")
    implementation("io.ktor:ktor-client-okhttp-jvm:2.2.3")

    // JSON serialization for parsing OpenAI responses
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.2.3")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.2.3")
}
