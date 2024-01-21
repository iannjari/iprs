plugins {
    `java-library`
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.google.cloud.tools.jib")
}

repositories {
    mavenCentral()
}

extra["netDevhVersion"] = "2.14.0.RELEASE"

dependencies {
    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")


    // MongoDB
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.data:spring-data-mongodb")



    // Spring Boot
    compileOnly("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")


    // proto
    api("com.google.protobuf:protobuf-java:3.21.6")
    api("com.google.protobuf:protobuf-java-util:3.21.12")
    // https://mvnrepository.com/artifact/com.google.protobuf/protobuf-gradle-plugin
    runtimeOnly("com.google.protobuf:protobuf-gradle-plugin:0.9.4")

    // netflix grpc spring boot starter
    implementation("net.devh:grpc-client-spring-boot-starter:${property("netDevhVersion")}")
    implementation("net.devh:grpc-server-spring-boot-starter:${property("netDevhVersion")}")
    implementation("net.devh:grpc-spring-boot-starter:${property("netDevhVersion")}")
}

jib {
//    from {
//        image = ""
//    }
    to.image = project.name
}