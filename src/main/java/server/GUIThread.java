package server;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIThread extends javafx.application.Application implements Runnable {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIThread.class.getResource("/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 381);
        stage.setTitle("TimeControl Server");
//        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent windowEvent) {
//                stage.close();
//                System.exit(0);
//            }
//        });
        stage.setScene(scene);
        stage.show();
    }

    public void run() { launch(); }
}
