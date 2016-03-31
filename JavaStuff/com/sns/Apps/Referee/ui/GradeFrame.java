package com.sns.Apps.Referee.ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import com.sns.ui.SNSToolBar;
import com.sns.ui.SNSBaseFrame;
import java.util.*;
import com.sns.Apps.Referee.support.RefGrade;

public class GradeFrame extends JFrame implements SNSBaseFrame {
	/** The controls and variables used by the frame */
	JTable tblGradeAdmin;

	SNSToolBar toolButtons;

	JPanel pnlDetails;

	JTabbedPane pnlTab;

	/* GradeAdmin Details */
	JLabel lblDetails[];

	JTextField txtDetails[];

	Properties theGrade;

	int curRow = 0;

	public GradeFrame() {
		super("Grade Maintenance");

		theGrade = RefGrade.getAll();
		loadControls();
	}

	public void loadControls() {
		int count = 0;

		lblDetails = new JLabel[2];
		txtDetails = new JTextField[2];

		getContentPane().setLayout(new BorderLayout());

		String[] colNames = new String[2];
		colNames[0] = "Grade Name";
		colNames[1] = "Grade Level";

		Enumeration items = theGrade.elements();
		Enumeration keys = theGrade.keys();
		String[][] dataVal = new String[theGrade.size()][2];

		while (items.hasMoreElements()) {
			String curKey = (String) keys.nextElement();
			String curVal = (String) items.nextElement();
			dataVal[count][0] = curKey;
			dataVal[count][1] = curVal;
			count++;
		}

		tblGradeAdmin = new JTable(dataVal, colNames);
		tblGradeAdmin.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel rowSM = tblGradeAdmin.getSelectionModel();
		rowSM.addListSelectionListener(new tableHandler());

		/* Set up the button panel */
		toolButtons = new SNSToolBar();

		/* Set up the details panel */
		pnlDetails = new JPanel();
		pnlDetails.setLayout(new GridLayout(2, 2));
		lblDetails[0] = new JLabel("Grade Name");
		lblDetails[1] = new JLabel("Grade Level");

		for (count = 0; count < 2; count++) {
			txtDetails[count] = new JTextField();
			pnlDetails.add(lblDetails[count]);
			pnlDetails.add(txtDetails[count]);
		}

		JScrollPane pnlScroll = new JScrollPane(tblGradeAdmin);
		pnlTab = new JTabbedPane();
		pnlTab.addTab("Details", null, pnlDetails, "Values for the grade");
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

		keyVal = (String) tblGradeAdmin.getValueAt(curRow, 0);
		theGrade.remove(keyVal);
		theGrade.put(txtDetails[1].getText(), txtDetails[0].getText());
		clear();
		RefGrade.setAll(theGrade);
		RefGrade.save();
	}

	public void close() {
		RefGrade.setAll(theGrade);
		RefGrade.save();
		this.dispose();
	}

	public void deleteItem() {
		theGrade.remove(tblGradeAdmin.getValueAt(curRow, 0));
		loadControls();
	}

	public void clear() {
		txtDetails[0].setText("");
		txtDetails[1].setText("");
		tblGradeAdmin.clearSelection();
	}

	public void addItem() {
		if ((txtDetails[1].getText() != null) && (txtDetails[0].getText() != null)) {
			String key, value;
			key = txtDetails[1].getText();
			value = txtDetails[0].getText();
			theGrade.put(key, value);
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
				txtDetails[1].setText(tblGradeAdmin.getValueAt(curRow, 0).toString());
				txtDetails[0].setText(tblGradeAdmin.getValueAt(curRow, 1).toString());
			}
		} /* End of valueChanged method */
	} /* End of tableHandler class definition */

}