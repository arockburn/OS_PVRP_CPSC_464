package edu.sru.thangiah.zeus.pvrp;

/**
 * Created by jks1010 on 10/1/2014.
 */
public class PVRPDays
{
	private int probType;
	private int numCust;
	private int numVehs;
	private int horizon;
	private int maxDur;
	private int maxCap;




	public PVRPDays(int probType, int numCust, int numVehs, int horizon, int maxDur, int maxCap){
		if(maxDur == 0){
			maxDur = 99999;
		}
		if(maxCap == 0){
			maxCap = 99999;
		}


		if(probType >= 0 && numCust > 0 && numVehs > 0 && horizon > 0)
		{
			this.probType = probType;
			this.numCust = numCust;
			this.numVehs = numVehs;
			this.horizon = horizon;
			this.maxDur = maxDur;
			this.maxCap = maxCap;
		}else{
			System.out.println("ERROR YO!!!!!!!!!>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}


	}


	public int getProbType()
	{
		return probType;
	}

	public int getNumCust()
	{
		return numCust;
	}

	public int getNumVehs()
	{
		return numVehs;
	}

	public int getHorizon()
	{
		return horizon;
	}

	public int getMaxDur()
	{
		return maxDur;
	}

	public int getMaxCap()
	{
		return maxCap;
	}



}
