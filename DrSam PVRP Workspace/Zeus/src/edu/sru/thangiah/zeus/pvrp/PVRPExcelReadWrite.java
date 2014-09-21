package edu.sru.thangiah.zeus.pvrp;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.sru.thangiah.zeus.core.ProblemInfo;

import edu.sru.thangiah.zeus.pvrp.PVRPShipmentLinkedList.PVRPShipmentLinkedList;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by joshuasarver on 9/13/2014.
 */


import java.io.*;
import java.util.Iterator;

import edu.sru.thangiah.zeus.core.ProblemInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;

import java.io.*;
import java.util.*;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.vrp.vrpqualityassurance.*;
import edu.sru.thangiah.zeus.gui.*;

import javax.lang.model.type.NullType;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by joshuasarver on 9/13/2014.
 */


import java.io.*;
import java.util.Iterator;

import edu.sru.thangiah.zeus.core.ProblemInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;

import java.io.*;
import java.util.*;

import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.vrp.vrpqualityassurance.*;
import edu.sru.thangiah.zeus.gui.*;

import javax.lang.model.type.NullType;





public class PVRPExcelReadWrite
{

	private String file;
	public int planningDays = 0;
	public int numberNodes = 0;
	public int numberVehicles = 0;
	public int maximumDrivingDistance = 0;
	public int maximumCapacity = 0;
	public int numberHeaderFields = 4;
	public int numberVehicleFields = 2;
	public double xCoordinates;
	public double yCoordinates;
	public int nodeNumber;
	public int demandQ;
	public int frequency;
	public int numberCombinations;
	Vector combinationsVector = new Vector (10,10);
	public PVRPShipment theShipment = new PVRPShipment();


	public PVRPExcelReadWrite(String excelInput, PVRPShipmentLinkedList list)
	{
		file = excelInput;
	}


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

/*
	public void readProblemHeader() throws IOException
	{
		FileInputStream fileStream = new FileInputStream(new File(ProblemInfo.inputPath + file));

		int rowNumber = 1;
		XSSFRow rowData;



		//Create workbook
		XSSFWorkbook workbook = new XSSFWorkbook(fileStream);

		//Grab needed sheet from the file
		XSSFSheet sheet = workbook.getSheetAt(0);

		//Set to use first row
		rowData = sheet.getRow(rowNumber);

		for(int count = 0; count < numberHeaderFields; count++)
		{
			switch (count){
				case 0:
					break;

				case 1:
					planningDays = (int) rowData.getCell(count + 1).getNumericCellValue();
					break;

				case 2:
					numberNodes = (int) rowData.getCell(count + 1).getNumericCellValue();
					break;

				case 3:
					numberVehicles = (int) rowData.getCell(count + 1).getNumericCellValue();
					break;

				default:
					break;
			}
			System.out.println("HEADER DATA:\t"+planningDays+"\t"+numberNodes+"\t"+numberVehicles+"\t");
		}

		fileStream.close();


	}

	public void readTruckInfo() throws IOException
	{
		FileInputStream fileStream = new FileInputStream(new File(ProblemInfo.inputPath + file));


		int rowNumber = 1;
		XSSFRow rowData;


		//Create workbook
		XSSFWorkbook workbook = new XSSFWorkbook(fileStream);

		//Grab needed sheet from the file
		XSSFSheet sheet = workbook.getSheetAt(0);

		//Set to use first row
		rowData = sheet.getRow(rowNumber);

		for(int rowCount = 0; rowCount < numberVehicles; rowCount++)
		{
			for (int columnCount = 0; columnCount < numberVehicleFields; columnCount++)
			{
				switch (columnCount)
				{
					case 0:
						maximumDrivingDistance = (int) rowData.getCell(columnCount + 1).getNumericCellValue();
						break;

					case 1:
						maximumCapacity = (int) rowData.getCell(columnCount + 1).getNumericCellValue();
						break;

					default:
						break;
				}

			}
			System.out.println("TRUCK "+rowCount+" DATA:\t"+maximumDrivingDistance+"\t"+maximumCapacity);
		}
		fileStream.close();
	}


	public void readNodes() throws IOException
	{
		//VARIABLES
		int currentRowNumber = 1;
		XSSFRow currentRowData;
		int customerNumber = -1;
		double xCoordinate = -1;
		double yCoordinate = -1;
		int demandQ = -1;
		int frequency = -1;
		int numberOfColumns = 5;
		String excelContents = "";
		int nodeNumber = 0;
		int numberCombinations;
		Vector combinationsVector = new Vector (10,10);




		//Open up file
		FileInputStream inputStream = new FileInputStream(new File(file));

		//Create workbook
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

		//Grab needed sheet from the file
		XSSFSheet sheet = workbook.getSheetAt(0);

		//Set to use first row
		currentRowData = sheet.getRow(currentRowNumber);
		//currentRowData = sheet.getRow(51);

//&&&&&&&&&&&&&&&&&&&&&&&&*******************

		//Iterate through each rows one by one
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext())
		{
			Row row = rowIterator.next();
			//For each row, iterate through all the columns
			Iterator<Cell> cellIterator = row.cellIterator();
			int countColumns = 0;
			while (cellIterator.hasNext())
			{
				Cell cell = cellIterator.next();
				//Check the cell type and format accordingly

				if(row.getRowNum() > numberVehicles + 1) //FIRST ROW FOR PROBLEM DATA
				{
					switch(countColumns)
					{
						case 0:
							break;  //empty column

						case 1:
							nodeNumber = (int) cell.getNumericCellValue();
							break;

						case 2:
							xCoordinate = cell.getNumericCellValue();
							break;

						case 3:
							yCoordinate = cell.getNumericCellValue();
							break;

						case 4:
							double DUMMY = cell.getNumericCellValue();
							break;      //NO CLUE WHAT THIS CAN BE USED FOR
										//USUALLY A VALUE OF ZERO

						case 5:
							demandQ = (int) cell.getNumericCellValue();
							break;

						case 6:
							frequency = (int) cell.getNumericCellValue();
							break;

						case 7:
							numberCombinations = (int) cell.getNumericCellValue();
							for(int start = 0; start < numberCombinations; start++)
							{

								combinationsVector.addElement(row.getCell(8 + start));  //starts at column EIGHT and keeps adding data to the vector
							}
							break;

					}
				}
				countColumns++;

			}
			System.out.println("");
		}
		inputStream.close();
	}

*/


	public void excelReader(String inputFile, PVRPShipmentLinkedList list)
	{
		try
		{
			int numTrucks = 0, rowCount, cellCount;
			int custNum, x, y, demand, freq;
			FileInputStream file = new FileInputStream(new File("data/PVRP/data/" + inputFile));

			XSSFWorkbook workbook = new XSSFWorkbook(file);

			XSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			//rowCount = 0;
			while (rowIterator.hasNext())
			{
				Row row = (Row) rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				Cell cell;
				if (row.getRowNum() == 0)
				{
					cellCount = 0;
					while (cellIterator.hasNext())
					{
						cell = (Cell) cellIterator.next();
						int currentCellValue = (int) cell.getNumericCellValue();
						switch (cellCount)
						{
							case 0:
								break;    //WE DON'T KNOW WHAT THIS VALUE IS -- POSSIBLY PROBLEM NUMBER
							case 1:
								planningDays = currentCellValue;
								break;
							case 2:
								numberNodes = currentCellValue;
								break;
							case 3:
								numberVehicles = currentCellValue;
						}
						cellCount++;
					}
					PVRPProblemInfo.noOfDays = planningDays;
					PVRPProblemInfo.noOfShips = numberNodes;
					PVRPProblemInfo.noOfVehs = numberVehicles;
				}
				else if (row.getRowNum() <= numberVehicles + 1 && row.getRowNum() > 0)      //don't forget the header row adds one
				{
					cellCount = 0;
					while (cellIterator.hasNext())
					{
						cell = (Cell) cellIterator.next();
						int val = (int) cell.getNumericCellValue();
						if (cellCount == 0)
						{
							list.truckInfo.addLast(val);
						}
						else if (cellCount == 1)
						{
							list.truckInfo.addLast(val);
						}
					}
					numTrucks--;
				}
				else if (row.getRowNum() > numberVehicles + 1)
				{
					cellCount = 0;
					while (cellIterator.hasNext())
					{
						cell = (Cell) cellIterator.next();
						int currentCellContents = (int) cell.getNumericCellValue();
						switch (cellCount)
						{
							case 0:
								break;       // first column is emppty
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
								double DUMMY = currentCellContents;     //USUALLY ALL ZEROS
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
								for(int start = 0; start < numberCombinations; start++)
								{
									combinationsVector.addElement(row.getCell(8 + start));  //starts at column EIGHT and keeps adding data to the vector
								}

						}
					}
					theShipment.insertShipment();

				}
				rowCount++;
			}//all rows have been parsed through
			file.close();
			FileOutputStream out = new FileOutputStream(new File("data/PVRP/results/output_3.xlsx"));
			workbook.write(out);
			out.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}


}