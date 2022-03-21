package org.lsmr.software;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;
import org.junit.Test;
import org.lsmr.selfcheckout.Coin;

public class payWithCoinTest {
	
	//Instance Creation
	public PayWithCoin testCoinPayment = new PayWithCoin();
 	public Currency currencyTest = Currency.getInstance("CAD");
 	
 	@Test
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
    }
    
    
}
