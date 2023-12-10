import org.gradle.kotlin.dsl.`java-library`

plugins {
	`java-library`
	id("com.google.protobuf") version("0.9.4")
}


repositories {
	mavenCentral()
}

dependencies {
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
