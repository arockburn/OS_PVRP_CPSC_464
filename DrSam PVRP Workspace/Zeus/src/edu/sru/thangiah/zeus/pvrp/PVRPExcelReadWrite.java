//PVRP PROBLEM
//CPSC 464
//AARON ROCKBURN; JOSHUA SARVER

//***********	DECLARATION_S_OTHER	**********************************************************************************\\
// FUNCTION_START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//PACKAGE TITLE
package edu.sru.thangiah.zeus.pvrp;


//IMPORTS
import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.pvrp.PVRPShipmentLinkedList.PVRPShipmentLinkedList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;





//CLASS
public class PVRPExcelReadWrite
{

	//***********	CLASS_VARIABLES	**********************************************************************************\\
	public String excelInput = "";
	public int planningDays = 0;
	public int numberNodes = 0;
	public int numberVehicles = 0;
	public double xCoordinates;
	public double yCoordinates;
	public int nodeNumber;
	public int demandQ;
	public int frequency;
	public int numberCombinations;

	public PVRPShipmentLinkedList mainShipments = new PVRPShipmentLinkedList();
	public PVRPDepot mainDepots;


	//***********	DECLARATION_S	*********************************************************************************\\
	public PVRPExcelReadWrite(String excelInput, PVRPShipmentLinkedList list)
	{
		this.excelInput = excelInput;
	}


	// EXCEL_WRITER >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//&&&&&??????????????? THIS WRITER NEED WORK -- READ FROM LINKED LIST!!!!!!!!!
	public void excelWriter(int[] list) throws IOException
	{

		FileOutputStream out = new FileOutputStream(new File(ProblemInfo.outputPath + "OUTPUT_" + excelInput));
		System.out.print("TEST OUTPUT");
		mainShipments.writePVRPShipments(out);





	}
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	// EXCEL_READER >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public void excelReader(int list[], int currentCombination[][]) throws IOException
	{
		//***********	FUNCTION_VARIABLES	**************************************************************************\\
		double DUMMY = -1;
		int cellCount = 0,
			rowCount = 0,
			typeNumeric = 0;

		FileInputStream file = new FileInputStream(new File(ProblemInfo.inputPath + excelInput));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();        //an iterator for rows

		//START SIFTING THROUGH THE DATA
		while (rowIterator.hasNext())                        //while we have another row below the current
		{
			Row row = (Row) rowIterator.next();                    //get the next row
			Iterator<Cell> cellIterator = row.cellIterator();    //an iterator for columns
			Cell cell;

			if (row.getRowNum() == 0)            //if on row zero
			{                                    //treat all data appropriately
				cellCount = 0;
				while (cellIterator.hasNext())    //while we have a next cell
				{
					cell = (Cell) cellIterator.next();                            //get the cell data
					cell.setCellType(typeNumeric);
					float currentCellValue = (float) cell.getNumericCellValue();    //extract cell data into int
					switch (cellCount)                                            //Finite State Machine
					{
						//first cell -- dunno what this is
						case 0:
							break;    //WE DON'T KNOW WHAT THIS VALUE IS -- POSSIBLY PROBLEM NUMBER

						//second cell -- the number of planning days/horizon (m)
						case 1:
							planningDays = (int) currentCellValue;
							break;

						//third cell -- the number of nodes (customers)
						case 2:
							numberNodes = (int) currentCellValue;
							break;

						//fourth cell -- number of vehicles
						case 3:
							numberVehicles = (int) currentCellValue;
					}
					cellCount++;    //increment cell counter so we can move through FSM
				}

				//ADD ALL THE NEW DATA TO THE PROBLEM_INFO CLASS
				PVRPProblemInfo.noOfDays = planningDays;
				PVRPProblemInfo.noOfShips = numberNodes;
				PVRPProblemInfo.noOfVehs = numberVehicles;
			}
			//else if the current row is between the header row (Read above) and the nodes (customer) rows
			else if (row.getRowNum() < numberVehicles && row.getRowNum() > 0)      //don't forget the header row adds one
			{
				cellCount = 0;

				while (cellIterator.hasNext())                    //while we have another cell
				{
					cell = (Cell) cellIterator.next();            //get dat cell
					cell.setCellType(typeNumeric);
					float cellData = (float) cell.getNumericCellValue();    //
					if (cellCount == 0)
					{
						//	list.truckInfo.addLast(cellData);		//WHAT THE FUCK IS THIS TRUCKINFO ******************>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<
					}
					else if (cellCount == 1)
					{
						//	list.truckInfo.addLast(cellData);		//WHAT THE FUCK IS THIS TRUCKINFO******************>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<
					}
				}
			}
			else if (row.getRowNum() > numberVehicles)    //else we are in NODE TERRITORY :D))))))
			{
				cellCount = 0;
				int listIndex = 0;

				while (cellIterator.hasNext())                //while we have another cell
				{
					cell = (Cell) cellIterator.next();
					cell.setCellType(typeNumeric);
					float currentCellContents = (float) cell.getNumericCellValue();

					switch (cellCount)
					{
						case 0:
							nodeNumber = (int) currentCellContents;
							break;
						case 1:
							xCoordinates = (double) currentCellContents;
							break;
						case 2:
							yCoordinates = (double) currentCellContents;
							break;
						case 3:
							DUMMY = (double) currentCellContents;     //USUALLY ALL ZEROS
							//DUNNO WHAT THIS ACTUALLY DOES
							break;
						case 4:
							demandQ = (int) currentCellContents;
							break;
						case 5:
							frequency = (int) currentCellContents;
							break;
						case 6:
							numberCombinations = (int) currentCellContents;
							break;
						default:
							list[listIndex] = (int) currentCellContents;
							listIndex++;
							break;

					}
					cellCount++;
				}

				if (row.getRowNum() == numberVehicles + 1)
				{                //first row in nodes is a DEPOT
					mainDepots = new PVRPDepot(nodeNumber, xCoordinates, yCoordinates);
				}
				else
				{
					for (int l = 0; l < numberCombinations; l++)
					{
/**NEED A VARIABLE IN HERE ??????????????????????????????????????????????????????????????????????????????????????????????*/
						currentCombination[l] = mainShipments.getCurrentComb(list, l, 15); // current visit comb
						mainShipments.insertShipment(nodeNumber, xCoordinates, yCoordinates, DUMMY, demandQ, frequency, numberCombinations, list, currentCombination);
						//mainShipments.
						//insert the customer data into the linked list
					}
				}

			}
			rowCount++;
		}//all rows have been parsed through
		file.close();    //ALWAYS CLOSE THE FILE WHEN DONE

	}
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

}