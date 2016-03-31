package com.sns.palm.apps.referee;
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

public class RefUtilScreen extends MIDlet implements CommandListener {
  private Command cbBack;
  private List theForms;

  private Display theDisplay;

  /**
   * The constructor sets up all of the graphical controls.
   */
  public RefUtilScreen() {
    /* Basic setup stuff */
    theDisplay = Display.getDisplay(this);

    /* Set up the buttons */
    cbBack = new Command("Back",Command.SCREEN,1);
    String[] theItems = {"Associations"};
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

      if (theItem.equals("Fields")) {
//        Field temp = new Field();
//        f = new FieldScreen(temp,this);
      } else if (theItem.equals("Associations")) {
        Association temp = new Association();
        f = new AssocScreen(temp,this);
      }
      f.updateScreen();
      theDisplay.setCurrent(f);
    }
  }
}
