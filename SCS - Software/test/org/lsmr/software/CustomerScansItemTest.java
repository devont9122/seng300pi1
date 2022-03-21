package org.lsmr.software;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

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
		assertTrue(scanner.isDisabled());
		
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
	
		
	}
	
	@Test
	public void addProductToCartAfterBlockTest() {
		
	}
	
	@Test
	public void scanWhenBlockedTest() {     //this tests scanner rather than CustomerScansItem lol
		
		
	}
	
	@Test
	public void scanAfterBlockOveriddenTest() {
		scanner.disable();
		scan.overrideBlock(scanner);
		
	}
	
	@Test
	public void itemNotAddedToBaggingAreaTest() {
		
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
		
	}

}
