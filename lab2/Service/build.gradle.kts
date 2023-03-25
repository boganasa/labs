plugins {
    id("java")
}

group = "ru.bogachenko"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":Dao")))
    implementation(project(mapOf("path" to ":Dao")))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.test {
    useJUnitPlatform()
}