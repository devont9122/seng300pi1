package org.lsmr.software;

import java.math.BigDecimal;

import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.CoinStorageUnit;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.CoinDispenserObserver;
import org.lsmr.selfcheckout.devices.observers.CoinStorageUnitObserver;
import org.lsmr.selfcheckout.devices.observers.CoinValidatorObserver;

public class PayWithCoin {

	public SelfCheckoutStation station;
	public boolean coinValidity = false;
	public boolean fullStorage = false;
	private BigDecimal coinValue = BigDecimal.ZERO;
	
	public BigDecimal totalPaid = BigDecimal.ZERO;
	
	private class CoinDispenserWatcher implements CoinDispenserObserver {

		@Override
		public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void coinsFull(CoinDispenser dispenser) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void coinsEmpty(CoinDispenser dispenser) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void coinAdded(CoinDispenser dispenser, Coin coin) {
			// TODO Auto-generated method stub
			coinAccepted();
		}

		@Override
		public void coinRemoved(CoinDispenser dispenser, Coin coin) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void coinsLoaded(CoinDispenser dispenser, Coin... coins) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void coinsUnloaded(CoinDispenser dispenser, Coin... coins) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public PayWithCoin(SelfCheckoutStation station) {
		this.station = station;
		station.coinValidator.attach(observeCoin);
		station.coinStorage.attach(observeStorage);
		
		// Create observers for each coin dispenser to know when a coin has been deposited
		BigDecimal[] coinDenoms = SelfCheckoutStationSetup.coinDenoms;
		for(BigDecimal denom : coinDenoms)
			station.coinDispensers.get(denom).attach(new CoinDispenserWatcher());
	}
	
	/*public void accept(Coin coin)
    {
        if(coinValidity == true && fullStorage == false)
        {
            value = coin.getValue();
        }
    }*/
	
	private void coinAccepted() {
		totalPaid = totalPaid.add(coinValue);
	}
	
	public CoinValidatorObserver observeCoin = new CoinValidatorObserver() {

		@Override
		public void validCoinDetected(CoinValidator validator, BigDecimal value) {
			coinValidity = true;
			coinValue = value;
		}

		@Override
		public void invalidCoinDetected(CoinValidator validator) {
			coinValidity = false;
			
		}
		
		@Override
		public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public CoinStorageUnitObserver observeStorage = new CoinStorageUnitObserver() {
		@Override
		public void coinsFull(CoinStorageUnit unit) {
			fullStorage = true;
			
		}
		
		@Override
		public void coinsUnloaded(CoinStorageUnit unit) {
			fullStorage = false;
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
		public void coinAdded(CoinStorageUnit unit) {
			// TODO Auto-generated method stub
			coinAccepted();
		}

		@Override
		public void coinsLoaded(CoinStorageUnit unit) {
			// TODO Auto-generated method stub
			
		}
		
	};

}
