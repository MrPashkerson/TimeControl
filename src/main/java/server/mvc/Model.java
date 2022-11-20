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
    private int port = 0;
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
        try (ServerSocket server = new ServerSocket(this.port);) {
            server.setReuseAddress(true);

            Thread clientHandler = null;

            ServerShutDownHandler serverShutDownHandler = new ServerShutDownHandler(server);

            do {
                this.client = server.accept();
                clientHandler = new Thread(new Model.ClientHandler(this.client));
                clientHandler.start();
            } while (!isShutDown());
        } catch (IOException e) {
            System.out.println("Server socket was closed.");
        }
    }

    class ClientHandler implements Runnable {
        private Socket client;
        ClientHandler(Socket client) {
            this.client = client;
        }
        @Override
        public void run() {
            PrintWriter out = null;
            BufferedReader in = null;
            boolean flag = true;
            try {
                notifyListeners("Connect" + "&" + ": {ip: " + this.client.getInetAddress().getHostAddress() + "; порт: " + this.client.getPort() + "};");
                while (flag && !isShutDown()) {
                    out = new PrintWriter(this.client.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

                    String line = in.readLine();
                    System.out.printf(" Sent from the client: %s\n", line); // for tests, delete when release
                    //sqlQuery mysqlQuery = new sqlQuery();
                    switch (line) {
                        case "Disconnect":
                            try {
                                out.close();
                                in.close();
                                this.client.close();
                                notifyListeners("Disconnect" + "&" + ": {ip: " + this.client.getInetAddress().getHostAddress() + "; порт: " + this.client.getPort() + "};");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            flag = false;
                            break;
                        default:
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class ServerShutDownHandler implements Runnable {
        Thread thread;
        ServerSocket serverSocket;
        public ServerShutDownHandler(ServerSocket serverSocket)
        {
            this.serverSocket = serverSocket;
            this.thread = new Thread(this, "ServerShutDown thread");
            this.thread.start();
        }
        @Override
        public void run() {
            while (!isShutDown()) {
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    System.out.println("Caught:" + e);
                }
            }
            try {
                this.serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // геттеры
    boolean isShutDown() {
        return this.shutDown;
    }
}


