package org.cri.levi.websocketcasinoserver.database.authenticator;

import org.apache.http.auth.InvalidCredentialsException;
import org.cri.levi.websocketcasinoserver.database.Sql;
import org.cri.levi.websocketcasinoserver.database.SqlModel;
import org.cri.levi.websocketcasinoserver.database.Sqlable;
import org.cri.levi.websocketcasinoshared.models.loginmodels.LoginModel;
import org.cri.levi.websocketcasinoshared.models.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Authenticator implements Authenticatorable {
    private static Authenticator instance;
    private Sqlable sql = new Sql();

    public static Authenticator getInstance() {
        if (instance == null) {
            instance = new Authenticator();
        }
        return instance;
    }

    @Override
    public Player register(LoginModel loginModel) throws SQLException {
        String query = "INSERT INTO user(username, password, balance) VALUE(?, ?, 0)";
        List<SqlModel> parameters = new ArrayList<>();
        parameters.add(new SqlModel(1, loginModel.getUsername()));
        parameters.add(new SqlModel(2, loginModel.getPassword()));

        try {
            sql.executeUpdate(query, parameters);
            return login(loginModel.getUsername(), loginModel.getPassword());

        } catch (InvalidCredentialsException e) {
            throw new SQLException("Insert new user failed...");
        } finally {
            sql.ClosedbPreparedStat();
        }
    }

    @Override
    public Player login(String username, String password) throws SQLException, InvalidCredentialsException {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        List<SqlModel> parameters = new ArrayList<>();
        parameters.add(new SqlModel(1, username));
        parameters.add(new SqlModel(2, password));
        ResultSet rs = sql.executeQuery(query, parameters);
        try {
            if (!rs.first())
                throw new InvalidCredentialsException("Invalid username or password given...");

            int id = rs.getInt(1);
            int balance = rs.getInt(4);
            return new Player(id, username, balance);
        }finally {
            rs.close();
            sql.ClosedbPreparedStat();
        }
    }
}
