plugins {
    `java-library`
    id("java")
    id("base-plugin")
    id("com.netflix.dgs.codegen")
}

group = "dev.njari"
version = "unspecified"
var dgsVersion = "7.3.2"

repositories {
    mavenCentral()
}


dependencyManagement {
    imports {
        mavenBom("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:${dgsVersion}")
    }
}

dependencies {
    // proto
    implementation (project(":protobuf"))
    // common-utils
    implementation(project(":common_utils"))

    implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:latest.release"))
    implementation("com.netflix.graphql.dgs:graphql-dgs-extended-scalars")
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-micrometer")
}