package com.sns.palm.apps.referee;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.Date;

/**
 * <p>Title: MIDP Referee Log</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */


public class GameSummaryScreen extends Form implements CommandListener {
  private Command cbSummary;
  private Command cbBack;

  private DateField startDate;
  private DateField endDate;
  private List gameList;
  private MIDlet parent;

  private Display theDisplay;

  public GameSummaryScreen(MIDlet p) {
    super("Games Summary");
    parent = p;

    /* Set up the buttons */
    cbSummary = new Command("View",Command.OK,1);
    cbBack = new Command("Back",Command.BACK,1);

    /* Create the data entry fields */
    startDate = new DateField("Start:",DateField.DATE_TIME);
    endDate = new DateField("End:",DateField.DATE_TIME);
    startDate.setDate(new Date());
    endDate.setDate(new Date());

    append(startDate);
    append(endDate);
    addCommand(cbSummary);
    addCommand(cbBack);
    setCommandListener(this);
  }

  public void startApp() {
    theDisplay.setCurrent(this);
  }

  protected void pauseApp() {}
  protected void destroyApp(boolean u) {}

  public void commandAction(Command c, Displayable d) {
    if (c == cbBack) {
      parent.notifyDestroyed();
    } else if (c==cbSummary) {
      /* Build the screen for age select */
      gameList = new List("Games done:",List.EXCLUSIVE,Game.SummaryGames(startDate.getDate().getTime(),endDate.getDate().getTime()),null);
      gameList.addCommand(cbBack);
      gameList.setCommandListener(this);
      Display.getDisplay(parent).setCurrent(gameList);
    }
  }
}
