package org.lsmr.software;

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
		
		//waits 5 seconds for customer to place item into bagging area before getting weight for product
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 *Since there isn't a way to get the expected weights of a product as of right now, I made the assumption that the weight increase in the bagging area 
		 *scale corresponds to the expected product weight
		 */
		while(getItemWeightFromBaggingArea() == 0) {
			barcodeScanner.disable();
		}
		barcodeScanner.enable();
		float productWeight = getItemWeightFromBaggingArea();
		shopCart.Add(product, productWeight);
		
		
	};
	
	
	/*
	 * To override a block due to a scanning discrepancy
	 */
	public void overrideBlock(BarcodeScanner barcodeScanner) {
		barcodeScanner.enable();
	}
	
	/*
	 *Since there isn't a way to get the expected weights of a product as of right now, I made the assumption that the weight increase in the bagging area 
	 *scale corresponds to the expected product weight
	 */
	public float lastBaggingAreaWeight = 0;
	/*
	 * this will be used to get a weight for a product to be put into the shopping cart
	 */
	public float getItemWeightFromBaggingArea() {
		float weight = 0;
		float itemWeight;
		try {
			weight = (float) station.scale.getCurrentWeight();
		
		} catch (OverloadException e) {
			e.printStackTrace();
		}
		
		itemWeight = weight - lastBaggingAreaWeight;
		lastBaggingAreaWeight = weight; 					//updates bagging area total weight to use to get weight of next item
		
		return itemWeight;
		
	}
	
	

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {}


}
