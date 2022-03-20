package org.lsmr.software;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;

public class payWithCoinTest {
	
    paymentTracker tracker = new paymentTracker();
    
    Currency currency = Currency.getInstance(Locale.CANADA);
    BigDecimal[] coinDenominations= {new BigDecimal(0.01), new BigDecimal(0.05), new BigDecimal(0.1),new BigDecimal(0.25), new BigDecimal(0.50)};
    int[] bankNoteDenominations= {5,10,20,50,100};
    SelfCheckoutStation station = new SelfCheckoutStation(currency, bankNoteDenominations, coinDenominations, 1, 1);
    PayWithCoin payingWithCoin = new PayWithCoin(station);
    
    @Before
    public void initialization() {
        station.coinSlot.attach(payingWithCoin);
        station.coinValidator.attach(payingWithCoin);
        station.coinStorage.attach(payingWithCoin);
        station.coinTray.attach(payingWithCoin);
    }
    
    // When coin is invalid
    @Test
    public void testInvalidCoinDetected(){
        BigDecimal invalidCoin = new BigDecimal(0.25);
        Coin coin = new Coin(currency, invalidCoin);
        
        BigDecimal insertedCoins = tracker.insertCoin(coin);
        BigDecimal expectedInserted = new BigDecimal(0);
        
        assertEquals(expectedInserted, insertedCoins);
    }
    
    // When coin is valid
    @Test
    public void testValidCoinDetected() {
    	BigDecimal validCoin = new BigDecimal(0.25);
        Coin coin = new Coin(currency, validCoin);
        
        
        BigDecimal insertedCoins = tracker.insertCoin(coin);
        BigDecimal expectedInserted = new BigDecimal(0.25);
        
        assertEquals(expectedInserted, insertedCoins);
    }

    @Test
    public void testCoinAdded() {
        fail("Not yet implemented");
    }

    @Test
    public void testCoinsLoaded() throws SimulationException, OverloadException {
    	BigDecimal validCoin = new BigDecimal(0.25);
        Coin coin = new Coin(currency, validCoin);
        
        BigDecimal anotherValidCoin = new BigDecimal(0.25);
        Coin loadingCoin = new Coin(currency, anotherValidCoin);
        
        station.coinStorage.load(loadingCoin, loadingCoin);
        BigDecimal insertedCoins = tracker.insertCoin(coin);
        BigDecimal expectedInserted = new BigDecimal(0.25);
        
        assertEquals(expectedInserted, insertedCoins);
    }

    @Test
    public void testCoinsUnloaded() {
        station.coinStorage.unload();
        int expectedStorage = 1000;
        int actualStorage = station.coinStorage.getCapacity();
        assertEquals(expectedStorage, actualStorage);
    }

    @Test
    public void testPaymentWhenEnabled() {
    	BigDecimal validCoin = new BigDecimal(0.25);
        Coin coin = new Coin(currency, validCoin);
        
        station.coinSlot.enable();
        BigDecimal insertedCoins = tracker.insertCoin(coin);
        BigDecimal expectedInserted = new BigDecimal(0.25);
        
        assertEquals(expectedInserted, insertedCoins);
    }

    @Test
    public void testPaymenyWhenDisabled() {
    	BigDecimal invalidCoin = new BigDecimal(0.25);
        Coin coin = new Coin(currency, invalidCoin);
        
        station.coinSlot.disable();
        BigDecimal insertedCoins = tracker.insertCoin(coin);
        BigDecimal expectedInserted = new BigDecimal(0);
        
        assertEquals(expectedInserted, insertedCoins);
    }
    
    /*
    @Test
    public void testCoinsFull() throws SimulationException, OverloadException {
        BigDecimal validCoin = new BigDecimal(0.25);
        Coin coin = new Coin(currency, validCoin);
        
        while (station.coinStorage.getCoinCount() <= 999) {
        	tracker.insertCoin(coin);
        }
        
        BigDecimal insertedCoins = tracker.insertCoin(coin);
        BigDecimal expectedInserted = new BigDecimal(0);
        assertEquals(expectedInserted, insertedCoins);
    }
    */
}
