package com.sns.Apps.Referee.ui;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import com.sns.Apps.Referee.db.*;
import com.sns.Apps.Referee.*;

/**
* The main program for the Referee application.  It uses
* Data and FieldInfo.
*
* @see com.sns.Apps.Referee.db.Data
* @version 1.0 30-Apr-2001
* @author Rob Broadhead
*/
public class RefList extends JFrame {
  /* Properties of the Frame */
  public static RefList thisFrame;
  /** contains the age and association fields
  * @serial northPane */
  private JPanel northPane;
  private JPanel southPane;
  /** Text field for interacting with the center
  * @serial sleCenter */
  private JTextField sleCenter;
  /** Text field for interacting with the line.
  * @serial sleLine */
  private JTextField sleLine;
  /** The table that is used to display game info.
  * @serial tblTable */
  private JTable tblTable;
  /** The database we'll connect to for this frame.
  * @serial theDB */
  private DataManager theDB;
  /** The model to connect the database to the table.
  * @serial dbTable */
  private DataTableModel dbTable;
  /** Keep up with the currently selected row instead of having to
  * constantly go back and retrieve it from the table.
  * @serial curRow */
  private int curRow;

  private JButton cbClose,cbAdd,cbEdit,cbDelete,cbSearch;
  private JCheckBox chkIndoor;
  private JToolBar toolbar;

  private Preference thePrefs;
  /* End properties definition */

  /**
  * A constructor for the class.  It takes parameters that determine whether
  * the database is on a network or local and an ip address for network files.
  *
  * @param boolean isNet If this is true we are looking for a network DB.
  * @param String parm This is the ip address for a net DB otherwise it is ignored.
  */
  public RefList () {
    /* Give the frame a title */
    super("Referee's Assistant Application");

    thisFrame = this;

    /* Set up default values */
    createPanel();
    curRow = -1; // A -1 value for curRow means no row is selected

    /* Add a listener in case user clicks the x to close the frame */
    this.addWindowListener(new windowHandler());
  } /* End of the constructor */

   /**
   * This takes a child frame and centers it within the main frame.
   * @param JFrame child The frame to be centered.
   */
   private void centerChild(JFrame child) {
      int newx,newy,x,y;

      x = getSize().width;
      y = getSize().height;
      newx = ((x - child.getSize().width)/2) + getLocation().x;
      newy = ((y - child.getSize().height)/2) + getLocation().y;
      child.setLocation(newx,newy);
   }  

  /** 
  * Update the given record with the data supplied.
  */
  public void updateData(String [] vals,DataInfo theData) { 

    if (vals == null) {
       DataManager.refDB.unlock(theData.getRecordNumber());
       return;
    }
    
    theData = new DataInfo(theData.getRecordNumber(),DataManager.refFields,vals);
    /* update the database */
    try {
      DataManager.refDB.unlock(theData.getRecordNumber());
      DataManager.refDB.modify(theData);
    } catch (DatabaseException exc) {
      JOptionPane.showMessageDialog(getContentPane(),exc.getMessage());
      return;
    }

    /* Rebuild the filter as a way to force the display to update since a repaint for
       some reason does not produce the desired effect */
    dbTable.setFilter(dbTable.getFilter());
  }

  /**
  * Add a referee record to the database.
  */
  public void addData(String [] vals) {
    /* update the database */
    try {
      vals[0] = "A" + DataManager.refDB.getRecordCount();
      DataManager.refDB.add(vals);
    } catch (DatabaseException exc) {
      JOptionPane.showMessageDialog(getContentPane(),exc.getMessage());
      return;
    }
    dbTable.setFilter(dbTable.getFilter());
  }

  /**
  * Performs all of the data and graphical object creation and initialization for
  * the frame.
  */
  public void createPanel() {
    /* Set up the table and database */
    dbTable = new DataTableModel(DataManager.refDB);

    /* Start the app with a filter that includes all records */
    dbTable.setFilter("Top Line='ANY'");
    tblTable = new JTable(dbTable);
    tblTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    ListSelectionModel rowSM = tblTable.getSelectionModel();
    rowSM.addListSelectionListener(new tableHandler());

    /* Set up and display the frames */
    northPane = new JPanel();
    southPane = new JPanel();

    /* Set up the button panel */
    northPane.setLayout(new GridLayout(1,5));
    southPane.setLayout(new GridLayout(1,5));
    southPane.setSize(600,40);

    /* Create and add the buttons */
    actionHandler ah = new actionHandler();

    /* The Add button */
    cbAdd = new JButton("Add");
    cbAdd.addActionListener(ah);
    cbAdd.setToolTipText("Add a referee");
    southPane.add(cbAdd);

    /* The Edit button */
    cbEdit = new JButton("Edit");
    cbEdit.addActionListener(ah);
    cbEdit.setToolTipText("Edit referee data");
    southPane.add(cbEdit);

    /* The Delete button */
    cbDelete = new JButton("Deactivate");
    cbDelete.addActionListener(ah);
    cbDelete.setToolTipText("Set Referee inactive");
    southPane.add(cbDelete);

    /* The Search button */
    cbSearch = new JButton("Search");
    cbSearch.addActionListener(ah);
    cbSearch.setToolTipText("Search/Filter data");
    southPane.add(cbSearch);

    /* The exit button */
    cbClose = new JButton("Close");
    cbClose.addActionListener(ah);
    cbClose.setToolTipText("Exit the application.");
    southPane.add(cbClose);

    /* Set up the origination/destination pane */
    northPane.add(new JLabel("Center"));
    sleCenter = new JTextField();
    sleCenter.setToolTipText("Center's name");
    northPane.add(sleCenter);

    northPane.add(new JLabel("Line"));
    sleLine = new JTextField();
    sleLine.setToolTipText("Line's name");
    northPane.add(sleLine);

    chkIndoor = new JCheckBox("Indoor");
    chkIndoor.setToolTipText("Indoor flag");
    northPane.add(chkIndoor);

    /* Set up and display the frame */
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(northPane,"North");
    getContentPane().add(new JScrollPane(tblTable),"Center");
    getContentPane().add(southPane,"South");
    setSize(600,400);
  } /* End of createPanel() */

/**
* Handles Frame events such as closing the Frame.
*/
class windowHandler extends WindowAdapter {
  public void windowClosing(WindowEvent event) {
    System.exit(0);
  }
} /* End of windowHandler class definition */

/**
* This class handles all menu click events in the application.  It could be
* broken down into a class for each menu family (admin,audit,file,data,etc)
* but this gives us one place to go for all menu events.
*/
class actionHandler implements ActionListener {
   public void actionPerformed (ActionEvent e) {
      Object src = e.getSource();
      DataInfo theData,actData;

      if (src == cbClose) {
         thisFrame.dispose();
      } else if (src == cbEdit) {
         RefDisplayFrame editFrame = null;

         /* Initialize values */
         theData = null;
         actData = null;

         /* Just return if no rows are selected */
         if (curRow == -1) {
            return;
         }

         try {
            /* Get the record from the table then get the information from the database.
               We need to retrieve the latest from the database just to make sure no one
               else has altered the data since our last update */
            theData = dbTable.getRecord(curRow);

            /* Lock the data here to make sure the transaction maintains its integrity */
            DataManager.refDB.unlock(theData.getRecordNumber());
            DataManager.refDB.lock(theData.getRecordNumber());

            actData = DataManager.refDB.getRecord(theData.getRecordNumber());
            editFrame = new RefDisplayFrame(actData);
            centerChild(editFrame);
            editFrame.setVisible(true);
         } catch (DatabaseException exc) {
            /* Clean up and bail out of this method*/
            DataManager.refDB.unlock(theData.getRecordNumber());
            JOptionPane.showMessageDialog(getContentPane(),"DB Err:" + exc.getMessage());
         } catch (IOException x) {
            /* Clean up and bail out of this method*/
            DataManager.refDB.unlock(theData.getRecordNumber());
            JOptionPane.showMessageDialog(getContentPane(),"IO Err:" + x.getMessage());
         }
      } else if (src == cbAdd) {
         NewRefFrame addFrame = new NewRefFrame();
         centerChild(addFrame);
         addFrame.setVisible(true);
      } else if (src == cbSearch) {
         String srcString,destString,critString,indString;

         /* Get the values to search for.  Assume that an empty field
            equates to ignoring that field. */
         srcString = sleCenter.getText();
         if (srcString.trim().length() == 0) {
            srcString = "ANY";
         }

         destString = sleLine.getText();
         if (destString.trim().length() == 0) {
            destString = "ANY";
         }

         /* Build the criteria string and set the filter */
         critString = "Top Line='" + destString + "',";
         critString += "Top Center='" + srcString + "',";
         if (chkIndoor.isSelected()) {
            critString += "Indoor='Y'";
         } else {
            critString += "Indoor='N'";
         }
         dbTable.setFilter(critString);

         /* Call repaint so the display is updated */
         tblTable.repaint();
      } else if (src == cbDelete) {
         /* Initialize values */

         theData = null;
         actData = null;

         /* Just return if no rows are selected */
         if (curRow == -1) {
            return;
         }

         try {
            /* Get the record from the table then get the information from the database.
               We need to retrieve the latest from the database just to make sure no one
               else has altered the data since our last update */
            theData = dbTable.getRecord(curRow);
            DataManager.refDB.lock(theData.getRecordNumber());
            String[] tempStore = theData.getValues();
            tempStore[4] = "01/01/1990";
            tempStore[11] = "";
            tempStore[12] = "";
            tempStore[13] = "";
            tempStore[9] = "N";
            theData = new DataInfo(theData.getRecordNumber(),DataManager.refFields,tempStore);

            /* update the database */
            DataManager.refDB.unlock(theData.getRecordNumber());
            DataManager.refDB.modify(theData);
         } catch (IOException x) {
            JOptionPane.showMessageDialog(getContentPane(),"DB Read Err:" + x.getMessage());
         } catch (DatabaseException exc) {
            /* Clean up and bail out of this method*/
            JOptionPane.showMessageDialog(getContentPane(),"DB Err:" + exc.getMessage());
         }
      }
   } /* End of actionPerformed */		
} /* End of actionHandler class definition*/


/**
* Handles clicks on the JTable control and other events tied to
item selection.
*/
class tableHandler implements ListSelectionListener{
  public void valueChanged(ListSelectionEvent e) {
    String[] vals = new String[9];

    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
    if (lsm.isSelectionEmpty()) {
      return;
    } else {
       /* We will keep track of the row we have currently selected to save
          processing throughout the application. */
       curRow = lsm.getMinSelectionIndex();
       sleCenter.setText(tblTable.getValueAt(curRow,4).toString());
       sleLine.setText(tblTable.getValueAt(curRow,5).toString());
       if (tblTable.getValueAt(curRow,7).toString().equals("Y")) {
          chkIndoor.setSelected(true);
       } else {
          chkIndoor.setSelected(false);
       }
    }
  } /* End of valueChanged method */
} /* End of tableHandler class definition */

} /* End of RefList class definition */

















