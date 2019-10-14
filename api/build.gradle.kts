import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin(module = "jvm")
    id("org.openjfx.javafxplugin")
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

javafx {
    modules("javafx.controls", "javafx.fxml")
}

buildDir = File(rootProject.projectDir, "build/${project.name}")

dependencies {
    // Kotlin libraries
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    testImplementation(kotlin("test"))
    testImplementation(project)
}