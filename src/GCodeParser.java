import java.awt.geom.Line2D;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class GCodeParser {
	NewProject MWlink;
	ArrayList<Line2D> lines;
	public GCodeParser(ArrayList<Line2D> lines){
		if(lines != null){
			this.lines = lines;
		}
		
	}
	public GCodeParser(NewProject mw){
		this.MWlink = mw;
	}
	public void saveFile(){
		
		String result1 = generateCode2();
		saveFile2(result1);
	}
	private void saveFile2(String result1) {
		// TODO Auto-generated method stub
		try(  PrintWriter out = new PrintWriter( "filename.gcode" )  ){
		    out.println( result1 );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String generateCode2(){
		String result = "";
		DefaultTableModel tempModel = MWlink.myGCodeListW.myModel2;
		int i = 0;
		int j;
		int toolno = 0;
		while(i<tempModel.getRowCount()){
			j = 0;
			toolno = (int) tempModel.getValueAt(i, 1);
			if( toolno == 2 || toolno == 3){
				/*result = result + "N"+tempModel.getValueAt(i, 0) + " G01 X" + tempModel.getValueAt(i, 2) + " Y" +
						tempModel.getValueAt(i, 3) +	" Z" + tempModel.getValueAt(i, 6) +"\n";*/
				result = result + "N"+tempModel.getValueAt(i, 0) + " G01 X" + tempModel.getValueAt(i, 2) + " Y" +
						tempModel.getValueAt(i, 3)+"\n"; // pojechanie do poczatku linii
				result = result + "N"+tempModel.getValueAt(i, 0) + " G01 Z1"+"\n"; // upuszczenie
				result = result + "N"+tempModel.getValueAt(i, 0) + " G01 X" + tempModel.getValueAt(i, 4) + " Y" +
						tempModel.getValueAt(i, 5)+"\n"; // pojechanie do konca linii
				result = result + "N"+tempModel.getValueAt(i, 0) + " G01 Z0"+"\n"; // podniesienie
			}else if(toolno == 4){
				result = result + "N"+tempModel.getValueAt(i, 0) + " G01 X" + tempModel.getValueAt(i, 2) + " Y" +
						tempModel.getValueAt(i, 3)+"\n";
				result = result + "N"+tempModel.getValueAt(i, 0) + " G01 Z1"+"\n"; // upuszczenie
				result = result + "N"+tempModel.getValueAt(i, 0) + " G02 X" + tempModel.getValueAt(i, 2) + " Y" +
						tempModel.getValueAt(i, 3)+ "R" + ((float)tempModel.getValueAt(i, 4) - (float)tempModel.getValueAt(i,2)) + "\n";
				result = result + "N"+tempModel.getValueAt(i, 0) + " G01 Z0"+"\n"; // podniesienie
			}
			i++;
			
		}
		//tempModel.getValueAt(row, column)
		System.out.println("a" + toolno);
		return result;
	}
	private String generateCode() {
		// TODO Auto-generated method stub
		String result = "";
		//int lno = 0;
		
		
		int i = 0;
		int j = 0;
		while(i<lines.size()){
			double tmpX1 = getXconv((float)lines.get(i).getX1());
			double tmpY1 = getYconv((float)lines.get(i).getY1());
			double tmpX2 = getXconv((float)lines.get(i).getX2());
			double tmpY2 = getYconv((float)lines.get(i).getY2());
			result = result + "N"+j + " G01 X" + tmpX1 + " Y" + tmpY1 +	" Z0\n";
			j++;
			/*
			if((tmpX1 != tmpX2) || (tmpY1 != tmpY2) ){
				result = result + "N"+j + " G01 X" + tmpX2 + " Y" + tmpY2 +	" Z0\n";
				j++;
			}*/
			
			i++;
			if(i == lines.size()){
				result = result + "N"+j + " G01 X" + tmpX2 + " Y" + tmpY2 +	" Z0\n"; // add ending point
			}
		}
		
		
		System.out.print(result);
		return result;
	}
	
	public float getXconv(float x1){ // coords to gcode
		x1 = x1 - 550;
		
		return x1;
	}
	public float getYconv(float y1){ // coords to gcode
		//y1 = -y1 - 550;
		if(y1 < 550){
			y1 = 550 - y1;
			
		}else{
			y1 = y1 - 550;
			y1 = -y1;
		}
		return y1;
	}
	public float getXconvBack(float x1){ //  gcode to coords
		System.out.println("xConvBack1 = " + x1);
		x1 = x1 + 550;
		System.out.println("xConvBack2 = " + x1);
		return x1;
	}
	public float getYconvBack(float y1){ // gcode to coords
		//y1 = -y1 - 550;
		System.out.println("yConvBack1 = " + y1);
		if(y1 < 0){
			y1 = -y1;
			y1 = 550 + y1;
			
		}else{
			y1 =  550 - y1;
			//y1 = -y1;
		}
		System.out.println("yConvBack2 = " + y1);
		return y1;
	}
	
}
