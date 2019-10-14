plugins {
    id("org.javamodularity.moduleplugin") version "1.6.0" apply false
    id("org.openjfx.javafxplugin") version "0.0.9-SNAPSHOT" apply false
    kotlin("jvm") version "1.3.50" apply false
}

subprojects {
    repositories {
        mavenCentral()
        jcenter()
        mavenLocal()
    }
}