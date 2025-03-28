import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude

plugins {
    java
    id("org.springframework.boot") version "3.4.3"   // Centralized version
    id("io.spring.dependency-management") version "1.1.7"
    id("org.hibernate.orm") version "6.4.4.Final"
}

val springBootVersion = "3.4.3" // Centralized version

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

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation(platform("org.junit:junit-bom:5.10.0")) {
//        exclude("org.junit.vintage", "junit-vintage-engine")
    }
    testImplementation("org.junit.jupiter:junit-jupiter")
}


tasks.test {
    useJUnitPlatform()
}