//PVRP PROBLEM
//CPSC 464
//AARON ROCKBURN; JOSHUA SARVER

//***********	DECLARATION_S_OTHER	**********************************************************************************\\
// FUNCTION_START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//PACKAGE TITLE
package edu.sru.thangiah.zeus.pvrp;


//IMPORTS
import edu.sru.thangiah.zeus.core.Depot;





//CLASS
public class PVRPDepot extends Depot
{

	//***********	DECLARATION_S	**********************************************************************************\\
	public PVRPDepot()
	{
		//set the attributes and the truck linked list
		setAttributes(new PVRPAttributes());
		setMainTrucks(new PVRPTruckLinkedList());
	}


	public PVRPDepot(int depotNumber, double xCoord, double yCoord)
	{
		setDepotNum(depotNumber);
		setXCoord(xCoord);
		setYCoord(yCoord);

		setAttributes(new PVRPAttributes());
		setMainTrucks(new PVRPTruckLinkedList());
	}


}
