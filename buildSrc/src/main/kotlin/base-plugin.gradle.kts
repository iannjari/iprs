plugins {
    `java-library`
    jacoco
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.google.cloud.tools.jib")
    id("org.sonarqube")
}

repositories {
    mavenCentral()
}

extra["netDevhVersion"] = "2.14.0.RELEASE"
var jacocoToolVersion = "0.8.11"

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
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // micrometer, prometheus
    implementation("io.micrometer:micrometer-registry-prometheus")



    // proto
    api("com.google.protobuf:protobuf-java:3.21.6")
    api("com.google.protobuf:protobuf-java-util:3.21.12")
    // https://mvnrepository.com/artifact/com.google.protobuf/protobuf-gradle-plugin
    runtimeOnly("com.google.protobuf:protobuf-gradle-plugin:0.9.4")

    // netflix grpc spring boot starter
    implementation("net.devh:grpc-client-spring-boot-starter:${property("netDevhVersion")}")
    implementation("net.devh:grpc-server-spring-boot-starter:${property("netDevhVersion")}")
    implementation("net.devh:grpc-spring-boot-starter:${property("netDevhVersion")}")

    // kafka
    implementation("org.springframework.kafka:spring-kafka")
//    implementation("io.confluent:kafka-protobuf-serializer")

    // mapstruct
    implementation("org.mapstruct:mapstruct:1.5.5.Final")

    // testing
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testCompileOnly("org.junit.jupiter:junit-jupiter-api")
    testCompileOnly("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

}

jib {
//    from {
//        image = ""
//    }
    to.image = project.name
}

jacoco {
    toolVersion = jacocoToolVersion
    reportsDirectory.set(layout.buildDirectory.dir("reports/jacoco"))
}

task<TestReport>("testReport") {
    destinationDir = file("$buildDir/reports/tests")
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
    dependsOn(tasks.test)
}

tasks.sonarqube {
    dependsOn(tasks.jacocoTestReport)
}

tasks.test {
    useJUnitPlatform()
}
