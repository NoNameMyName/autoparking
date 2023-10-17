module org.frontend.auto_ui {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    opens org.frontend;

    exports org.backend;
    opens org.backend to javafx.base;
}