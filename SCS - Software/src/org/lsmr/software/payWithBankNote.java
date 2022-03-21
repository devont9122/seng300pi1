package org.lsmr.software;

import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteStorageUnitObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteValidatorObserver;

public class payWithBankNote{

		public SelfCheckoutStation station;
		public boolean valid = false;
		public boolean isFull = false;
		
		//payment tracker would look at this value
		public BigDecimal value = BigDecimal.valueOf(0);
		
		public void accept(Banknote bNote)
		{
			if(valid == true && isFull == false)
			{
				value = BigDecimal.valueOf(bNote.getValue());
			}
		}
		
		public BanknoteValidatorObserver listener = new BanknoteValidatorObserver() {

			@Override
			public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void validBanknoteDetected(BanknoteValidator validator, Currency currency, int value) {
				// TODO Auto-generated method stub
				valid = true;
				
			}

			@Override
			public void invalidBanknoteDetected(BanknoteValidator validator) {
				// TODO Auto-generated method stub
				valid = false;
			}
			
				
		};
		
		public BanknoteStorageUnitObserver listener2 = new BanknoteStorageUnitObserver () 
		{

			@Override
			public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void banknotesFull(BanknoteStorageUnit unit) {
				// TODO Auto-generated method stub
				isFull = true;
			}

			@Override
			public void banknoteAdded(BanknoteStorageUnit unit) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void banknotesLoaded(BanknoteStorageUnit unit) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void banknotesUnloaded(BanknoteStorageUnit unit) {
				// TODO Auto-generated method stub
				isFull = false;
			}
			
		};
		

}
