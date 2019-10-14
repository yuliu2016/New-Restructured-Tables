module rt.app {
    requires kotlin.stdlib;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires rt.android.vfive;
    requires org.controlsfx.controls;
    requires webcam.capture;
    requires klaxon;
    requires krangl;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.iconli.core;
    requires slf4j.simple;
    requires java.logging;
    requires java.desktop;
    requires commons.csv;
    requires commons.math3;
    requires org.apache.commons.io;
    requires java.xml;
    requires rt.api;

    opens ca.warp7.rt.core.app;
    opens ca.warp7.rt.ext.ast;
    opens ca.warp7.rt.ext.formulas;
    opens ca.warp7.rt.ext.scanner;
    opens ca.warp7.rt.ext.sheet;
    opens ca.warp7.rt.ext.views;

    exports ca.warp7.rt.core;
}