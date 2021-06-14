package gameManager;

import org.cri.levi.websocketcasinoserver.gamemanager.ResultRouletteGames;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

public class ResultRouletteGamesTest {

    private ResultRouletteGames resultRouletteGames;
    private UUID gameID;

    @BeforeEach
    void setUp() {
        resultRouletteGames = new ResultRouletteGames();
        resultRouletteGames.getResultGames().clear();
        UUID gameID = UUID.randomUUID();
    }

    @Test
    void testAddRouletteGame() {

        resultRouletteGames.addnewGameRouletteGame(gameID);
        assertEquals(1, resultRouletteGames.getResultGames().size());

    }

}
