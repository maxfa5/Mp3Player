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
    implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

    implementation("javazoom:jlayer:1.0.1")
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    implementation(platform("org.junit:junit-bom:5.10.0"))
    implementation("org.junit.jupiter:junit-jupiter:5.10.0")
    implementation("org.assertj:assertj-core:3.23.1")

}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}


tasks.named<BootRun>("bootRun") {

    standardInput = System.`in`
}