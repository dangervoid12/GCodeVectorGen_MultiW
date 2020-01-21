import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class TouchPanelOverlay extends JPanel implements MouseListener{
	float myWidth,myHeight;
	float myMargin = 50;
	public TouchPanelOverlay(float myWidth, float myHeight){
		this.setSize((int)myWidth, (int)myHeight);
		this.myWidth = myWidth;
		this.myHeight = myHeight;
		
	}
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		
        //g2.draw(lin2);
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("ssss");
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
