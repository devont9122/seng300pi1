package org.lsmr.software;

import static org.junit.Assert.*;

import java.math.BigDecimal;
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
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.Product;
import org.lsmr.software.ShoppingCart.ShoppingCartEntry;

class ItemStub extends Item{
	ItemStub(double weightInGrams){
		super(weightInGrams);
	}
}

public class CustomerScansItemTest {
	BarcodeScanner scanner;
	CustomerScansItem scan;
	ElectronicScale scale;
	ProductDatabase database = ProductDatabase.Instance;
	ShoppingCart cart = ShoppingCart.Instance;
	
	
	@Before
	public void setUp() {
		scanner = new BarcodeScanner();
		scan = new CustomerScansItem();
		scale = new ElectronicScale(10, 2);
	
	}
	
	@Test
	public void barcodeHasNoProductTest() {
		Numeral[] n = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b = new Barcode(n);
		BarcodedItem item = new BarcodedItem(b, 2.0);
		scanner.scan(item);
		if(scan.getScanStatus(b).equals(1)) {
			assertTrue(scanner.isDisabled());
		}
		
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
		
		scanner.scan(item);
		scale.add(item);
		
		
		 List<ShoppingCartEntry> cartEntries = cart.getEntries();
		 Product cartProduct = cartEntries.get(0).getProduct();
		 assertSame(product, cartProduct);
		 
		 cart.Empty();
	
		
	}
	
	@Test
	public void addProductToCartAfterBlockTest() {
		Numeral[] n1 = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b1 = new Barcode(n1);
		BarcodedItem item1 = new BarcodedItem(b1, 2.0);
		scanner.scan(item1);
		
		Numeral[] n2 = {Numeral.three, Numeral.two, Numeral.one};
		Barcode b2 = new Barcode(n2);
		BarcodedItem item2 = new BarcodedItem(b2, 2.0);
		scanner.scan(item2);
		
		assertEquals(scan.getScanStatus(b2), null);
		
		cart.Empty();
		scan.emptyStatus();
	}
	
	@Test
	public void scanDuplicateItems() {     //this tests scanner rather than CustomerScansItem lol
		Numeral[] n1 = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b1 = new Barcode(n1);
		BarcodedItem item1 = new BarcodedItem(b1, 2.0);
		scanner.scan(item1);
		scale.add(item1);
		
		scanner.scan(item1);
		scale.add(item1);
		
		assertEquals(cart.getEntries().get(0).getProduct(), cart.getEntries().get(1).getProduct());
		cart.Empty();
		scan.emptyStatus();
	}
	
	@Test
	public void scanAfterBlockOveriddenTest() {
		scanner.disable();
		scan.overrideBlock(scanner);
		
		Numeral[] n = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b = new Barcode(n);
		BarcodedItem item = new BarcodedItem(b, 2.0);
		
		BarcodedProduct product = new BarcodedProduct(b, "a product", BigDecimal.valueOf(1.99));
		database.RegisterProducts(product);
		
		scanner.scan(item);
		scale.add(item);
		
		 List<ShoppingCartEntry> cartEntries = cart.getEntries();
		 Product cartProduct = cartEntries.get(0).getProduct();
		 assertSame(product, cartProduct);
		
		 cart.Empty();
	}
	
	@Test
	public void itemNotAddedToBaggingAreaTest() {
		Numeral[] n = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b = new Barcode(n);
		BarcodedItem item = new BarcodedItem(b, 2.0);
		
		BarcodedProduct product = new BarcodedProduct(b, "a product", BigDecimal.valueOf(1.99));
		database.RegisterProducts(product);
		
		scanner.scan(item);
		try {
			TimeUnit.SECONDS.sleep(6);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertTrue(scanner.isDisabled());
		
		
	}
	
	@Test(expected = OverloadException.class)
	public void currentWeightOverLimitTest() throws OverloadException {
		ItemStub item = new ItemStub(15.0);
		scale.add(item);	
		
	}
	
	@Test
	public void itemWeightCalculatedTest() {
		ItemStub item = new ItemStub(5.0);
		scale.add(item);
		float weight = scan.getItemWeightFromBaggingArea();
		assertEquals(weight, 5.0f, 0.001);
		
	}
	
	@Test
	public void multipleItemWeightCalculatedTest() {
		Numeral[] n = {Numeral.one, Numeral.two, Numeral.three};
		Barcode b = new Barcode(n);
		BarcodedItem item = new BarcodedItem(b, 2.0);
		
		BarcodedProduct product = new BarcodedProduct(b, "a product", BigDecimal.valueOf(1.99));
		database.RegisterProducts(product);
		
		scale.add(item);
		scanner.scan(item);
		
		Numeral[] n1 = {Numeral.two, Numeral.two, Numeral.three};
		Barcode b1 = new Barcode(n1);
		BarcodedItem item1 = new BarcodedItem(b1, 3.0);
		
		scale.add(item1);
		assertEquals(scan.getItemWeightFromBaggingArea(), 5.0f, 0.001);
		
		
		
	}

}
