import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

apply {
    plugin("io.spring.dependency-management")
}

plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-security")
}
