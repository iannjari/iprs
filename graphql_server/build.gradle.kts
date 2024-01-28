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
    // proto
    implementation (project(":protobuf"))
    // common-utils
    implementation(project(":common_utils"))
}