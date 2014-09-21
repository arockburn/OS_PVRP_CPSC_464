package edu.sru.thangiah.zeus.pvrp;

import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.vrp.VRP;
import edu.sru.thangiah.zeus.vrp.vrpcostfunctions.*;
import edu.sru.thangiah.zeus.pvrp.pvrpcostfunctions.*;


import java.io.*;
import java.util.*;
import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.vrp.vrpqualityassurance.*;
import edu.sru.thangiah.zeus.gui.*;
import edu.sru.thangiah.zeus.pvrp.pvrpcostfunctions.*;
/**
 * Created by joshuasarver on 9/13/2014.
 */
public class PVRPRoot  {

	public PVRPRoot() throws Exception
	{
		//Settings for the ProblemInfo class
		//Problem info consists of a set of static values that are used by a number
		//of different classes. The following has to be set in order for the program
		//to function correctly.
		ProblemInfo.nodesLLLevelCostF = new PVRPNodesLLCostFunctions();
		ProblemInfo.truckLevelCostF = new PVRPTruckCostFunctions();
		ProblemInfo.truckLLLevelCostF = new PVRPTruckLLCostFunctions();
		ProblemInfo.depotLevelCostF = new PVRPDepotCostFunctions();
		ProblemInfo.depotLLLevelCostF = new PVRPDepotLLCostFunctions();
		//Paths for temporary, input and output files
		//ProblemInfo.currDir gives the working directory of the program
		ProblemInfo.tempFileLocation = ProblemInfo.workingDirectory+"\\temp";
		ProblemInfo.inputPath = ProblemInfo.workingDirectory+"\\data\\PVRP\\data\\";

		ProblemInfo.outputPath = ProblemInfo.workingDirectory+"\\data\\PVRP\\results\\";

		//Solve the VRP for the enclosed data
		new PVRP("input_1.xlsx");


		//PVRPExcel test = new PVRPExcel("data/PVRP/data/input_1.xlsx", "data/PVRP/results/output_1.xlsx");
		//test.readProblemInfoFromExcel();


	}
}
