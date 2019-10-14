import org.javamodularity.moduleplugin.tasks.ModularJavaExec

plugins {
    java
    id("org.javamodularity.moduleplugin")
}

dependencies {
    implementation(project(":app"))
    implementation(fileTree(File(rootDir, "libs")))
}

tasks.register("run", ModularJavaExec::class.java) {
    group = "abc"
    main = "rt.exec/ca.warp7.rt.exec.ResTableABC"
    jvmArgs = listOf(
            "--add-exports=javafx.controls/com.sun.javafx.scene.control.behavior=org.controlsfx.controls",
            "--add-exports=javafx.base/com.sun.javafx.event=org.controlsfx.controls",
            // For accessing VirtualFlow field from the base class in GridViewSkin
            "--add-opens=javafx.controls/javafx.scene.control.skin=org.controlsfx.controls",
            // For accessing getChildren in ImplUtils
            "--add-opens=javafx.graphics/javafx.scene=org.controlsfx.controls"
    )
}