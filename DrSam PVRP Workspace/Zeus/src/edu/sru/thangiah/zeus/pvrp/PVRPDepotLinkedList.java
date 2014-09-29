//PVRP PROBLEM
//CPSC 464
//AARON ROCKBURN; JOSHUA SARVER

//***********	DECLARATION_S_OTHER	**********************************************************************************\\
// FUNCTION_START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//PACKAGE TITLE

package edu.sru.thangiah.zeus.pvrp;


//IMPORTS
import edu.sru.thangiah.zeus.core.DepotLinkedList;
import java.io.Serializable;





//CLASS
public class PVRPDepotLinkedList extends DepotLinkedList implements Serializable, Cloneable
{


	//***********	DECLARATION_S	**********************************************************************************\\
	public PVRPDepotLinkedList()
	{
		setHead(new PVRPDepot());   //head node
		setTail(new PVRPDepot());   //tail node
		linkHeadTail();             //link head and tail together

		//setAttributes(new PVRPAttributes());
	}



	// INSERT_SHIPMENT >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public boolean insertShipment(PVRPShipment theShipment)
	{
		boolean status = false;

		PVRPDepot depot = (PVRPDepot) super.getHead().getNext();
		//PVRPTrunkLinkedList truckLL = (PVRPTruckLinkedList) depot.getMainTrucks();

		while (depot != this.getTail())
		{
			//truckLL = (PVRPTrunkLinkedList) depot.getMainTrucks();

			//status = truckLL.insertShipment(theShipment);

			if (status == true)
			{
				break;
			}
			depot = (PVRPDepot) depot.getNext();
		}
		return status;
	}
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	// GET_PVRP_HEAD >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public PVRPDepot getPVRPHead()
	{
		return (PVRPDepot) getHead();
	}
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	// CLONE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public Object clone()
	{
		PVRPDepotLinkedList clonedDepotLinkedList = new PVRPDepotLinkedList();

		//clonedDepotLinkedList.setAttributes( (PVRPAttributes) this.getAttributes().clone() );

		if (this.getHead() != this.getTail())
		{
			PVRPDepot currentDepot = (PVRPDepot) clonedDepotLinkedList.getHead();
			PVRPDepot nextDepot = (PVRPDepot) this.getHead().getNext();

			while (nextDepot != null)
			{
				//	currentDepot.setNext( (PVRP) nextDepot.clone());
				currentDepot.getNext().setPrev(currentDepot);
				currentDepot = (PVRPDepot) currentDepot.getNext();
				nextDepot = (PVRPDepot) nextDepot.getNext();

				if (nextDepot == null)
				{
					clonedDepotLinkedList.setTail(currentDepot);
					currentDepot.setNext(null);
				}
			}
		}
		else
		{
			clonedDepotLinkedList.setTail(clonedDepotLinkedList.getHead());
		}
		return clonedDepotLinkedList;
	}
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


}
