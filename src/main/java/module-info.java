module aa {
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires transitive javafx.media;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.apache.commons.io;

    exports aa.view;
    exports aa.view.animations;
    exports aa.model.gameobjects;
    opens aa.view to javafx.fxml;
    opens aa.model to com.google.gson, javafx.base;
}
