package org.cri.levi.websocketcasinoserver.database.playerfinder;

import org.cri.levi.websocketcasinoshared.models.Player;

import java.sql.SQLException;

public interface Finderable {
    Player getPlayer(int id) throws SQLException;

    Player getPlayer(int id, String sessionID) throws SQLException;

}
