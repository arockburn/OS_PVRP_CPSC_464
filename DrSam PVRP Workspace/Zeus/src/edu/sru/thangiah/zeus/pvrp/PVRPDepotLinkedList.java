package edu.sru.thangiah.zeus.pvrp;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.pvrp.PVRPTrunkLinkedList;

import java.io.Serializable;

/**
 * Created by joshuasarver on 9/13/2014.
 */
public class PVRPDepotLinkedList extends DepotLinkedList implements Serializable, Cloneable
{

	public PVRPDepotLinkedList(){
		setHead(new PVRPDepot());   //head node
		setTail(new PVRPDepot());   //tail node
		linkHeadTail();             //link head and tail together


		//setAttributes(new PVRPAttributes());
	}

	public boolean insertShipment(PVRPShipment theShipment)
	{
		boolean status = false;

		PVRPDepot depot = (PVRPDepot) super.getHead().getNext();
		//PVRPTrunkLinkedList truckLL = (PVRPTruckLinkedList) depot.getMainTrucks();

		while(depot != this.getTail())
		{
			//truckLL = (PVRPTrunkLinkedList) depot.getMainTrucks();

			//status = truckLL.insertShipment(theShipment);

			if(status == true)
			{
				break;
			}
			depot = (PVRPDepot) depot.getNext();
		}
		return status;
	}

	public PVRPDepot getVRPHead()
	{
		return (PVRPDepot) getHead();
	}

	public Object clone()
	{
		PVRPDepotLinkedList clonedDepotLinkedList = new PVRPDepotLinkedList();

		//clonedDepotLinkedList.setAttributes( (PVRPAttributes) this.getAttributes().clone() );

		if(this.getHead() != this.getTail())
		{
			PVRPDepot currentDepot = (PVRPDepot) clonedDepotLinkedList.getHead();
			PVRPDepot nextDepot = (PVRPDepot) this.getHead().getNext();

			while(nextDepot != null)
			{
			//	currentDepot.setNext( (PVRP) nextDepot.clone());
				currentDepot.getNext().setPrev(currentDepot);
				currentDepot = (PVRPDepot) currentDepot.getNext();
				nextDepot = (PVRPDepot) nextDepot.getNext();

				if(nextDepot == null)
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


}
