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
    implementation("com.netflix.graphql.dgs.codegen:graphql-dgs-codegen-gradle:5.6.7")
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:4.0.0.2929")
}