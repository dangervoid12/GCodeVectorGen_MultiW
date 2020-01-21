import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class NewProject extends JInternalFrame {
	NewProject ja;
	boolean clickone = false;
	boolean clicktwo = false;
	//DrawingPanel myPanel1;
	DrawingPanelOverlay myDrawOverlay1;
	TouchPanelOverlay myTouchOverlay1;
	SidePanel mySidePanel1;
	GCodeListWindow myGCodeListW;

	GCodeParser myGCodeParser;
	//MouseEventListener myMouseEventListener1;
	static float tmpx1,tmpy1 = -1;
	static float tmpx2,tmpy2 = -1;
	int checkTouchLine = -1;
	int checkTouchCirc = -1;
	int checkTouchArcs = -1;
	public NewProject(SidePanel sidePanel, GCodeListWindow gCodeListWindow){
		ja = this;
		ja.mySidePanel1 = sidePanel;
		ja.myGCodeListW = gCodeListWindow;
		ja.setSize(1100, 1100);
		ja.setLocation(150, 0);
		ja.setTitle("GCode Vector Gen");
		ja.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ja.setResizable(false);
		//BasicInternalFrameUI myui = ja.getUI();
		//Dimension da = ( (javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).getNorthPane().getPreferredSize();
		//da.setSize(new Dimension((int)da.getWidth(),1));
		//( (javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).getNorthPane().setPreferredSize(da);
		( (javax.swing.plaf.basic.BasicInternalFrameUI) getUI()).setNorthPane(null);
		//System.out.println("dime: " + da.getWidth() + " " + da.getHeight());
		ja.setVisible(true);
		//myPanel1 = new DrawingPanel(1000,1000);
		myGCodeParser = new GCodeParser(ja);
		myDrawOverlay1 = new DrawingPanelOverlay(ja,1000,1000);
		myTouchOverlay1 = new TouchPanelOverlay(1000,1000);

		
		System.out.println("aaa" + myGCodeParser.getXconv(100));
		//myMouseEventListener1 = new MouseEventListener(ja,myDrawOverlay1, mySidePanel1);

		//myPanel2 = new DrawingPanel(400,400);
		//myPanel.paint(this.getGraphics());
		//myPanel.setSize(100, 100);
		//this.add(myPanel);
		//this.add(myPanel1, BorderLayout.CENTER);
		this.add(myDrawOverlay1,BorderLayout.CENTER);
		
		
		
		//this.add(myTouchOverlay1,BorderLayout.EAST);
		//myDrawOverlay1.addLine(100, 100, 200, 300);
		myDrawOverlay1.addMouseListener(new MouseListener(){
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getButton() == MouseEvent.BUTTON1){
					int tool= mySidePanel1.getTool();
					if(clickone){//second click
						
						//myDrawOverlay1.addLine(100, 100, 200, 300);
						if(clicktwo){ // third click
							
							if(tool == 5){
								//arc
								
								float widthX = tmpx2 - tmpx1;
								float widthY = tmpy2 - tmpy1;
								if(widthX < 0){
									widthX = -widthX;
								}
								widthX = widthX *2;
								if(widthY < 0){
									widthY = -widthY;
								}
								widthY = widthY*2;
								
								double result = Math.pow((double)widthX, 2) + Math.pow((double)widthY, 2);
								result = Math.sqrt(result); //srednica
								//result = result / 2; // promien
								float newX = (float) e.getX();
								float newY = (float) e.getY();
								float widthX2 = newX - tmpx2;
								float widthY2 = newY - tmpy2;
								/*if(widthX2 < 0){
									widthX2 = -widthX2;
								}
								widthX2 = widthX2 *2;
								if(widthY2 < 0){
									widthY2 = -widthY2;
								}
								widthY2 = widthY2*2;
								*/
								
								float startdeg,enddeg;
								//startdeg = 90;//-45;
								enddeg = 90;
								/////////////////////zle rysuje arc problemy z obliczeniem kata trojkata wpisanego w kolo
								System.out.println("Result: " + result );
								//float sideA = (float) Math.sqrt(Math.pow((double)result,2) * 2); 
								startdeg = (float) Math.atan2(widthX2, widthY2);
								startdeg = (float) (Math.toDegrees(startdeg) -45);		
								
								//startdeg = (float) (sideA / (2 * result));
								//System.out.println("SideA: " + sideA  + " startdeg=" + startdeg);
								/*float sideA2 = (float) Math.sqrt(Math.pow((double)result,2) * 2); 
								enddeg = (float) (sideA2 / (2 * result));*/
								//myDrawOverlay1.addCircle(tmpx1, tmpy1, (float) Math.round(result));
								myDrawOverlay1.addArc(tmpx1,tmpy1,(float) Math.round(result),startdeg,enddeg);
								
								ja.mySidePanel1.setB(0);
								myDrawOverlay1.clearEndings();
							}
							clicktwo = false;
							clickone = false;
							ja.repaint();
						}else{ // still second click
							if(tool == 1){
								//wybieranie
								clickone = false;
							}else if(tool ==2){
								//prosta
								if(tmpx1 == -1){
									tmpx1 = 550;
									tmpy1 = 550;
								}
								if(tmpy1 == -1){
									tmpy1 = 550;
									tmpx1 = 550;
								}
								myDrawOverlay1.addLine(tmpx1, tmpy1, (float) e.getX(), (float) e.getY());
								myDrawOverlay1.clearEndings();
								//mySidePanel1.setLinePos(myDrawOverlay1.getLineDataGcode(myDrawOverlay1.getLines().size()-1));
								//myDrawOverlay1.drawEndings(myDrawOverlay1.getLines().size()-1);
								clickone = false;
							}else if(tool == 3){
								//krzywa
								if(tmpx1 == -1){
									tmpx1 = 550;
									tmpy1 = 550;
								}
								if(tmpy1 == -1){
									tmpy1 = 550;
									tmpx1 = 550;
								}
								myDrawOverlay1.addLine(tmpx1, tmpy1, (float) e.getX(), (float) e.getY());
								tmpx1 = (float) e.getX();
								tmpy1 = (float) e.getY();
							}else if(tool == 4){
								//kolo
								float newX = (float) e.getX();
								float newY = (float) e.getY();
								float widthX = newX - tmpx1;
								float widthY = newY - tmpy1;
								if(widthX < 0){
									widthX = -widthX;
								}
								widthX = widthX *2;
								if(widthY < 0){
									widthY = -widthY;
								}
								widthY = widthY*2;
								
								double result = Math.pow((double)widthX, 2) + Math.pow((double)widthY, 2);
								result = Math.sqrt(result);
								
									myDrawOverlay1.addCircle(tmpx1, tmpy1, (float) Math.round(result));
								
								clickone = false;
								ja.mySidePanel1.setB(0);
								myDrawOverlay1.clearEndings();
							
							}else if(tool == 5){
								//arc
								tmpx2 = (float) e.getX();
								tmpy2 = (float) e.getY();
								clicktwo = true;
								
								
							}
							//myDrawOverlay1.repaint();
							ja.repaint();
							mySidePanel1.setLines(myDrawOverlay1.getLines());
							//clickone = false;
							//tmpx1 = (float) e.getX();
							//tmpy1 = (float) e.getY();
							//System.out.println("sss: "+ tmpx1 + " " + tmpy1 + " " + e.getX() + " " + e.getY());
						}
					}else{// First click
						
						//System.out.println("tool == " + tool);
						if(tool == 1){
							
							checkTouchLine = myDrawOverlay1.returnTouchedLine(e.getX(), e.getY());
							//System.out.println("assb " + checkTouchLine);
							if(checkTouchLine != -1){
								//System.out.println("assa " + checkTouchLine);
								//System.out.println("assa1" + myDrawOverlay1.getLineData(checkTouchLine));
								mySidePanel1.setLinePos(myDrawOverlay1.getLineDataGcode(checkTouchLine));
								myDrawOverlay1.drawEndings(checkTouchLine,0);
								myGCodeListW.selectItem(checkTouchLine);
							}else{
								
								checkTouchCirc = myDrawOverlay1.returnTouchedCircle(e.getX(),e.getY());
								if(checkTouchCirc != -1){
									mySidePanel1.setCircPos(myDrawOverlay1.getCircDataGcode(checkTouchCirc));
									myDrawOverlay1.drawEndings(checkTouchCirc,1);
								}else{
									checkTouchArcs = myDrawOverlay1.returnTouchedArcs(e.getX(),e.getY());
									if(checkTouchArcs != -1){
										mySidePanel1.setCircPos(myDrawOverlay1.getCircDataGcode(checkTouchArcs));
										myDrawOverlay1.drawEndings(checkTouchArcs,2);
									}
								}
							}
							if(myDrawOverlay1.getLastSelected() == -1){
								myDrawOverlay1.clearEndings();
							}
							
						}else if(tool == 2){
							//prosta
							tmpx1 = (float) e.getX();
							tmpy1 = (float) e.getY();
							myDrawOverlay1.drawTempStartCircle(tmpx1, tmpy1);
							clickone = true;
						}else if(tool == 3){
							//krzywa
							tmpx1 = (float) e.getX();
							tmpy1 = (float) e.getY();
							myDrawOverlay1.drawTempStartCircle(tmpx1, tmpy1);
							clickone = true;
						}else if(tool == 4){
							//kolo
							tmpx1 = (float) e.getX();
							tmpy1 = (float) e.getY();
							myDrawOverlay1.drawTempStartCircle(tmpx1, tmpy1);
							clickone = true;
						}else if(tool == 5){
							//arc
							tmpx1 = (float) e.getX();
							tmpy1 = (float) e.getY();
							myDrawOverlay1.drawTempStartCircle(tmpx1, tmpy1);
							clickone = true;
						}
						ja.repaint();
					}
				}else if(e.getButton() == MouseEvent.BUTTON3){
					//System.out.println("b2");
					if(clickone){
						clickone = false;
						
					}
					mySidePanel1.setB(0);
					myDrawOverlay1.clearEndings();
					myGCodeListW.clearSelection();
					mySidePanel1.clearAllPos();

					ja.repaint();
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("2ss");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("3ss");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("4ss");
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("5ss");
			}
			
		});
		//this.add(myPanel2, BorderLayout.EAST);
		
		
	}
	public void clearAll(){
		myDrawOverlay1.clearAllArrays();
		mySidePanel1.clearAllPos();
		myGCodeListW.clearAllTable();
		//myGCodeListW.dispose();

		//myGCodeListW = new GCodeListWindow();
		//myGCodeListW.setProject(ja);
		//myGCodeListW.repaint();
	}
	public void paintComponent(Graphics g){
		//super.paintComponents(g);
		//myPanel1.paint(g);
		//myDrawOverlay1.paint(g);
		myTouchOverlay1.paint(g);
		//myGCodeListW.paint(g);
		//myPanel2.paint(g);
	}
}
