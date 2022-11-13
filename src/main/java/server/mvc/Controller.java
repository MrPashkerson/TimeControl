package server.mvc;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Pattern;

public class Controller extends View {
    @FXML
    public Button btnStart;
    @FXML
    public Button btnStop;
    @FXML
    public TextField fieldIP;
    @FXML
    public TextField fieldPort;

    @FXML
    protected void onStartButtonClick() throws InterruptedException {
        this.elListView.getItems().clear();
        this.model.setShutDown(false);

        this.btnStart.setDisable(true);
        this.btnStop.setDisable(false);
        main();
    }

    @FXML
    protected void onStopButtonClick() {
        this.btnStart.setDisable(false);
        this.btnStop.setDisable(true);
        this.model.setShutDown(true);
    }

    private void main() throws InterruptedException {
        this.fieldClientsConnected.setText(Integer.toString(Integer.parseInt(this.fieldClientsConnected.getText()) + 1));
        int port = 6568;
        if (isPort(this.fieldPort.getText())) {
            port = Integer.parseInt(this.fieldPort.getText());
        }
        this.model.setPort(port);
        this.model.main();
    }

    public boolean isPort(String strNum) {
        if (strNum == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("\\d\\d\\d\\d");
        return pattern.matcher(strNum).matches();
    }
}