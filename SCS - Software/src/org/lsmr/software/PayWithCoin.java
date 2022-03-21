package org.lsmr.software;

import java.math.BigDecimal;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CoinSlot;
import org.lsmr.selfcheckout.devices.CoinStorageUnit;
import org.lsmr.selfcheckout.devices.CoinTray;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.CoinSlotObserver;
import org.lsmr.selfcheckout.devices.observers.CoinStorageUnitObserver;
import org.lsmr.selfcheckout.devices.observers.CoinTrayObserver;
import org.lsmr.selfcheckout.devices.observers.CoinValidatorObserver;

public class PayWithCoin implements AbstractDeviceObserver, CoinStorageUnitObserver, CoinValidatorObserver, CoinSlotObserver, CoinTrayObserver {

	public boolean coinValidity = false;
    private SelfCheckoutStation CheckoutStation;
    
    //Constructor
    public PayWithCoin(SelfCheckoutStation station) {
        CheckoutStation=station;
    }
    public BigDecimal value = BigDecimal.valueOf(0);
    public boolean valid = false;
	public boolean isFull = false;
	
	
	
	
	/* EXPERIMENTAL
	 * // System accepts a coin
    public void accept(Coin coin)
	{
		if(valid == true && isFull == false)
		{
			value = (coin.getValue());
		}
	}
    */
    @Override
    public void invalidCoinDetected(CoinValidator validator) {
    	//
    }
    
    @Override
    public void validCoinDetected(CoinValidator validator, BigDecimal value) {
        coinValidity = true;
    }

    
    /*
     * 
     * Existing classes handles everything from this point on
     * 
     */
    
    @Override
    public void coinAdded(CoinStorageUnit unit) {
        // This is handled by coinStorageUnit class
    }
    @Override
    public void coinsFull(CoinStorageUnit unit) {
        // This is handled by the coinStorageUnit class
    }

    @Override
    public void coinsLoaded(CoinStorageUnit unit) {
        // This is handled by the coinStorageUnit class
    }

    @Override
    public void coinsUnloaded(CoinStorageUnit unit) {
        // This is handled by the coinStorageUnit class
    }

    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
        // This is handled by AbstractDevice
    }

    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
        // This is handled by AbstractDevice
    }

    @Override
    public void coinAdded(CoinTray tray) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void coinInserted(CoinSlot slot) {
        // TODO Auto-generated method stub
        
    }
	
}
