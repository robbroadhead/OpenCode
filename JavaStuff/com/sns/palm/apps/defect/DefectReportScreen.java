package com.sns.palm.apps.defect;

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
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import com.sns.palm.util.*;


public class DefectReportScreen extends MIDlet implements CommandListener {
  private Command cbReport;
  private Command cbBack;
  private Command cbSelect;
  private Form f;
  private DefectForm df;

  private ChoiceGroup theStatus;
  private List defectList;

  private Display theDisplay = Display.getDisplay(this);

  /**
   * The screen is just a status list to select from with a set of buttons to
   * view the report and the list of defects selected can be selected from to
   * edit a specific defect.
   */
  public DefectReportScreen() {
    /* Set up the buttons */
    cbReport = new Command("View",Command.OK,1);
    cbBack = new Command("Back",Command.SCREEN,2);
    cbSelect = new Command("Select",Command.SCREEN,1);

    /* Create the data entry fields */
    theStatus = new ChoiceGroup("Status:",Choice.EXCLUSIVE,DefectUtils.LoadStatus(),null);

    f = new Form("Defects Report");
    f.append(theStatus);
    f.addCommand(cbBack);
    f.addCommand(cbReport);
    f.setCommandListener(this);
  }

  public void startApp() {
    theDisplay.setCurrent(f);
  }

  protected void pauseApp() {
    defectList = null;
  }

  protected void destroyApp(boolean u) {}

  public void commandAction(Command c, Displayable d) {
    if (c == cbBack) {
      destroyApp(false);
      notifyDestroyed();
    } else if (c==cbSelect) {
      Defect curDefect = new Defect();
      String theLine = defectList.getString(defectList.getSelectedIndex());
      int pos = theLine.indexOf("|");
      try {
        curDefect.ID = Integer.parseInt(theLine.substring(0,pos));
      } catch (Exception e) {
        curDefect.ID = 0;
      }
      curDefect.load();
      df = new DefectForm(curDefect,this);
      df.updateScreen();
      theDisplay.setCurrent(df);
    } else if (c==cbReport) {
      /* Build the screen for age select */
      Defect.theStatus = theStatus.getSelectedIndex() + 1;
      Defect theDefect = new Defect();
      String[] result = theDefect.listItems("DefectData",new Defect());
      defectList = new List("Defects found:",List.EXCLUSIVE,result,null);
      defectList.addCommand(cbBack);
      defectList.addCommand(cbSelect);
      defectList.setCommandListener(this);
      theDisplay.setCurrent(defectList);
    }
  }
}
