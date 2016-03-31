package com.sns.palm.apps.referee;

import javax.microedition.lcdui.*;
import com.sns.palm.apps.common.BaseForm;
import com.sns.palm.base.Reportable;
import javax.microedition.midlet.MIDlet;

/**
 * <p>Title: MIDP Association Maintenance</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */


public class AssocScreen extends BaseForm {
  private TextField name;
  private TextField phone;
  private TextField assignor;

  public void InitForm() {
    theField = new Association();
    /* Create the data entry fields */
    name = new TextField("Name:","",30,TextField.ANY);
    assignor = new TextField("Assignor:","",30,TextField.ANY);
    phone = new TextField("Phone:","",15,TextField.PHONENUMBER);

    append(name);
    append(assignor);
    append(phone);
  }

  public AssocScreen(Association g,MIDlet p) {
    super(g,p,"Association Maintenance");
  }

  public void updateScreen() {
    Association current = (Association) theField;
    name.setString(current.Name);
    assignor.setString(current.Assignor);
    phone.setString(current.Phone);
  }

  public void updateStore() {
    Association current = (Association) theField;
    current.Name = name.getString();
    current.Assignor = assignor.getString();
    current.Phone = phone.getString();
  }

  protected Reportable createItem() {
    return new Association();
  }

}
