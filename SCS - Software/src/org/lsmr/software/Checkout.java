package org.lsmr.software;

import org.lsmr.software.*;
import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;


public class Checkout {
	private SelfCheckoutStation CheckoutStation;
	
	PayWithCoin coinPayment = new PayWithCoin(CheckoutStation);
	payWithBankNote banknotePayment = new payWithBankNote();
	ShoppingCartReceiptPrinter printReceipt = new ShoppingCartReceiptPrinter(CheckoutStation);
	paymentTracker paymentTracker = new paymentTracker();
	 
	//ShoppingCart ShoppingCart = new ShoppingCart();//
	
	
	public void displayPrice() //maybe
	{
		//would get getTotalPrice from shopping cat 
		//and display to the user total price before they select which payment option 
	}

	public void displayCart() //maybe
	{
		//would get Cart contents from shopping cat 
		//and display to the user before they select which payment option 
	}
	
	
	
	
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