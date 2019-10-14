pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven {
            setUrl("https://oss.sonatype.org/content/repositories/snapshots")
        }
    }
}


rootProject.name = "Restructured-Tables-New"

include(":android-v5")
include(":api")
include(":app")
include(":exec")