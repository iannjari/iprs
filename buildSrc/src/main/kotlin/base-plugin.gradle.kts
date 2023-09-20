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
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security
    implementation("org.springframework.boot:spring-boot-starter-security")

}
