import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {
	float myWidth,myHeight;
	float myMargin = 50;
	public DrawingPanel(float myWidth, float myHeight){
		this.setSize((int)myWidth, (int)myHeight);
		this.myWidth = myWidth;
		this.myHeight = myHeight;
	}
	public void paintComponent(Graphics g){
		
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D rec1 = new Rectangle2D.Float(50,50,myWidth,myHeight);
        Line2D lin1 = new Line2D.Float((myWidth/2)+myMargin, myMargin, (myWidth/2)+myMargin, myWidth + myMargin);
        Line2D lin2 = new Line2D.Float(myMargin, (myHeight/2)+myMargin, myHeight + myMargin, (myHeight/2)+myMargin);
        g2.draw(rec1);
        g2.draw(lin1);
        g2.draw(lin2);
	}
	
}
