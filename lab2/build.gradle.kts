plugins {
    id("java")
}

group = "ru.bogachenko.lab2"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation(project(mapOf("path" to ":Dao")))
    testImplementation(project(mapOf("path" to ":Service")))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation ("org.hibernate:hibernate-core-jakarta:5.5.6.Final")
    implementation ("org.postgresql:postgresql:42.6.0")

}

tasks.test {
    useJUnitPlatform()
}