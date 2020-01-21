import java.awt.geom.Line2D;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class GCodeListWindow extends JInternalFrame{
	NewProject MWlink;
	boolean eventLock = false;
	private JInternalFrame me;
	JScrollPane scp;
	private JTable stepsTable;
	DefaultTableModel myModel2;
	private int counter = 0;
	public GCodeListWindow(){
		me = this;

		setupWindow();
		createTable();
		me.setVisible(true);
	}
	public void setProject(NewProject newProject){
		this.MWlink = newProject;
	}
	private void setupWindow(){
		me.setSize(400, 800);
		me.setTitle("GCode List");
		me.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		me.setResizable(false);
		me.setLocation(1250, 0);

		
	}
	
	private void createTable(){
		//String[] columnNames = {"No","Type","X1", "Y1", "X2", "Y2", "A"};
		//Object[][] table = {{1,2,100,100,200,200,10},{2,11,200,200,200,-200,0},{3,3,200,-200,-200,200,10}};
		
		
		myModel2 = new DefaultTableModel();
		myModel2.addColumn("No");
		myModel2.addColumn("Type");
		myModel2.addColumn("X1");
		myModel2.addColumn("Y1");
		myModel2.addColumn("X2");
		myModel2.addColumn("Y2");
		myModel2.addColumn("Z");
		myModel2.addColumn("A");
		myModel2.addColumn("B");
		//addNewItem(2,0,0,0,0,0,0,0);
		//myModel2.addRow(new Object[]{2,11,200,200,200,-200,0});
		//myModel2.addRow(new Object[]{3,3,200,-200,-200,200,10});
		myModel2.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent tableModelEvent) {
				if(eventLock == false) {
					if (tableModelEvent.getType() == TableModelEvent.UPDATE) {
						int row = tableModelEvent.getFirstRow();
						int col = tableModelEvent.getColumn();
						System.out.println("Changed[" + row + "x" + col + "] now is = " + stepsTable.getValueAt(row, col));

						if ((int) stepsTable.getValueAt(row, 1) == 2 || (int) stepsTable.getValueAt(row, 1) == 3) {
							float x1, y1, x2, y2;


							try{
								x1 = Float.parseFloat((String) stepsTable.getValueAt(row, 2));
							}catch (Exception e){
								x1 = (float) stepsTable.getValueAt(row, 2);
							}
							try{
								y1 = Float.parseFloat((String) stepsTable.getValueAt(row, 3));
							}catch (Exception e){
								y1 = (float) stepsTable.getValueAt(row, 3);
							}
							try{
								x2 = Float.parseFloat((String) stepsTable.getValueAt(row, 4));
							}catch (Exception e){
								x2 = (float) stepsTable.getValueAt(row, 4);
							}
							try{
								y2 = Float.parseFloat((String) stepsTable.getValueAt(row, 5));
							}catch (Exception e){
								y2 = (float) stepsTable.getValueAt(row, 5);
							}

							System.out.println("af: " + x1 + "," + y1 + "," + x2 + "," + y2);
							Line2D newline = new Line2D.Float(MWlink.myGCodeParser.getXconvBack(x1), MWlink.myGCodeParser.getYconvBack(y1),
									MWlink.myGCodeParser.getXconvBack(x2), MWlink.myGCodeParser.getYconvBack(y2));


							MWlink.myDrawOverlay1.replaceLine(row, newline);
							MWlink.myDrawOverlay1.drawEndings(row,0);
							MWlink.mySidePanel1.setLinePos(MWlink.myDrawOverlay1.getLineDataGcode(row));
							MWlink.repaint();
							MWlink.myDrawOverlay1.repaint();
							// teraz trzeba przeniesc rzeczy z sidepanel change button tutaj, i podstawic nowa linie za stara
							// trzeba najpierw znalezc stara linie w tablicy wszystkich linii.
						} else if ((int) stepsTable.getValueAt(row, 1) == 4) {
							//circle
						}

					}
				}
			}
		});
		stepsTable = new JTable(myModel2);
		scp = new JScrollPane(stepsTable);
		stepsTable.setFillsViewportHeight(true);
		me.add(scp);
		//addNewItem(1,1,1,1,1,1);
		//addNewItem(1,2,2,2,2,1);
		//addNewItem(1,1,3,3,1,1);
		//me.repaint();

		//myModel.addRow(new Object[]{2,1,0,0,100,100,5});
	}

	public void selectItem(int idx){
		stepsTable.setRowSelectionInterval(idx,idx);
	}

	public void clearSelection(){
		stepsTable.clearSelection();
	}

	public void addNewItem(int toolNo,float x1, float y1, float x2, float y2, float z, float a, float b){
		//System.out.println("" + stepsTable.getModel().getRowCount());
		myModel2.addRow(new Object[]{myModel2.getRowCount() + 1,toolNo,x1,y1,x2,y2,z,a,b});
	}
	public void replaceItem(int idx,int toolNo,float x1, float y1, float x2, float y2, float z, float a, float b){
		//System.out.println("" + stepsTable.getModel().getRowCount());
		//myModel2.addRow(new Object[]{myModel2.getRowCount() + 1,toolNo,x1,y1,x2,y2,z,a,b});
		myModel2.setValueAt(idx, idx, 0);
		myModel2.setValueAt(toolNo, idx, 1);
		myModel2.setValueAt(x1, idx, 2);
		myModel2.setValueAt(y1, idx, 3);
		myModel2.setValueAt(x2, idx, 4);
		myModel2.setValueAt(y2, idx, 5);
		myModel2.setValueAt(z, idx, 6);
		myModel2.setValueAt(a, idx, 7);
		myModel2.setValueAt(b, idx, 8);
		stepsTable.repaint();
	}
	public void clearAllTable(){
		myModel2 = null;
		
		stepsTable = null;
		
		
		scp = null;

		createTable();
		me.repaint();
	}

}
