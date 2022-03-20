package org.lsmr.software;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;

public class PlaceItemInBaggingAreaTest {
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testAddItem() {
		//Creation of the Self-Checkout Machine & Bagging Area
		Currency testCurrency = Currency.getInstance("CAD");
		int[] testBanknoteDenominations = {1, 2, 5};
		BigDecimal[] testCoinDenominations = {BigDecimal.TEN};
		SelfCheckoutStation testStation = new SelfCheckoutStation(testCurrency, testBanknoteDenominations, testCoinDenominations, 50, 2);	// Wasn't sure what the max weight should be (not addressed in Assignment 1 or Project Iteration 1), had to look up: 23kg or 50lbs
		PlaceItemInBaggingArea testBaggingArea = new PlaceItemInBaggingArea(testStation);
		
		//Creation of Item
		Barcode testBarcode = new Barcode(new Numeral[] {Numeral.five, Numeral.eight, Numeral.three, Numeral.four});
		Item testItem = new BarcodedItem(testBarcode, 10);
		
		//Adding item onto baggage area
		testBaggingArea.addItem(testItem);
		
		//Expected Result
		ArrayList<Item> expectedList = new ArrayList<>();
		expectedList.add(testItem);
		
		//Actual Result
		ArrayList<Item> actualList = testBaggingArea.items;
		assertEquals(actualList, expectedList);
	}

	@Test
	public void testAddMultipleItems() {
		//Creation
	}
}
