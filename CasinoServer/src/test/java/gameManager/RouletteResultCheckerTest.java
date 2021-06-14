package gameManager;

import org.cri.levi.websocketcasinoserver.gamemanager.RouletteResultChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RouletteResultCheckerTest {
    private RouletteResultChecker rouletteResultChecker;

    @BeforeEach
    void setUp() {
        rouletteResultChecker = new RouletteResultChecker();
    }

    @Test
    void testCheckBlackColorFalse() {
        assertFalse(rouletteResultChecker.checkBlack(7));
    }

    @Test
    void testCheckBlackColorTrue() {
        assertTrue(rouletteResultChecker.checkBlack(6));

    }

    @Test
    void testCheckRedColorFalse() {
        assertFalse(rouletteResultChecker.checkRed(20));
    }

    @Test
    void testCheckRedColorTrue() {
        assertTrue(rouletteResultChecker.checkRed(18));

    }

    @Test
    void testCheckEvenTrue() {
        assertTrue(rouletteResultChecker.checkEven(2));
    }

    @Test
    void testCheckEvenFalse() {
        assertFalse(rouletteResultChecker.checkEven(3));
    }

    @Test
    void testCheckOddTrue() {
        assertTrue(rouletteResultChecker.checkOdd(5));
    }

    @Test
    void testCheckOddFalse() {
        assertFalse(rouletteResultChecker.checkOdd(10));
    }

    @Test
    void testCheckFirstTwelveTrue() {
        assertTrue(rouletteResultChecker.checkFirstTwelve(11));
    }

    @Test
    void testCheckSecondTwelveTrue() {
        assertTrue(rouletteResultChecker.checkSecondTwelve(13));
    }

    @Test
    void testCheckThirthTwelveTrue() {
        assertTrue(rouletteResultChecker.checkThirthTwelve(35));
    }

    @Test
    void testCheckFirstEighteenTrue() {
        assertTrue(rouletteResultChecker.checkFirstEighteen(15));
    }

    @Test
    void testCheckSecondEighteenTrue() {
        assertTrue(rouletteResultChecker.checkSecondEighteen(19));
    }

    @Test
    void testCalculateProfit(){
        assertEquals(36,rouletteResultChecker.calculateProfit(12,3));
    }


}
