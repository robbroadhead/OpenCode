package com.sns.palm.apps.defect;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * <p>Title: MIDP Defect Log</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sleepless Nights Software</p>
 * @author Rob Broadhead
 * @version 1.0
 */
public class AppScreen extends MIDlet implements CommandListener {
  private Command cbBack;
  private Command cbNew;
  private Command cbNext;
  private Command cbPrev;
  private Command cbSave;
  private Form f;

  private TextField sleName;
  private TextField sleTarget;
  private Application theApp;

//  private RecordStore rs;
  private Display theDisplay;

  public AppScreen() {

    /* Basic setup stuff */
    theDisplay = Display.getDisplay(this);
    theApp = new Application();

    /* Set up the buttons */
    cbBack = new Command("Back",Command.BACK,1);
    cbNew = new Command("New",Command.ITEM,3);
    cbNext = new Command("Next",Command.OK,4);
    cbPrev = new Command("Prev",Command.ITEM,5);
    cbSave = new Command("Save",Command.SCREEN,1);

    /* Create the data entry fields */
    sleName = new TextField("Name:","",30,TextField.ANY);
    sleTarget = new TextField("Target OS:","",30,TextField.ANY);

    f = new Form("Applications");
    f.append(sleName);
    f.append(sleTarget);
    f.addCommand(cbNext);
    f.addCommand(cbPrev);
    f.addCommand(cbNew);
    f.addCommand(cbSave);
    f.addCommand(cbBack);
    f.setCommandListener(this);
  }

  public void startApp() {
    theDisplay.setCurrent(f);
  }

  protected void pauseApp() {}
  protected void destroyApp(boolean u) {}

  protected void updateScreen() {
    sleName.setString(theApp.name);
    sleTarget.setString(theApp.target);
  }

  protected void updateStore() {
    theApp.name = sleName.getString();
    theApp.target = sleTarget.getString();
  }

  public void commandAction(Command c, Displayable d) {
    if (c == cbBack) {
      destroyApp(false);
      notifyDestroyed();
    } else if (c==cbNew) {
      theApp.ID = 0;
      theApp = new Application();
      updateScreen();
    } else if (c==cbSave) {
      updateStore();
      theApp.save();
    } else if (c==cbPrev) {
      theApp.ID = theApp.ID - 1;
      theApp.load();
      updateScreen();
    } else if (c==cbNext) {
      theApp.ID = theApp.ID + 1;
      theApp.load();
      updateScreen();
    }
  }
}
