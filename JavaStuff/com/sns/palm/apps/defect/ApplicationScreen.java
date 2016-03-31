package com.sns.palm.apps.defect;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import com.sns.palm.base.Reportable;
import com.sns.palm.apps.common.BaseForm;

/**
 * <p>Title: Application Screen</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */

public class ApplicationScreen extends BaseForm {
  private TextField Name;
  private TextField Target;

  protected Reportable createItem() {
    return new Application();
  }

  public ApplicationScreen(Application g,MIDlet p) {
    super(g,p,"Application Admin");
  }

  public void InitForm() {
    /* Create the data entry fields */
    Name = new TextField("Name:","",30,TextField.ANY);
    Target = new TextField("Target:","",30,TextField.ANY);

    append(Name);
    append(Target);
    setCommandListener(this);
  }

  public void updateScreen() {
    Application current = (Application) theField;
    Name.setString(current.name);
    Target.setString(current.target);
  }

  public void updateStore() {
    Application current = (Application) theField;
    current.name = Name.getString();
    current.target = Target.getString();
  }
}
