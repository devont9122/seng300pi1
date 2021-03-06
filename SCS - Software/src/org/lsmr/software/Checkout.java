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

	private SelfCheckoutStation station;
	//private Product product;

	PayWithCoin coinPayment;
	payWithBankNote banknotePayment;
	ShoppingCartReceiptPrinter printReceipt;

	public Checkout(SelfCheckoutStation station) {
		this.station = station;
		coinPayment = new PayWithCoin(station);
		banknotePayment = new payWithBankNote(station);
		printReceipt = new ShoppingCartReceiptPrinter(station);
	}

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
		BigDecimal totalPrice = ShoppingCart.getInstance().getTotalPrice();
		coinPayment.totalPaid = BigDecimal.ZERO; // Reset total amount paid
		while(coinPayment.totalPaid.compareTo(totalPrice) == -1) {
			// Wait?
		}
	}

	public void branchToPayWithBankNote()
	{
		//payment.add(banknotePayment.totalPaid);
		BigDecimal totalPrice = ShoppingCart.getInstance().getTotalPrice();
		banknotePayment.totalPaid = BigDecimal.ZERO;
		while(banknotePayment.totalPaid.compareTo(totalPrice) == -1) {
			// Wait?
		}
	}
	//Calls printReceipt method in ShoppingCartReceiptPrinter class
	//This will print the receipt of the transaction
	public void printReceipt()
	{
		printReceipt.printReceipt();
	}
}
