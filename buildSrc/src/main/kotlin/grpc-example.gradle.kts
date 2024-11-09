import com.google.protobuf.gradle.id

plugins {
    idea
    application
    id("com.google.protobuf")
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

    // logging
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("ch.qos.logback:logback-core:1.2.3")
}

val app = mapOf(
    "Server" to "ru.goncharenko.examples.server.App",
    "Client" to "ru.goncharenko.examples.client.App",
)

app.forEach { (appName, mainClassName) ->
    tasks.register<CreateStartScripts>("create${appName}StartScripts") {
        mainClass.set(mainClassName)
        applicationName = appName
        outputDir = file("build/scripts$appName")
        classpath = files(tasks.named("jar")) + sourceSets["main"].runtimeClasspath
    }
    tasks.register<JavaExec>("run${appName}") {
        group = "application"
        description = "Run the $appName example"
        mainClass.set(mainClassName)
        classpath = sourceSets["main"].runtimeClasspath + project.files("$rootDir/buildSrc/src/main/resources")
    }
}

distributions {
    app.forEach { (appName, _) ->
        create(appName) {
            distributionBaseName.set(appName)
            contents {
                from(tasks.named("create${appName}StartScripts")) {
                    into("bin")
                }
                from(tasks.named("jar")) {
                    into("lib")
                }
                from(configurations.runtimeClasspath) {
                    into("lib")
                }
            }
        }
    }
}

tasks.named("assemble") {
    dependsOn(
        tasks.named("ServerDistTar"),
        tasks.named("ClientDistTar"),
    )
}

tasks.named("run").configure {
    enabled = false
    group = "other"
    description = "Run the example Client or Server instead"
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.5"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.68.1"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc") { }
            }
        }
    }
}