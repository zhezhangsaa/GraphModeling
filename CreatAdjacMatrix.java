

/**
 *
 * @author Zhe Zhang
 */
import java.util.ArrayList;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Vector;
import java.lang.Object;
import java.io.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class CreatAdjacMatrix {
  
  //public static final int BSIZE=6;
  //int board[][]=new int[BSIZE][BSIZE];
  public ArrayList<String> road; 
  //constructor
  public CreatAdjacMatrix (ArrayList<String> road1){
    this.road= road1;  
  }
  
  public static Vector ReadCSV(String fileName) {
		Vector cellVectorHolder = new Vector();
                
		try {
		// read string file path	
                    FileInputStream myInput = new FileInputStream(fileName);

			POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

			HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

			HSSFSheet mySheet = myWorkBook.getSheetAt(0);

			Iterator rowIter = mySheet.rowIterator();

			while (rowIter.hasNext()) {
				HSSFRow myRow = (HSSFRow) rowIter.next();
                                // for each row´s each cell
				Iterator cellIter = myRow.cellIterator();
				Vector cellStoreVector = new Vector();
				while (cellIter.hasNext()) {
					HSSFCell myCell = (HSSFCell) cellIter.next();
					cellStoreVector.addElement(myCell);
				}
				cellVectorHolder.addElement(cellStoreVector);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellVectorHolder;
	}
//read excel data and stroe each excel cell data to arraylist
	public static ArrayList printCellDataToConsole(Vector dataHolder) {
            // store each cell value to an array
           ArrayList arrayCell=new ArrayList();
		for (int i = 0; i < dataHolder.size(); i++) {
			Vector cellStoreVector = (Vector) dataHolder.elementAt(i);
			for (int j = 0; j < cellStoreVector.size(); j++) {
				HSSFCell myCell = (HSSFCell) cellStoreVector.elementAt(j);
				String stringCellValue = myCell.toString();
                                arrayCell.add(stringCellValue);
                                
                               
			}
			
		}
           return arrayCell;
	}
 
    public static void main(String[] args){
      try{
          
      Writer output = null;
      
      File file= new File("D:\\work\\zhe\\data.txt");
      output = new BufferedWriter(new FileWriter(file));
      PrintWriter pw= new PrintWriter(output);
      
      
      String fileName = "D:\\work\\zhe\\data1.xls";
      Vector dataHolder = ReadCSV(fileName);
      ArrayList road =printCellDataToConsole(dataHolder);
        
      for (int i=0; i<= road.size()-6;i+=6){
          for (int j= i+6;j<=road.size()-6;j+=6){                
              if ((road.get(i+1).equals(road.get(j+1))&& road.get(i+2).equals(road.get(j+2)))||(road.get(i+1).equals(road.get(j+3))&&road.get(i+2).equals(road.get(j+4)))||(road.get(i+3).equals(road.get(j+1))&&road.get(i+4).equals(road.get(j+2)))||(road.get(i+3).equals(road.get(j+3))&&road.get(i+4).equals(road.get(j+4)))){
                  //System.out.println((road.get(i))+","+(road.get(i+5))+","+(road.get(j))+","+(road.get(j+5)));
                  pw.println((road.get(i))+","+(road.get(i+5))+","+(road.get(j))+","+(road.get(j+5)));
                 
              }
            
          }

      }
                 output.close();
      }catch (IOException e)
    {
              e.printStackTrace();
          }

}

}
