//AARON ROCKBURN; JOSHUA SARVER
//CPSC 464
//PVRP PROBLEM


//PACKAGE TITLE
package edu.sru.thangiah.zeus.pvrp;

//IMPORTS
import edu.sru.thangiah.zeus.core.Depot;

public class PVRPDepot extends Depot {
	
	public PVRPDepot(int depotNumber, double xCoord, double yCoord){
		setDepotNum(depotNumber);
		setXCoord(xCoord);
		setYCoord(yCoord);

		setAttributes(new PVRPAttributes());
		setMainTrucks(new PVRPTrunkLinkedList());
	}




}
