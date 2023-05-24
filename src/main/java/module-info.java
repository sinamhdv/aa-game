module aa {
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.apache.commons.io;

    exports aa.view;
    opens aa.view.eventcontrollers to javafx.fxml;
    opens aa.model to com.google.gson;
}
