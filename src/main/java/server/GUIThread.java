package server;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class GUIThread extends javafx.application.Application implements Runnable {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIThread.class.getResource("/server/server.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 475, 321);
        stage.setTitle("TimeControl Server");
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                stage.close();
                System.exit(0);
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    public void run() { launch(); }
}
