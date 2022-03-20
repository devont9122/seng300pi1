package org.lsmr.software;

import java.math.BigDecimal;
import java.util.List;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ReceiptPrinterObserver;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;

public class ShoppingCartReceiptPrinter implements ReceiptPrinterObserver {
	private SelfCheckoutStation station;
	
	private boolean noPaper;
	private boolean noInk;
	

	public ShoppingCartReceiptPrinter(SelfCheckoutStation station) {
		noPaper = false;
		noInk = false;
		
		this.station = station;
		station.printer.attach(this);
	}
	
	public void printReceipt() {
		List<ShoppingCart.ShoppingCartEntry> cart = ShoppingCart.getInstance().getEntries();
		
		// print a header?
		
		// Print each item line by line
		for(ShoppingCart.ShoppingCartEntry entry : cart) {
			if(canPrint()) {
				Product prod = entry.getProduct();
				BigDecimal price = entry.getPrice();
				
				// Refactor in HW: both barcoded and PLU product classes have a description field; move up into product class?
				String prodDesc;
				if(prod.getClass() == BarcodedProduct.class) {
					prodDesc = ((BarcodedProduct)prod).getDescription();
				}
				else if(prod.getClass() == PLUCodedProduct.class) {
					prodDesc = ((PLUCodedProduct)prod).getDescription();
				}
				else {
					// explode
					prodDesc = "";
				}
				
				for(int i = 0; i < prodDesc.length(); i++) {
					if(canPrint())
						station.printer.print(prodDesc.charAt(i));
					else {
						//
					}
				}
				
				String priceStr = price.toString();
				for(int i = 0; i < 3; i++)
					station.printer.print(' ');
				
				station.printer.print('$');
				for(int i = 0; i < priceStr.length(); i++) {
					station.printer.print(priceStr.charAt(i));
				}
				
				// New line
				station.printer.print('\n');
			}
			else {
				// explode
			}
		}
		
		// End message
		
		// Cut the receipt
		station.printer.cutPaper();
	}
	
	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void outOfPaper(ReceiptPrinter printer) {
		noPaper = true;
	}

	@Override
	public void outOfInk(ReceiptPrinter printer) {
		noInk = true;
	}

	@Override
	public void paperAdded(ReceiptPrinter printer) {
		noPaper = false;
	}

	@Override
	public void inkAdded(ReceiptPrinter printer) {
		noInk = false;
	}
	
	private boolean canPrint() {
		return !(noPaper && noInk);
	}

}
