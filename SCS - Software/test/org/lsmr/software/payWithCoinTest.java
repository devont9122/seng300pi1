package org.lsmr.software;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

public class payWithCoinTest {
	
	//Instance Creation
	SelfCheckoutStation station;
	public PayWithCoin testCoinPayment;
 	public Currency currencyTest = SelfCheckoutStationSetup.currency;
 	
 	@Before
 	public void setUp() {
 		station = SelfCheckoutStationSetup.createSelfCheckoutStationFromInit();
 		testCoinPayment = new PayWithCoin(station);
 	}
 	
 	@Test
 	public void testCoinPaid() {
 		BigDecimal value = new BigDecimal("0.05");
 		Coin testCoin = new Coin(currencyTest, value);
 		
 		try {
 			station.coinSlot.accept(testCoin);
 			
 			assertEquals("Coin was not paid.", new BigDecimal("0.05"), testCoinPayment.totalPaid);
 		}
 		catch(DisabledException e) {
 			e.printStackTrace();
 		}
 		
 		
 	}
 	
 	/*@Test
    public void testAccept() {
 		BigDecimal value = new BigDecimal(5);
        Coin testCoin = new Coin (currencyTest, value);    
        testCoinPayment.coinValidity = true;
        testCoinPayment.fullStorage = false;
        
        testCoinPayment.accept(testCoin);
       
        assertEquals(new BigDecimal(5), testCoinPayment.totalPaid);
    }
 	
 	@Test
    public void testAcceptWhenInvalid() {
    	BigDecimal value = new BigDecimal(5);
        Coin testCoin = new Coin (currencyTest, value);
    	testCoinPayment.coinValidity = false;
    	testCoinPayment.fullStorage = true;
        
    	testCoinPayment.accept(testCoin);
        
    }
 	
    @Test
    public void testAcceptWhenFullStorage() {
    	BigDecimal value = new BigDecimal(5);
        Coin testCoin = new Coin (currencyTest, value);
      
        testCoinPayment.coinValidity = true;
        testCoinPayment.fullStorage = true;
        
        testCoinPayment.accept(testCoin);
       
        assertEquals(new BigDecimal(0), testCoinPayment.totalPaid);
        
    }
    
    //Tests when Coin is Valid
    @Test
    public void testValidCoin() {
    	BigDecimal value = new BigDecimal(5);
        
        testCoinPayment.observeCoin.validCoinDetected(null, value);
        assertTrue(testCoinPayment.coinValidity == true);
    }
    
    //Tests when Coin is inValid
    @Test
    public void testInvalidCoin() {
        testCoinPayment.observeCoin.invalidCoinDetected(null);
        assertTrue(testCoinPayment.coinValidity == false);
    }
    
    //Tests when CoinStorageUnit is Full
    @Test
    public void testCoinsFull() {
        testCoinPayment.observeStorage.coinsFull(null);
        
        assertTrue(testCoinPayment.fullStorage == true);
    }
    
    //Tests when CoinStorageUnit is not full
    @Test
    public void testCoinsUnloaded() {
        testCoinPayment.observeStorage.coinsUnloaded(null);
        
        assertTrue(testCoinPayment.fullStorage == false);
    }*/
    
    
}
