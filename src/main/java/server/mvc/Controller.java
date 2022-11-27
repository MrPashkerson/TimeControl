package server.mvc;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
        elListView.getItems().clear();
        btnStart.setDisable(true);
        btnStop.setDisable(false);
        fieldPort.setDisable(true);
        model.setShutDown(false);
        new Thread(new ServerHandler()).start();
    }

    @FXML
    protected void onStopButtonClick() {
        btnStart.setDisable(false);
        btnStop.setDisable(true);
        fieldPort.setDisable(false);
        model.setShutDown(true);
    }

    class ServerHandler implements Runnable {
        @Override
        public void run() {
            int port = 6568;
            if (isPort(fieldPort.getText())) {
                port = Integer.parseInt(fieldPort.getText());
            } else {
                fieldPort.setText(Integer.toString(port));
            }
            model.setPort(port);
            try {
                model.main();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isPort(String strNum) {
        if (strNum == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("\\d\\d\\d\\d");
        return pattern.matcher(strNum).matches();
    }
}