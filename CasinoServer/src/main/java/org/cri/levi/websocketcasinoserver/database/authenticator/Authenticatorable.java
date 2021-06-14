package org.cri.levi.websocketcasinoserver.database.authenticator;

import org.apache.http.auth.InvalidCredentialsException;
import org.cri.levi.websocketcasinoshared.models.loginmodels.LoginModel;
import org.cri.levi.websocketcasinoshared.models.Player;

import java.sql.SQLException;

public interface Authenticatorable {

    Player register(LoginModel loginModel) throws SQLException;

    Player login(String username, String password) throws SQLException, InvalidCredentialsException;
}

