package org.lsmr.software;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.BarcodedProduct;

public class ShoppingCartReceiptPrinterTest {
	SelfCheckoutStation station;
	ShoppingCartReceiptPrinter printer;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		// Set up a test product database
		Barcode bc = new Barcode(new Numeral[] {Numeral.zero});
		
		BarcodedProduct[] products = new BarcodedProduct[] {
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.zero}), "Box of Biscuits", new BigDecimal("10.99")),
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.one}), "Milk 2% 4L", new BigDecimal("4.88")),
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.two}), "Magnesium 150mg", new BigDecimal("6.99")),
				new BarcodedProduct(new Barcode(new Numeral[] {Numeral.three}), "GTX 3090", new BigDecimal("0.99"))
		};
		ProductDatabase.Instance.RegisterProducts(products);
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		ProductDatabase.Instance.clearDatabase();
	}

	@Before
	public void setUp() throws Exception {
		station = new SelfCheckoutStation(Currency.getInstance(Locale.CANADA), new int[] {5,10,20,50,100}, new BigDecimal[] {new BigDecimal("0.05"), new BigDecimal("0.10"), new BigDecimal("0.25"), new BigDecimal("1.00"), new BigDecimal("2.00")}, 1000, 1);
		printer = new ShoppingCartReceiptPrinter(station);
		
		ShoppingCart.getInstance().Empty();
	}

	@Test
	public void testPrintSimpleReceipt() {
		// Load paper and ink
		station.printer.addInk(ReceiptPrinter.MAXIMUM_INK);
		station.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER);
		
		Barcode bc = createBarcodeFromString("0");
		ShoppingCart.getInstance().Add(ProductDatabase.Instance.LookupItemViaBarcode(bc), 0);
		
		printer.printReceipt();
		
		String receipt = station.printer.removeReceipt();
		
		System.out.print(receipt);
		
		assertEquals("Number of lines not correct", 1, receipt.chars().filter(ch -> ch == '\n').count());
	}
	
	private Barcode createBarcodeFromString(String barcode) {
		Numeral[] numerals = new Numeral[barcode.length()];
		
		for(int i = 0; i < numerals.length; i++) {
			Byte b = Byte.valueOf(Character.toString(barcode.charAt(i)));
			numerals[i] = Numeral.valueOf(b);
		}
		
		return new Barcode(numerals);
	}

}
