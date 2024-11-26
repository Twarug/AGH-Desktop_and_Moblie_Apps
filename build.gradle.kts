plugins {
    id("java")
    application
}

group = "dev.twardosz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass = "dev.twardosz.Main"
}


tasks {
    jar {
        sourceSets.main.get()
        manifest {
            attributes["Main-Class"] = "dev.twardosz.Main"
        }
    }

    test {
        useJUnitPlatform()
    }
}
