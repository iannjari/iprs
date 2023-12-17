import com.google.protobuf.gradle.id
import org.gradle.kotlin.dsl.`java-library`

plugins {
	`java-library`
	id("com.google.protobuf") version("0.9.4")
}


repositories {
	mavenCentral()
	gradlePluginPortal()
}

dependencies {
	api("com.google.protobuf:protobuf-java:3.21.7")
	// https://mvnrepository.com/artifact/com.google.protobuf/protobuf-gradle-plugin
	runtimeOnly("com.google.protobuf:protobuf-gradle-plugin:0.9.4")
	compileOnly("io.grpc:grpc-all:1.49.1")
	compileOnly("com.google.protobuf:protobuf-gradle-plugin:0.8.0")
}

sourceSets {
	main {
		proto.srcDir("proto")
		java.srcDir("$projectDir/proto/java/")
	}
}

protobuf {
	protoc { artifact = "com.google.protobuf:protoc:3.21.6" }
	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:1.15.1"
		}
	}
	generateProtoTasks {
		all().forEach {
			it.builtins {
				// Generate the classes for Java or Kotlin (choose one)
				java {

				}
			}

			it.plugins {
				id("grpc") { }
			}

		}
	}
}

tasks.register<JavaCompile>("compileProtoJava") {
	classpath = configurations["compileClasspath"]
}

tasks.register<Jar>("generateProtoJar") {
	dependsOn("compileProtoJava")
	from(sourceSets["main"].output)
	archiveFileName.set("proto-generated.jar")
	destinationDirectory.set(file("$buildDir/libs"))
}


tasks.getByName("compileJava") {
	dependsOn("generateProto")
}
