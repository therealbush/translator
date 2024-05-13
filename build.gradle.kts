plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.serialization") version "1.9.22"
    id("org.jetbrains.dokka") version "1.9.20"
}

group = "me.bush"
version = "1.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test", "1.9.24"))

    implementation(kotlin("stdlib-jdk8", "1.9.24"))

    api("io.ktor:ktor-client-core:2.3.11")
    api("io.ktor:ktor-client-cio:2.3.11")

    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks {
    test {
        testLogging.showStandardStreams = true
        useJUnitPlatform()
    }
    named<Jar>("javadocJar") {
        from(named("dokkaJavadoc"))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
