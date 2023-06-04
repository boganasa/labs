import java.util.regex.Pattern.compile

plugins {
    id("java")
    id ("org.springframework.boot") version "3.0.5"
    id ("io.spring.dependency-management") version "1.0.11.RELEASE"
    id ("war")
}

group = "ru.bogachenko"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.netflix.zuul:zuul-processor:2.3.0")
    implementation("com.netflix.zuul:zuul-groovy:2.3.0")
    implementation(project(mapOf("path" to ":lab5:CatMicroservice")))
    implementation(project(mapOf("path" to ":lab5:OwnerMicroservice")))
    implementation(project(mapOf("path" to ":lab5:OwnerMicroservice")))
    implementation("org.springframework.amqp:spring-rabbit:3.0.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    implementation ("org.postgresql:postgresql:42.6.0")

    implementation ("org.springframework.boot:spring-boot-starter-web:3.0.4")
    testImplementation ("org.springframework.boot:spring-boot-starter-test:3.0.4")

    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-integration")
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.integration:spring-integration-http")
    implementation ("org.springframework.integration:spring-integration-jpa")
    developmentOnly ("org.springframework.boot:spring-boot-devtools")
    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
    testImplementation ("org.springframework.integration:spring-integration-test")


    compileOnly ("javax.servlet:javax.servlet-api:4.0.1")
    implementation ("javax.servlet:jstl:1.2")
    implementation ("org.webjars:bootstrap:5.2.0")
    testImplementation ("org.springframework:spring-test:5.2.22.RELEASE")
    testImplementation ("org.hamcrest:hamcrest-core:2.2")
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation(project(mapOf("path" to ":lab5:LibraryMicroservice")))
    testImplementation ("org.springframework.amqp:spring-rabbit-test")
    implementation ("org.springframework.boot:spring-boot-starter-amqp")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}