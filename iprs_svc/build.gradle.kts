plugins {
    id("java")
    id("base-plugin")
}

repositories {
    mavenCentral()
}

dependencies {
    // proto
    implementation (project(":protobuf"))
    // common-utils
    implementation(project(":common_utils"))
}

sonarqube {
    properties {
        property("sonar.projectName", "IPRS Service")
        property("sonar.projectKey", "iprs")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.organization", "iannjari")
//        property("sonar.token", "")
    }
}
