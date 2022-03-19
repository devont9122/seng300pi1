// SENG 300 Assignment 3
// Group members: Owen Fielder, Joshua Tolentino, Sean Choi, Abdul Bari Mostafa

package org.lsmr.software;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.observers.BanknoteValidatorObserver;

import static org.junit.Assert.*;

import java.util.Currency;

import org.junit.*;

public class payWithBankNoteTest{
    
	
    @Test
    public void checkAccept()
    {
    	
        payWithBankNote test = new payWithBankNote();
        
    	Currency cTest = Currency.getInstance("CAD");
        Banknote testNote = new Banknote (cTest, 5);
      
        test.valid = true;
        test.isFull = false;
        
        assertEquals(5 ,test.accept(testNote));
        
    }
    
    @Test
    public void checkAcceptWhenFull()
    {
    	
        payWithBankNote test = new payWithBankNote();
        
    	Currency cTest = Currency.getInstance("CAD");
        Banknote testNote = new Banknote (cTest, 5);
      
        test.valid = true;
        test.isFull = true;
        
        assertEquals(0 ,test.accept(testNote));
        
    }
    
    @Test
    public void checkAcceptWhenNotValid()
    {
    	
        payWithBankNote test = new payWithBankNote();
        
    	Currency cTest = Currency.getInstance("CAD");
        Banknote testNote = new Banknote (cTest, 5);
      
        test.valid = false;
        test.isFull = true;
        
        assertEquals(0 ,test.accept(testNote));
        
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