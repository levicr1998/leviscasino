import org.cri.levi.websocketcasinoclient.frontend.logic.RouletteGame;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.PlayerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RouletteGameTest {
private RouletteGame rouletteGame;
private UUID gameID;
    @BeforeEach
    void setUp(){
        gameID= UUID.randomUUID();
        List<PlayerModel> playerModels = new ArrayList<>();
rouletteGame = new RouletteGame(gameID,"Casino",20, playerModels);
    }


    @Test
    void testConstructor(){
        assertEquals(gameID,rouletteGame.getId());
        assertEquals("Casino",rouletteGame.getGameName());
        assertEquals(20,rouletteGame.getMaxAmountOfPlayers());
        assertEquals(0,rouletteGame.getPlayers().size());
    }

    @Test
    void testSetId(){
        UUID id = UUID.randomUUID();
        rouletteGame.setId(id);
        assertEquals(id,rouletteGame.getId());
    }
}
