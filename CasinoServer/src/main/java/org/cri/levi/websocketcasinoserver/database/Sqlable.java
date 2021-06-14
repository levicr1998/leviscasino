package org.cri.levi.websocketcasinoserver.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Sqlable {
    ResultSet executeQuery(String query, java.util.List<SqlModel> parameters) throws SQLException;

    void executeUpdate(String query, java.util.List<SqlModel> parameters) throws SQLException;

    void ClosedbPreparedStat() throws SQLException;
}
