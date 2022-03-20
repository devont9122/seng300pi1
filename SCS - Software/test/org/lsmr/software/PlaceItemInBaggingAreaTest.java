package org.lsmr.software;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;

import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;

public class PlaceItemInBaggingAreaTest {
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testAddItem() {
		// Test Creations
		Currency testCurrency = Currency.getInstance("CAD");
		int[] testBanknoteDenominations = {1, 2, 5};
		BigDecimal[] testCoinDenominations = {BigDecimal.TEN};
		SelfCheckoutStation testStation = new SelfCheckoutStation(testCurrency, testBanknoteDenominations, testCoinDenominations, 0, 0);
		
		PlaceItemInBaggingArea testBagging = new PlaceItemInBaggingArea();
		
	}

}
