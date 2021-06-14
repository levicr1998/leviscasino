import org.cri.levi.websocketcasinoshared.models.Game;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.PlayerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;
    private Game game1;
    private Game game2;
    private UUID gameID;
    @BeforeEach
    void SetUp(){
        gameID = UUID.randomUUID();
        List<PlayerModel> playerModels = new ArrayList<>();
        playerModels.add(new PlayerModel("Levi"));
        game = new Game();
        game1 = new Game("Roulette");
        game2 = new Game(gameID, "RouletteGame", 20, playerModels);
    }


    @Test
    void testConstructor1(){
     assertEquals(20,game.getMaxAmountOfPlayers());
     assertEquals(0,game.getPlayers().size());
    }

    @Test
    void testConstructor2(){
        assertEquals("Roulette",game1.getGameName());
        assertEquals(20, game1.getMaxAmountOfPlayers());
        assertEquals(0, game1.getPlayers().size());
    }

    @Test
    void testConstructor3(){
        assertEquals(gameID,game2.getId());
        assertEquals("RouletteGame",game2.getGameName());
        assertEquals(20, game2.getMaxAmountOfPlayers());
        assertEquals(1,game2.getPlayers().size());
    }
}
