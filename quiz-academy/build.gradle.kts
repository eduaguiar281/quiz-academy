import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.noarg.gradle.NoArgExtension //Adicionado NoArgs

plugins {
	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"

	id("org.jetbrains.kotlin.plugin.noarg") version "1.7.22" //Adicionado NoArgs
	id("org.jetbrains.kotlin.plugin.jpa") version "1.7.22" //Adicionado NoArgs

	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
}
//Adicionado NoArgs
noArg {
	invokeInitializers = true
}

group = "io.arcotech"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-amqp")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.h2database:h2")
	implementation("org.flywaydb:flyway-core")
	implementation("org.postgresql:postgresql:42.5.4")

	implementation("software.amazon.awssdk:appconfigdata:2.20.124")
	implementation("com.github.ben-manes.caffeine:caffeine:3.1.5")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.testcontainers:testcontainers:1.18.3")
	testImplementation("org.testcontainers:postgresql:1.18.3")
	testImplementation("org.testcontainers:rabbitmq:1.18.3")
	testImplementation("org.testcontainers:junit-jupiter:1.18.3")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
