package edu.sru.thangiah.zeus.pvrp;

import edu.sru.thangiah.zeus.core.Shipment;
import edu.sru.thangiah.zeus.gui.ShipmentFrame;

/**
 * Created by joshuasarver on 9/13/2014.
 */
public class PVRPShipment extends Shipment {

	public PVRPShipment() {
	}


	public PVRPShipment(int customerNumber, double xCoordinate, double yCoordinate, int demand, int frequency)
	{
		setIndex(customerNumber);
		setxCoord(xCoordinate);
		setyCoord(yCoordinate);
		setDemand(demand);
		setFrequency(frequency);

	}

}
