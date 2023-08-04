import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "3.2.0-M1"
  id("io.spring.dependency-management") version "1.1.2"
  kotlin("jvm") version "1.8.22"
  kotlin("plugin.spring") version "1.8.22"
}

group = "com.example"

version = "0.0.1-SNAPSHOT"

extra["spring-framework.version"] = "6.1.0-SNAPSHOT"

extra["spring-data-bom.version"] = "2023.1.0-SNAPSHOT"

java { sourceCompatibility = JavaVersion.VERSION_17 }

configurations { compileOnly { extendsFrom(configurations.annotationProcessor.get()) } }

repositories {
  mavenCentral()
  maven { url = uri("https://repo.spring.io/milestone") }
  maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-devtools")

  // persistence
  implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
  implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
  implementation("com.h2database:h2")
  runtimeOnly("io.r2dbc:r2dbc-h2")

  // Spring Modulith & JMolecules
  implementation("org.springframework.modulith:spring-modulith-starter-core")
  implementation("org.springframework.modulith:spring-modulith-starter-jdbc")
  runtimeOnly("org.springframework.modulith:spring-modulith-actuator")
  runtimeOnly("org.springframework.modulith:spring-modulith-observability")
  testRuntimeOnly("org.jmolecules:jmolecules-ddd:1.8.0")
  implementation("org.jmolecules:kmolecules-ddd:1.8.0")
  testImplementation("org.springframework.modulith:spring-modulith-starter-test")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("io.micrometer:micrometer-tracing-bridge-brave")
  implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("io.projectreactor:reactor-test")
  testImplementation("com.ninja-squad:springmockk:4.0.2")

  testImplementation("org.testcontainers:junit-jupiter")
  testImplementation("io.kotest:kotest-assertions-core:5.6.2")
}

dependencyManagement {
  imports { mavenBom("org.springframework.modulith:spring-modulith-bom:1.0.0-M1") }
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs += "-Xjsr305=strict"
    jvmTarget = "17"
  }
}

tasks.withType<Test> { useJUnitPlatform() }
