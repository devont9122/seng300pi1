package org.lsmr.software;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BarcodeScannerObserver;
import org.lsmr.selfcheckout.products.BarcodedProduct;

public class CustomerScansItem implements BarcodeScannerObserver {
	public SelfCheckoutStation station ;  
	public ShoppingCart shopCart;
	public ProductDatabase database;
	
	private Map<Barcode, Integer> scanStatus = new HashMap<>();
	
	
	public CustomerScansItem(SelfCheckoutStation s, ShoppingCart c, ProductDatabase d) {
		station = s;
		shopCart = c;
		database = d;
	}
	
	/**
	 * An event announcing that the indicated barcode has been successfully scanned.
	 * 
	 * @param barcodeScanner
	 *            The device on which the event occurred.
	 * @param barcode
	 *            The barcode that was read by the scanner.
	 */
	public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
		
		
		if (scanStatus.get(barcode) == null) {
			scanStatus.put(barcode, 1);
		}
		else {
			int quantity = scanStatus.get(barcode);
			scanStatus.replace(barcode, quantity + 1 );
		}
		
		scanItem(barcodeScanner, barcode);
		
	};
	
	public void scanItem(BarcodeScanner barcodeScanner, Barcode barcode) {
		BarcodedProduct product = null;
		if (database.LookupItemViaBarcode(barcode) != null) {
			product = database.LookupItemViaBarcode(barcode);
		}
		//barcode does not map to a product, discrepancy
		if(database.LookupItemViaBarcode(barcode) == null) {	
			barcodeScanner.disable();
			return;
		}

		shopCart.Add(product, 1);
		
	}
	
	
	public Integer getScanStatus(Barcode barcode) {
		return scanStatus.get(barcode);
	}
	
	public void emptyStatus() {
		scanStatus.clear();
	}
	
	
	/*
	 * To override a block due to a scanning discrepancy
	 */
	public void overrideBlock(BarcodeScanner barcodeScanner) {
		barcodeScanner.enable();
	}
	

	

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {}


}
