plugins {
    id("java")
    id("base-plugin")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation (project(":protobuf"))

}
