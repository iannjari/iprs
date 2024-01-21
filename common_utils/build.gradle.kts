plugins {
    id("java")
    id("base-plugin")
}

group = "dev.njari"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.springframework.kafka:spring-kafka")
    compileOnly("io.confluent:kafka-protobuf-serializer")
}

tasks.test {
    useJUnitPlatform()
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}