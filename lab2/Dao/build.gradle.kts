plugins {
    id("java")
}

group = "ru.bogachenko"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    implementation ("org.hibernate:hibernate-core-jakarta:5.6.15.Final")
    implementation ("org.postgresql:postgresql:42.6.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.test {
    useJUnitPlatform()
}