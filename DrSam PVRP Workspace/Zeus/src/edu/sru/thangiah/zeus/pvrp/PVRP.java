package edu.sru.thangiah.zeus.pvrp;


import java.io.*;
import java.util.*;

import edu.sru.thangiah.zeus.Zeus;
import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.pvrp.PVRPShipmentLinkedList.*;
import edu.sru.thangiah.zeus.pvrp.pvrpnodeslinkedlist.*;
import edu.sru.thangiah.zeus.pvrp.pvrpqualityassurance.*;
import edu.sru.thangiah.zeus.gui.*;
import edu.sru.thangiah.zeus.vrp.VRPDepot;
import edu.sru.thangiah.zeus.vrp.VRPDepotLinkedList;
import sun.dc.pr.PRError;

import java.io.*;
import java.util.*;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.vrp.vrpqualityassurance.*;
import edu.sru.thangiah.zeus.gui.*;

/**
 * Created by joshuasarver on 9/13/2014.
 */
public class PVRP  {

    //PROBLEM DATA VARIABLES
	double depotXCoordinates = 0;
	double depotYCoordinates = 0;
	int vehicleCapacity = 0;
	int periodLength = 0;

	//NODES VARIABLES
	int customerNumber = 0;
	double xCoordinates = 0;
	double yCoordinates = 0;
	int demandQ = 0;
	int frequency = 0;

	//OTHER VARIABLES
    long startTime = 0; //tracks the CPU processing time
    long endTime = 0;

    private Vector mainOpts = new Vector(); //collection of optmizations
    private Vector optInformation = new Vector(); //contains route information
    private PVRPShipmentLinkedList mainShipments = new PVRPShipmentLinkedList(); //customers read in from a file or database that are available
    private PVRPDepotLinkedList mainDepots = new PVRPDepotLinkedList(); //linked list for depots
    private PVRPQualityAssurance pvrpQA; //checks our solution for good data
	private PVRPExcel excelReader;
	private PVRPExcelReadWrite excel = new PVRPExcelReadWrite();


	//public String inputExcelLocation;
	//public String excelData;

	public PVRP(String excelDataInput) throws IOException
	{
		excel.excelReader(excelDataInput, mainShipments);
		
		Shipment ship = mainShipments.find(59);
		System.out.println(ship.getIndex());
		

		//Various truck types will be placed into vector (we should only have one type...may be able to trash the vector)
		//ProblemInfo.truckTypes = new Vector();

		//VARIABLES
		boolean isDiagnostic = false;
		Shipment tempShipment;
		Depot thisDepot;
		int type;
		int depotNumber;
		int countAssignLoop;
		boolean status;
		String outputFileName;

		//TIME TO READ IN SOME DATA ;-)
		//readDataFromFile();
		//Settings.printDebug(Settings.COMMENT, "READ DATA FILE:\t" + ProblemInfo.inputPath + excelDataInput);

		//printDataToConsole();
		//writeDataToFile();

		//MAKE SURE OUR LINKED LIST HAS ALL THE BEAUTIFUL LOVELY DATA WE ARE ABOUT TO RIP APART
		/*if(mainShipments.getPVRPHead() == null)
		{
			Settings.printDebug(Settings.ERROR, "PVRP: THE LINKED LIST IS EMPTY.");
		}*/

		//SHIPMENT SELECTION TYPE
		//ProblemInfo.selectShipType = new SmallestPolarAngleToDepot();
		//Settings.printDebug(Settings.COMMENT, SmallestPolarAngleToDepot.WhoAmI());

		//SETUP SHIPMENT INSERTION TYPE
		//ProblemInfo.insertShipType = new LinearGreedyInsertShipment();
		//Settings.printDebug(Settings.COMMENT, LinearGreedyInsertShipment.WhoAmI());

		//START THE CPU TIME CLOCK
		//startTime = System.currentTimeMillis();

		//SETUP THE ROUTES
		//createInitialRoutes();
		//System.out.println("COMPLETED INITIAL ROUTES");

		//INITIAL SOLUTION - YES! IT'S PROBABLY NOT THAT GOOD AT THIS POINT, HOWEVER
		//Settings.printDebug(Settings.COMMENT, "INITIAL ROUTES CREATED");
		//Settings.printDebug(Settings.COMMENT, "INITIAL STATISTICS:\t" + mainDepots.getSolutionString());

		//ALL SHIPMENTS HAVE BEEN ASSIGNED! SUCH WOW
		//writeLongSolution(excelDataInput.substring(excelDataInput.lastIndexOf("/") + 1) );

		//QUALITY CHECK - IF YOU'RE NOT SATISFIED YOUR MONEY BACK GUARANTEE...
		//                  ...HOPEFULLY YOU DIDN'T PAY FOR THIS
		//System.out.println("INITIALIZING QUALITY CHECK PROCESS");
		//pvrpQA = new PVRPQualityAssurance(mainDepots, mainShipments);

		/*if(pvrpQA.runQA() == false)
		{
			Settings.printDebug(Settings.ERROR, "\tQUALITY CHECK FAILED: THERE WAS A LACK OF QUALITY");
		}
		else
		{
			Settings.printDebug(Settings.COMMENT, "\tQUALITY CHECK: SUCCESS");
		}
*/

		//AH, THE BELOVED GRAPHIC INTERFACE
		//THANKS XEROX
		//ZeusGui guiPost = new ZeusGui(mainDepots, mainShipments);
	}


	/*public void createInitialRoutes()
	{
		PVRPDepot currentDepot = null;
		PVRPShipment currentShipment = null;

		//HAVE SELECTION/INSERTION METHODS BEEN SELECTED
		if(ProblemInfo.selectShipType == null)
		{
			Settings.printDebug(Settings.ERROR, "NO SHIPMENT SELECTION TYPE HAS BEEN SELECTED");
		}

		if(ProblemInfo.insertShipType == null)
		{
			Settings.printDebug(Settings.ERROR, "NO SHIPMENT INSERTION TYPE HAS BEEN SELECTED");
		}


		while(!mainShipments.isAllShipsAssigned())
		{
			int i = 0;

			//get x,y of depot
			//currentDepot = (VRPDepot) mainDepots.getPVRPHead().getNext();
			xCoordinates = mainDepots.getHead().getXCoord();
			yCoordinates = mainDepots.getHead().getYCoord();

			PVRPShipment theShipment = mainShipments.getNextInsertShipment(mainDepots, currentDepot, mainShipments, currentShipment);

			if(theShipment == null)
			{
				Settings.printDebug(Settings.COMMENT, "NO SHIPMENT WAS SELECTED");
			}

			if(!mainDepots.insertShipment(theShipment))
			{
				Settings.printDebug(Settings.COMMENT, "THE SHIPMENT [" + theShipment.getIndex() + "] CANNOT BE ROUTED");
			}
			else
			{
				Settings.printDebug(Settings.COMMENT, "THE SHIPMENT [" + theShipment.getIndex() + "] WAS ROUTED");
			}

			theShipment.setIsAssigned(true);
		}

		ProblemInfo.depotLLLevelCostF.calculateTotalsStats(mainDepots);

	}*/



	public void readDataFromFile() throws IOException
	{

		//STRING VARIABLES FOR EASIER READING
		String newline = "\n";
		String space = " ";

		//EXTRACT THE NEEDED DATA FROM EXCEL
		String problemInfo = excelReader.readProblemInfoFromExcel();
		String nodesInfo = excelReader.readNodesFromExcel();    //LAST NODE IS DEPOT
		//WHAT ABOUT MULTIPLE DEPOTS?

		//SPLIT PROBLEM INFO INTO COLUMNS AS WE ONLY HAVE ONE ROW OF THIS
		String[] problemInfoByColumn = problemInfo.split(space);

		//GET DEPOT COORDINATES, VEHICLE CAPACITY, ETC.
		//*********THIS WILL NEED MORE WORK @#$%&*^%$#*(&%$&^^&&^&^^&^%
		depotXCoordinates = Integer.parseInt(problemInfoByColumn[0]);
		depotYCoordinates = Integer.parseInt(problemInfoByColumn[1]);
		vehicleCapacity = Integer.parseInt(problemInfoByColumn[2]);

		if (vehicleCapacity == 0)
		{              //IF CAPACITY DOESN'T MATTER
			vehicleCapacity = 99999999;    //JUST PICK A LARGE NUMBER
		}


		//SPLIT NODES STRING INTO ROWS AS WE JUST GET BACK ONE BIG CHUNK OF TEXT
		String[] nodesInfoByRow = problemInfo.split(newline);

		//GET NUMBER OF ROWS IN STRING
		int numberOfRows = nodesInfoByRow.length;

		//FOR EVERY ROW WE WILL SPLIT IT INTO COLUMNS
		for (int counter = 0; counter < numberOfRows; counter++)
		{
			String[] nodesInfoByColumn = nodesInfoByRow[counter].split(space);
			customerNumber = Integer.parseInt(nodesInfoByColumn[0]);
			xCoordinates = Integer.parseInt(nodesInfoByColumn[1]);
			yCoordinates = Integer.parseInt(nodesInfoByColumn[2]);
			demandQ = Integer.parseInt(nodesInfoByColumn[3]);
			frequency = Integer.parseInt(nodesInfoByColumn[4]);

			//AFTER WE COLLECT OUR DATA WE ADD IT AS A SHIPMENT
			//mainShipments.insertShipment(customerNumber, xCoordinates, yCoordinates, demandQ, frequency);


			//HANDLE MULTIPLE DEPOTS AS IN VRP
		}
	}

		public void writeDataToFile() throws FileNotFoundException
		{
			PrintStream ps = new PrintStream(new FileOutputStream(ProblemInfo.outputPath + "genericOutput.xlsx"));
			mainDepots.printDepotLinkedList(ps);

		}




}
