package org.lsmr.software;

import java.math.BigDecimal;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CoinStorageUnit;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.CoinStorageUnitObserver;
import org.lsmr.selfcheckout.devices.observers.CoinValidatorObserver;


public class payWithCoin implements AbstractDeviceObserver, CoinStorageUnitObserver, CoinValidatorObserver {

	private SelfCheckoutStation station;
	public BigDecimal value;
	
	@Override
	public void validCoinDetected(CoinValidator validator, BigDecimal value) {
		this.value = value;
	}

	@Override
	public void invalidCoinDetected(CoinValidator validator) {
		// This is handled by the coinValidator class
	}

	@Override
	public void coinsFull(CoinStorageUnit unit) {
		// This is handled by the coinStorageUnit class
	}

	@Override
	public void coinAdded(CoinStorageUnit unit) {
		//
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
	
}
