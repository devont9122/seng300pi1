package org.lsmr.software;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.Product;
import org.lsmr.software.ShoppingCart.ShoppingCartEntry;

class ItemStub extends Item{
	ItemStub(double weightInGrams){
		super(weightInGrams);
	}
}




public class CustomerScansItemTest {
	
	CustomerScansItem scan;
	
	ProductDatabase database = ProductDatabase.Instance;
	ShoppingCart cart = ShoppingCart.Instance;
	Currency currency = Currency.getInstance("CAD");
	int[] banknoteDenominations = {1, 2, 5, 10};
	BigDecimal[] coinDenominations = {BigDecimal.TEN};
	SelfCheckoutStation station = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, 10, 2);
	
	
	@Before
	public void setUp() {
		scan = new CustomerScansItem(station, cart, database);
		station.scanner.attach(scan);
		station.scanner.enable();
		station.scale.enable();
		
		cart.Empty();
		scan.emptyStatus();
		database.clearDatabase();
	
	}
	
	@Test
	public void barcodeHasNoProductTest() {
		Numeral[] n = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b = new Barcode(n);
		BarcodedItem item = new BarcodedItem(b, 2.0);
		station.scanner.scan(item);
	
		assertTrue(station.scanner.isDisabled());
	
		
		scan.emptyStatus();
	}
	
	@Test
	public void waitingInterruptedTest() {
		
		
	}
	
	@Test
	public void addProductToCartTest() {
		Numeral[] n = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b = new Barcode(n);
		BarcodedItem item = new BarcodedItem(b, 2.0);
		
		BarcodedProduct product = new BarcodedProduct(b, "a product", BigDecimal.valueOf(1.99));
		database.RegisterProducts(product);
		
		station.scanner.scan(item);
		station.scale.add(item);
		
		
		 List<ShoppingCartEntry> cartEntries = cart.getEntries();
		 assertFalse(cartEntries.isEmpty());
		 
		 cart.Empty();
		 scan.emptyStatus();
		 station.scale.remove(item);
	
		
	}
	
	@Test
	public void addProductToCartAfterBlockTest() {
		Numeral[] n1 = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b1 = new Barcode(n1);
		BarcodedItem item1 = new BarcodedItem(b1, 2.0);
		station.scanner.scan(item1);
		
		Numeral[] n2 = {Numeral.three, Numeral.two, Numeral.one};
		Barcode b2 = new Barcode(n2);
		BarcodedItem item2 = new BarcodedItem(b2, 2.0);
		station.scanner.scan(item2);
		
		assertEquals(scan.getScanStatus(b2), null);
		
		cart.Empty();
		scan.emptyStatus();
	}
	
	@Test
	public void scanDuplicateProducts() {     //this tests scanner rather than CustomerScansItem lol
		Numeral[] n1 = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b1 = new Barcode(n1);
		BarcodedItem item1 = new BarcodedItem(b1, 2.0);
		BarcodedProduct product = new BarcodedProduct(b1, "a product", BigDecimal.valueOf(1.99));
		database.RegisterProducts(product);
		
		BarcodedItem item2 = new BarcodedItem(b1, 2.0);
		
		
		station.scanner.scan(item1);
		station.scale.add(item1);
	
		station.scale.remove(item1);
		
		
		station.scanner.scan(item2);
		station.scale.add(item2);
		
		station.scale.remove(item2);
		
		assertEquals(cart.getEntries().size(), 2);
		cart.Empty();
		scan.emptyStatus();
	}
	
	@Test
	public void scanAfterBlockOveriddenTest() {
		station.scanner.disable();
		scan.overrideBlock(station.scanner);
		
		Numeral[] n = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b = new Barcode(n);
		BarcodedItem item = new BarcodedItem(b, 2.0);
		
		BarcodedProduct product = new BarcodedProduct(b, "a product", BigDecimal.valueOf(1.99));
		database.RegisterProducts(product);
		
		station.scanner.scan(item);
		station.scale.add(item);
		
		 List<ShoppingCartEntry> cartEntries = cart.getEntries();
	
		 assertFalse(cartEntries.isEmpty());
		
		 cart.Empty();
		 station.scale.remove(item);
	}
	
	/*
	@Test
	public void itemNotAddedToBaggingAreaTest() {
		Numeral[] n = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b = new Barcode(n);
		BarcodedItem item = new BarcodedItem(b, 2.0);
		
		BarcodedProduct product = new BarcodedProduct(b, "a product", BigDecimal.valueOf(1.99));
		database.RegisterProducts(product);
		
		station.scanner.scan(item);
	
		assertTrue(station.scanner.isDisabled());
		
		scan.emptyStatus();
	}
	*/
	
	/*
	@Test(expected = OverloadException.class)
	public void currentWeightOverLimitTest() throws OverloadException {
		ItemStub item = new ItemStub(15.0);
		station.scale.add(item);
		scan.getItemWeightFromBaggingArea();
		station.scale.remove(item);
	}
	*/
	/*
	@Test
	public void itemWeightCalculatedTest() {
		ItemStub item = new ItemStub(5.0);
		station.scale.add(item);
		float weight = scan.getItemWeightFromBaggingArea();
		assertEquals(weight, 5.0f, 0.001);
		station.scale.remove(item);
		
	}
	*/
	
	@Test
	public void multipleProductTest() {
		Numeral[] n = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b = new Barcode(n);
		BarcodedItem item = new BarcodedItem(b, 2.0);
		
		BarcodedProduct product = new BarcodedProduct(b, "a product", BigDecimal.valueOf(1.99));
		database.RegisterProducts(product);
		
		station.scanner.scan(item);
		
		Numeral[] n1 = {Numeral.two, Numeral.two, Numeral.three};
		Barcode b1 = new Barcode(n1);
		BarcodedItem item1 = new BarcodedItem(b1, 3.0);

		BarcodedProduct product1 = new BarcodedProduct(b1, "a product", BigDecimal.valueOf(1.99));
		database.RegisterProducts(product1);
		
		station.scanner.scan(item1);

		assertEquals(cart.getEntries().size(), 2);
	
		cart.Empty();
		scan.emptyStatus();
		
		
	}

}
