plugins {
    id("java")
}

group = "dev.arun"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    maxHeapSize = "4g"
    useJUnitPlatform()
}