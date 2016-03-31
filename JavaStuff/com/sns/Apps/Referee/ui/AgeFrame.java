package com.sns.Apps.Referee.ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import com.sns.ui.SNSToolBar;
import com.sns.ui.SNSBaseFrame;
import java.util.*;
import com.sns.Apps.Referee.support.Age;

public class AgeFrame extends JFrame implements SNSBaseFrame {
	/** The controls and variables used by the frame */
	JTable tblAgeAdmin;

	SNSToolBar toolButtons;

	JPanel pnlDetails;

	JTabbedPane pnlTab;

	/* AgeAdmin Details */
	JLabel lblDetails[];

	JTextField txtDetails[];

	Properties theAge;

	int curRow = 0;

	public AgeFrame() {
		super("Age Maintenance");

		theAge = Age.getAll();
		loadControls();
	}

	public void loadControls() {
		int count = 0;

		lblDetails = new JLabel[2];
		txtDetails = new JTextField[2];

		getContentPane().setLayout(new BorderLayout());

		String[] colNames = new String[2];
		colNames[0] = "Age Code";
		colNames[1] = "Age";

		Enumeration items = theAge.elements();
		Enumeration keys = theAge.keys();
		String[][] dataVal = new String[theAge.size()][2];

		while (items.hasMoreElements()) {
			String curKey = (String) keys.nextElement();
			String curVal = (String) items.nextElement();
			dataVal[count][0] = curKey;
			dataVal[count][1] = curVal;
			count++;
		}

		tblAgeAdmin = new JTable(dataVal, colNames);
		tblAgeAdmin.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel rowSM = tblAgeAdmin.getSelectionModel();
		rowSM.addListSelectionListener(new tableHandler());

		/* Set up the button panel */
		toolButtons = new SNSToolBar();

		/* Set up the details panel */
		pnlDetails = new JPanel();
		pnlDetails.setLayout(new GridLayout(2, 2));
		lblDetails[0] = new JLabel("Age Name");
		lblDetails[1] = new JLabel("Age Code");

		for (count = 0; count < 2; count++) {
			txtDetails[count] = new JTextField();
			pnlDetails.add(lblDetails[count]);
			pnlDetails.add(txtDetails[count]);
		}

		JScrollPane pnlScroll = new JScrollPane(tblAgeAdmin);
		pnlTab = new JTabbedPane();
		pnlTab.addTab("Details", null, pnlDetails, "Details of the Age bracket");
		pnlScroll.setSize(200, 480);
		JPanel pnlBottom = new JPanel();
		pnlBottom.setLayout(new BorderLayout());
		pnlBottom.add(pnlScroll, "Center");
		pnlBottom.add(pnlTab, "South");
		getContentPane().add(toolButtons, "North");
		getContentPane().add(pnlBottom, "Center");
	}

	public void saveChanges() {
		String keyVal;

		keyVal = (String) tblAgeAdmin.getValueAt(curRow, 0);
		theAge.remove(keyVal);
		theAge.put(txtDetails[1].getText(), txtDetails[0].getText());
		clear();
		Age.setAll(theAge);
		Age.save();
	}

	public void close() {
		Age.setAll(theAge);
		Age.save();
		this.dispose();
	}

	public void deleteItem() {
		theAge.remove(tblAgeAdmin.getValueAt(curRow, 0));
		loadControls();
	}

	public void clear() {
		txtDetails[0].setText("");
		txtDetails[1].setText("");
		tblAgeAdmin.clearSelection();
	}

	public void addItem() {
		if ((txtDetails[1].getText() != null) && (txtDetails[0].getText() != null)) {
			String key, value;
			key = txtDetails[1].getText();
			value = txtDetails[0].getText();
			theAge.put(key, value);
			loadControls();
		}
	}

	class tableHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			if (lsm.isSelectionEmpty()) {
				return;
			} else {
				/*
				 * We will keep track of the row we have currently selected to save
				 * processing throughout the application.
				 */
				curRow = lsm.getMinSelectionIndex();
				txtDetails[1].setText(tblAgeAdmin.getValueAt(curRow, 0).toString());
				txtDetails[0].setText(tblAgeAdmin.getValueAt(curRow, 1).toString());
			}
		} /* End of valueChanged method */
	} /* End of tableHandler class definition */

}