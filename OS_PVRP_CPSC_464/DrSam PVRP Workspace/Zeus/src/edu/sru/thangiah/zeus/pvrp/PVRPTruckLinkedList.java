//PVRP PROBLEM
//CPSC 464
//AARON ROCKBURN; JOSHUA SARVER

//***********	DECLARATION_S_OTHER	**********************************************************************************\\
// FUNCTION_START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//PACKAGE TITLE
package edu.sru.thangiah.zeus.pvrp;



//IMPORTS
import edu.sru.thangiah.zeus.core.TruckLinkedList;



//CLASS
public class PVRPTruckLinkedList extends TruckLinkedList implements java.io.Serializable, java.lang.Cloneable
{

	//***********	DECLARATION **********************************************************************************\\
	public PVRPTruckLinkedList()
	{
		//Housekeeping for the linked list
		setHead(new PVRPTruck()); //header node for head
		setTail(new PVRPTruck()); //tail node for tail
		linkHeadTail();              //point head and tail to each other

		//Assign the attributes
		setAttributes(new PVRPAttributes());
	}

}
