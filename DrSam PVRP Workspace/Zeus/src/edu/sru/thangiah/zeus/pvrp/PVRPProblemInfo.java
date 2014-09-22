package edu.sru.thangiah.zeus.pvrp;

import edu.sru.thangiah.zeus.core.ProblemInfo;

/**
 * Created by joshuasarver on 9/13/2014.
 */
public class PVRPProblemInfo extends ProblemInfo {

	public void PVRPProblemInfo(){

	}

	public void insertProblemHeader(int planningDays, int numberNodes, int numberVehicles){
		noOfDays = planningDays;
		noOfShips = numberNodes;
		noOfVehs = numberVehicles;
	}

	public  int getNumberOfPlanningDays(){
		return noOfDays;
	}

	public int getNumberOfNodes(){
		return noOfShips;
	}

	public int getNumberOfVehicles(){
		return noOfVehs;
	}

}
