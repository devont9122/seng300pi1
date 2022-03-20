package org.lsmr.software;

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
	public ShoppingCart shopCart = ShoppingCart.Instance;
	public ProductDatabase database;
	
	
	
	/**
	 * An event announcing that the indicated barcode has been successfully scanned.
	 * 
	 * @param barcodeScanner
	 *            The device on which the event occurred.
	 * @param barcode
	 *            The barcode that was read by the scanner.
	 */
	public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
		
		BarcodedProduct product = null;
		if (database.LookupItemViaBarcode(barcode) != null) {
			product = database.LookupItemViaBarcode(barcode);
		}
		//barcode does not map to a product, discrepancy
		else {	
			barcodeScanner.disable();
		}
		
		/*
		 *Since there isn't a way to get the expected weights of a product, I made the assumption that the weight increase in the bagging area 
		 *scale corresponds to the expected product weight weight
		 */
		float weight = 0;
		try {
			weight = (float) station.scale.getCurrentWeight();
		
		} catch (OverloadException e) {
			e.printStackTrace();
		}
		
		shopCart.Add(product, weight);
		
	
	};
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
