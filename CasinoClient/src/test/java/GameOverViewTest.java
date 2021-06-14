import org.cri.levi.websocketcasinoclient.frontend.logic.GamesOverview;
import org.cri.levi.websocketcasinoshared.models.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class GameOverViewTest {
private GamesOverview gamesOverview;
private List<Game> games = new ArrayList<>();
private Game game;

    @BeforeEach
    void setUp(){
        gamesOverview = null;
        games.clear();
games.add(new Game());
games.add(new Game());
game = new Game();
    }

    @Test
    void testConstructor(){
        gamesOverview = new GamesOverview(games);
        assertEquals(2,gamesOverview.getGames().size());
    }

    @Test
    void addGame(){
        gamesOverview = new GamesOverview(games);
        gamesOverview.addGame(game);
        assertEquals(3,gamesOverview.getGames().size());
    }
}
