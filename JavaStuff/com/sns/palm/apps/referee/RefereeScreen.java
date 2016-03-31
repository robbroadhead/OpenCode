package com.sns.palm.apps.referee;

import javax.microedition.lcdui.*;
import com.sns.palm.apps.common.BaseScreen;
import com.sns.palm.base.Reportable;

/**
 * <p>Title: MIDP Referee Log</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */

public class RefereeScreen extends BaseScreen {
  private TextField refName, phone, email;

  public void InitForm() {
    theField = new Referee();

    /* Create the data entry fields */
    refName = new TextField("Name:","",30,TextField.ANY);
    phone = new TextField("Phone:","",15,TextField.PHONENUMBER);
    email = new TextField("Email:","",50,TextField.EMAILADDR);

    f.append(refName);
    f.append(phone);
    f.append(email);
    f.setCommandListener(this);
    updateScreen();

  }

  public RefereeScreen() {
    super("Ref's Assistant");
  }

  protected void updateScreen() {
    Referee current = (Referee) theField;
    refName.setString(current.Name);
    phone.setString(current.Phone);
    email.setString(current.Email);
  }

  protected void updateStore() {
    Referee current = (Referee) theField;
    current.Name = refName.getString();
    current.Phone = phone.getString();
    current.Email = email.getString();
  }

  protected Reportable createItem() {
    return new Referee();
  }
}
