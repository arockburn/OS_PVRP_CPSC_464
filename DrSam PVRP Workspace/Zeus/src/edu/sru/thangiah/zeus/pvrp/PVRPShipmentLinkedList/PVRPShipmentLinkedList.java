//AARON ROCKBURN; JOSHUA SARVER
//CPSC 464
//PVRP PROBLEM


//PACKAGE TITLE
package edu.sru.thangiah.zeus.pvrp.PVRPShipmentLinkedList;


//IMPORTS
import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.core.Shipment;
import edu.sru.thangiah.zeus.core.ShipmentLinkedList;
import edu.sru.thangiah.zeus.pvrp.PVRPDepot;
import edu.sru.thangiah.zeus.pvrp.PVRPDepotLinkedList;
import edu.sru.thangiah.zeus.pvrp.PVRPShipment;

import java.io.PrintStream;

//import the parent class


//***********	CLASS	**********************************************************************************\\
public class PVRPShipmentLinkedList
		extends ShipmentLinkedList
		implements java.io.Serializable, java.lang.Cloneable
{


	//***********	CLASS_FUNCTIONS	**********************************************************************************\\
	public PVRPShipmentLinkedList()
	{
		setTail(new PVRPShipment());
		setHead(new PVRPShipment());
		linkHeadTail();
		setNumShipments(0);
	}


	//INSERT_SHIPMENT
	public void insertShipment(int node, double x, double y, double DUMMY, int demandQ, int frequency, int numberCombos, int[] comboList, int[][] currentCombo)
	{
		if (comboList.length <= ProblemInfo.MAX_COMBINATIONS)
		{
			//create an instance of the Shipment
			PVRPShipment thisShip = new PVRPShipment(node, x, y, DUMMY, demandQ, frequency, numberCombos, comboList, currentCombo);
			//add the instance to the linked list - in this case it is added at the end of the list
			//the total number of shipments is incremented in the insert
			insertLast(thisShip);
		} else
		{
			System.out.println("VRPShipmentLinkedList: Maximum number of combinations exceeded");
		}
	}//\\insert shipment


	//GET PVRP HEAD
	public PVRPShipment getPVRPHead()
	{
		return (PVRPShipment) getHead();
	}//\\get vrp head


	//GET PVRP TAIL
	public PVRPShipment getPVRPTail()
	{
		return (PVRPShipment) getTail();
	}


	//GET NEXT INSERT SHIPMENT
	public PVRPShipment getNextInsertShipment(PVRPDepotLinkedList currDepotLL, PVRPDepot currDepot, PVRPShipmentLinkedList currShipmentLL, PVRPShipment currShip)
	{

		PVRPShipmentLinkedList selectShip = (PVRPShipmentLinkedList) ProblemInfo.selectShipType;
		return selectShip.getSelectShipment(currDepotLL, currDepot, currShipmentLL, currShip);
	}//\\get next insert shipment


	//GET SELECT SHIPMENT
	public PVRPShipment getSelectShipment(PVRPDepotLinkedList currDepotLL, PVRPDepot currDepot, PVRPShipmentLinkedList currShipmentLL,
										  PVRPShipment currShip)
	{
		return null;
	}//\\get select shipment


	//PRINT PVRP SHIPMENTS TO CONSOLE
	public void printPVRPShipmentsToConsole()
	{
		System.out.println(this.getNumShipments());

		Shipment ship = super.getHead();
		PVRPShipment pvrpShip;
		while (ship != getTail())
		{
			pvrpShip = (PVRPShipment) ship;
			System.out.println(pvrpShip.getIndex() + " " + pvrpShip.getTruckTypeNeeded() + " " +
					pvrpShip.getDemand() + " " + pvrpShip.getXCoord() + " " +
					//ship.getYCoord() + " " + ship.getPickUpPointName() +
					pvrpShip.getYCoord() + " " +
					pvrpShip.getYCoord() + " " +
					pvrpShip.getExtraVariable()) ;
			ship = ship.getNext();
		}
	}//\\print pvrp shipments to console


	//WRITE PVRP SHIPMENTS
	public void writePVRPShipments(PrintStream out)
	{
		out.println(this.getNumShipments());


		Shipment ship = super.getHead();
		PVRPShipment pvrpShip;
		while (ship != getTail())
		{
			pvrpShip = (PVRPShipment) ship;
			out.println(pvrpShip.getIndex() + " " + pvrpShip.getTruckTypeNeeded() + " " +
					pvrpShip.getDemand() + " " + pvrpShip.getXCoord() + " " +
					//ship.getYCoord() + " " + ship.getPickUpPointName() +
					pvrpShip.getYCoord() + " " +
					pvrpShip.getExtraVariable());
			ship = ship.getNext();
		}
	}//\\write pvrp shipments

}

