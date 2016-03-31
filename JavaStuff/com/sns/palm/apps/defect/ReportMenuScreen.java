package com.sns.palm.apps.defect;
/**
 * <p>Title: ReportMenuScreen</p>
 * <p>Description: This screen is used to diplay reports available for the application</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Sleepless Nights Software</p>
 * @author Rob Broadhead
 * @version 1.0
 */
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import com.sns.palm.apps.common.BaseForm;

public class ReportMenuScreen extends MIDlet implements CommandListener {
  private Command cbBack;
  private List theForms;

  private Display theDisplay;

  /**
   * The constructor sets up all of the graphical controls.
   */
  public ReportMenuScreen() {
    /* Basic setup stuff */
    theDisplay = Display.getDisplay(this);

    /* Set up the buttons */
    cbBack = new Command("Back",Command.SCREEN,1);
    String[] theItems = {"Users","Projects","Files","Issues-Priority","Issues-Status"};
    theForms = new List("Reports Available",List.IMPLICIT,theItems,null);

    theForms.addCommand(cbBack);
    theForms.setCommandListener(this);
    theDisplay.setCurrent(theForms);
  }

  public void startApp() {
    theDisplay.setCurrent(theForms);
  }

  protected void pauseApp() {}
  protected void destroyApp(boolean u) {}

  /**
   * This is the main event handler for the command objects on the RefUtilScreen
   * form.
   *
   * @param c the command object that triggered this event.
   * @param d the form that the control is on.
   */
  public void commandAction(Command c, Displayable d) {
    if (c == cbBack) {
      destroyApp(false);
      notifyDestroyed();
    } else if (c == List.SELECT_COMMAND) {
      String theItem = theForms.getString(theForms.getSelectedIndex());
      BaseForm f = null;

      if (theItem.equals("Users")) {
//          User temp = new User();
//          f = new UserScreen(temp,this);
      } else if (theItem.equals("Projects")) {
//        Project temp = new Project();
//        f = new ProjectScreen(temp,this);
      } else if (theItem.equals("Files")) {
//        Files temp = new Files();
//        f = new FilesScreen(temp,this);
      } else if (theItem.equals("Issues-Priority")) {
//        Status temp = new Status();
//        f = new StatusScreen(temp,this);
      } else if (theItem.equals("Issues-Status")) {
//        Priority temp = new Priority();
//        f = new PriorityScreen(temp,this);
      } else if (theItem.equals("Severity")) {
//        Severity temp = new Severity();
//        f = new SeverityScreen(temp,this);
      } else if (theItem.equals("Type")) {
//        Type temp = new Type();
//        f = new TypeScreen(temp,this);
      }
      f.updateScreen();
      theDisplay.setCurrent(f);
    }
  }
}
