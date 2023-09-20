plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

repositories {
    mavenCentral()
}

dependencies {

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-autoconfigure
    implementation("org.springframework.boot:spring-boot-autoconfigure:3.1.2")

}
