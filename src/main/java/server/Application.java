package server;

public class Application {
    public static void main(String[] args) {
        Thread guiLaunch = new Thread(new GUIThread());
        guiLaunch.start();
    }
}