package edu.sru.thangiah.zeus.pvrp.PVRPShipmentLinkedList;
import java.io.*;
import java.util.*;

//import the parent class
import edu.sru.thangiah.zeus.core.ShipmentLinkedList;
import edu.sru.thangiah.zeus.core.Shipment;
import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.pvrp.PVRPShipment;

import edu.sru.thangiah.zeus.pvrp.PVRPTruck;

/**
 * Created by jks1010 on 9/14/2014.
 */
import java.io.PrintStream;

/**
 *
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */




public class PVRPShipmentLinkedList
		extends ShipmentLinkedList
		implements java.io.Serializable, java.lang.Cloneable {
	
	public int period, numNodes, numTrucks;
	public LinkedList<Integer> truckInfo = new LinkedList<Integer>();
	public LinkedList<PVRPShipment> mainShipments = new LinkedList<PVRPShipment>();
	//public LinkedList<PVRPTruck> trucks = new LinkedList<PVRPTruck>();
	
	public PVRPShipmentLinkedList(){
		/*setTail(new PVRPShipment());
		setHead(new PVRPShipment());
		linkHeadTail();
		setNumShipments(0);
		mainShips.addLast(0.0);*/
		
	}



	
	public void insertShipment(int node, double x, double y, double DUMMY, int demandQ, int frequency, int numberCombos, int[] comboList, int[][] currentCombo){
		
	}
		
}

