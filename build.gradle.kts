import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    java
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.hibernate.orm") version "6.4.4.Final"
}

val springBootVersion = "3.4.3"

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
    }
}

dependencies {
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//?
     implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
//
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")
//
    implementation("javazoom:jlayer:1.0.1")
//
    compileOnly("org.projectlombok:lombok:1.18.34") // Check for latest
    annotationProcessor("org.projectlombok:lombok:1.18.34")  // Check for latest
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.12.0")  // Используйте актуальную версию JUnit 5
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    // Mockito 5 (Совместимость с JUnit 5)
    testImplementation("org.mockito:mockito-core:5.11.0") // Используйте актуальную версию Mockito
    testImplementation("org.mockito:mockito-junit-jupiter:5.11.0")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<BootRun>("bootRun") {
    standardInput = System.`in`
}
tasks.test {
    useJUnitPlatform()
}