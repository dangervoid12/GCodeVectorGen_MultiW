import javax.swing.*;
import java.awt.*;

public class GCodeVectorGen {
	static NewProject mw;
	static JFrame MainWindow;
	static JDesktopPane desktop;
	static NewProject newProject;
	static SidePanel mySidePanel1;
	static GCodeListWindow myGCodeListW;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//prepareMainWindow();
		prepareMainWindow();


		mySidePanel1 = new SidePanel();
		myGCodeListW = new GCodeListWindow();
		newProject = new NewProject(mySidePanel1,myGCodeListW);
		mySidePanel1.setProject(newProject);
		myGCodeListW.setProject(newProject);
		desktop.add(mySidePanel1);
		desktop.add(myGCodeListW);
		desktop.add(newProject);
	}
	public void createnewGcodeListW(){

	}

	public static void prepareMainWindow(){
		MainWindow = new JFrame();
		int inset = 50;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//MainWindow.setBounds(inset, inset,
		//		screenSize.width  - inset*2,
		//		screenSize.height - inset*2);
		MainWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
		desktop = new JDesktopPane();
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		MainWindow.setContentPane(desktop);
		MainWindow.setTitle("GCode Vector Gen");
		MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainWindow.setResizable(false);
		MainWindow.setVisible(true);


	}
}
