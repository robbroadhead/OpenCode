package com.sns.palm.apps.referee.assign;

import javax.microedition.lcdui.*;
import com.sns.palm.apps.common.BaseForm;
import com.sns.palm.base.Reportable;
import javax.microedition.midlet.MIDlet;

/**
 * <p>Title: Assignor Field Maintenance</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */


public class FieldScreen extends BaseForm {

  private TextField name;
  private ChoiceGroup fieldSize;

  public FieldScreen(Field g,MIDlet p) {
    super(g,p,"Field Maintenance");
  }

  public void InitForm() {
    /* Create the data entry fields */
    name = new TextField("Name:","",25,TextField.ANY);

    /* Build the screen for age select */
    fieldSize = new ChoiceGroup("Size:",Choice.EXCLUSIVE,Field.loadSizes(),null);

    append(name);
    append(fieldSize);
  }

  protected Reportable createItem() {
    return new Field();
  }

  public void updateScreen() {
    Field current = (Field) theField;
    name.setString(current.name);
    fieldSize.setSelectedIndex(current.fieldSize,true);
  }

  public void updateStore() {
    Field current = (Field) theField;
    current.name = name.getString();
    current.fieldSize = (byte) fieldSize.getSelectedIndex();
  }

}
