plugins {
    idea
    application
}

group = "ru.goncharenko.examples"
version = "0.0.1"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    // grpc
    runtimeOnly("io.grpc:grpc-netty-shaded:1.68.1")
    implementation("io.grpc:grpc-protobuf:1.68.1")
    implementation("io.grpc:grpc-stub:1.68.1")
    compileOnly("org.apache.tomcat:annotations-api:6.0.53")
}

tasks.register<JavaExec>("runServer") {
    group = "application"
    description = "Run the example Server"
    mainClass.set("ru.goncharenko.examples.server.App")
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.register<JavaExec>("runClient") {
    group = "application"
    description = "Run the example Client"
    mainClass.set("ru.goncharenko.examples.client.App")
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.named("run").configure {
    enabled = false
    group = "other"
    description = "Run the example Client or Server instead"
}