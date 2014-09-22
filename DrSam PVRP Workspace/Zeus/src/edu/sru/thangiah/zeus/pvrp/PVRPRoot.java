//AARON ROCKBURN; JOSHUA SARVER
//CPSC 464
//PVRP PROBLEM


//PACKAGE TITLE
package edu.sru.thangiah.zeus.pvrp;


//IMPORT STATEMENTS
import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.pvrp.pvrpcostfunctions.*;


//CLASS
public class PVRPRoot
{

	//INSTANTIATION
	public PVRPRoot() throws Exception
	{

		//SETUP SOME PARAMETERS FOR THE PROBLEM INFO CLASS
		//these are all very important...the program will not function
		//properly without them
		ProblemInfo.nodesLLLevelCostF   = new PVRPNodesLLCostFunctions();
		ProblemInfo.truckLevelCostF     = new PVRPTruckCostFunctions();
		ProblemInfo.truckLLLevelCostF   = new PVRPTruckLLCostFunctions();
		ProblemInfo.depotLevelCostF     = new PVRPDepotCostFunctions();
		ProblemInfo.depotLLLevelCostF   = new PVRPDepotLLCostFunctions();

		//FILE LOCATIONS
		ProblemInfo.tempFileLocation    = ProblemInfo.workingDirectory + "\\temp";                  //temp file location
		ProblemInfo.inputPath           = ProblemInfo.workingDirectory + "\\data\\PVRP\\data\\";    //input file location
		ProblemInfo.outputPath          = ProblemInfo.workingDirectory + "\\data\\PVRP\\results\\"; //output file location


		//CREATE NEW PVRP PROBLEM WITH DATA FROM THE SPECIFIED FILE NAME
		new PVRP("input_1.xlsx");
	}
}
