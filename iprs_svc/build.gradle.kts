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
