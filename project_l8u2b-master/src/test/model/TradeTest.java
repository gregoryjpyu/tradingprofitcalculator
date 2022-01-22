package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TradeTest {
    private Trade trade1;
    private Trade trade2;
    private Trade trade3;
    private Trade trade4;
    private Trade trade5;

    @BeforeEach
    void runBefore() {
        trade1 = new Trade("FB", 10030, 20040, 10);
        trade2 = new Trade("GME", 50000, 500, 2000);
        trade3 = new Trade("MEOH", 4532, 4532, 50);
        trade4 = new Trade("BB", 0, 400, 3000);
        trade5 = new Trade("KEKW", 10000000, 0, 1);
    }

    @Test
    void testGetTicker() {
        assertEquals("FB", trade1.getTicker());
        assertEquals("GME", trade2.getTicker());
    }

    @Test
    void testGetPriceBought() {
        assertEquals(10030, trade1.getPriceBought());
        assertEquals(0, trade4.getPriceBought());
    }

    @Test
    void testGetPriceSold() {
        assertEquals(500, trade2.getPriceSold());
        assertEquals(0, trade5.getPriceSold());
    }

    @Test
    void testGetShares() {
        assertEquals(10, trade1.getShares());
        assertEquals(3000, trade4.getShares());
    }


    @Test
    void testIsWin() {
        assertTrue(trade1.isWin());
        assertFalse(trade2.isWin());
        assertTrue(trade4.isWin());
        assertFalse(trade5.isWin());

    }

    @Test
    void testIsWinEqual() {
        assertFalse(trade3.isWin());

    }

    @Test
    void testPositiveProfit() {
        trade1.profitAmount();
        assertEquals(1001, trade1.getProfit());

        trade4.profitAmount();
        assertEquals(12000, trade4.getProfit());
    }

    @Test
    void testNegativeProfit() {
        trade2.profitAmount();
        assertEquals(-990000, trade2.getProfit());

        trade5.profitAmount();
        assertEquals(-100000, trade5.getProfit());

    }

    @Test
    void noProfit() {
        trade3.profitAmount();
        assertEquals(0, trade3.getProfit());

    }


}

