@file:Suppress("SpellCheckingInspection")

import org.javamodularity.moduleplugin.tasks.ModularJavaExec
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin(module = "jvm")
    id("org.javamodularity.moduleplugin")
    id("org.openjfx.javafxplugin")
}

val versionName = "2019.2.0"

version = versionName

repositories {
    mavenCentral()
    mavenLocal()
    jcenter()
    maven("http://dl.bintray.com/kyonifer/maven")
}

javafx {
    modules("javafx.controls", "javafx.fxml", "javafx.swing")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

buildDir = File(rootProject.projectDir, "build/${project.name}")

dependencies {
    implementation(dependencyNotation = project(":api"))
    implementation(dependencyNotation = project(":android-v5"))
    implementation(fileTree(File(rootDir, "libs")))

    // Java libraries
    implementation(group = "commons-io", name = "commons-io", version = "2.6")
    implementation(group = "org.apache.commons", name = "commons-math3", version = "3.0")
    implementation(group = "com.github.sarxos", name = "webcam-capture", version = "0.3.12")
    implementation(group = "com.google.zxing", name = "core", version = "3.4.0")
    implementation(group = "com.google.zxing", name = "javase", version = "3.4.0")
    implementation(group = "org.kordamp.ikonli", name = "ikonli-javafx", version = "11.3.4")
    implementation(group = "org.kordamp.ikonli", name = "ikonli-fontawesome5-pack", version = "11.3.4")
    implementation(group = "org.slf4j", name = "slf4j-simple", version = "1.7.6")

    // Kotlin libraries
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation(group = "de.mpicbg.scicomp", name = "krangl", version = "0.10.3")
    implementation(group = "com.beust", name = "klaxon", version = "5.0.11")
    testImplementation(kotlin("test"))
}