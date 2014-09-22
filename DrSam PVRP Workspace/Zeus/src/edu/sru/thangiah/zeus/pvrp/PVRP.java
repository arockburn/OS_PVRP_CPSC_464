//AARON ROCKBURN; JOSHUA SARVER
//CPSC 464
//PVRP PROBLEM


//PACKAGE TITLE
package edu.sru.thangiah.zeus.pvrp;

//IMPORT STATEMENTS
import edu.sru.thangiah.zeus.core.*;

import edu.sru.thangiah.zeus.pvrp.PVRPShipmentLinkedList.*;
import edu.sru.thangiah.zeus.pvrp.pvrpnodeslinkedlist.*;
import edu.sru.thangiah.zeus.pvrp.PVRPShipmentLinkedList.PVRPShipmentLinkedList;
import edu.sru.thangiah.zeus.pvrp.pvrpqualityassurance.PVRPQualityAssurance;
import edu.sru.thangiah.zeus.vrp.vrpqualityassurance.VRPQualityAssurance;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;



//CLASS
public class PVRP
{
	//***********	CLASS_VARIABLES	**********************************************************************************\\
	//PROBLEM DATA VARIABLES
		double  depotXCoordinates   = 0,
				depotYCoordinates   = 0;
		int     vehicleCapacity     = 0;

	//NODES VARIABLES
		int     customerNumber  = 0;
		double  xCoordinates    = 0,
				yCoordinates    = 0;
		int     demandQ     = 0,
				frequency   = 0;

	//OTHER VARIABLES
		long    startTime   = 0,
				endTime     = 0;    //tracks the CPU processing time


	//INSTANTIATE SOME OBJECTS
		//not sure what these are yet
		private Vector	mainOpts         = new Vector();         //collection of optmizations
		private Vector	optInformation   = new Vector();         //contains route information

		private PVRPShipmentLinkedList  mainShipments   = new PVRPShipmentLinkedList(); //customers read in from a file or database that are available
		private PVRPDepotLinkedList     mainDepots      = new PVRPDepotLinkedList();    //linked list for depots

		private PVRPQualityAssurance 	pvrpQA;		//checks our solution for good data
		private PVRPExcelReadWrite 		excel;		//handles reading our EXCEL file



	//CLASS
	public PVRP(String excelDataInput) throws IOException	//not a curveball
	{
		//***********	FUNCTION_VARIABLES	**************************************************************************\\
		boolean		isDiagnostic = false,
					status;
		Shipment	tempShip;
		Depot		thisDepot;
		int 		type,
					depotNo,
					countAssignLoop;
		String 		outputFileName;

		ProblemInfo.truckTypes 	= new Vector(); 										//WE SHOULD ONLY HAVE ONE TRUCK TYPE
		int list[]				= new int[ProblemInfo.MAX_COMBINATIONS];				//array of 0'1 and 1's for the combinations
		int currentComb[][] 	= new int[ProblemInfo.MAX_HORIZON][ProblemInfo.MAX_COMBINATIONS];

		excel = new PVRPExcelReadWrite(excelDataInput, mainShipments);	//instantiate the excel class

		//read some data -- this class adds the data to a linked list
		excel.excelReader(list, currentComb);

		Settings.printDebug(Settings.COMMENT, "Read Data File: " + ProblemInfo.inputPath + excelDataInput);
		printDataToConsole();
		excel.excelWriter(list);

		//MAKE SURE DATA IS IN OUR SHIPMENT LINKED LIST
		if (mainShipments.getVRPHead() == null) {
			Settings.printDebug(Settings.ERROR, "VRP: Shipment linked list is empty");
		}

		ProblemInfo.selectShipType = new SmallestPolarAngleToDepot();
		Settings.printDebug(Settings.COMMENT, SmallestPolarAngleToDepot.WhoAmI());

		//set up the shipment insertion type
		ProblemInfo.insertShipType = new LinearGreedyInsertShipment();
		Settings.printDebug(Settings.COMMENT, LinearGreedyInsertShipment.WhoAmI());

		//Capture the CPU time required for solving the problem
		startTime = System.currentTimeMillis();

		createInitialRoutes();
		System.out.println("Completed initial routes");

		//Get the initial solution
		//Depending on the Settings status, display information on the routes
		//Trucks used, total demand, dist, travel time and cost
			Settings.printDebug(Settings.COMMENT, "Created Initial Routes ");
			Settings.printDebug(Settings.COMMENT, "Initial Stats: " + mainDepots.getSolutionString());


		//Check for the quality and integrity of the solution
			System.out.println("Starting QUALITY ASSURANCE");
			vrpQA = new VRPQualityAssurance(mainDepots, mainShipments);
			if (vrpQA.runQA() == false) {
				Settings.printDebug(Settings.ERROR, "QA FAILED!");
			}
			else {
				Settings.printDebug(Settings.COMMENT, "QA succeeded");
			}


		//ZeusGui guiPost = new ZeusGui(mainDepots, mainShipments);		//CALL THIS SOME OTHER DAY...WHEN EVERYTHING WORKS

	}



	//***********	FUNCTIONS	**************************************************************************************\\
	//PRINT DATA TO CONSOLE
	public void printDataToConsole(){
		mainShipments.printPVRPShipmentsToConsole();
	}



	//READ DATA FROM FILE
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
