package com.sns.palm.apps.common;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import com.sns.palm.util.Tracer;
import com.sns.palm.base.Reportable;

/**
 * <p>Title: Base Data Entry Form</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */


abstract public class BaseScreen extends MIDlet implements CommandListener {
  private Command cbBack;
  private Command cbNew;
  private Command cbNext;
  private Command cbPrev;
  private Command cbSave;
  protected Form f;

  protected Reportable theField;
  protected Display theDisplay = Display.getDisplay(this);

  public BaseScreen(String title) {
    /* Set up the buttons */
    Tracer.setFile("BaseLog");
    Tracer.setActive(true);

    cbBack = new Command("Back",Command.BACK,6);
    cbNew = new Command("New",Command.SCREEN,2);
    cbNext = new Command("Next",Command.OK,5);
    cbPrev = new Command("Prev",Command.ITEM,5);
    cbSave = new Command("Save",Command.SCREEN,1);

    f = new Form(title);
    f.addCommand(cbNext);
    f.addCommand(cbPrev);
    f.addCommand(cbNew);
    f.addCommand(cbSave);
    f.addCommand(cbBack);
    f.setCommandListener(this);

    InitForm();
  }

  public void startApp() {
    theDisplay.setCurrent(f);
  }

  protected void pauseApp() {}
  protected void destroyApp(boolean u) {}

  abstract public void InitForm();
  abstract protected void updateScreen();
  abstract protected void updateStore();
  abstract protected Reportable createItem();

  public void commandAction(Command c, Displayable d) {
    Alert a;
    if (c == cbBack) {
      destroyApp(false);
      notifyDestroyed();
    } else if (c==cbNew) {
      theField.ID = 0;
      theField = createItem();
      updateScreen();
    } else if (c==cbSave) {
      updateStore();
      if (!theField.isValid()) {
        a = new Alert("Save Failed!");
        a.setString("Please complete all fields.");
        theDisplay.setCurrent(a);
      } else {
        theField.save();
      }
      if (!theField.msg.equals("")) {
        Tracer.log(theField.msg,Tracer.ERROR);
        a = new Alert("Save Failed!");
        a.setString(theField.msg);
        theDisplay.setCurrent(a);
      }
      Tracer.log("Save Complete",Tracer.LOG);
    } else if (c==cbPrev) {
      theField.ID = theField.ID - 1;
      theField.load();
      if (!theField.msg.equals("")) {
        Tracer.log(theField.msg,Tracer.ERROR);
        a = new Alert("Load Failed!");
        a.setString(theField.msg);
        theDisplay.setCurrent(a);
      }
      Tracer.log("Load Complete",Tracer.LOG);
      updateScreen();
    } else if (c==cbNext) {
      theField.ID = theField.ID + 1;
      theField.load();
      if (!theField.msg.equals("")) {
        Tracer.log(theField.msg,Tracer.ERROR);
        a = new Alert("Load Failed!");
        a.setString(theField.msg);
        theDisplay.setCurrent(a);
      }
      Tracer.log("Load Complete",Tracer.LOG);
      updateScreen();
    }
  }
}
