import org.cri.levi.websocketcasinoclient.frontend.logic.RouletteBet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RouletteBetTest {
private RouletteBet rouletteBet;
    @BeforeEach
    void setUp(){
rouletteBet = new RouletteBet();
rouletteBet.setAmountRouletteBet(5);
    }

    @Test
    void testGetRouletteBet(){
        assertEquals(5,rouletteBet.getAmountRouletteBet());
    }

    @Test
    void testSetRouletteBet(){
        rouletteBet.setAmountRouletteBet(10);
        assertEquals(10,rouletteBet.getAmountRouletteBet());
    }
}
