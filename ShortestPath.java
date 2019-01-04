
/**
 *
 * @author ZheZhang
 */
/*
 * Compute the shortest path. 
 */

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;
import java.io.*;
import jxl.*;
import java.util.*;
import jxl.Workbook;
import jxl.write.DateFormat;
import jxl.write.Number;
import jxl.write.*;
import java.text.SimpleDateFormat;



public class ShortestPathTest2 {
   

 public static void main(String []args){
    GraphToolkit1 test = new GraphToolkit1();
   HashMap<String, GraphSegment1> allNodesMap = new HashMap<String, GraphSegment1>();
   HashMap<Integer, Integer> betweenness = new HashMap<Integer, Integer>();
   ArrayList<GraphSegment1> shortestPath = new ArrayList<GraphSegment1>(); 
   ArrayList<String> allnodesString= new ArrayList<String>();
   ArrayList<Integer> outputInteger= new ArrayList<Integer>();
   ArrayList<Integer> allnodesInt= new ArrayList<Integer>();
   ArrayList<GraphSegment1> getAllNodesClass =new ArrayList<GraphSegment1>();
   GraphSegment1 nodesStart; 
  try {
   
  BufferedReader input = new BufferedReader(new FileReader("D:\\work\\zhe\\data.txt")); 
   GraphToolkit1 toolkit = new GraphToolkit1();
   GetAllNodes getAllnodes = new GetAllNodes(getAllNodesClass);
 
    
   GraphSegment1 proto = new GraphSegment1("0.0",46.835443793);
   
   getAllNodesClass=getAllnodes.buildGraph(proto, input);
   GraphSegment1 firstNode = toolkit.buildGraph(proto, input);  
  
    for (int i=0; i<getAllNodesClass.size();i++){    
       
           if (getAllNodesClass.get(i).getData().equals("1500.0")){
           System.out.println(getAllNodesClass.get(i).getData()+"this is i"+i); 
        }
      
  }
    
    ArrayList<GraphSegment1> allnodes = toolkit.getNodes(getAllNodesClass.get(3492)); 
   
     String filename = "D:\\work\\zhe\\data1.xls";
   WorkbookSettings ws = new WorkbookSettings();
   ws.setLocale(new Locale("en", "EN"));
   WritableWorkbook workbook = Workbook.createWorkbook(new File(filename), ws);
   WritableSheet s = workbook.createSheet("Sheet1", 0);
   WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
   WritableCellFormat cf = new WritableCellFormat(wf);
   cf.setWrap(true);
// store all the nodes to allNodesMap.       
      String output = "";
      for ( int i = 0; i < allnodes.size(); i++) {
          allnodesString.add(allnodes.get(i).getData() + " ");
          output += allnodes.get(i).getData() + " ";
          allNodesMap.put(allnodes.get(i).getData(), allnodes.get(i));
          
      }
      for(int i=0;i<allnodes.size();i++){
          int tt= Math.round(Float.valueOf(allnodes.get(i).getData()));
          allnodesInt.add(tt);
      }
      //this print all the nodes
      //these for loops print all the shortest path from all the nodes. 
      //outputInteger contain all the shortest path. Node ID was transformed from String to Integer. 
      for(int i=0;i<allnodes.size();i++){
          betweenness.put(allnodesInt.get(i), 0);
      }
      for (int i=0; i<allnodes.size();i++){
           for (int j=i+1;j< allnodes.size();j++){
            shortestPath = test.shortestPath(allnodes.get(i), allnodes.get(j)) ; 
                  for(int t=0;t<shortestPath.size();t++){
                      Integer intShortest= Math.round(Float.valueOf(shortestPath.get(t).getData().trim()).floatValue());
                      //System.out.println(intShortest);
                      Integer val = betweenness.get(intShortest);
                      betweenness.put(intShortest, new Integer(val.intValue()+1));
                  }            
          }
      }
    
   
    Label l = new Label(0,0,"ID",cf);
    s.addCell(l);
    WritableCellFormat addID = new WritableCellFormat(NumberFormats.INTEGER);
    Label m = new Label(1,0,"Betweenness",cf);
    s.addCell(m);
    WritableCellFormat addBetw = new WritableCellFormat(NumberFormats.INTEGER);
   
    Iterator iKey = betweenness.keySet().iterator();
    Collection cValue= betweenness.values();
    Iterator iValue = cValue.iterator();
    while (iKey.hasNext()){
        for (int i=1;i<=betweenness.size();i++){
        
           
         Number n = new Number(0,i,Integer.parseInt((iKey.next().toString())),addID);
         s.addCell(n);
        
        }
         
    }
    while (iValue.hasNext()){
        for (int i=1;i<=betweenness.size();i++){
        
           
          Number m1 = new Number(1,i,Integer.parseInt(iValue.next().toString()),addBetw);
          s.addCell(m1);
        
        }
         
    }
    workbook.write();
    workbook.close();  
   
    } //try end here
    catch (IOException ioe) 
    {
      ioe.printStackTrace();
    }
    catch (Exception e) 
    {
      e.printStackTrace();
    }

 }    
    
 }


