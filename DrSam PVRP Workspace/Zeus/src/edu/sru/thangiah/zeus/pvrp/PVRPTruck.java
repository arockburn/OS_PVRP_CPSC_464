package edu.sru.thangiah.zeus.pvrp;

import edu.sru.thangiah.zeus.core.Truck;

/**
 * Created by joshuasarver on 9/13/2014.
 */
public class PVRPTruck extends Truck {
	
	private int capacity, maxDist, truckNum;
	public PVRPTruck(){
		
	}
	
	public void setCapacity(int val){
		capacity = val;
	}
	
	public void setMaxDist(int val){
		maxDist = val;
	}
	
	public void setTruckNum(int val){
		truckNum = val;
	}
}
