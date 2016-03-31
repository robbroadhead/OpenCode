package com.sns.palm.apps.common;

import javax.microedition.midlet.MIDlet;
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

abstract public class BaseForm extends Form implements CommandListener {
  private Command cbBack;
  private Command cbNew;
  private Command cbNext;
  private Command cbPrev;
  private Command cbSave;
  protected Form f;

  protected MIDlet parent;
  protected Reportable theField;

  public BaseForm(Reportable r, MIDlet p,String title) {
    super(title);
    Tracer.setFile("BaseLog");
    if (r == null) {
      r = createItem();
    }

    theField = r;
    parent = p;

    /* Set up the buttons */
    cbBack = new Command("Back",Command.BACK,1);
    cbNew = new Command("New",Command.ITEM,4);
    cbNext = new Command("Next",Command.OK,4);
    cbPrev = new Command("Prev",Command.ITEM,5);
    cbSave = new Command("Save",Command.SCREEN,1);

    addCommand(cbNext);
    addCommand(cbPrev);
    addCommand(cbNew);
    addCommand(cbSave);
    addCommand(cbBack);
    setCommandListener(this);

    Tracer.log("Going into init",Tracer.LOG);
    InitForm();
  }

  abstract protected Reportable createItem();
  abstract public void InitForm();
  abstract public void updateScreen();
  abstract public void updateStore();

  public void commandAction(Command c, Displayable d) {
    Alert a;
    if (c == cbBack) {
      parent.notifyDestroyed();
    } else if (c==cbNew) {
      theField.ID = 0;
      theField = createItem();
      updateScreen();
    } else if (c==cbSave) {
      updateStore();
      if (!theField.isValid()) {
        a = new Alert("Save Failed!");
        a.setString("Please complete all fields.");
        Display.getDisplay(parent).setCurrent(a);
      } else {
        theField.save();
        if (!theField.msg.equals("")) {
          Tracer.log(theField.msg,Tracer.ERROR);
          a = new Alert("Save Failed!");
          a.setString(theField.msg);
          Display.getDisplay(parent).setCurrent(a);
        }
      }
    } else if (c==cbPrev) {
      theField.ID = theField.ID - 1;
      theField.load();
      if (!theField.msg.equals("")) {
        Tracer.log(theField.msg,Tracer.ERROR);
        a = new Alert("Load Failed!");
        a.setString(theField.msg);
        Display.getDisplay(parent).setCurrent(a);
      }
      updateScreen();
    } else if (c==cbNext) {
      theField.ID = theField.ID + 1;
      theField.load();
      if (!theField.msg.equals("")) {
        Tracer.log(theField.msg,Tracer.ERROR);
        a = new Alert("Load Failed!");
        a.setString(theField.msg);
        Display.getDisplay(parent).setCurrent(a);
      }
      updateScreen();
    }
  }
}
