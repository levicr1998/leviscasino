import org.cri.levi.websocketcasinoshared.models.Player;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    Player player1;
    Player player2;
    Player player;
    String session;
 @BeforeEach
    void setUp(){
     session = "888-596-345vgf";
     player = new Player();
     player1 = new Player(1,"Levi",50,session);
     player2 = new Player(1,"Levi",50);

 }


 @Test
    void testConstructor1(){
     assertEquals(1,player1.getId());
     assertEquals("Levi", player1.getUsername());
     assertEquals(50,player1.getBalance());
     assertEquals("888-596-345vgf",player1.getSessionId());
    }

    @Test
    void testConstructor2(){
        assertEquals(1,player2.getId());
        assertEquals("Levi", player2.getUsername());
        assertEquals(50,player2.getBalance());
    }

    @Test
    void testSetGameId(){
     UUID GameID = UUID.randomUUID();

player.setGameID(GameID);
assertEquals(GameID, player.getGameID());
    }

    @Test
    void testSetBalance(){
     int balance = 100;
     player.setBalance(balance);
     assertEquals(balance,player.getBalance());
    }
}
