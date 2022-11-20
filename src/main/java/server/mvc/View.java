package server.mvc;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import server.mvc.observer.Listener;

import java.net.SocketException;
import java.util.Objects;

public class View implements Listener {
    Model model = new Model();
    @FXML
    ListView<String> elListView = new ListView<String>();
    @FXML
    public TextField fieldClientsConnected;

    View() {
        model.registerListener(this);
    }

    @Override
    public void notification(String message) {
        String[] args = message.split("&");
        if (Objects.equals(args[0], "Connect")){
            Platform.runLater(() -> {
                this.fieldClientsConnected.setText(Integer.toString(Integer.parseInt(fieldClientsConnected.getText()) + 1));
                this.elListView.getItems().add("Подключился клиент"
                        + args[1]);
            });
        } else {
            Platform.runLater(() -> {
                this.fieldClientsConnected.setText(Integer.toString(Integer.parseInt(fieldClientsConnected.getText()) - 1));
                this.elListView.getItems().add("Отключился клиент"
                        + args[1]);
            });
        }
    }

}
