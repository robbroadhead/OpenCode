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


public class GameReportScreen extends Form implements CommandListener {
  private Command cbReport;
  private Command cbBack;
  private MIDlet parent;

  private DateField startDate;
  private DateField endDate;
  private List gameList;

  public GameReportScreen(MIDlet p) {
    super("Games Report");
    parent = p;

    /* Set up the buttons */
    cbReport = new Command("View Report",Command.OK,1);
    cbBack = new Command("Back",Command.BACK,1);

    /* Create the data entry fields */
    startDate = new DateField("Start:",DateField.DATE_TIME);
    endDate = new DateField("End:",DateField.DATE_TIME);
    startDate.setDate(new Date());
    endDate.setDate(new Date());

    append(startDate);
    append(endDate);
    addCommand(cbReport);
    addCommand(cbBack);
    setCommandListener(this);
  }

  public void commandAction(Command c, Displayable d) {
    if (c == cbBack) {
      parent.notifyDestroyed();
    } else if (c==cbReport) {
      /* Build the screen for age select */
      Game.startDate = startDate.getDate().getTime();
      Game.endDate = endDate.getDate().getTime();
      Game theGame = new Game();
      String[] result = theGame.listItems("GameData",new Game());
      gameList = new List("Games done:",List.EXCLUSIVE,result,null);
      gameList.addCommand(cbBack);
      gameList.setCommandListener(this);
      Display.getDisplay(parent).setCurrent(gameList);
    }
  }
}
