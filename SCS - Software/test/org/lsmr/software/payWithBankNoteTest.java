// SENG 300 Assignment 3
// Group members: Owen Fielder, Joshua Tolentino, Sean Choi, Abdul Bari Mostafa

package org.lsmr.software;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.observers.BanknoteValidatorObserver;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.*;

public class payWithBankNoteTest{
	
	public payWithBankNote test;
   
 	public Currency cTest = SelfCheckoutStationSetup.currency;
 	
 	SelfCheckoutStation station;
 	
 	@Before
 	public void setUp() {
 		station = SelfCheckoutStationSetup.createSelfCheckoutStationFromInit();
 		
 		test = new payWithBankNote(station);
 	}
 	
 	@Test
 	public void testBanknotePaid() {
 		Banknote bn = new Banknote(cTest, 5);
 		
 		try {
 			station.banknoteInput.accept(bn);
 			
 			assertEquals("Banknote was not paid.", new BigDecimal(5), test.totalPaid);
 		}
 		catch(DisabledException e) {
 			e.printStackTrace();
 		} catch (OverloadException e) {
			e.printStackTrace();
		}
 		
 		
 	}
	
    /*@Test
    public void checkAccept()
    {

        Banknote testNote = new Banknote (cTest, 5);    
        test.valid = true;
        test.isFull = false;
        
        test.accept(testNote);
       
        assertEquals(BigDecimal.valueOf(5), test.totalPaid);
    }
    
    @Test
    public void checkAcceptWhenFull()
    {
    	
        Banknote testNote = new Banknote (cTest, 5);
      
        test.valid = true;
        test.isFull = true;
        
        test.accept(testNote);
      
        
        assertEquals(BigDecimal.valueOf(0), test.totalPaid);
        
    }
    
    @Test
    public void checkAcceptWhenNotValid()
    {
    	Banknote testNote = new Banknote (cTest, 5);
        test.valid = false;
        test.isFull = true;
        
        test.accept(testNote);
      
       // assertEquals(0 , tTrack.getPaidAmount());
        
    }
    
    @Test
    public void checkInvalid()
    {
        payWithBankNote test = new payWithBankNote();
        
    	Currency cTest = Currency.getInstance("CAD");
        Banknote testNote = new Banknote (cTest, 5);
        
        test.listener.invalidBanknoteDetected(null);
        
        assertTrue(test.valid == false);
        
    }
    
    @Test
    public void checksValid()
    {
    	
        payWithBankNote test = new payWithBankNote();
        
    	Currency cTest = Currency.getInstance("CAD");
        Banknote testNote = new Banknote (cTest, 5);
        test.listener.validBanknoteDetected(null, cTest, 0);
        
        assertTrue(test.valid == true);
        
    }
    
    @Test
    public void checkFull()
    {
    	
        payWithBankNote test = new payWithBankNote();
        
    	Currency cTest = Currency.getInstance("CAD");
        Banknote testNote = new Banknote (cTest, 5);
        test.listener2.banknotesFull(null);
        
        assertTrue(test.isFull == true);
        
    }
    
    @Test
    public void checkNotFull()
    {
    	
        payWithBankNote test = new payWithBankNote();
        
    	Currency cTest = Currency.getInstance("CAD");
        Banknote testNote = new Banknote (cTest, 5);
        test.listener2.banknotesUnloaded(null);
        
        assertTrue(test.isFull == false);
        
    }*/
    
    

 

}