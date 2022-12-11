package server.mvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.sqlQuery;

import java.sql.SQLException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthTest {
    public static String authorization(sqlQuery mysqlQuery, String login, String password) throws SQLException {
        String user = mysqlQuery.selectEmployee("'" + login + "'");
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
    @DisplayName("Test model.authorization()")
    @Test
    void testAuthorization() throws SQLException {
        //given
        sqlQuery mysqlQuery = new sqlQuery();

        //test
        assertEquals("Пользователь не найден!", authorization(mysqlQuery, "testing", "test"), "model.authorization() didn't pass!");
    }
}
