package gameManager;

import org.cri.levi.websocketcasinoserver.gamemanager.BetModel;
import org.cri.levi.websocketcasinoserver.gamemanager.GameManager;
import org.cri.levi.websocketcasinoserver.gamemanager.GameRoom;
import org.cri.levi.websocketcasinoshared.models.Game;
import org.cri.levi.websocketcasinoshared.models.GameType;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.CreateGameModel;
import org.cri.levi.websocketcasinoshared.models.lobbymodels.PlayerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GameManagerTest {
    private GameManager gameManager;
    private UUID gameID;
    private GameRoom gameRoom;
    private String sessionID;
    private Player standardPlayer;
    private CreateGameModel createGameModel;

    @BeforeEach
    void setUp() {
        gameID = UUID.randomUUID();
        gameManager = new GameManager();
        createGameModel = new CreateGameModel("Game",11, GameType.ROULETTE);
        sessionID = "34424323";
        standardPlayer = new Player(3,"Levi",100);

        gameManager.getGames().clear();
        gameManager.getGameRooms().clear();


    }


    @Test
    void testGetGames() {
        gameManager.getGames().add(new Game());
        gameManager.getGames().add(new Game());
        assertEquals(2, gameManager.getGames().size());
    }

    @Test
    void testGetGameRooms() {
        gameRoom = new GameRoom(gameID);
        gameManager.getGameRooms().add(gameRoom);
        assertEquals(1, gameManager.getGameRooms().size());
    }

    @Test
    void testUpdatePlayerListRoom() {
        gameRoom = new GameRoom(gameID);
        gameManager.getGameRooms().add(gameRoom);
        gameRoom.addnewPlayer(standardPlayer,gameID,sessionID);

        List<PlayerModel> playerModels = gameManager.updatePlayerListRoom(gameID);

        for(PlayerModel p:playerModels){
            assertEquals("Levi",p.getUsername());
            assertEquals(3, p.getPlayerID());
        }

    }

    @Test
    void testCreateGame() {
        gameManager.createGame(createGameModel);
        assertEquals(1,gameManager.getGameRooms().size());
        assertEquals(1,gameManager.getGames().size());

    }

    @Test
    void testJoinGame() {
        Game game = new Game();
        GameRoom gameRoom = new GameRoom();
        gameManager.createGame(createGameModel);
        for(Game g: gameManager.getGames()){
            game = new Game(g.getId(),g.getGameName(),g.getMaxAmountOfPlayers(),g.getPlayers());
        }
        for(GameRoom g: gameManager.getGameRooms()){
            gameRoom = new GameRoom(g.getGameID());
        }
gameManager.joinGame(standardPlayer,gameID,sessionID);
for(Game g: gameManager.getGames()){
    for(PlayerModel p: g.getPlayers()){
        assertEquals(p.getPlayerID(),standardPlayer.getId());
    }
}

        for(GameRoom g: gameManager.getGameRooms()){
            for(Player p: g.getPlayerList()){
                assertEquals(p.getId(),standardPlayer.getId());
            }
        }
    }

    @Test
    void testPlaceBet() {
        BetModel betModel = new BetModel(standardPlayer.getId(),gameID,20,1);
        gameRoom = new GameRoom(gameID);
        gameManager.getGameRooms().add(gameRoom);
        gameManager.placeBet(betModel);
        for(GameRoom g: gameManager.getGameRooms()){
            assertEquals(1,g.getBetsInGameRoom().size());
        }
    }



    @Test
    void testRemoveBets() {
        BetModel betModel = new BetModel(standardPlayer.getId(),gameID,20,1);
        gameRoom = new GameRoom(gameID);
        gameManager.getGameRooms().add(gameRoom);
        gameManager.placeBet(betModel);
        gameManager.removeBets(gameID);
        for(GameRoom g: gameManager.getGameRooms()){
            assertEquals(0,g.getBetsInGameRoom().size());
        }
    }

    @Test
    void testLeaveGame() {
        Game game = new Game();
        GameRoom gameRoom = new GameRoom();
        gameManager.createGame(createGameModel);
        for(Game g: gameManager.getGames()){
            game = new Game(g.getId(),g.getGameName(),g.getMaxAmountOfPlayers(),g.getPlayers());
        }
        for(GameRoom g: gameManager.getGameRooms()){
            gameRoom = new GameRoom(g.getGameID());
        }
        gameManager.joinGame(standardPlayer,gameID,sessionID);
        gameManager.leaveGame(standardPlayer);

        for(Game g: gameManager.getGames()){
            for(PlayerModel p: g.getPlayers()){
                assertEquals(0,g.getPlayers().size());
            }
        }

        for(GameRoom g: gameManager.getGameRooms()){
            for(Player p: g.getPlayerList()){
                assertEquals(0,g.getPlayerList().size());
            }
        }
    }
}
