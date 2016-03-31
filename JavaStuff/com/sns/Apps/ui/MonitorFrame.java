package com.sns.Apps.ui;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import com.sns.Util.Tracer;
import com.sns.Util.MonitorTool;

/**
 * The main program for the Directory application.
 * 
 * @version 1.0 12-Jun-2006
 * @author Rob Broadhead
 */
public class MonitorFrame extends JFrame {

	/* Properties of the Frame */
	public static MonitorFrame thisFrame;

	private MonitorTool monitor;
	public JTable sitesList;
	private String[] columnNames = {"Site",
            "Status",
            "Valid"};
	private	Object[][] data;
	/* End properties definition */

	static {
		/*
		 * try { // Start the GUI with a Windows look and feel
		 * UIManager.setLookAndFeel("javax.swing.plaf.metal."); }catch (Exception e){}
		 */
		Tracer.setFile("AppTrace.log");
		Tracer.setActive(true);
	}

	/**
	 * A constructor for the class. This takes no parameters.
	 * 
	 */
	public MonitorFrame() {
	} /* End of the constructor */

	/**
	 * A constructor for the class. It takes a parameter that is used to build the info about the
	 * path.
	 * 
	 * @param String parm The path we'll be working with.
	 */
	public MonitorFrame(String parm) {
		/* Give the frame a title */
		super("My Sites Monitor");

		thisFrame = this;
		monitor = new MonitorTool();
		monitor.loadSites();

		/* Set up and display the frame */
		getContentPane().setLayout(new BorderLayout());
		setSize(300, 200);

		data = new Object[monitor.getSites().size() + 1][3];

		/* Set up default values */
		createPanel();

		/* Add a listener in case user clicks the x to close the frame */
		this.addWindowListener(new windowHandler());
		
		/* Handle the timer  */
		int QTR_MINUTE=15000;
		Timer timer = new Timer(QTR_MINUTE, new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {
		    		updateData();
		    		Timer t = (Timer) evt.getSource();
		    		t.start();
		    		MonitorFrame.thisFrame.repaint();
		    }    
		});
		timer.start();
		
	} /* End of the constructor */

	private void updateData() {
		// Reload the sites in case the file changed
		monitor.loadSites();

		int idx=0;
		String curSite = "";
		Iterator it = monitor.getSites().iterator(); 
		while (it.hasNext()) {
			curSite = (String) it.next();
			
			data[idx][0] = monitor.getSiteName(curSite);
			if (monitor.getSiteStatus(curSite)) {
				data[idx][1] = "Up";
			} else {
				data[idx][1] = "Down";
			}
			if (monitor.verifySiteAddress(curSite)) {
				data[idx][2] = "Y";
			} else {
				data[idx][2] = "N";
			}
			idx++;
		}
		data[idx][1] = "Last";
		Calendar cal = Calendar.getInstance();
		data[idx][0] = cal.getTime();
		data[idx][2] = "Check";
	}
	
	/**
	 * Performs all of the data and graphical object creation and initialization for the frame.
	 */
	public void createPanel() {
		updateData();

		sitesList = new JTable(data, columnNames);
		TableColumn column = null;
		for (int i = 0; i < 3; i++) {
		    column = sitesList.getColumnModel().getColumn(i);
		    if (i == 0) {
		        column.setPreferredWidth(200); //sport column is bigger
		    } else {
		        column.setPreferredWidth(20);
		    }
		}
		JScrollPane listView = new JScrollPane(sitesList);

		getContentPane().removeAll();
		getContentPane().add(listView, "Center");
	} /* End of createPanel() */

	/**
	 * Handles Frame events such as closing the Frame.
	 */
	class windowHandler extends WindowAdapter {

		public void windowClosing(WindowEvent event) {
			System.exit(0);
		}

		public void windowActivated(WindowEvent e) {
			/* Update look and feel */
			SwingUtilities.updateComponentTreeUI(thisFrame);
		} /* End of actionPerformed */
	} /* End of windowHandler class definition */

} /* End of DirectoryFrame class definition */
