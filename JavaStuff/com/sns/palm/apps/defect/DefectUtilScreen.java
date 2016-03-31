package com.sns.palm.apps.defect;
/**
 * <p>Title: MIDP Defect Log</p>
 * <p>Description: This screen is used to add and edit users for the application</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sleepless Nights Software</p>
 * @author Rob Broadhead
 * @version 1.0
 */
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import com.sns.palm.apps.common.BaseForm;

public class DefectUtilScreen extends MIDlet implements CommandListener {
  private Command cbBack;
  private List theForms;

  private Display theDisplay;

  /**
   * The constructor sets up all of the graphical controls.
   */
  public DefectUtilScreen() {
    /* Basic setup stuff */
    theDisplay = Display.getDisplay(this);

    /* Set up the buttons */
    cbBack = new Command("Back",Command.SCREEN,1);
    String[] theItems = {"Users","Projects","Files","Applications"};
    theForms = new List("Admin Screens",List.IMPLICIT,theItems,null);

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
        User temp = new User();
        f = new UserScreen(temp,this);
      } else if (theItem.equals("Projects")) {
        Project temp = new Project();
        f = new ProjectScreen(temp,this);
      } else if (theItem.equals("Applications")) {
        Application temp = new Application();
        f = new ApplicationScreen(temp,this);
      } else if (theItem.equals("Files")) {
        Files temp = new Files();
        f = new FilesScreen(temp,this);
      }
      f.updateScreen();
      theDisplay.setCurrent(f);
    }
  }
}
