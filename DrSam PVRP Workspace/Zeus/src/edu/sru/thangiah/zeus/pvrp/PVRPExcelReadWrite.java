//AARON ROCKBURN; JOSHUA SARVER
//CPSC 464
//PVRP PROBLEM


//PACKAGE TITLE
package edu.sru.thangiah.zeus.pvrp;

//IMPORTS
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
	public String	excelInput		= "";
	public int		planningDays	= 0;
	public int		numberNodes		= 0;
	public int		numberVehicles	= 0;
	public double	xCoordinates;
	public double	yCoordinates;
	public int		nodeNumber;
	public int		demandQ;
	public int		frequency;
	public int		numberCombinations;

	public PVRPShipmentLinkedList mainShipments = new PVRPShipmentLinkedList();
	public PVRPDepot mainDepots;



	//***********	CLASS_FUNCTIONS	*********************************************************************************\\
	public PVRPExcelReadWrite(String excelInput, PVRPShipmentLinkedList list)
	{
		this.excelInput = excelInput;
	}


	//EXCEL WRITER
	public void excelWriter(PVRPShipmentLinkedList list)
	{
		XSSFWorkbook wb = new XSSFWorkbook();

		XSSFSheet sheet = wb.createSheet("Customer Data");
		//int size = list.size();
		for (int i = 0; i < /*size*/5; i++)
		{
			//String line = (String)mainShipments.get(i);
			//line = line.trim();
			//String[] ary = line.split(" +");
			Cell cell = null;
			Row row = sheet.createRow(i);
			for (int j = 0; j < 5; j++)
			{
				cell = row.createCell(j);
				// double val = mainShipments.get(i*5+j);
				// cell.setCellValue(list.get(i*5+j));
			}
		}
		try
		{
			FileOutputStream out = new FileOutputStream(new File("data/PVRP/results/output_1.xlsx"));
			wb.write(out);
			out.close();
			System.out.println("File written correctly");
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	//EXCEL READER
	public void excelReader(int list[], int currentCombination[][]) throws IOException
	{
		//***********	FUNCTION_VARIABLES	**************************************************************************\\
		double DUMMY	= -1;
		int cellCount	= 0,
			rowCount	= 0;

			FileInputStream	file		= new FileInputStream(new File("data/PVRP/data/" + excelInput));
			XSSFWorkbook	workbook	= new XSSFWorkbook(file);
			XSSFSheet 		sheet 		= workbook.getSheetAt(0);
			Iterator<Row> rowIterator 	= sheet.iterator();		//an iterator for rows

			//START SIFTING THROUGH THE DATA
			while (rowIterator.hasNext())						//while we have another row below the current
			{
				Row row = (Row) rowIterator.next();					//get the next row
				Iterator<Cell> cellIterator = row.cellIterator();	//an iterator for columns
				Cell cell;

				if (row.getRowNum() == 0)			//if on row zero
				{									//treat all data appropriately
					cellCount = 0;
					while (cellIterator.hasNext())	//while we have a next cell
					{
						cell = (Cell) cellIterator.next();							//get the cell data
						int currentCellValue = (int) cell.getNumericCellValue();	//extract cell data into int
						switch (cellCount)											//Finite State Machine
						{
						//first cell -- dunno what this is
							case 0:
								break;    //WE DON'T KNOW WHAT THIS VALUE IS -- POSSIBLY PROBLEM NUMBER

						//second cell -- the number of planning days/horizon (m)
							case 1:
								planningDays = currentCellValue;
								break;

						//third cell -- the number of nodes (customers)
							case 2:
								numberNodes = currentCellValue;
								break;

						//fourth cell -- number of vehicles
							case 3:
								numberVehicles = currentCellValue;
						}
						cellCount++;	//increment cell counter so we can move through FSM
					}

					//ADD ALL THE NEW DATA TO THE PROBLEM_INFO CLASS
					PVRPProblemInfo.noOfDays	= planningDays;
					PVRPProblemInfo.noOfShips	= numberNodes;
					PVRPProblemInfo.noOfVehs	= numberVehicles;
				}
				//else if the current row is between the header row (Read above) and the nodes (customer) rows
				else if (row.getRowNum() <= numberVehicles + 1 && row.getRowNum() > 0)      //don't forget the header row adds one
				{
					cellCount = 0;

					while (cellIterator.hasNext())					//while we have another cell
					{
						cell = (Cell) cellIterator.next();			//get dat cell
						int cellData = (int) cell.getNumericCellValue();	//
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
				else if (row.getRowNum() > numberVehicles + 1)	//else we are in NODE TERRITORY :D))))))
				{
					cellCount		= 0;
					int listIndex	= 0;

					while (cellIterator.hasNext())				//while we have another cell
					{
						cell = (Cell) cellIterator.next();
						int currentCellContents = (int) cell.getNumericCellValue();
						switch (cellCount)
						{
							case 0:
								break;       // first column is empty
							case 1:
								nodeNumber = currentCellContents;
								break;
							case 2:
								xCoordinates = currentCellContents;
								break;
							case 4:
								yCoordinates = currentCellContents;
								break;
							case 5:
								 DUMMY = currentCellContents;     //USUALLY ALL ZEROS
																		//DUNNO WHAT THIS ACTUALLY DOES
								break;
							case 6:
								demandQ = currentCellContents;
								break;
							case 7:
								frequency = currentCellContents;
								break;
							case 8:
								numberCombinations = currentCellContents;
								break;
							default:
								list[listIndex] = currentCellContents;
								listIndex++;
								break;

						}
					}
					for (int l = 0; l < numberCombinations; l++) {
						currentCombination[l] = mainShipments.getCurrentComb(list, l, planningDays); // current visit comb

						//insert the customer data into the linked list
					}
					if(row.getRowNum() == numberVehicles + 1){				//first row in nodes is a DEPOT
						mainDepots = new PVRPDepot(nodeNumber, xCoordinates, yCoordinates);
					}
					mainShipments.insertShipment(nodeNumber, xCoordinates, yCoordinates, DUMMY, demandQ, frequency, numberCombinations, list, currentCombination);

				}
				rowCount++;
			}//all rows have been parsed through
			file.close();


	}


}