package com.sns.palm.apps.referee;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * <p>Title: MIDP Referee Log</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */


public class GameLogScreen extends MIDlet {
  private GameForm f;
  private Game theGame;

  private Display theDisplay;

  public GameLogScreen() {
    theDisplay = Display.getDisplay(this);
    theGame = new Game();

    f = new GameForm(theGame,this);
  }

  public void startApp() {
    theDisplay.setCurrent(f);
  }

  protected void pauseApp() {}
  protected void destroyApp(boolean u) {}
}
