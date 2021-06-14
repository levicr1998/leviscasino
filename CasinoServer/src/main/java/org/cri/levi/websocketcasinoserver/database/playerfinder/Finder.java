package org.cri.levi.websocketcasinoserver.database.playerfinder;

import javassist.NotFoundException;
import org.cri.levi.websocketcasinoserver.database.Sql;
import org.cri.levi.websocketcasinoserver.database.SqlModel;
import org.cri.levi.websocketcasinoserver.database.Sqlable;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Finder implements Finderable {
    private static Finder instance;
    private static final Logger log = LoggerFactory.getLogger(Finder.class);
    private Sqlable sql = new Sql();

    public static Finder getInstance() {
        if (instance == null) {
            instance = new Finder();
        }
        return instance;
    }

    public Player getPlayer(int id) throws SQLException {
        String query = "SELECT * FROM user WHERE id = ?";
        List<SqlModel> parameters = new ArrayList<>();
        parameters.add(new SqlModel(1, id));
        ResultSet rs = sql.executeQuery(query, parameters);
        if (!rs.first()) {
            try {
                throw new NotFoundException("Invalid id given...");
            } catch (NotFoundException e) {
                log.info(e.getMessage());
            }
        }
        int balance = rs.getInt(4);
        String username = rs.getString(2);
        rs.close();
        sql.ClosedbPreparedStat();
        return new Player(id, username, balance);

    }

    public Player getPlayer(int id, String sessionID) throws SQLException {

        String query = "SELECT * FROM user WHERE id = ?";
        List<SqlModel> parameters = new ArrayList<>();
        parameters.add(new SqlModel(1, id));
        ResultSet rs = sql.executeQuery(query, parameters);
        if (!rs.first()) {
            try {
                throw new NotFoundException("Invalid id given...");
            } catch (NotFoundException e) {
                log.info(e.getMessage());
            }
        }
        int balance = rs.getInt(4);
        String username = rs.getString(2);
        rs.close();
        sql.ClosedbPreparedStat();

        return new Player(id, username, balance, sessionID);

    }
}
