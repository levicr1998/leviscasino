package gameManager;

import org.cri.levi.websocketcasinoserver.gamemanager.BetModel;
import org.cri.levi.websocketcasinoserver.gamemanager.GameRoom;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GameRoomTest {
    GameRoom gameRoom;
    UUID gameID;
    Player player;
    String sessionID;

    @BeforeEach
    void setUp(){
        gameID = UUID.randomUUID();
        gameRoom = new GameRoom(gameID);
        player = new Player(1,"Levi",100);
        sessionID = "gkgjss";

    }

    @Test
    void testGameID(){
        assertEquals(gameID,gameRoom.getGameID());
    }

    @Test
    void addNewPlayer(){
        gameRoom.addnewPlayer(player,gameID,sessionID);

        assertEquals(1,gameRoom.getPlayerList().size());
    }

    @Test
    void addNewBet(){
        BetModel betModel = new BetModel(1,gameID,20,1);
        gameRoom.getBetsInGameRoom().add(betModel);

        assertEquals(1,gameRoom.getBetsInGameRoom().size());
    }


}
