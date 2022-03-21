package org.lsmr.software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.lsmr.software.*;
import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;

public class Checkout {
	
	private SelfCheckoutStation CheckoutStation;
	//private Product product;
	
	PayWithCoin coinPayment = new PayWithCoin();
	payWithBankNote banknotePayment = new payWithBankNote();
	ShoppingCartReceiptPrinter printReceipt = new ShoppingCartReceiptPrinter(CheckoutStation);

	//BigDecimal paymentTotal = paymentTracker.getPaidAmount();
	
	/*public BigDecimal displayPrice() 
	{
		//would get getTotalPrice from shopping cat 
		//and display to the user total price before they select which payment option 
		BigDecimal cartTotal = ShoppingCart.getInstance().getTotalPrice();
		return cartTotal;
	}*/

	
	public void branchToPayWithcoin()
	{
		
	}
	
	public void branchToPayWithBankNote()
	{
	
	}
	//Calls printReceipt method in ShoppingCartReceiptPrinter class 
	//This will print the receipt of the transaction
	public void printReceipt()
	{
		printReceipt.printReceipt();
	}
}
