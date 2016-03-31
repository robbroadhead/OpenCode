package com.sns.palm.apps.defect;

/**
 * <p>Title: MIDP Defect Log</p>
 * <p>Description: </p>
 * This class does the display and data management tasks for the Defect screen.
 * Storage is done through the Defect class, but this class acts as the controller
 * by keeping the data on the screen and in the class synchronized.
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sleepless Nights Software</p>
 * @author Rob Broadhead
 * @version 1.0
 */
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import com.sns.palm.apps.common.BaseForm;
import com.sns.palm.base.Reportable;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
import com.sns.palm.util.*;

public class DefectForm extends BaseForm implements CommandListener {
  private static final byte LogType = 1;
  private Command cbHistory;

  private TextField sleTitle;
  private ChoiceGroup lsFile;
  private TextField sleDesc;
  private TextField sleNotes;
  private TextField gVersion;
  private TextField gVerMin;
  private ChoiceGroup lsStatus;
  private ChoiceGroup lsPriority;
  private ChoiceGroup lsCategory;
  private ChoiceGroup lsAssign;
  private ChoiceGroup lsApp;
  private StringItem enterDt;
  private StringItem statusDt;

  private Display theDisplay;
  private MIDlet parent;
  private List histForm;

  public DefectForm(Defect g,MIDlet p) {
    super(g,p,"Defect Admin");
  }

  protected Reportable createItem() {
    return new Defect();
  }

  /**
  *
  * Create the controls for the form and load any default values that are used.
  * This also creates the event "listeners" and ties them into the code that will
  * be fired upon triggering the event.
  *
  * @param d The defect to set as the current defect.
  * @param p The parent screen for this form.
  */
  public void InitForm() {
    /* Set up the buttons */
    cbHistory = new Command("History",Command.ITEM,6);

    /* Create the data entry fields */
    sleTitle = new TextField("Title:","",60,TextField.ANY);
    sleDesc = new TextField("Detail:","",400,TextField.ANY);
    sleNotes = new TextField("Notes:","",400,TextField.ANY);
    gVersion = new TextField("Version:","",3,TextField.NUMERIC);
    gVerMin = new TextField("Minor Ver.:","",3,TextField.NUMERIC);
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    enterDt = new StringItem("Created:",cal.toString());
    statusDt = new StringItem("Status Set:",cal.toString());
    /* Build the list for Status,Category and Priority select */
    lsStatus = new ChoiceGroup("Status:",Choice.EXCLUSIVE,DefectUtils.LoadStatus(),null);
    lsPriority = new ChoiceGroup("Priority:",Choice.EXCLUSIVE,DefectUtils.LoadPriority(),null);
    lsCategory = new ChoiceGroup("Category:",Choice.EXCLUSIVE,DefectUtils.LoadCategory(),null);

	User temp = new User();
    String[] result = Utility.sortStringList((((Defect) theField).listItems("RBUsers",temp)));

    /* Build the list for Assignment select */
    lsAssign = new ChoiceGroup("Assign:",Choice.EXCLUSIVE,result,null);

    /* Build the list for Application select */
    Application appTemp = new Application();
    result = Utility.sortStringList((((Defect) theField).listItems("AppNames",appTemp)));
    lsApp = new ChoiceGroup("Application:",Choice.EXCLUSIVE,result,null);

    /* Build the list for File select */
    Files fileTemp = new Files();
    result = Utility.sortStringList((((Defect) theField).listItems("RBFiles",fileTemp)));
    lsFile = new ChoiceGroup("File:",Choice.EXCLUSIVE,result,null);

    append(sleTitle);
    append(lsFile);
    append(gVersion);
    append(gVerMin);
    append(sleDesc);
    append(lsStatus);
    append(lsCategory);
    append(lsPriority);
    append(lsApp);
    append(lsAssign);
    append(statusDt);
    append(enterDt);
    append(sleNotes);
    addCommand(cbHistory);
    setCommandListener(this);
  }

  /**
  * Updates the screen with information from the current Defect Record.
  */
  public void updateScreen() {
  	Defect current = (Defect) theField;
    sleTitle.setString(current.title);
    sleDesc.setString(current.desc);
    sleNotes.setString(current.notes);
    gVersion.setString("" + current.ver);
    gVerMin.setString("" + current.ver_min);

    Calendar cal = Calendar.getInstance();
    cal.setTimeZone(TimeZone.getTimeZone("CST"));
    cal.setTime(new Date(current.statusDate));
    statusDt.setText(cal.toString());
    cal.setTime(new Date(current.enterDate));
    enterDt.setText(cal.toString());

    if (current.status > 0) {
      lsStatus.setSelectedIndex(current.status,true);
    }
    if (current.category > 0) {
      lsCategory.setSelectedIndex(current.category,true);
    }
    if (current.priority > 0) {
      lsPriority.setSelectedIndex(current.priority,true);
    }
//    lsFile.setSelectedIndex(current.file);


    /* Search for assigned text */
    int cidx,lidx;
    cidx = 0;
    lidx = 0;
    User tempUser = new User();
    tempUser.ID = current.assigned;
    tempUser.load();
    String curName = " " + tempUser.lname.trim() + ", " + tempUser.fname.trim();
    while ((!lsAssign.getString(cidx).equals(curName)) && (cidx < lsAssign.size())) {
      cidx++;
    }
    lsAssign.setSelectedIndex(cidx,true);

    if (current.application > 1) {
      lsApp.setSelectedIndex(current.application - 1,true);
    }
  }

  /**
   * This utility method is used to parse through a display name and build a
   * string that can easily be converted to a byte array and then used with
   * the searching methods.
   *
   * @param val The name as a display string.
   * @return A padded string to use for searching for a name in the RecordStore.
   */
  private String fixUserName(String val) {
    int idx = val.indexOf(", ");
    String fName = val.substring(idx + 2,val.length());
    String lName = val.substring(0,idx);

    return Utility.padString(fName,20) + Utility.padString(lName,20);
  }

  /**
  * Updates the Defect storage class with information from the screen.
  */
  public void updateStore() {
  	Defect current = (Defect) theField;
	Tracer.setActive(true);
	Tracer.log("Entering Update",Tracer.LOG);
    current.title = sleTitle.getString();
    current.desc = sleDesc.getString();
    current.notes = sleNotes.getString();
    current.ver = Integer.parseInt(gVersion.getString().trim());
    current.ver_min = Integer.parseInt(gVerMin.getString().trim());

	// Store the selected user
    String assignName = lsAssign.getString(lsAssign.getSelectedIndex());
    assignName = fixUserName(assignName);
    byte[] tempByte = Utility.StrToByte(assignName);
    current.assigned = Utility.searchStore(tempByte,2,40,"RBUsers");

	// Store the selected file
	String fileName = lsAssign.getString(lsFile.getSelectedIndex());
	assignName = fixUserName(fileName);
	tempByte = Utility.StrToByte(fileName);
	current.file = Utility.searchStore(tempByte,2,40,"RBFiles");

	// Store the Application
	String appName = lsAssign.getString(lsApp.getSelectedIndex());
	assignName = fixUserName(assignName);
	tempByte = Utility.StrToByte(assignName);
	current.application = Utility.searchStore(tempByte,2,40,"AppNames");
	
	
    if (current.statusDate==0) {
      long today = new Date().getTime();
      current.statusDate = today;
      current.enterDate = today;
    }

    if (current.status!=lsStatus.getSelectedIndex()) {
      current.statusDate = new Date().getTime();
      LogEntry le = new LogEntry();
      le.defectID = current.ID;
      le.status = (byte) current.status;
      le.setDate = new Date().getTime();
      le.save();
      current.status = (byte) lsStatus.getSelectedIndex();
    }

	// Store the remaining list items    
    current.category = (byte) lsCategory.getSelectedIndex();
    current.status = (byte) lsStatus.getSelectedIndex();
	current.priority = (byte) lsPriority.getSelectedIndex();

	Tracer.log("Update Complete",Tracer.LOG);
  }

  /**
  * This is where all event processing is handled. New record ids are also created
  * here.
  *
  * @param c command that called this
  * @param d form that called this
  */
/*  public void commandAction(Command c, Displayable d) {
    } else if (c==cbHistory) {
      byte[] theVal = new byte[1];
      theVal[0] = (byte) current.ID;
      Vector histList = Utility.multiSearchStore(theVal,2,1,"StatusHist");
      Enumeration e = histList.elements();
      String[] result = new String[histList.size()];
      int idx = 0;
      LogEntry curLog = new LogEntry();
      while (e.hasMoreElements()) {
        curLog.ID = ((Integer) e.nextElement()).intValue();
        curLog.load();
        result[idx++] = curLog.Display();
      }
      histForm = new List("History:",List.EXCLUSIVE,result,null);
      histForm.addCommand(cbBack);
      histForm.setCommandListener(this);
      theDisplay.setCurrent(histForm);
    }
  }
  */
}
