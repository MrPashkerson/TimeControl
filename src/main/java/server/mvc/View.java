package server.mvc;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import server.mvc.observer.Listener;

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
        this.elListView.getItems().add("Подключился клиент #" + this.fieldClientsConnected.getText()
                + message);
    }

}
