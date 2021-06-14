package org.cri.levi.websocketcasinoserver.database.bank;

import org.cri.levi.websocketcasinoshared.models.bankmodels.TransferModel;
import org.cri.levi.websocketcasinoshared.models.Player;

import java.sql.SQLException;

public interface Bankable {

    Player deposit(TransferModel transferModel) throws SQLException;

    Player withdraw(TransferModel transferModel) throws SQLException;

    boolean checkPlaceBetPossible(int playerID, int bet) throws SQLException;

    Player removeBetFromBalance(int playerID, int bet) throws SQLException;

    Player addProfitToBalance(int playerID, int profit) throws SQLException;

}
