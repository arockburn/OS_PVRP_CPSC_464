package edu.sru.thangiah.zeus.pvrp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFRow.CellIterator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.sru.thangiah.zeus.pvrp.PVRPShipmentLinkedList.PVRPShipmentLinkedList;
import edu.sru.thangiah.zeus.core.*;

public class PVRPExcelReadWrite
{
	public PVRPExcelReadWrite(){
		
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
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void excelReader(String inputFile, PVRPShipmentLinkedList list)
  {
    try
    {
      int numTrucks = 0, rowCount, cellCount;
      int custNum = 0, x = 0, y = 0, demand = 0, freq = 0;
      FileInputStream file = new FileInputStream(new File("data/PVRP/data/" + inputFile));
      
      XSSFWorkbook workbook = new XSSFWorkbook(file);
      
      XSSFSheet sheet = workbook.getSheetAt(0);
      
      Iterator<Row> rowIterator = sheet.iterator();
      rowCount = 0;
      while (rowIterator.hasNext())
      {
        Row row = (Row)rowIterator.next();
        Iterator<Cell> cellIterator = row.cellIterator();
        Cell cell;
        if(rowCount == 0){
        	cellCount = 0;
	        while (cellIterator.hasNext()){
	          cell = (Cell)cellIterator.next();
	          int val = (int) cell.getNumericCellValue();
	          switch(cellCount){
	          case 0:
	        	  break;
	          case 1:
	        	  list.period = val;
	        	  break;
	          case 2:
	        	  list.numNodes = val;
	        	  break;
	          case 3:
	        	  list.numTrucks = val;
	          }
	          cellCount++;
	        }
        }
        else if(rowCount < numTrucks){
    		   cellCount = 0;
    		   while(cellIterator.hasNext()){
    			   cell = (Cell)cellIterator.next();
    			   int val = (int) cell.getNumericCellValue();
    			   if(cellCount == 0){
    				   list.truckInfo.addLast(val);
    			   }
    			   else if(cellCount == 1){
    				   list.truckInfo.addLast(val);
    			   }
    		   }
    		   numTrucks --;
       }
        else if(rowCount > 1){
        	cellCount = 0;
        	while(cellIterator.hasNext()){
        		cell = (Cell)cellIterator.next();
        		int val = (int) cell.getNumericCellValue();
        		switch(cellCount){
        		case 0:
        			custNum = val;
        			break;
        		case 1:
        			x = val;
        			break;
        		case 2: 
        			y = val;
        			break;
        		case 4:
        			demand = val;
        			break;
        		case 5:
        			freq = val;
        			break;	
        		default:
        			break;
        		}
        		cellCount++;
        	}
        	list.insertShipment(custNum, x, y, demand, freq);
        	
        }
       rowCount++;
      }//all rows have been parsed through
      file.close();
      FileOutputStream out = new FileOutputStream(new File("data/PVRP/results/output_3.xlsx"));
      workbook.write(out);
      out.close();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  
}