import org.gradle.kotlin.dsl.`java-library`

plugins {
	`java-library`
	id("com.google.protobuf") version("0.9.4")
}


repositories {
	mavenCentral()
}

dependencies {
	api("com.google.protobuf:protobuf-java:3.21.6")
	// https://mvnrepository.com/artifact/com.google.protobuf/protobuf-gradle-plugin
	runtimeOnly("com.google.protobuf:protobuf-gradle-plugin:0.9.4")
	implementation("io.grpc:grpc-all:1.49.1")
}

sourceSets {
	main {
		proto.srcDir("proto")
		java.srcDir("$projectDir/proto/java/")
		}
	}

protobuf {
	protoc { artifact = "com.google.protobuf:protoc:3.21.6" }
	generateProtoTasks {
		all().forEach {
			it.builtins {
				// Generate the classes for Java or Kotlin (choose one)
				java {
				}
			}

		}
	}
}



tasks.getByName("compileJava") {
	dependsOn("generateProto")
}
