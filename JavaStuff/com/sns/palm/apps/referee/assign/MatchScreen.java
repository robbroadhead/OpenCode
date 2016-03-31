package com.sns.palm.apps.referee.assign;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.Display;

/**
 * <p>Title: Match Maintenance</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */


public class MatchScreen extends MIDlet {
  private MatchForm f;
  private Match theMatch;

  private Display theDisplay;

  public MatchScreen() {
    /* Basic setup stuff */
    theDisplay = Display.getDisplay(this);
    theMatch = new Match();

    f = new MatchForm(theMatch,this);
  }

  public void startApp() {
    theDisplay.setCurrent(f);
  }

  protected void pauseApp() {}
  protected void destroyApp(boolean u) {}


}
