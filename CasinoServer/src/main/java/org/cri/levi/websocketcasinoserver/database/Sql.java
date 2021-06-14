package org.cri.levi.websocketcasinoserver.database;

import org.cri.levi.websocketcasinoserver.ConfigFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class Sql implements Sqlable {
    private Connection dbConn;
    private PreparedStatement dbPrepareStat;

    private static final Logger log = LoggerFactory.getLogger(Sql.class);

    public Sql() {
        ConfigFileReader config = new ConfigFileReader();

        try {
            dbConn = DriverManager.getConnection(config.getDBConnectionURL(), config.getDBUsername(), config.getDBPassword());
            log.info("Connected with db");
        } catch (SQLException e) {
            log.info("MySQL Connection Failed!");
            log.info(e.getMessage());

        }
    }

   private Connection getDbConn() throws SQLException{
       ConfigFileReader config = new ConfigFileReader();
           return DriverManager.getConnection(config.getDBConnectionURL(), config.getDBUsername(), config.getDBPassword());
   }

    @Override
    public ResultSet executeQuery(String query, List<SqlModel> parameters) throws SQLException {

        dbConn = getDbConn();
        dbPrepareStat = dbConn.prepareStatement(query);
        for (SqlModel model : parameters) {
            dbPrepareStat.setObject(model.getPosition(), model.getItem());
        }

        return dbPrepareStat.executeQuery();
    }

    @Override
    public void executeUpdate(String query, List<SqlModel> parameters) throws SQLException {
        dbPrepareStat = dbConn.prepareStatement(query);
        for (SqlModel model : parameters) {
            dbPrepareStat.setObject(model.getPosition(), model.getItem());
        }

        dbPrepareStat.executeUpdate();
    }

   @Override
   public void ClosedbPreparedStat() throws SQLException{
   dbPrepareStat.close();
   }

}
