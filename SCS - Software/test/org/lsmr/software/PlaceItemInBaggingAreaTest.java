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
		//Creation of the Self-Checkout Machine & Bagging Area
		Currency testCurrency = Currency.getInstance("CAD");
		int[] testBanknoteDenominations = {1, 2, 5};
		BigDecimal[] testCoinDenominations = {BigDecimal.TEN};
		SelfCheckoutStation testStation = new SelfCheckoutStation(testCurrency, testBanknoteDenominations, testCoinDenominations, 50, 2);	// Wasn't sure what the max weight should be (not addressed in Assignment 1 or Project Iteration 1), had to look up: 23kg or 50lbs
		PlaceItemInBaggingArea testBaggingArea = new PlaceItemInBaggingArea(testStation);
				
		//Creation of Items
		Barcode testBarcode1 = new Barcode(new Numeral[] {Numeral.three, Numeral.two, Numeral.seven, Numeral.one});
		Item testItem1 = new BarcodedItem(testBarcode1, 10);
		Barcode testBarcode2 = new Barcode(new Numeral[] {Numeral.six, Numeral.five, Numeral.five, Numeral.eight});
		Item testItem2 = new BarcodedItem(testBarcode2, 6);
		
		//Adding items onto baggage area
		testBaggingArea.addItem(testItem1);
		testBaggingArea.addItem(testItem2);
				
		//Expected Result
		ArrayList<Item> expectedList = new ArrayList<>();
		expectedList.add(testItem1);
		expectedList.add(testItem2);
				
		//Actual Result
		ArrayList<Item> actualList = testBaggingArea.items;
		assertEquals(actualList, expectedList);
	}
	
	// Item is overweight
	@Test
	public void testAddOverloadItem() {
		//Creation of the Self-Checkout Machine & Bagging Area
		Currency testCurrency = Currency.getInstance("CAD");
		int[] testBanknoteDenominations = {1, 2, 5};
		BigDecimal[] testCoinDenominations = {BigDecimal.TEN};
		SelfCheckoutStation testStation = new SelfCheckoutStation(testCurrency, testBanknoteDenominations, testCoinDenominations, 50, 2);
		PlaceItemInBaggingArea testBaggingArea = new PlaceItemInBaggingArea(testStation);
				
		//Creation of Item
		Barcode testBarcode = new Barcode(new Numeral[] {Numeral.one, Numeral.two, Numeral.three, Numeral.four});
		Item testItem = new BarcodedItem(testBarcode, 550);
				
		//Adding item onto baggage area
		testBaggingArea.addItem(testItem);
				
		//Expected Result
		ArrayList<Item> expectedList = new ArrayList<>();
		expectedList.add(testItem);
				
		//Actual Result
		ArrayList<Item> actualList = testBaggingArea.items;
		assertEquals(actualList, expectedList);
	}
	
	//Item is under-weight
	@Test
	public void testAddUnderweighItem() {
		//Creation of the Self-Checkout Machine & Bagging Area
		Currency testCurrency = Currency.getInstance("CAD");
		int[] testBanknoteDenominations = {1, 2, 5};
		BigDecimal[] testCoinDenominations = {BigDecimal.TEN};
		SelfCheckoutStation testStation = new SelfCheckoutStation(testCurrency, testBanknoteDenominations, testCoinDenominations, 50, 2);	// Wasn't sure what the max weight should be (not addressed in Assignment 1 or Project Iteration 1), had to look up: 23kg or 50lbs
		PlaceItemInBaggingArea testBaggingArea = new PlaceItemInBaggingArea(testStation);
				
		//Creation of Item
		Barcode testBarcode = new Barcode(new Numeral[] {Numeral.five, Numeral.eight, Numeral.three, Numeral.four});
		Item testItem = new BarcodedItem(testBarcode, 1);
				
		//Adding item onto baggage area
		testBaggingArea.addItem(testItem);
				
		//Expected Result
		ArrayList<Item> expectedList = new ArrayList<>();
				
		//Actual Result
		ArrayList<Item> actualList = testBaggingArea.items;
		assertEquals(actualList, expectedList);
	}
}
