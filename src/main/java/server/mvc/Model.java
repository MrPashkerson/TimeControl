package server.mvc;

import server.mvc.observer.Listener;
import server.mvc.observer.Observer;

import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Model implements Observer, Runnable {
    private List<Listener> listeners;
    Thread thread;
    private Socket clientSocket = null;
    private boolean disconnectClient = false;

    Model() {
        this.thread = new Thread(this, "Client handler");
        this.thread.start();
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
    void setClientSocket(Socket socket) {
        this.clientSocket = socket;
    }

    // вычисления + notifyListeners("message");
    public void run() {

    }

    // геттеры

}
