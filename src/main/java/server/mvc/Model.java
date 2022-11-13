package server.mvc;

import server.mvc.observer.Listener;
import server.mvc.observer.Observer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Model implements Observer {
    private List<Listener> listeners;
    private boolean shutDown = false;
    private int port = 6568;
    private Socket client = null;

    Model() {
        this.listeners = new LinkedList<>();
    }

    // регистрация слушателя
    @Override
    public void registerListener(Listener listener) {
        this.listeners.add(listener);
    }

    // уведомление слушателей
    @Override
    public void notifyListeners(String message) {
        for (Listener listener : listeners) {
            listener.notification(message);
        }
    }

    // сеттеры
    void setShutDown(Boolean status) {
        this.shutDown = status;
    }

    void setPort(int port) {
        this.port = port;
    }

    // вычисления + notifyListeners("message");
    public void main() throws InterruptedException {
        try (ServerSocket server = new ServerSocket(port);) {
            server.setReuseAddress(true);

            Runnable clientHandler = () -> {
                PrintWriter out = null;
                BufferedReader in = null;
                do {
                    try {
                        client = server.accept();
                        notifyListeners(": {ip: " + client.getInetAddress().getHostAddress() + "; порт: " + client.getPort() + "};");
                        while (flag) {
                            out = new PrintWriter(client.getOutputStream(), true);
                            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

//                            sqlQuery mysqlQuery = new sqlQuery();
//                            String line = in.readLine();
                        }

//                        if (out != null) {
//                          out.close();
//                        }
//                        if (in != null) {
//                          in.close();
//                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } while (!isShutDown());
            };

            Thread handler = new Thread(clientHandler);
            handler.start();
            handler.join();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



    // геттеры
    boolean isShutDown() {
        return this.shutDown;
    }
}


