//PVRP PROBLEM
//CPSC 464
//AARON ROCKBURN; JOSHUA SARVER

//***********	DECLARATION_S_OTHER	**********************************************************************************\\
// FUNCTION_START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//PACKAGE TITLE
package edu.sru.thangiah.zeus.pvrp;

//IMPORT STATEMENTS
import edu.sru.thangiah.zeus.core.Depot;
import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.core.Settings;
import edu.sru.thangiah.zeus.core.Shipment;
import edu.sru.thangiah.zeus.pvrp.PVRPShipmentLinkedList.PVRPShipmentLinkedList;
import edu.sru.thangiah.zeus.pvrp.PVRPShipmentLinkedList.SmallestPolarAngleToDepot;
import edu.sru.thangiah.zeus.pvrp.pvrpnodeslinkedlist.LinearGreedyInsertShipment;
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
	double	depotXCoordinates 	= 0,
			depotYCoordinates 	= 0,
			vehicleCapacity 	= 0;

	//NODES VARIABLES
	int 	customerNumber 	= 0;
	double 	xCoordinates 	= 0,
			yCoordinates 	= 0;
	int		demandQ 		= 0,
			frequency 		= 0;

	//OTHER VARIABLES
	long	startTime 	= 0,
			endTime 	= 0;    //tracks the CPU processing time


	//INSTANTIATE SOME OBJECTS
	//not sure what these are yet
	private Vector mainOpts = new Vector();         //collection of optmizations
	private Vector optInformation = new Vector();         //contains route information


	private PVRPShipmentLinkedList mainShipments = new PVRPShipmentLinkedList(); //customers read in from a file or database that are available
	private PVRPDepotLinkedList mainDepots = new PVRPDepotLinkedList();    //linked list for depots

	private PVRPQualityAssurance pvrpQA;        //checks our solution for good data
	private PVRPExcelReadWrite excel;        //handles reading our EXCEL file

	int list[] = new int[ProblemInfo.MAX_COMBINATIONS];
	int currentCombination[][] = new int[ProblemInfo.MAX_HORIZON][ProblemInfo.MAX_COMBINATIONS];



	//***********	DECLARATION_S	**********************************************************************************\\
	public PVRP(String excelDataInput) throws IOException    //not a curveball
	{
		//DECLARTION VARIABLES
		boolean 	isDiagnostic = false,
					status;
		Shipment	tempShip;
		Depot 		thisDepot;
		int 		type,
					depotNo,
					totalDemand,
					totalCapacity,
					countAssignLoop;
		String 		outputFileName;

		ProblemInfo.truckTypes = new Vector();                                        //WE SHOULD ONLY HAVE ONE TRUCK TYPE
		int list[] = new int[ProblemInfo.MAX_COMBINATIONS];                //array of 0'1 and 1's for the combinations
		int currentComb[][] = new int[ProblemInfo.MAX_HORIZON][ProblemInfo.MAX_COMBINATIONS];

		excel = new PVRPExcelReadWrite(excelDataInput, mainShipments);    //instantiate the excel class

		//read some data -- this class adds the data to a linked list
		excel.excelReader(list, currentComb);

		Settings.printDebug(Settings.COMMENT, "Read Data File: " + ProblemInfo.inputPath + excelDataInput);
		printDataToConsole();
		System.out.println("E#RGTY#$%TYG#$%TYG#$%HY$%");
		excel.excelWriter(list);

		//MAKE SURE DATA IS IN OUR SHIPMENT LINKED LIST
		if (mainShipments.getPVRPHead() == null)
		{
			Settings.printDebug(Settings.ERROR, "VRP: Shipment linked list is empty");
		}

		/*ProblemInfo.selectShipType = new SmallestPolarAngleToDepot();
		Settings.printDebug(Settings.COMMENT, SmallestPolarAngleToDepot.WhoAmI());

		//set up the shipment insertion type
		ProblemInfo.insertShipType = new LinearGreedyInsertShipment();
		Settings.printDebug(Settings.COMMENT, LinearGreedyInsertShipment.WhoAmI());*/
		
		//relaxation of capacity
		
		//not sure if this function does what I think it does. I don't have the core classes right now to look at it and check
		totalDemand = mainShipments.getTotalDemand(); 	//get total demand for all customers. 
		totalCapacity = ProblemInfo.noOfVehs * excel.truckCapacity;	//get total capacity for all trucks on a day 
		
		if(totalCapacity >= totalDemand * .9){
			vehicleCapacity = excel.truckCapacity * 1.1;
		}

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
		pvrpQA = new PVRPQualityAssurance(mainDepots, mainShipments);
		if (pvrpQA.runQA() == false)
		{
			Settings.printDebug(Settings.ERROR, "QA FAILED!");
		}
		else
		{
			Settings.printDebug(Settings.COMMENT, "QA succeeded");
		}


		//ZeusGui guiPost = new ZeusGui(mainDepots, mainShipments);		//CALL THIS SOME OTHER DAY...WHEN EVERYTHING WORKS

	}


	//***********	FUNCTIONS	**************************************************************************************\\
	// PRINT_DATA_TO_CONSOLE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public void printDataToConsole()
	{
		mainShipments.printPVRPShipmentsToConsole();
	}
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


/*

	// READ_DATA_FROM_FILE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public void readDataFromFile() throws IOException
	{

		//EXTRACT THE NEEDED DATA FROM EXCEL

		excel.excelReader(list, currentCombination); //LAST NODE IS DEPOT
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
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
*/

	// WRITE_DATA_TO_FILE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public void writeDataToFile() throws FileNotFoundException
	{
		PrintStream ps = new PrintStream(new FileOutputStream(ProblemInfo.outputPath + "genericOutput.xlsx"));
		mainDepots.printDepotLinkedList(ps);

	}
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	// CREATE_INITIAL_ROUTES >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public void createInitialRoutes() {
		//OptInfo has old and new attributes
		PVRPDepot currDepot = null; //current depot
		PVRPShipment currShip = null; //current shipment
		//int countLoop=0;

		//check if selection and insertion type methods have been selected
		if (ProblemInfo.selectShipType == null) {
			Settings.printDebug(Settings.ERROR,
									   "No selection shipment type has been assigned");

		}
		if (ProblemInfo.insertShipType == null) {
			Settings.printDebug(Settings.ERROR,
									   "No insertion shipment type has been assigned");
		}


		//countLoop=1;
		while (!mainShipments.isAllShipsAssigned()) {
			double x, y;
			int i = 0;
			//Get the x an y coordinate of the depot
			//Then use those to get the customer, that has not been allocated,
			// that is closest to the depot
			currDepot = (PVRPDepot) mainDepots.getPVRPHead().getNext();
			x = mainDepots.getHead().getXCoord();
			y = mainDepots.getHead().getYCoord();
			//Send the entire mainDepots and mainShipments to get the next shipment
			//to be inserted including the current depot
			PVRPShipment theShipment = mainShipments.getNextInsertShipment(mainDepots,
																				 currDepot, mainShipments, currShip);

			if (theShipment == null) { //shipment is null, print error message
				Settings.printDebug(Settings.COMMENT, "No shipment was selected");
			}
			//The selected shipment will be inserted into the route
			if (!mainDepots.insertShipment(theShipment)) {
				Settings.printDebug(Settings.COMMENT, "The Shipment: <" + theShipment.getIndex() +
															  "> cannot be routed");
			}
			else {
				Settings.printDebug(Settings.COMMENT,
										   "The Shipment: <" + theShipment.getIndex() +// " " + theShipment +
												   "> was routed");		
				//while theShipment is not the last shipment in the list
				/*while(theShipment != mainShipments.getTail()){
					
					//assign a day combination to the customer
					
				}*/
				//tag the shipment as being routed
				theShipment.setIsAssigned(true);
			}
			
			//define an array to hold capacities for all days in period
			int[] dayCapacity = new int[ProblemInfo.noOfDays];
			
			//check to see if demand for each day exceeds total capacity of trucks for that day
			for(int j = 0; j < ProblemInfo.noOfDays; j ++){
				//variable that tracks which day is being filled
				int dayToFill = j;
				
				//run through all other days to see how their capacities compare to the current day capacity
				for(int q = 0; q < ProblemInfo.noOfDays; q++){
					
					//if the day to be filled has not changed
					if(dayToFill == j){
						//compare the capacity and if the j capacity is larger than the q capacity, switch to day q
						if(dayCapacity[j] > dayCapacity[q]){
							dayToFill = q;
						}
					}
				}
				//dayCapacity[dayToFill] += mainShipments.getDemand(j); //define this method
				
				//assign customer to day and truck 
			}
			
		}

		ProblemInfo.depotLLLevelCostF.calculateTotalsStats(mainDepots);
	}
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


}
