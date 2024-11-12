plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.serialization") version "2.0.21"
    id("org.jetbrains.dokka") version "1.9.20"
}

group = "me.bush"
version = "1.1.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test", "2.0.21"))

    implementation(kotlin("stdlib-jdk8", "2.0.21"))

    api("io.ktor:ktor-client-core:3.0.1")
    api("io.ktor:ktor-client-cio:3.0.1")

    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
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
