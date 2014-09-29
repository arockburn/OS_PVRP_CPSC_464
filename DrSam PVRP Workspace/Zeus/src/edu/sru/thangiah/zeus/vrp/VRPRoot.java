package edu.sru.thangiah.zeus.vrp;

import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.vrp.vrpcostfunctions.*;

import edu.sru.thangiah.zeus.vrp.VRPExcelSupport;

public class VRPRoot {
  /**
   * Constructor. Runs the VRP and calculates the total CPU time
   */
  public VRPRoot() {

    //Settings for the ProblemInfo class
    //Problem info consists of a set of static values that are used by a number
    //of different classes. The following has to be set in order for the program
    //to function correctly.
    ProblemInfo.nodesLLLevelCostF = new VRPNodesLLCostFunctions();
    ProblemInfo.truckLevelCostF = new VRPTruckCostFunctions();
    ProblemInfo.truckLLLevelCostF = new VRPTruckLLCostFunctions();
    ProblemInfo.depotLevelCostF = new VRPDepotCostFunctions();
    ProblemInfo.depotLLLevelCostF = new VRPDepotLLCostFunctions();
    //Paths for temporary, input and output files
    //ProblemInfo.currDir gives the working directory of the program
    ProblemInfo.tempFileLocation = ProblemInfo.workingDirectory+"\\temp";
    ProblemInfo.inputPath = ProblemInfo.workingDirectory+"\\data\\vrp\\problems\\";

    ProblemInfo.outputPath = ProblemInfo.workingDirectory+"\\data\\vrp\\results\\";

    //Solve the VRP for the enclosed data
   	new VRP("mdvrp_p01.txt");

  }
}
