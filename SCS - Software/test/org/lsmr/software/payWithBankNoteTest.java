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
    
	public payWithBankNote test = new payWithBankNote();
   
 	public Currency cTest = Currency.getInstance("CAD");
	
    @Test
    public void checkAccept()
    {

        Banknote testNote = new Banknote (cTest, 5);    
        test.valid = true;
        test.isFull = false;
        
        test.accept(testNote);
       
        assertEquals(5 , test.total.getPaidAmount() == BigDecimal.valueOf(5));
    }
    
    @Test
    public void checkAcceptWhenFull()
    {
    	
        Banknote testNote = new Banknote (cTest, 5);
      
        test.valid = true;
        test.isFull = true;
        
        test.accept(testNote);
      
        
        assertEquals(0 , test.total.getPaidAmount() == BigDecimal.valueOf(0));
        
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
        
    }
    
    

 

}