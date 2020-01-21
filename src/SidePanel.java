import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.*;

public class SidePanel extends JInternalFrame {
	JInternalFrame ja = this;
	NewProject MWlink;
	GCodeParser gCodeParser;
	int typeSelected = -1;
	int lineSelected = -1;
	int circSelected = -1;
	int tool = 0;
	ArrayList<Line2D> lines;
	JTextField tX1,tX2,tY1,tY2;
	JToggleButton b1,b2,b3,b4,b5;
	public SidePanel(){
		this.setSize(150, 400);
		this.setTitle("Panel");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//mySidePanel1.setResizable(false);
		this.setVisible(true);
		//this.MWlink = MWlink;
		//this.setUndecorated(true);


		//System.out.println("aaa" + MWlink.myGCodeParser.getXconv(100));
		//this.setLocation(1100, 50);
		createAllComp();
	}
	public void setProject(NewProject newProject){
		this.MWlink = newProject;
		gCodeParser = MWlink.myGCodeParser;
	}


	public void setLines(ArrayList<Line2D> lines){
		this.lines = lines;
	}
	public int getTool(){
		return tool;
	}
	private void createAllComp(){
		JPanel myPanel = new JPanel();
		myPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		myPanel.setLayout(new BoxLayout(myPanel,BoxLayout.Y_AXIS));
		JButton BSave,BChange,BChange2,BClear;
		JPanel changePanel = new JPanel();
		changePanel.setLayout(new BoxLayout(changePanel,BoxLayout.Y_AXIS));
		changePanel.setBorder(BorderFactory.createTitledBorder("Change"));
		JPanel panelTop = new JPanel();
		panelTop.setLayout(new BoxLayout(panelTop,BoxLayout.LINE_AXIS));

		//panelTop.setBorder(BorderFactory.createTitledBorder("hhh"));
		JLabel lblTopX = new JLabel("X");
		JLabel lblTopY = new JLabel("Y");
		lblTopX.setPreferredSize(new Dimension(30,16));
		lblTopX.setMaximumSize(lblTopX.getPreferredSize());
		lblTopY.setPreferredSize(new Dimension(30,16));
		lblTopY.setMaximumSize(lblTopY.getPreferredSize());

		panelTop.add(Box.createRigidArea(new Dimension(50,0)));
		panelTop.add(lblTopX);
		panelTop.add(Box.createHorizontalGlue());
		panelTop.add(lblTopY);
		//myPanel.add(panelTop);
		changePanel.add(panelTop);
		tX1 = new JTextField("X1");
		tX1.setPreferredSize(new Dimension(50,16));
		tX1.setMaximumSize(tX1.getPreferredSize());
		//myPanel.add(tX1);
		tY1 = new JTextField("Y1");
		tY1.setPreferredSize(new Dimension(50,16));
		tY1.setMaximumSize(tY1.getPreferredSize());
		//myPanel.add(tY1);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1,BoxLayout.LINE_AXIS));
		JLabel lbl1 = new JLabel("Start");
		panel1.add(lbl1);
		panel1.add(tX1);
		panel1.add(tY1);
		panel1.setSize(200,16);
		//myPanel.add(panel1);
		changePanel.add(panel1);
		tX2 = new JTextField("X2");
		tX2.setPreferredSize(new Dimension(50,16));
		tX2.setMaximumSize(tX2.getPreferredSize());
		//myPanel.add(tX2);
		tY2 = new JTextField("Y2");
		tY2.setPreferredSize(new Dimension(50,16));
		tY2.setMaximumSize(tY2.getPreferredSize());
		//myPanel.add(tY2);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2,BoxLayout.LINE_AXIS));
		JLabel lbl2 = new JLabel("End  ");
		panel2.add(lbl2);
		panel2.add(tX2);
		panel2.add(tY2);

		//myPanel.add(panel2);
		changePanel.add(panel2);
		
		BChange = new JButton("Change");
		BChange.setPreferredSize(new Dimension(100,16));
		//myPanel.add(BChange);
		changePanel.add(BChange);
		BChange.setAlignmentX(0.7f);
		BChange.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(typeSelected == 0){
					if(lineSelected != -1){
						Line2D tmpline = MWlink.myDrawOverlay1.getLines().get(lineSelected);
						Line2D newLine = new Line2D.Float(MWlink.myGCodeParser.getXconvBack(Float.parseFloat(tX1.getText())),MWlink.myGCodeParser.getYconvBack(Float.parseFloat(tY1.getText())),
								MWlink.myGCodeParser.getXconvBack(Float.parseFloat(tX2.getText())),MWlink.myGCodeParser.getYconvBack(Float.parseFloat(tY2.getText())));
						MWlink.myDrawOverlay1.replaceLine(lineSelected, newLine);
						MWlink.myDrawOverlay1.drawEndings(lineSelected,0);
						MWlink.myGCodeListW.eventLock = true;
						MWlink.myGCodeListW.replaceItem(lineSelected, (int)MWlink.myGCodeListW.myModel2.getValueAt(lineSelected, 1),
								MWlink.myGCodeParser.getXconv((float)newLine.getX1()), 
								-MWlink.myGCodeParser.getXconv((float)newLine.getY1()),
								MWlink.myGCodeParser.getXconv((float)newLine.getX2()),
								-MWlink.myGCodeParser.getXconv((float)newLine.getY2()),
								(float)MWlink.myGCodeListW.myModel2.getValueAt(lineSelected, 6),
								(float)MWlink.myGCodeListW.myModel2.getValueAt(lineSelected, 7), 
								(float)MWlink.myGCodeListW.myModel2.getValueAt(lineSelected, 8));
						MWlink.myGCodeListW.eventLock = false;
						//MWlink.myGCodeListW.repaint();
						MWlink.repaint();
					}
				}else if(typeSelected == 1){
					if(circSelected != -1){
						float width = Float.parseFloat(tX2.getText()) - Float.parseFloat(tX1.getText());
						width = width * 2;
						float newX = MWlink.myGCodeParser.getXconvBack(Float.parseFloat(tX1.getText()) - (width / 2));
						//float newY = gCodeParser.getYconvBack(Float.parseFloat(tY1.getText()) - (width / 2));
						float newY = MWlink.myGCodeParser.getYconvBack(Float.parseFloat(tY1.getText())) - (width / 2);
						System.out.println("sa: " + newX +" " + newY + " " + width);
						Ellipse2D newCircle = new Ellipse2D.Float(newX,newY,width,width);
						MWlink.myDrawOverlay1.replaceCirc(circSelected, newCircle);
						MWlink.myDrawOverlay1.drawEndings(circSelected,1);
						MWlink.repaint();
					}
				}
			}
			
		});
		BChange2 = new JButton("Change All");
		BChange2.setPreferredSize(new Dimension(110,16));
		BChange2.setAlignmentX(0.6f);
		//myPanel.add(BChange2);
		changePanel.add(BChange2);
		BChange2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(lineSelected != -1){
					//Line2D tmpline = MWlink.myDrawOverlay1.getLines().get(lineSelected);
					Line2D prevLine ,newLine,nextLine = null;
					System.out.println("lineS:"+lineSelected + " " + gCodeParser.getXconv(100));
					newLine = new Line2D.Float(gCodeParser.getXconvBack(Float.parseFloat(tX1.getText())),gCodeParser.getYconvBack(Float.parseFloat(tY1.getText())),
							gCodeParser.getXconvBack(Float.parseFloat(tX2.getText())),gCodeParser.getYconvBack(Float.parseFloat(tY2.getText())));
					MWlink.myGCodeListW.eventLock = true;
					if(lineSelected>0){
						prevLine = MWlink.myDrawOverlay1.getLines().get(lineSelected -1);
						prevLine.setLine((double)prevLine.getX1(), (double)prevLine.getY1(), 
								(double)gCodeParser.getXconvBack(Float.parseFloat(tX1.getText())), (double)gCodeParser.getYconvBack(Float.parseFloat(tY1.getText())));
						MWlink.myDrawOverlay1.replaceLine(lineSelected - 1, prevLine);
						MWlink.myGCodeListW.replaceItem(lineSelected - 1, (int)MWlink.myGCodeListW.myModel2.getValueAt(lineSelected - 1, 1),
								MWlink.myGCodeParser.getXconv((float)prevLine.getX1()),
								-MWlink.myGCodeParser.getXconv((float)prevLine.getY1()),
								MWlink.myGCodeParser.getXconv((float)prevLine.getX2()),
								-MWlink.myGCodeParser.getXconv((float)prevLine.getY2()),
								(float)MWlink.myGCodeListW.myModel2.getValueAt(lineSelected - 1, 6),
								(float)MWlink.myGCodeListW.myModel2.getValueAt(lineSelected - 1, 7),
								(float)MWlink.myGCodeListW.myModel2.getValueAt(lineSelected - 1, 8));
					}
					if(lineSelected<(MWlink.myDrawOverlay1.getLines().size()-1)){
						nextLine = MWlink.myDrawOverlay1.getLines().get(lineSelected +1);
						nextLine.setLine((double)gCodeParser.getXconvBack(Float.parseFloat(tX2.getText())), (double)gCodeParser.getYconvBack(Float.parseFloat(tY2.getText())),
								nextLine.getX2(), nextLine.getY2() );
						MWlink.myDrawOverlay1.replaceLine(lineSelected+1, nextLine);
						MWlink.myGCodeListW.replaceItem(lineSelected +1, (int)MWlink.myGCodeListW.myModel2.getValueAt(lineSelected+1, 1),
								MWlink.myGCodeParser.getXconv((float)nextLine.getX1()),
								-MWlink.myGCodeParser.getXconv((float)nextLine.getY1()),
								MWlink.myGCodeParser.getXconv((float)nextLine.getX2()),
								-MWlink.myGCodeParser.getXconv((float)nextLine.getY2()),
								(float)MWlink.myGCodeListW.myModel2.getValueAt(lineSelected +1, 6),
								(float)MWlink.myGCodeListW.myModel2.getValueAt(lineSelected +1, 7),
								(float)MWlink.myGCodeListW.myModel2.getValueAt(lineSelected +1, 8));
					}
					
					MWlink.myDrawOverlay1.replaceLine(lineSelected, newLine);
					MWlink.myDrawOverlay1.drawEndings(lineSelected,0);
					MWlink.myGCodeListW.replaceItem(lineSelected, (int)MWlink.myGCodeListW.myModel2.getValueAt(lineSelected, 1),
							MWlink.myGCodeParser.getXconv((float)newLine.getX1()),
							-MWlink.myGCodeParser.getXconv((float)newLine.getY1()),
							MWlink.myGCodeParser.getXconv((float)newLine.getX2()),
							-MWlink.myGCodeParser.getXconv((float)newLine.getY2()),
							(float)MWlink.myGCodeListW.myModel2.getValueAt(lineSelected, 6),
							(float)MWlink.myGCodeListW.myModel2.getValueAt(lineSelected, 7),
							(float)MWlink.myGCodeListW.myModel2.getValueAt(lineSelected, 8));
					MWlink.myGCodeListW.eventLock = false;
					MWlink.repaint();
				}
			}
			
		});
		BSave = new JButton("Save");
		BSave.setPreferredSize(new Dimension(100,16));
		
		BSave.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//gCodeParser = new GCodeParser(lines);
				
				MWlink.myGCodeParser.saveFile();
			}
			
		});
		BClear = new JButton("Clear");
		BClear.setPreferredSize(new Dimension(100,16));
		
		BClear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//gCodeParser = new GCodeParser(lines);
				
				MWlink.clearAll();
			}
			
		});
		JPanel paneltools = new JPanel();
		paneltools.setLayout(new BoxLayout(paneltools,BoxLayout.Y_AXIS));
		paneltools.setBorder(BorderFactory.createTitledBorder("Tools"));
		paneltools.setMaximumSize(new Dimension(200,125));
		//paneltools.setAlignmentX(0.6f);
		paneltools.setAlignmentX(Component.CENTER_ALIGNMENT);
		b1= new JToggleButton("1. +");
		b1.setPreferredSize(new Dimension(150,16));
		b1.setMaximumSize(b1.getPreferredSize());
		//b1.setAlignmentX(Component.CENTER_ALIGNMENT);
		paneltools.add(b1);
		b2= new JToggleButton("2. -");
		b2.setPreferredSize(new Dimension(150,16));
		b2.setMaximumSize(b2.getPreferredSize());
		paneltools.add(b2);
		b3= new JToggleButton("3. N");
		b3.setPreferredSize(new Dimension(150,16));
		b3.setMaximumSize(b3.getPreferredSize());
		paneltools.add(b3);
		b4= new JToggleButton("4. O");
		b4.setPreferredSize(new Dimension(150,16));
		b4.setMaximumSize(b4.getPreferredSize());
		paneltools.add(b4);
		b5= new JToggleButton("5. C");
		b5.setPreferredSize(new Dimension(150,16));
		b5.setMaximumSize(b5.getPreferredSize());
		paneltools.add(b5);



		b1.addActionListener(new ActionListener (){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
					setB(1);
			}
			
		});
		b2.addActionListener(new ActionListener (){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
					setB(2);
			}
			
		});
		b3.addActionListener(new ActionListener (){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
					setB(3);
			}
			
		});
		b4.addActionListener(new ActionListener (){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
					setB(4);
			}
			
		});
		b5.addActionListener(new ActionListener (){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
					setB(5);
			}
			
		});
		myPanel.add(changePanel);
		myPanel.add(paneltools);
		BSave.setAlignmentX(0.6f);
		myPanel.add(BSave);
		BClear.setAlignmentX(0.6f);
		myPanel.add(BClear);
		ja.add(myPanel);
	}
	
	public void setB(int newnr){
		System.out.println("Tool change: " + tool + "->" + newnr);
		MWlink.myDrawOverlay1.clearEndings();
		b1.setSelected(false);
		b2.setSelected(false);
		b3.setSelected(false);
		b4.setSelected(false);
		b5.setSelected(false);
		if(newnr == 0){
			tool = 0;
		}
		if(newnr == 1){//Select
			b1.setSelected(true);
			tool = 1;
		}else if(newnr == 2){//line
			b2.setSelected(true);
			tool = 2;
		}else if(newnr == 3){//krzywa
			b3.setSelected(true);
			//MWlink.clickone = true;
			tool = 3;
		}else if(newnr == 4){//circle
			b4.setSelected(true);
			tool = 4;
		}else if(newnr == 5){//arc
			b5.setSelected(true);
			tool = 5;
		}
		NewProject.tmpx1 = -1;
		NewProject.tmpy1 = -1;
		
	}
	
	
	public void clearAllPos(){
		tX1.setText("");
		tY1.setText("");
		tX2.setText("");
		tY2.setText("");
	}
	
	public void setLinePos(float[] lineData){
		typeSelected = 0;
		lineSelected = Math.round(lineData[0]);
		tX1.setText("" +Math.round(lineData[1]));
		tY1.setText("" +Math.round(lineData[2]));
		tX2.setText("" +Math.round(lineData[3]));
		tY2.setText("" +Math.round(lineData[4]));
	}
	public void setCircPos(float[] circData){
		typeSelected = 1;
		circSelected = Math.round(circData[0]);
		tX1.setText("" +circData[1]);
		tY1.setText("" +circData[2]);
		tX2.setText("" +circData[3]);
		tY2.setText("" +circData[4]);
	}
}
