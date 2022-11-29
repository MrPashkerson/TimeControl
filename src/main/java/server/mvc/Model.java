package server.mvc;

import server.mvc.observer.Listener;
import server.mvc.observer.Observer;
import server.sqlQuery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Model implements Observer {
    private List<Listener> listeners;
    private boolean shutDown = false;
    private int port = 0;

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

    // вычисления
    public void main() throws InterruptedException, SQLException {
        sqlQuery mysqlQuery = new sqlQuery();
        try (ServerSocket server = new ServerSocket(this.port)) {
            server.setReuseAddress(true);

            Thread clientHandler;

            ServerShutDownHandler serverShutDownHandler = new ServerShutDownHandler(server);
            do {
                Socket client = server.accept();
                clientHandler = new Thread(new Model.ClientHandler(client, mysqlQuery));
                clientHandler.start();
                clientHandler.join();
            } while (!isShutDown());
        } catch (IOException e) {
            System.out.println("Server socket was closed.");
            mysqlQuery.disconnect();
        }
    }

    class ClientHandler implements Runnable {
        private Socket client;
        private String user = null;
        private sqlQuery mysqlQuery;
        ClientHandler(Socket client, sqlQuery mysqlQuery) {
            this.client = client;
            this.mysqlQuery = mysqlQuery;
        }
        @Override
        public void run() {
            PrintWriter out;
            BufferedReader in;
            boolean flag = true;
            try {
                notifyListeners("Connect" + "&" + ": {ip: " + this.client.getInetAddress().getHostAddress() + "; порт: " + this.client.getPort() + "};");
                while (flag && !isShutDown()) {
                    out = new PrintWriter(this.client.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

                    String line = in.readLine();
                    System.out.printf(" Sent from the client: %s\n", line); // for tests, delete when release

                    switch (line) {
                        case "Disconnect" -> {
                            disconnect();
                            flag = false;
                        }
                        case "Logout" -> logout();
                        case "getAllEmployee" -> out.println(getAllEmployee(this.mysqlQuery));
                        case "getAllDepartment" -> out.println(getAllDepartment(this.mysqlQuery));
                        case "getAllPosition" -> out.println(getAllPosition(this.mysqlQuery));
                        case "getAllEquipment" -> out.println(getAllEquipment(this.mysqlQuery));
                        case "getOccupiedEquipment" -> out.println(getOccupiedEquipment(this.mysqlQuery));
                        case "getAllUserStat" -> out.println(getAllUserStat(this.mysqlQuery));
                        default -> {
                            String[] args = line.split("&");
                            switch (args[0]) {
                                case "Authorization" -> out.println(authorization(mysqlQuery, args[1], args[2]));
                                case "addUser" -> addUser(this.mysqlQuery, args[1]);
                                case "editUser" -> editUser(this.mysqlQuery, args[1]);
                                case "deleteUser" -> deleteUser(this.mysqlQuery, args[1]);
                                case "generateReport" -> out.println(generateReport(this.mysqlQuery, args[1]));
                                case "searchUser" -> out.println(searchUser(this.mysqlQuery, args[1]));
                                case "addStat" -> addStat(this.mysqlQuery, args[1]);
                                default -> {
                                }
                            }
                        }
                    }
                }
            } catch (IOException | SQLException e) {
                disconnect();
            }
        }

        public void disconnect() {
            try {
                notifyListeners("Disconnect" + "&" + ": {ip: " + this.client.getInetAddress().getHostAddress() + "; порт: " + this.client.getPort() + "};");
                this.client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void logout() {
            user = null;
        }

        public void addStat(sqlQuery mysqlQuery, String data) {
            String[] args = data.split("; ");
            mysqlQuery.insertRecordStatistics(args[0], args[1], Integer.parseInt(args[2]), args[3]);
        }

        public String authorization(sqlQuery mysqlQuery, String login, String password) throws SQLException {
            user = mysqlQuery.selectEmployee("'" + login + "'");
            if (Objects.equals(user, "")) {
                user = null;
                return "Пользователь не найден!";
            } else {
                String[] args = user.split("; ");
                if (Objects.equals(args[4], password)) {
                    return user;
                } else {
                    user = null;
                    return "Пароль неверный!";
                }
            }
        }

        public void addUser(sqlQuery mysqlQuery, String userData) throws SQLException {
            String[] args = userData.split("; ");
            mysqlQuery.insertRecordEmployee(args[0], args[1], args[2], args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
            String newUserId = mysqlQuery.selectEmployee("'" + args[0] + "'").split("; ")[0];
            mysqlQuery.updateRecordEquipment(Integer.parseInt(newUserId), Integer.parseInt(args[6]));
        }

        public void editUser(sqlQuery mysqlQuery, String userData) {
            String[] args = userData.split("; ");
            mysqlQuery.updateRecordEmployee(args[1], args[2], args[3], args[4], Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]), Integer.parseInt(args[0]));
            mysqlQuery.clearRecordEquipment(Integer.parseInt(args[0]));
            mysqlQuery.updateRecordEquipment(Integer.parseInt(args[0]), Integer.parseInt(args[7]));
        }

        public void deleteUser(sqlQuery mysqlQuery, String employee_id) {
            mysqlQuery.deleteRecordEmployee(Integer.parseInt(employee_id));
        }

        public String generateReport(sqlQuery mysqlQuery, String usernameReport) throws SQLException {
            String[] args = usernameReport.split("; ");
            String comp_id = args[7];
            return mysqlQuery.selectStatistics(comp_id);
        }


        public String searchUser(sqlQuery mysqlQuery, String usernameSearch) throws SQLException {
            return mysqlQuery.selectEmployee("'" + usernameSearch + "'");
        }

        public String getAllEmployee(sqlQuery mysqlQuery) throws SQLException {
            return mysqlQuery.selectAllEmployee();
        }

        public String getAllUserStat(sqlQuery mysqlQuery) throws SQLException {
            String comp_id = user.split("; ")[7];
            return mysqlQuery.selectStatistics(comp_id);
        }

        public String getAllDepartment(sqlQuery mysqlQuery) throws SQLException {
            return mysqlQuery.selectAllDepartment();
        }

        public String getAllPosition(sqlQuery mysqlQuery) throws SQLException {
            return mysqlQuery.selectAllPosition();
        }

        public String getAllEquipment(sqlQuery mysqlQuery) throws SQLException {
            return mysqlQuery.selectAllEquipment();
        }

        public String getOccupiedEquipment(sqlQuery mysqlQuery) throws SQLException {
            return mysqlQuery.selectOccupiedEquipment();
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


