package com.sns.Apps.Referee.ui;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import com.sns.Apps.Referee.db.*;
import com.sns.Apps.Referee.*;
import javax.swing.table.TableColumn;
import com.sns.Util.Tracer;

/**
* The main program for the Referee application.  It uses
* Data and FieldInfo.
*
* @see com.sns.Apps.Referee.db.Data
* @version 1.0 30-Apr-2001
* @author Rob Broadhead
*/
public class RefFrame extends JFrame {
  /* Properties of the Frame */
  public static RefFrame thisFrame;
  /** contains the age and association fields
  * @serial northPane */
  private JPanel northPane;
  /** Text field for interacting with the association.
  * @serial sleAssoc */
  private JTextField sleAssoc;
  /** Text field for interacting with the age.
  * @serial sleAge */
  private JTextField sleAge;
  private JTextField sleDateBeg;
  private JTextField sleDateEnd;
  private JLabel labRows;
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

  private Preference thePrefs;
  private JMenuBar theMenu;
  private JMenu refMenu,fileMenu,editMenu,adminMenu;
  private JMenuItem exitItem,prefItem,refAdmin,gameAdmin,gameAdd,searchItem,delItem,assocAdmin,ageAdmin,gradeAdmin;
   private JCheckBox chkPaid;
  /* End properties definition */

  static {
      try {
          // Start the GUI with a Windows look and feel
          UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
      }catch (Exception e){}

      Tracer.setFile("RefTrace.log");
      Tracer.setActive(true);
  }

  /**
  * A constructor for the class.  This takes no parameters and uses the data
  * directory in the ini file to locate the database files.
  *
  */
  public RefFrame () {
    /* Give the frame a title */
    super("Referee's Assistant Application");

    String dbFile;
    thisFrame = this;

    /* Set up default values */
    thePrefs = new Preference();
    try {
       thePrefs.loadPrefs("c:/utility/prefs.ini");
    } catch (Exception e) {
       System.out.println("Unable to locate preferences file");
       System.exit(0);
    }

    /* First open the database */
    theDB = new DataManager();
    dbFile = Preference.getPreference("Data Directory");

    /* Get the file name */
    try {
      /* Database will be set by parameters at some point */
      Tracer.setActive(true);
      Tracer.log("Files in: " + dbFile + "/GameDB");
      DataManager.gameDB = new Data(dbFile + "/GameDB");
      DataManager.refDB = new Data(dbFile + "/refDB");
      theDB.Initialize();
    } catch (IOException e) {
      /* If we can't open the database then close the app. */
      System.out.println("Unable to locate database " + dbFile + " in the current directory.");
      System.exit(0);
    }

    createPanel();
    curRow = -1; // A -1 value for curRow means no row is selected

    /* Add a listener in case user clicks the x to close the frame */
    this.addWindowListener(new windowHandler());
  } /* End of the constructor */

  /**
  * A constructor for the class.  It takes parameters that determine whether
  * the database is on a network or local and an ip address for network files.
  *
  * @param boolean isNet If this is true we are looking for a network DB.
  * @param String parm This is the ip address for a net DB otherwise it is ignored.
  */
  public RefFrame (boolean isNet,String dbFile,String parm) {
    /* Give the frame a title */
    super("Referee's Assistant Application");

    thisFrame = this;
    /* First open the database */
    theDB = new DataManager();
    /* First open the database */
    try {
      /* Database will be set by parameters at some point */
      if (isNet == true) {
        DataManager.gameDB = new DataNet(dbFile,parm);
		DataManager.refDB = new DataNet("refDB",parm);
      } else {
		DataManager.gameDB = new Data(dbFile);
		DataManager.refDB = new Data("refDB");
      }
      theDB.Initialize();
    } catch (IOException e) {
      /* If we can't open the database then close the app. */
      System.out.println("Unable to locate database " + dbFile + " in the current directory.");
      System.exit(0);
    }

    /* Set up default values */
    thePrefs = new Preference();
    try {
       thePrefs.loadPrefs("prefs.ini");
    } catch (Exception e) {}
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
   * This creates all menus and menu items for the frame.  This includes short
   * cut keys and tool tips where needed.  All listeners are also assigned in
   * this method, so any menu changes will be done in this method.
   */
   private void createMenu() {
      actionHandler ah = new actionHandler();
      theMenu = new JMenuBar();

      /* Create the menu items */
      fileMenu = new JMenu("File");
      fileMenu.setMnemonic((int) 'F');
      exitItem = new JMenuItem("Exit");
      exitItem.addActionListener(ah);
      exitItem.setMnemonic((int) 'x');
      fileMenu.add(exitItem);

      prefItem = new JMenuItem("Preferences");
      prefItem.addActionListener(ah);
      prefItem.setMnemonic((int) 'P');
      fileMenu.add(prefItem);

      editMenu = new JMenu("Edit");
      editMenu.setMnemonic((int) 'E');
      searchItem = new JMenuItem("Search");
      searchItem.addActionListener(ah);
      searchItem.setMnemonic((int) 'S');
      editMenu.add(searchItem);
      delItem = new JMenuItem("Delete");
      delItem.addActionListener(ah);
      delItem.setMnemonic((int) 'D');
      editMenu.add(delItem);

      refMenu = new JMenu("Game");
      refMenu.setMnemonic((int) 'R');
      gameAdmin = new JMenuItem("Edit Game");
      gameAdmin.addActionListener(ah);
      gameAdmin.setMnemonic((int) 'E');
      refMenu.add(gameAdmin);
      gameAdd = new JMenuItem("New Game");
      gameAdd.addActionListener(ah);
      gameAdd.setMnemonic((int) 'N');
      refMenu.add(gameAdd);

      adminMenu = new JMenu("Admin");
      adminMenu.setMnemonic((int) 'A');
      assocAdmin = new JMenuItem("Associations");
      assocAdmin.addActionListener(ah);
      assocAdmin.setMnemonic((int) 's');
      adminMenu.add(assocAdmin);

      refAdmin = new JMenuItem("Referee List");
      refAdmin.addActionListener(ah);
      refAdmin.setMnemonic((int) 'R');
      adminMenu.add(refAdmin);

      gradeAdmin = new JMenuItem("Referee Grades");
      gradeAdmin.addActionListener(ah);
      gradeAdmin.setMnemonic((int) 'G');
      adminMenu.add(gradeAdmin);

      ageAdmin = new JMenuItem("Age Brackets");
      ageAdmin.addActionListener(ah);
      ageAdmin.setMnemonic((int) 'B');
      adminMenu.add(ageAdmin);

      /* Add the main items to the menu */
      theMenu.add(fileMenu);
      theMenu.add(editMenu);
      theMenu.add(refMenu);
      theMenu.add(adminMenu);
   }

  /**
  * Update the given record with the data supplied.
  */
  public void updateData(String [] vals,DataInfo theData) {

    if (vals == null) {
       DataManager.gameDB.unlock(theData.getRecordNumber());
       return;
    }

    theData = new DataInfo(theData.getRecordNumber(),DataManager.gameFields,vals);
    /* update the database */
    try {
      DataManager.gameDB.unlock(theData.getRecordNumber());
      DataManager.gameDB.modify(theData);
    } catch (DatabaseException exc) {
      JOptionPane.showMessageDialog(getContentPane(),exc.getMessage());
      return;
    }

    /* Rebuild the filter as a way to force the display to update since a repaint for
       some reason does not produce the desired effect */
    dbTable.setFilter(dbTable.getFilter());
  }

  /**
  * Add a game record to the database.
  */
  public void addData(String [] vals) {
    /* update the database */
    try {
      vals[0] = "A" + DataManager.gameDB.getRecordCount();
      DataManager.gameDB.add(vals);
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
    dbTable = new DataTableModel(DataManager.gameDB);

    /* Start the app with a filter that includes all records */
    dbTable.setFilter("Association='ANY'");
    tblTable = new JTable(dbTable);
    TableColumn paidColumn = tblTable.getColumnModel().getColumn(10);

    chkPaid = new JCheckBox();
    paidColumn.setCellEditor(new DefaultCellEditor(chkPaid));
    tblTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    ListSelectionModel rowSM = tblTable.getSelectionModel();
    rowSM.addListSelectionListener(new tableHandler());

    /* Set up and display the frames */
    northPane = new JPanel();

    /* Set up the button panel */
    northPane.setLayout(new GridLayout(1,10));

    /* Set up the origination/destination pane */
    northPane.add(new JLabel("Association"));
    sleAssoc = new JTextField();
    sleAssoc.setToolTipText("Game association");
    northPane.add(sleAssoc);

    northPane.add(new JLabel("Age"));
    sleAge = new JTextField();
    sleAge.setToolTipText("The age bracket");
    northPane.add(sleAge);

    northPane.add(new JLabel("Start Date"));
    sleDateBeg = new JTextField();
    sleDateBeg.setToolTipText("Start date range when game was played");
    northPane.add(sleDateBeg);

    northPane.add(new JLabel("End Date"));
    sleDateEnd = new JTextField();
    sleDateEnd.setToolTipText("End date range when game was played");
    northPane.add(sleDateEnd);

    northPane.add(new JLabel("Rows:"));
    labRows = new JLabel("" + tblTable.getRowCount());
    northPane.add(labRows);

    /* Set up and display the frame */
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(northPane,"South");
    getContentPane().add(new JScrollPane(tblTable),"Center");
    createMenu();
    getContentPane().add(theMenu,"North");
    setSize(800,600);
  } /* End of createPanel() */

/**
* Handles Frame events such as closing the Frame.
*/
class windowHandler extends WindowAdapter {
  public void windowClosing(WindowEvent event) {
    System.exit(0);
  }

  public void windowActivated (WindowEvent e) {
     /* Update look and feel */
     SwingUtilities.updateComponentTreeUI(thisFrame);
  } /* End of actionPerformed */
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

      if (src == exitItem) {
         System.exit(0);
      } else if (src == prefItem) {
         PreferenceFrame theFrame;

         theFrame = new PreferenceFrame(thePrefs);
         theFrame.setSize(640,360);
         centerChild(theFrame);
         theFrame.setVisible(true);
      } else if (src == refAdmin) {
         RefList refFrame = new RefList();
         centerChild(refFrame);
         refFrame.setVisible(true);
      } else if (src == assocAdmin) {
         AssociationsFrame assocFrame = new AssociationsFrame();
         assocFrame.setSize(300,250);
         centerChild(assocFrame);
         assocFrame.setVisible(true);
      } else if (src == ageAdmin) {
         AgeFrame ageFrame = new AgeFrame();
         ageFrame.setSize(300,250);
         centerChild(ageFrame);
         ageFrame.setVisible(true);
      } else if (src == gradeAdmin) {
         GradeFrame gradeFrame = new GradeFrame();
         gradeFrame.setSize(300,250);
         centerChild(gradeFrame);
         gradeFrame.setVisible(true);
     } else if (src == gameAdmin) {
         GameDisplayFrame editFrame = null;

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
            DataManager.gameDB.unlock(theData.getRecordNumber());
            DataManager.gameDB.lock(theData.getRecordNumber());

            actData = DataManager.gameDB.getRecord(theData.getRecordNumber());
            editFrame = new GameDisplayFrame(actData);
            centerChild(editFrame);
            editFrame.setVisible(true);
         } catch (DatabaseException exc) {
            /* Clean up and bail out of this method*/
            DataManager.gameDB.unlock(theData.getRecordNumber());
            JOptionPane.showMessageDialog(getContentPane(),"DB Err:" + exc.getMessage());
         } catch (IOException x) {
            /* Clean up and bail out of this method*/
            DataManager.gameDB.unlock(theData.getRecordNumber());
            JOptionPane.showMessageDialog(getContentPane(),"IO Err:" + x.getMessage());
         }
      } else if (src == gameAdd) {
         NewGameFrame addFrame = new NewGameFrame();
         centerChild(addFrame);
         addFrame.setVisible(true);
         labRows.setText("" + tblTable.getRowCount());
      } else if (src == searchItem) {
         String srcString,destString,critString,dateStringBegin,dateStringEnd;

         /* Get the values to search for.  Assume that an empty field
            equates to ignoring that field. */
         srcString = sleAssoc.getText();
         if (srcString.trim().length() == 0) {
            srcString = "ANY";
         }

         destString = sleAge.getText();
         if (destString.trim().length() == 0) {
            destString = "ANY";
         }

         dateStringBegin = sleDateBeg.getText();
         if (dateStringBegin.trim().length() == 0) {
            dateStringBegin = "ANY";
         }

         dateStringEnd = sleDateEnd.getText();
         if (dateStringEnd.trim().length() == 0) {
            dateStringEnd = "ANY";
         }

         /* Build the criteria string and set the filter */
         critString = "Age='" + destString + "',";
         critString += "Association='" + srcString + "',";
         critString += "Date>'" + dateStringBegin + "',";
         critString += "Date<'" + dateStringEnd + "'";
         Tracer.log("Filter String is:" + critString);
         dbTable.setFilter(critString);

         /* Call repaint so the display is updated */
         labRows.setText("" + tblTable.getRowCount());
         tblTable.repaint();
      } else if (src == delItem) {
         GameDisplayFrame deleteFrame = null;

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
            DataManager.gameDB.delete(theData);
         } catch (DatabaseException exc) {
            /* Clean up and bail out of this method*/
            JOptionPane.showMessageDialog(getContentPane(),"DB Err:" + exc.getMessage());
         }
         labRows.setText("" + tblTable.getRowCount());
      }
   } /* End of actionPerformed */
} /* End of actionHandler class definition*/


/**
* Handles clicks on the JTable control and other events tied to
item selection.
*/
class tableHandler implements ListSelectionListener{
  public void valueChanged(ListSelectionEvent e) {
    String[] vals = new String[14];

    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
    if (lsm.isSelectionEmpty()) {
      return;
    } else {
       /* We will keep track of the row we have currently selected to save
          processing throughout the application. */
       curRow = lsm.getMinSelectionIndex();
       sleAssoc.setText(tblTable.getValueAt(curRow,8).toString());
       sleAge.setText(tblTable.getValueAt(curRow,11).toString());
       sleDateBeg.setText(tblTable.getValueAt(curRow,6).toString());
       sleDateEnd.setText(tblTable.getValueAt(curRow,6).toString());
    }
  } /* End of valueChanged method */
} /* End of tableHandler class definition */

} /* End of RefFRame class definition */

