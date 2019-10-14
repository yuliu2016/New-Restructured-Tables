import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("org.javamodularity.moduleplugin")
    kotlin( "jvm")
}

repositories {
    mavenCentral()
    jcenter()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

buildDir = File(rootProject.projectDir, "build/${project.name}")

dependencies {
    // Kotlin libraries
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    testImplementation(kotlin("test"))
    testImplementation(project)
}