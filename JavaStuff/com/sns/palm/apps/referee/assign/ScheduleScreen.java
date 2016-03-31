package com.sns.palm.apps.referee.assign;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.Display;

/**
 * <p>Title: Schedule Maintenance</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */


public class ScheduleScreen extends MIDlet {
  private ScheduleForm f;
  private Schedule theSchedule;

  private Display theDisplay;

  public ScheduleScreen() {
    /* Basic setup stuff */
    theDisplay = Display.getDisplay(this);
    theSchedule = new Schedule();

    f = new ScheduleForm(theSchedule,this);
  }

  public void startApp() {
    theDisplay.setCurrent(f);
  }

  protected void pauseApp() {}
  protected void destroyApp(boolean u) {}


}
