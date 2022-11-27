module server {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires java.sql;

    exports server.mvc;
    opens server.mvc to javafx.fxml;

    exports server;
    opens server to javafx.fxml;
}