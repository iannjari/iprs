plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.0.4")
    implementation("com.google.cloud.tools:jib-gradle-plugin:3.3.1")
}