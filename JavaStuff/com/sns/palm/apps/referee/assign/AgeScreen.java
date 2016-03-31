package com.sns.palm.apps.referee.assign;

import javax.microedition.lcdui.*;
import com.sns.palm.util.*;
import com.sns.palm.apps.common.BaseForm;
import com.sns.palm.base.Reportable;
import javax.microedition.midlet.MIDlet;

/**
 * <p>Title: Assignor Age Maintenance</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */


public class AgeScreen extends BaseForm {

  private TextField name;
  private TextField duration;
  private ChoiceGroup quarter;
  private ChoiceGroup ageBrack;
  private ChoiceGroup fieldSize;

  public AgeScreen(Age g,MIDlet p) {
    super(g,p,"Age Maintenance");
  }

  public void InitForm() {
    /* Create the data entry fields */
    name = new TextField("Name:","",25,TextField.ANY);
    duration = new TextField("Dur. per pd.:","",3,TextField.NUMERIC);
    String items[] = new String[3];
    items[0] = "Quarters";
    items[1] = "Halves";
    items[2] = "Single";
    quarter = new ChoiceGroup("Period:",Choice.EXCLUSIVE,items,null);

    /* Build the screen for age select */
    ageBrack = new ChoiceGroup("Bracket:",Choice.EXCLUSIVE,RefereeUtils.LoadAges(),null);

    /* Build the screen for age select */
    fieldSize = new ChoiceGroup("Size:",Choice.EXCLUSIVE,Field.loadSizes(),null);

    append(name);
    append(quarter);
    append(ageBrack);
    append(duration);
    append(fieldSize);
  }

  protected Reportable createItem() {
    return new Age();
  }

  public void updateScreen() {
    Age current = (Age) theField;
    name.setString(current.name);
    duration.setString("" + current.duration);

    if (current.ageEquiv >= 0) {
      ageBrack.setSelectedIndex((int) current.ageEquiv,true);
    }

    if (current.qtr >= 0) {
      quarter.setSelectedIndex((int) current.qtr,true);
    }

    if (current.field >= 0) {
      fieldSize.setSelectedIndex((int) current.field,true);
    }
  }

  public void updateStore() {
    Age current = (Age) theField;
    current.name = name.getString();
    current.ageEquiv = (byte) ageBrack.getSelectedIndex();
    current.qtr = (byte) quarter.getSelectedIndex();
    current.field = (byte) fieldSize.getSelectedIndex();
    current.duration = Byte.parseByte(duration.getString());
  }

}
