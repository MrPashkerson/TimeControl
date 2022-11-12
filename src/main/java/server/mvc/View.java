package server.mvc;

import server.mvc.observer.Listener;

public class View implements Listener {
    Model model = new Model();

    View() {
        model.registerListener(this);
    }

    @Override
    public void notification(String message) {
        if(message.equals("")) {
            this.displaySomething1(model.getter1());
        } else if(message.equals("")) {
            this.displaySomething2(model.getter2());
        }
    }

    private void displaySomething1(String s) {

    }

    private void displaySomething2(String s) {

    }
}
