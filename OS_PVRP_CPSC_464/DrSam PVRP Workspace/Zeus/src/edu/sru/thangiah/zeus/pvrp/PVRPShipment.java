//PVRP PROBLEM
//CPSC 464
//AARON ROCKBURN; JOSHUA SARVER

//***********	DECLARATION_S_OTHER	**********************************************************************************\\
// FUNCTION_START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//PACKAGE TITLE
package edu.sru.thangiah.zeus.pvrp;


//IMPORTS
import edu.sru.thangiah.zeus.core.Shipment;





//CLASS
public class PVRPShipment extends Shipment
{

	//***********	CLASS_VARIABLES	*********************************************************************************\\
	private double extraVariable;


	//***********	DECLARATION_S	**********************************************************************************\\
	public PVRPShipment()
	{
	}


	public PVRPShipment(int customerNumber, double xCoordinate, double yCoordinate, double DUMMY, int demand, int frequency, int numberCombinations, int list[], int currentCombination[][])
	{
		setIndex(customerNumber);       //set node number
		setxCoord(xCoordinate);         //set x coordinate
		setyCoord(yCoordinate);            //set y coordinate
		setDemand(demand);                //set the demand for the node
		setFrequency(frequency);        //set the visit frequency for the node
		//set


		extraVariable = Math.random();
	}


	// GET_EXTRA_VARIABLE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public double getExtraVariable()
	{
		//get the next shipment from the shipment linked list
		//but return it as a VRPShipment
		return extraVariable;
	}
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

}
