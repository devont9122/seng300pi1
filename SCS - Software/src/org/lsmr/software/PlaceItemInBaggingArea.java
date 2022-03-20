package org.lsmr.software;

import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;
import java.util.ArrayList;

public class PlaceItemInBaggingArea implements ElectronicScaleObserver {
	public SelfCheckoutStation station;
	public ArrayList<Item> items = new ArrayList<>();
	public boolean isAdded = false;
	public boolean isRemoved = false;
	public boolean isOverloaded = false;
	
	
	public void addItem(Item anItem) {
		station.scale.add(anItem);
		if (isAdded || isOverloaded) {
			items.add(anItem);
			isOverloaded = false;
			isAdded = false;
		}
	}
	
	public void removeItem(Item anItem) {
		station.scale.remove(anItem);
		if (isRemoved || isAdded) {
			int index = items.indexOf(anItem);
			items.remove(index);
			isRemoved = false;
			isAdded = false;
		}
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
	public void weightChanged(ElectronicScale scale, double weightInGrams) {
		isAdded = true;
	}

	@Override
	public void overload(ElectronicScale scale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void outOfOverload(ElectronicScale scale) {
		// TODO Auto-generated method stub
		
	}
}
