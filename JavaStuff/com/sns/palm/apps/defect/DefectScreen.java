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

public class DefectScreen extends MIDlet {
  private DefectForm f;
  private Display theDisplay;

  /**
   * The constructor sets up the form and creates the defect form that is the
   * basis of this screen.
   */
  public DefectScreen() {
    /* Basic setup stuff */
    Defect theDefect = new Defect();
    theDisplay = Display.getDisplay(this);

    f = new DefectForm(theDefect,this);
  }

  /**
   * Standard MIDP method
   */
  public void startApp() {
    theDisplay.setCurrent(f);
  }

  protected void pauseApp() {}
  protected void destroyApp(boolean u) {}
}
