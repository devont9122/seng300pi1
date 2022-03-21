	package org.lsmr.software;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

public class paymentTracker {
	//Initializing variables
	public final Currency currency = Currency.getInstance(Locale.CANADA);
	public final BigDecimal[] coinDenominations = {new BigDecimal("0.05"), new BigDecimal("0.10"), new BigDecimal("0.25"), new BigDecimal("1.00"), new BigDecimal("2.00")};
	public final int[] bankNoteDenominations = {5,10,20,50,100};
	
	
	private BigDecimal paidAmount = new BigDecimal("0");
	public boolean coinValidity = false;
	
	//Initializing Devices
	private SelfCheckoutStation station = new SelfCheckoutStation(currency, bankNoteDenominations, coinDenominations, 1, 1);
	public PayWithCoin payingWithCoin = new PayWithCoin();
	public payWithBankNote payingWithBankNote = new payWithBankNote();
	
	
	// coin being inserted
	public BigDecimal insertCoin(Coin coin){
		BigDecimal coinsInserted = new BigDecimal("0");
		
		try {
			station.coinSlot.accept(coin);
			if (coinValidity == true) {
				coinsInserted = coinsInserted.add(coin.getValue());
				coinValidity = false;
			}
		} catch (Exception e) {
			return coinsInserted;
		}
		return coinsInserted;
	}
	
	
	// Getting amount paid
	public BigDecimal getPaidAmount() {
		return this.paidAmount;
	}
	
	
//	public void addRemainingPayment(BigDecimal value) {
//		this.paidAmount = this.paidAmount.add(value);
//	}
	
	
	// Call this method to update paid amount in the observers
	public void addPayment() {
		this.paidAmount = this.paidAmount.add(payingWithBankNote.value);
		this.paidAmount = this.paidAmount.add(payingWithCoin.totalPaid);
	}
	
	
	
}
