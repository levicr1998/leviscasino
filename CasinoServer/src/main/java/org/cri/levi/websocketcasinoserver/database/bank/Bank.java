package org.cri.levi.websocketcasinoserver.database.bank;


import javassist.NotFoundException;
import org.cri.levi.websocketcasinoserver.database.Sql;
import org.cri.levi.websocketcasinoserver.database.SqlModel;
import org.cri.levi.websocketcasinoserver.database.Sqlable;
import org.cri.levi.websocketcasinoserver.database.playerfinder.Finder;
import org.cri.levi.websocketcasinoshared.models.bankmodels.TransferModel;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Bank implements Bankable {
    private static Bank instance;
    private static final Logger log = LoggerFactory.getLogger(Bank.class);
    private Sqlable sql = new Sql();
    String updateBalanceQuery = "UPDATE user SET balance = ? WHERE id = ?";

    public static Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    @Override
    public Player deposit(TransferModel transferModel) throws SQLException {
        Player player = Finder.getInstance().getPlayer(transferModel.getPlayer().getId());
        String query = updateBalanceQuery;
        List<SqlModel> parameters = new ArrayList<>();
        int currentBalance = player.getBalance() + transferModel.getTransferMoney();
        parameters.add(new SqlModel(1, currentBalance));
        parameters.add(new SqlModel(2, transferModel.getPlayer().getId()));

        sql.executeUpdate(query, parameters);
        player = Finder.getInstance().getPlayer(transferModel.getPlayer().getId());
        return player;

    }

    @Override
    public Player withdraw(TransferModel transferModel) throws SQLException {
        Player player = Finder.getInstance().getPlayer(transferModel.getPlayer().getId());
        String query = updateBalanceQuery;
        List<SqlModel> parameters = new ArrayList<>();
        int currentBalance = player.getBalance() - transferModel.getTransferMoney();
        parameters.add(new SqlModel(1, currentBalance));
        parameters.add(new SqlModel(2, transferModel.getPlayer().getId()));

        sql.executeUpdate(query, parameters);
        sql.ClosedbPreparedStat();
        player = Finder.getInstance().getPlayer(transferModel.getPlayer().getId());
        return player;
    }

    public boolean checkPlaceBetPossible(int playerID, int bet) throws SQLException {
        String query = "SELECT * FROM user WHERE id = ?";
        List<SqlModel> parameters = new ArrayList<>();
        parameters.add(new SqlModel(1, playerID));
        ResultSet rs = sql.executeQuery(query, parameters);
        if (!rs.first()) {
            try {
                throw new NotFoundException("Invalid id given...");
            } catch (NotFoundException e) {
                log.info(e.getMessage());
            }
        }
        int balance = rs.getInt(4);
        rs.close();
        sql.ClosedbPreparedStat();

        if (balance < bet) {
            return false;
        }
        return true;
    }

    public Player removeBetFromBalance(int playerID, int bet) throws SQLException {
        String query = updateBalanceQuery;
        List<SqlModel> parameters = new ArrayList<>();
        int currentBalance = Finder.getInstance().getPlayer(playerID).getBalance() - bet;
        parameters.add(new SqlModel(1, currentBalance));
        parameters.add(new SqlModel(2, playerID));
        sql.ClosedbPreparedStat();
        sql.executeUpdate(query, parameters);
        return Finder.getInstance().getPlayer(playerID);

    }

    public Player addProfitToBalance(int playerID, int profit) throws SQLException {


        String query = updateBalanceQuery;
        List<SqlModel> parameters = new ArrayList<>();
        int currentBalance = Finder.getInstance().getPlayer(playerID).getBalance() + profit;
        parameters.add(new SqlModel(1, currentBalance));
        parameters.add(new SqlModel(2, playerID));

        sql.executeUpdate(query, parameters);

        sql.ClosedbPreparedStat();
        return Finder.getInstance().getPlayer(playerID);

    }


}
