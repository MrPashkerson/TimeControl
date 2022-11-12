package server.mvc;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Controller extends View {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        this.go();
    }

    private void go(){
        try (ServerSocket server = new ServerSocket(1024);) {
            server.setReuseAddress(true);

            while (true) {
                Socket client = server.accept();
                //System.out.println("New client connected " + client.getInetAddress().getHostAddress());
                //new Thread(new ClientHandler(client)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new Thread(this.model).start();
    }
}