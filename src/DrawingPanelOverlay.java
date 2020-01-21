import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DrawingPanelOverlay extends JPanel {
	NewProject MWlink;
	
	private int lastSelected = -1;
	private int typeSelected = -1;
	private int txtObjNo = -1;
	int myWidth,myHeight;
	float myMargin = 25;
	
	private  ArrayList<Line2D> lines = new ArrayList<Line2D>();
	private  ArrayList<Ellipse2D> circles = new ArrayList<Ellipse2D>();
	private  ArrayList<Arc2D> arcs = new ArrayList<Arc2D>();
	private  ArrayList<Ellipse2D> endings = new ArrayList<Ellipse2D>();
	
	public DrawingPanelOverlay(NewProject mw, int myWidth, int myHeight){
		this.MWlink = mw;
		this.setSize((int)myWidth, (int)myHeight);
		this.myWidth = myWidth;
		this.myHeight = myHeight;

		//lines.add(new Line2D.Float(0,0,0,0));
		
	}
	
	public void clearAllArrays(){
		lines = null;
		lines = new ArrayList<Line2D>();
		circles = null;
		circles = new ArrayList<Ellipse2D>();
		arcs = null;
		arcs = new ArrayList<Arc2D>();
		endings = null;
		endings = new ArrayList<Ellipse2D>();
		MWlink.repaint();
	}
	public void paint(Graphics g){
//mozliwe brakujace super.paintcomponent SPRAWDZIC !!!!!!!!!!!!!!!!
		paintComponent(g);
	}
	public void addLine(float mx, float my, float mx2, float my2){
		this.lines.add(new Line2D.Float(mx,my,mx2,my2));
		MWlink.myGCodeListW.addNewItem(MWlink.mySidePanel1.getTool(), MWlink.myGCodeParser.getXconv(mx), 
				MWlink.myGCodeParser.getYconv(my), MWlink.myGCodeParser.getXconv(mx2),MWlink.myGCodeParser.getYconv(my2), 1,0,0);
	}
	public int getLastSelected(){
		return lastSelected;
	}
	public int getTypeSelected(){
		return typeSelected;
	}
	public void replaceLine(int lineidx, Line2D newLine){
		System.out.println("lines size : " + lines.size());
		this.lines.set(lineidx, newLine);
	}
	public void replaceCirc(int circidx, Ellipse2D newCirc){
		System.out.println("circ size : " + circles.size());
		this.circles.set(circidx, newCirc);
		MWlink.myGCodeListW.replaceItem(circidx+1,4,Float.parseFloat(MWlink.mySidePanel1.tX1.getText()), Float.parseFloat(MWlink.mySidePanel1.tY1.getText()),
				Float.parseFloat(MWlink.mySidePanel1.tX2.getText()), Float.parseFloat(MWlink.mySidePanel1.tY2.getText()),1,0,0);
	}
	public void drawEndings(int objnr, int objType){
		clearEndings();
		//System.out.println("ssa");
		typeSelected = objType;
			lastSelected = objnr;
			
			float width = 20;
			float height = 20;
		if(typeSelected == 0){
			double newX = lines.get(lastSelected).getX1() - width / 2.0;
		    double newY = lines.get(lastSelected).getY1() - height / 2.0;
			this.endings.add(new Ellipse2D.Float((float) newX, (float) newY, width, height));
			//System.out.println("11lastSel:" + lastSelected + " typeSel:" + typeSelected);
			newX = (float)lines.get(lastSelected).getX2() - width / 2.0;
		    newY = (float)lines.get(lastSelected).getY2() - height / 2.0;
			this.endings.add(new Ellipse2D.Float((float) newX, (float) newY, width, height));
		}else if(typeSelected == 1){
			//double newX = circles.get(lastSelected).getCenterX();
			double newX = circles.get(lastSelected).getX() + (circles.get(lastSelected).getWidth() /2);
			double newY = circles.get(lastSelected).getY() + (circles.get(lastSelected).getHeight() /2);
		    //double newY = circles.get(lastSelected).getCenterY();
			this.endings.add(new Ellipse2D.Float((float) newX - 10, (float) newY - 10, width, height));
			newX = newX + (circles.get(lastSelected).getWidth() / 2); 
		    
		    this.endings.add(new Ellipse2D.Float((float) newX - 10, (float) newY - 10, width, height));
		}else if(typeSelected == 2){
			//double newX = circles.get(lastSelected).getCenterX();
			double newX = arcs.get(lastSelected).getX() + (arcs.get(lastSelected).getWidth() /2);
			double newY = arcs.get(lastSelected).getY() + (arcs.get(lastSelected).getHeight() /2);
		    //double newY = circles.get(lastSelected).getCenterY();
			this.endings.add(new Ellipse2D.Float((float) newX - 10, (float) newY - 10, width, height));
			newX = newX + (circles.get(lastSelected).getWidth() / 2); 
		    
		    this.endings.add(new Ellipse2D.Float((float) newX - 10, (float) newY - 10, width, height));
		}
		System.out.println("11lastSel:" + lastSelected + " typeSel:" + typeSelected);
	}
	public void addCircle(float centerX, float centerY, float width){
		float newX = centerX - (width / 2);
		float newY = centerY - (width / 2);
		//float newX2 = centerX + width;
		//float newY2 = centerY + width;
		this.circles.add(new Ellipse2D.Float(newX, newY, width, width));
		MWlink.myGCodeListW.addNewItem(MWlink.mySidePanel1.getTool(), MWlink.myGCodeParser.getXconv(centerX), 
				MWlink.myGCodeParser.getYconv(centerY), MWlink.myGCodeParser.getXconv(centerX + (width/2)),MWlink.myGCodeParser.getYconv(centerY), 1,0,0);
		//this.circles.add(new Ellipse2D.Float(450, 450, 200, 200));
	}
	public void addArc(float centerX, float centerY, float width, float startDeg, float endDeg){
		float newX = centerX - (width / 2);
		float newY = centerY - (width / 2);
		//float newX2 = centerX + width;
		//float newY2 = centerY + width;
		//startDeg = startDeg * 100;
		
		System.out.println("k:" + newX + " " + newY + " " + width + " " + startDeg + " " + endDeg);
		this.arcs.add(new Arc2D.Float(newX, newY, width, width,startDeg,endDeg, Arc2D.OPEN));
		
		System.out.println("arcssize:" + arcs.size());
		
	}
	public void drawTempStartCircle(float newX, float newY){
		clearEndings();
		float width = 20;
		float height = 20;
		newX = newX - width / 2;
	    newY = newY - height / 2;
		this.endings.add(new Ellipse2D.Float( newX,  newY, 20, 20));
		//MWlink.repaint();
	}
	
	public void clearEndings(){
		endings = null;
		endings = new ArrayList<Ellipse2D>();
		lastSelected = -1;
		typeSelected = -1;
	}

	public int findLine2(Line2D lineToFind){
		return lines.indexOf(lineToFind);
	}

	public int findLine(float x1, float y1, float x2, float y2){
		int result = -1;
		int i = 0;
		while(i<lines.size()){
			Line2D tmpLine = lines.get(i);
			if(tmpLine.getX1() == x1 && tmpLine.getY1() == y1){
				if(tmpLine.getX2() == x2 && tmpLine.getY2() == y2){
					result = i;
				}
			}
			i++;
		}
		return result;
	}
	public int findLineAtBegin(float x1, float y1){
		int result = -1;
		int i=0;
		while (i<lines.size()){
			Line2D tmpLine = lines.get(i);
			if(tmpLine.getX1() == x1 && tmpLine.getY1() == y1){
				result = i;
			}
		}
		return result;
	}

	public ArrayList<Line2D> getLines(){
		return lines;
	}
	public void setLines(ArrayList<Line2D> newLines){
		this.lines = newLines;
	}
	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		//int myWidth = 1000;
		//int myHeight = 1000;
		//int myMargin = 50;
		
		Rectangle2D rec1 = new Rectangle2D.Float(10,10,myWidth +10,myHeight +10);
        Line2D lin1 = new Line2D.Float((myWidth/2)+myMargin, myMargin, (myWidth/2)+myMargin, myWidth + myMargin);
        Line2D lin2 = new Line2D.Float(myMargin, (myHeight/2)+myMargin, myHeight + myMargin, (myHeight/2)+myMargin);
        Line2D lin3 = new Line2D.Float(515,425,535,425); // 100 on y axis
        Line2D lin4 = new Line2D.Float(625,515,625,535); // 100 on x axis
        g2.draw(rec1);
        g2.draw(lin1);
        g2.draw(lin2);
        g2.draw(lin3);
        g2.draw(lin4);
		g2.setStroke(new BasicStroke(5f));
		
		g2.drawString("-500", 25, 15);
		g2.drawString("0", 522, 15);
		g2.drawString("500", 1005, 15);
		
		g2.drawString("500", 0, 35);
		g2.drawString("0", 0, 530);
		g2.drawString("-500", 0, 1025);
        for(final Line2D r : lines) {
            //r.paint(g);
        	g2.draw(r);
        }
        
        //System.out.println("endings =" + endings.size());
        for(final Ellipse2D e : circles) {
            //r.paint(g);
        	g2.draw(e);
        }
        for(final Arc2D ar : arcs) {
            //r.paint(g);
        	g2.draw(ar);
        }
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(Color.RED);
        for(final Ellipse2D ca : endings) {
            //r.paint(g);
        	//System.out.println("ca " );
        	g2.draw(ca);
        	g2.setStroke(new BasicStroke(3f));
        	
        	if(endings.indexOf(ca) == 0){
        		g2.drawString("Start", (int) ca.getCenterX()+ 10, (int)ca.getCenterY() - 10);
        	}else if(endings.indexOf(ca) == 1){
        		g2.drawString("End", (int) ca.getCenterX()+ 10, (int)ca.getCenterY() - 10);
        	}
        	g2.setStroke(new BasicStroke(2f));
        }
        System.out.println("33lastSel:" + lastSelected + " typeSel:" + typeSelected);
        if(lastSelected != -1){
        	//if(typeSelected == 0){
	        
	        	float newX = 0, newY = 0;
	        	float oldX1 = 0,oldX2,oldY1 = 0,oldY2;
	        	if(typeSelected == 0){
	        		oldX1 = (float) lines.get(lastSelected).getX1();
	        		oldY1 = (float) lines.get(lastSelected).getY1();
	        		oldX2 = (float) lines.get(lastSelected).getX2();
	        		oldY2 = (float) lines.get(lastSelected).getY2();
	        		newX = (oldX1 - oldX2) / 2;
		        	newY = (oldY1 - oldY2) / 2;
		        	newX = oldX2 + newX;
		        	newY = oldY2 + newY;
	        	}else if(typeSelected == 1){
	        		oldX1 = (float) circles.get(lastSelected).getX();
	        		oldY1 = (float) circles.get(lastSelected).getY();
	        		
	        	}else if(typeSelected == 2){
	        		oldX1 = (float) circles.get(lastSelected).getX();
	        		oldY1 = (float) circles.get(lastSelected).getY();
	        	}
	        	
	        	if(typeSelected == 0){
	        		g2.drawString("Line: " + (lastSelected + 1), newX, newY -10);
	        	}else if(typeSelected == 1){
	        		
	        		g2.drawString("Circle: " + (lastSelected + 1), oldX1, oldY1 -10);
	        	}else if(typeSelected == 2){
	        		g2.drawString("Arc: " + (lastSelected + 1), oldX1, oldY1 -10);
	        	}
	        
	        //}
        }
        
    }
	
	public float[] getLineData(int lineIdx){
		Line2D tmpLine = lines.get(lineIdx);
		float result[] = {(float)lineIdx,(float)tmpLine.getX1(),(float)tmpLine.getY1(),(float)tmpLine.getX2(),(float)tmpLine.getY2()};
		
		return result;
	}
	public float[] getLineDataGcode(int lineIdx){
		Line2D tmpLine = lines.get(lineIdx);
		//MWlink.myGCodeParser.ge();
		float result[] = {(float)lineIdx,MWlink.myGCodeParser.getXconv((float)tmpLine.getX1()),MWlink.myGCodeParser.getYconv((float)tmpLine.getY1()),
				MWlink.myGCodeParser.getXconv((float)tmpLine.getX2()),MWlink.myGCodeParser.getYconv((float)tmpLine.getY2())};
		
		return result;
	}
	public float[] getCircDataGcode(int circIdx){
		Ellipse2D tmpCirc = circles.get(circIdx);
		//MWlink.mySidePanel1.createGParser();
		float result[] = {(float)circIdx,
				MWlink.myGCodeParser.getXconv((float)tmpCirc.getX() + (float)(tmpCirc.getWidth()/2)),
				MWlink.myGCodeParser.getYconv((float)tmpCirc.getY() + (float)(tmpCirc.getHeight() /2)),
				MWlink.myGCodeParser.getXconv((float)tmpCirc.getX() + (float)tmpCirc.getWidth()),
				MWlink.myGCodeParser.getYconv((float)tmpCirc.getY() + (float)(tmpCirc.getHeight() /2))};
		System.out.println("af " + tmpCirc.getHeight() + "  " + (tmpCirc.getX()  + tmpCirc.getWidth()));
		return result;
	}
	public int returnTouchedLine(float mx, float my){
		int result = -1;
		float HIT_BOX_SIZE = 10;
		float boxX = mx - HIT_BOX_SIZE / 2;
		float boxY = my - HIT_BOX_SIZE / 2;

		float width = HIT_BOX_SIZE;
		float height = HIT_BOX_SIZE;

		for (Line2D line : lines) {
			//System.out.println("checking line: " + lines.indexOf(line));
			if (line.intersects(boxX, boxY, width, height)) {
				result = lines.indexOf(line);
			}		
		}
		lastSelected = result;
		typeSelected = 0;
		System.out.println("2lastSel2:" + lastSelected + " typeSel:" + typeSelected);
		return result;
	}
	public int returnTouchedCircle(float mx, float my){
		int result = -1;
		float HIT_BOX_SIZE = 10;
		float boxX = mx - HIT_BOX_SIZE / 2;
		float boxY = my - HIT_BOX_SIZE / 2;

		float width = HIT_BOX_SIZE;
		float height = HIT_BOX_SIZE;

		for (Ellipse2D e : circles) {
			//System.out.println("checking line: " + lines.indexOf(line));
			if (e.intersects(boxX, boxY, width, height)) {
				result = circles.indexOf(e);
			}		
		}
		lastSelected = result;
		typeSelected = 1;
		return result;
	}
	public int returnTouchedArcs(float mx, float my){
		int result = -1;
		float HIT_BOX_SIZE = 10;
		float boxX = mx - HIT_BOX_SIZE / 2;
		float boxY = my - HIT_BOX_SIZE / 2;

		float width = HIT_BOX_SIZE;
		float height = HIT_BOX_SIZE;

		for (Arc2D e : arcs) {
			//System.out.println("checking line: " + lines.indexOf(line));
			if (e.intersects(boxX, boxY, width, height)) {
				result = arcs.indexOf(e);
			}		
		}
		lastSelected = result;
		typeSelected = 1;
		return result;
	}
}
