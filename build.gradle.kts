import org.gradle.kotlin.dsl.implementation
import org.gradle.kotlin.dsl.testImplementation

plugins {
    id("java")
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "dev.twardosz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.3")
    implementation("io.hypersistence:hypersistence-utils-hibernate-63:3.7.1")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.impossibl.pgjdbc-ng:pgjdbc-ng:0.8.9") // lub inny sterownik

    implementation("me.paulschwarz:spring-dotenv:4.0.0")

    // ===  Tests  ===
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
