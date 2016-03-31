package com.sns.Apps.Referee.ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import com.sns.ui.SNSToolBar;
import com.sns.ui.SNSBaseFrame;
import java.util.*;
import com.sns.Apps.Referee.support.Associations;

public class AssociationsFrame extends JFrame implements SNSBaseFrame {
	/** The controls and variables used by the frame */
	JTable tblAssocAdmin;
  SNSToolBar toolButtons;
	JPanel pnlDetails;
	JTabbedPane pnlTab;

	/* AssocAdmin Details */
	JLabel lblDetails[];
	JTextField txtDetails[];
  Properties theAssoc;
  int curRow = 0;

	public AssociationsFrame() {
		super("Associations Maintenance");

     theAssoc = Associations.getAll();
		loadControls();
	}

	public void loadControls() {
		int count = 0;

		lblDetails = new JLabel[2];
		txtDetails = new JTextField[2];

		getContentPane().setLayout(new BorderLayout());

		String[] colNames = new String[2];
		colNames[0] = "Association Name";
		colNames[1] = "Association ";

     Enumeration items = theAssoc.elements();
     Enumeration keys = theAssoc.keys();
		String[][] dataVal = new String[theAssoc.size()][2];

     while (items.hasMoreElements()) {
        String curKey = (String) keys.nextElement();
        String curVal = (String) items.nextElement();
        dataVal[count][0] = curKey;
        dataVal[count][1] = curVal;
        count++;
     }

		tblAssocAdmin = new JTable(dataVal,colNames);
		tblAssocAdmin.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel rowSM = tblAssocAdmin.getSelectionModel();
		rowSM.addListSelectionListener(new tableHandler());

		/* Set up the button panel */
		toolButtons = new SNSToolBar();

		/* Set up the details panel */
		pnlDetails = new JPanel();
		pnlDetails.setLayout(new GridLayout(2,2));
		lblDetails[0] = new JLabel("Association Name");
		lblDetails[1] = new JLabel("Association Code");

		for (count=0;count < 2;count++) {
			txtDetails[count] = new JTextField();
			pnlDetails.add(lblDetails[count]);
			pnlDetails.add(txtDetails[count]);
		}

		JScrollPane pnlScroll = new JScrollPane(tblAssocAdmin);
		pnlTab = new JTabbedPane();
		pnlTab.addTab("Details",null,pnlDetails,"Details of the association");
		pnlScroll.setSize(200,480);
		JPanel pnlBottom = new JPanel();
     pnlBottom.setLayout(new BorderLayout());
		pnlBottom.add(pnlScroll,"Center");
		pnlBottom.add(pnlTab,"South");
		getContentPane().add(toolButtons,"North");
		getContentPane().add(pnlBottom,"Center");
	}

  public void saveChanges() {
     String keyVal;

     keyVal = (String) tblAssocAdmin.getValueAt(curRow,0);
     theAssoc.remove(keyVal);
     theAssoc.put(txtDetails[1].getText(),txtDetails[0].getText());
     clear();
     Associations.setAll(theAssoc);
     Associations.save();
  }

  public void close() {
     Associations.setAll(theAssoc);
     Associations.save();
     this.dispose();
  }

  public void deleteItem() {
     theAssoc.remove(tblAssocAdmin.getValueAt(curRow,0));
     loadControls();
  }

  public void clear() {
     txtDetails[0].setText("");
     txtDetails[1].setText("");
     tblAssocAdmin.clearSelection();
  }

  public void addItem() {
     if ((txtDetails[1].getText()!=null) && (txtDetails[0].getText()!=null)) {
        String key,value;
        key = txtDetails[1].getText();
        value = txtDetails[0].getText();
        theAssoc.put(key,value);
        loadControls();
     }
  }

class tableHandler implements ListSelectionListener{
  public void valueChanged(ListSelectionEvent e) {
    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
    if (lsm.isSelectionEmpty()) {
      return;
    } else {
      /* We will keep track of the row we have currently selected to save
         processing throughout the application. */
      curRow = lsm.getMinSelectionIndex();
      txtDetails[1].setText(tblAssocAdmin.getValueAt(curRow,0).toString());
      txtDetails[0].setText(tblAssocAdmin.getValueAt(curRow,1).toString());
    }
  } /* End of valueChanged method */
} /* End of tableHandler class definition */

}
