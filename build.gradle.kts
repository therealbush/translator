import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.serialization") version "1.6.21"
    id("org.jetbrains.dokka") version "1.6.21"
}

group = "me.bush"
version = "1.0.0"

repositories.mavenCentral()

dependencies {
    testImplementation(kotlin("test"))

    implementation(kotlin("stdlib-jdk8", "1.6.21"))

    api("io.ktor:ktor-client-core:2.0.1")
    api("io.ktor:ktor-client-cio:2.0.1")

    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
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
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
    named<Jar>("javadocJar") {
        from(named("dokkaJavadoc"))
    }
}

publishing.publications.create<MavenPublication>("maven").from(components["java"])
