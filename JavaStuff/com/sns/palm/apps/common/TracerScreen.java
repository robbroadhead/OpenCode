package com.sns.palm.apps.common;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import com.sns.palm.util.Tracer;

/**
 * <p>Title: MIDP Defect Log</p>
 * <p>Description: </p>
 * This displays a control that allows the user to select a status and then a
 * list is displayed of all defects that match the selected status.
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sleepless Nights Software</p>
 * @author Rob Broadhead
 * @version 1.0
 */
public class TracerScreen extends MIDlet implements CommandListener {
  private Command cbReport;
  private Command cbBack;
  private Command cbClear;
  private ChoiceGroup fileList;
  private Form f;

  private ChoiceGroup theStatus;
  private List defectList;

  private Display theDisplay;

  public TracerScreen() {
    /* Basic setup stuff */
    theDisplay = Display.getDisplay(this);

    /* Set up the buttons */
    cbReport = new Command("View",Command.OK,1);
    cbBack = new Command("Back",Command.SCREEN,2);
    cbClear = new Command("Clear Log",Command.SCREEN,3);

    /* Create the data entry fields */
    fileList = new ChoiceGroup("Store:",Choice.EXCLUSIVE,RecordStore.listRecordStores(),null);
    theStatus = new ChoiceGroup("Code",Choice.EXCLUSIVE,Tracer.loadCodes(),null);

    f = new Form("Log Report");
    f.append(fileList);
    f.append(theStatus);
    f.addCommand(cbBack);
    f.addCommand(cbReport);
    f.addCommand(cbClear);
    f.setCommandListener(this);
  }

  public void startApp() {
    theDisplay.setCurrent(f);
  }

  protected void pauseApp() {
  }

  protected void destroyApp(boolean u) {}

  public void commandAction(Command c, Displayable d) {
    if (c == cbBack) {
      destroyApp(false);
      notifyDestroyed();
    } else if (c==cbReport) {
      /* Build the screen for age select */
      defectList = null;
      defectList = new List("Application Log:",List.EXCLUSIVE,Tracer.listItems(fileList.getString(fileList.getSelectedIndex())),null);
      defectList.addCommand(cbBack);
      defectList.setCommandListener(this);
      theDisplay.setCurrent(defectList);
    } else if (c==cbClear) {
      String theFile = fileList.getString(fileList.getSelectedIndex());
      if ((theFile.indexOf("log") > 0) || (theFile.indexOf("Log") > 0)) {
        Tracer.setFile(theFile);
        Tracer.resetLog();
      }
    }
  }
}
