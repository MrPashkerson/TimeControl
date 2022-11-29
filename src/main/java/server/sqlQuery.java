package server;


import java.sql.*;
public class sqlQuery {
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "toor";
    private static final String URL = "jdbc:mysql://localhost:3306/mysql";
    public static final String SELECT_QUERY_DEPARTMENT = "SELECT * FROM department";
    public static final String INSERT_QUERY_STATISTICS = "INSERT INTO statistics (app_name, app_time, comp_id, app_date) VALUES (?, ?, ?, ?)";
    public static final String SELECT_QUERY_STATISTICS = "SELECT * FROM statistics WHERE comp_id = ";
    public static final String UPDATE_QUERY_EQUIPMENT = "UPDATE equipment SET employee_id = ? WHERE comp_id = ?";
    public static final String CLEAR_QUERY_EQUIPMENT = "UPDATE equipment SET employee_id = NULL WHERE employee_id = ?";
    public static final String SELECT_QUERY_EQUIPMENT = "SELECT * FROM equipment WHERE employee_id IS NULL";
    public static final String SELECT_QUERY_EQUIPMENT_OCCUPIED = "SELECT * FROM equipment WHERE employee_id IS NOT NULL";
    public static final String INSERT_QUERY_EMPLOYEE = "INSERT INTO employee (username, lastname, firstname, password, department_id, position_id, comp_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_QUERY_EMPLOYEE = "UPDATE employee SET username = ?, lastname = ?, firstname = ?, password = ?, department_id = ?, position_id = ?, comp_id = ? WHERE employee_id = ?";
    public static final String DELETE_QUERY_EMPLOYEE = "DELETE FROM employee WHERE employee_id = ?";
    public static final String SELECT_QUERY_EMPLOYEE = "SELECT * FROM employee";
    public static final String SELECT_QUERY_POSITION = "SELECT * FROM position";
    private static Statement statement;
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void disconnect() throws SQLException {
        statement.close();
        connection.close();
    }

    public void insertRecordStatistics(String app_name, String app_time, int comp_id, String app_date) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_STATISTICS)) {
            preparedStatement.setString(1, app_name);
            preparedStatement.setString(2, app_time);
            preparedStatement.setInt(3, comp_id);
            preparedStatement.setString(4, app_date);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            try {
                preparedStatement.close();
            } catch (SQLException ignored) {}
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void insertRecordEmployee(String username, String lastname, String firstname, String password, int department_id, int position_id, int comp_id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_EMPLOYEE)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, firstname);
            preparedStatement.setString(4, password);
            preparedStatement.setInt(5, department_id);
            preparedStatement.setInt(6, position_id);
            preparedStatement.setInt(7, comp_id);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            try {
                preparedStatement.close();
            } catch (SQLException ignored) {}
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void updateRecordEquipment(int employee_id, int comp_id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY_EQUIPMENT)) {
            preparedStatement.setInt(1, employee_id);
            preparedStatement.setInt(2, comp_id);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            try {
                preparedStatement.close();
            } catch (SQLException ignored) {}
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void clearRecordEquipment(int employee_id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_QUERY_EQUIPMENT)) {
            preparedStatement.setInt(1, employee_id);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            try {
                preparedStatement.close();
            } catch (SQLException ignored) {}
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void updateRecordEmployee(String username, String lastname, String firstname, String password, int department_id, int position_id, int comp_id, int employee_id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY_EMPLOYEE)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, firstname);
            preparedStatement.setString(4, password);
            preparedStatement.setInt(5, department_id);
            preparedStatement.setInt(6, position_id);
            preparedStatement.setInt(7, comp_id);
            preparedStatement.setInt(8, employee_id);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            try {
                preparedStatement.close();
            } catch (SQLException ignored) {}
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void deleteRecordEmployee(int employee_id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY_EMPLOYEE)) {
            preparedStatement.setInt(1, employee_id);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            try {
                preparedStatement.close();
            } catch (SQLException ignored) {}
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public String selectAllDepartment() throws SQLException {
        ResultSet resultSet = statement.executeQuery(SELECT_QUERY_DEPARTMENT);
        StringBuilder result = new StringBuilder();
        while (resultSet.next()) {
            result.append(resultSet.getString(1)).append("; ").append(resultSet.getString(2)).append("; ").append(resultSet.getString(3)).append("&");
        }
        resultSet.close();
        if(result.toString().equals("")) {
            result = new StringBuilder();}
        return result.toString();
    }

    public String selectEmployee(String username) throws SQLException {
        ResultSet resultSet = statement.executeQuery(SELECT_QUERY_EMPLOYEE + " WHERE username = " + username);
        StringBuilder result = new StringBuilder();
        while (resultSet.next()) {
            result.append(resultSet.getString(1)).append("; ").append(resultSet.getString(2)).append("; ").append(resultSet.getString(3)).append("; ").append(resultSet.getString(4)).append("; ").append(resultSet.getString(5)).append("; ").append(resultSet.getString(6)).append("; ").append(resultSet.getString(7)).append("; ").append(resultSet.getString(8));
        }
        resultSet.close();
        if(result.toString().equals("")) {
            result = new StringBuilder();}
        return result.toString();
    }

    public String selectAllEmployee() throws SQLException {
        ResultSet resultSet = statement.executeQuery(SELECT_QUERY_EMPLOYEE);
        StringBuilder result = new StringBuilder();
        while (resultSet.next()) {
            result.append(resultSet.getString(1)).append("; ").append(resultSet.getString(2)).append("; ").append(resultSet.getString(3)).append("; ").append(resultSet.getString(4)).append("; ").append(resultSet.getString(5)).append("; ").append(resultSet.getString(6)).append("; ").append(resultSet.getString(7)).append("; ").append(resultSet.getString(8)).append("&");
        }
        resultSet.close();
        if(result.toString().equals("")) {
            result = new StringBuilder();}
        return result.toString();
    }

    public String selectAllEquipment() throws SQLException {
        ResultSet resultSet = statement.executeQuery(SELECT_QUERY_EQUIPMENT);
        StringBuilder result = new StringBuilder();
        while (resultSet.next()) {
            result.append(resultSet.getString(1)).append("; ").append(resultSet.getString(2)).append("; ").append(resultSet.getString(3)).append("&");
        }
        resultSet.close();
        if(result.toString().equals("")) {
            result = new StringBuilder();}
        return result.toString();
    }

    public String selectOccupiedEquipment() throws SQLException {
        ResultSet resultSet = statement.executeQuery(SELECT_QUERY_EQUIPMENT_OCCUPIED);
        StringBuilder result = new StringBuilder();
        while (resultSet.next()) {
            result.append(resultSet.getString(1)).append("; ").append(resultSet.getString(2)).append("; ").append(resultSet.getString(3)).append("&");
        }
        resultSet.close();
        if(result.toString().equals("")) {
            result = new StringBuilder();}
        return result.toString();
    }

    public String selectAllPosition() throws SQLException {
        ResultSet resultSet = statement.executeQuery(SELECT_QUERY_POSITION);
        StringBuilder result = new StringBuilder();
        while (resultSet.next()) {
            result.append(resultSet.getString(1)).append("; ").append(resultSet.getString(2)).append("; ").append(resultSet.getString(3)).append("; ").append(resultSet.getString(4)).append("; ").append(resultSet.getString(5)).append("; ").append(resultSet.getString(6)).append("; ").append(resultSet.getString(7)).append("; ").append(resultSet.getString(8)).append("; ").append(resultSet.getString(9)).append("; ").append(resultSet.getString(10)).append("&");
        }
        resultSet.close();
        if(result.toString().equals("")) {
            result = new StringBuilder();}
        return result.toString();
    }

    public String selectStatistics(String comp_id) throws SQLException {
        ResultSet resultSet = statement.executeQuery(SELECT_QUERY_STATISTICS + comp_id);
        StringBuilder result = new StringBuilder();
        while (resultSet.next()) {
            result.append(resultSet.getString(1)).append("; ").append(resultSet.getString(2)).append("; ").append(resultSet.getString(3)).append("; ").append(resultSet.getString(4)).append("; ").append(resultSet.getString(5)).append("&");
        }
        resultSet.close();
        if(result.toString().equals("")) {
            result = new StringBuilder();}
        return result.toString();
    }


    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
