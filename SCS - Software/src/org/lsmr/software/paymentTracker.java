package org.lsmr.software;

import org.lsmr.selfcheckout.devices.*;
import java.util.Locale;
import java.util.Currency;
import java.math.BigDecimal;

public class paymentTracker {
	//Initializing variables
	public final Locale country = new Locale("CANADA");
	public final Currency currency = Currency.getInstance(country);
	public final BigDecimal[] coinDenominations = {new BigDecimal("0.05"), new BigDecimal("0.10"), new BigDecimal("0.25"), new BigDecimal("1.00"), new BigDecimal("2.00")};
	public final int[] bankNoteDenominations = {5,10,20,50,100};
	
	
	private BigDecimal paidAmount = new BigDecimal("0");
	
	//Initializing Devices
	public SelfCheckoutStation station;
	public payWithCoin payingWithCoin = new payWithCoin();
	public payWithBankNote payingWithBankNote = new payWithBankNote();
	
	// Getting amount paid
	public BigDecimal getPaidAmount() {
		return this.paidAmount;
	}
	
	// Call this method to update paid amount in the observers
	public void addRemainingPayment(BigDecimal value) {
		this.paidAmount = this.paidAmount.add(value);
	}
}
