package com.sns.palm.apps.defect;
/**
 * <p>Title: MIDP Defect Log</p>
 * <p>Description: This screen is used to add and edit users for the application</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sleepless Nights Software</p>
 * @author Rob Broadhead
 * @version 1.0
 */
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import com.sns.palm.util.Tracer;
import com.sns.palm.apps.common.BaseForm;
import com.sns.palm.base.Reportable;

public class UserScreen extends BaseForm {
  private TextField sleFName;
  private TextField sleLName;
  private TextField sleDept;
  /**
   * The constructor sets up all of the graphical controls.
   */
  public void InitForm() {
    Tracer.setFile("defectLog");
    Tracer.setActive(true);

    /* Create the data entry fields */
    sleFName = new TextField("First Name:","",20,TextField.ANY);
    sleLName = new TextField("Last Name:","",20,TextField.ANY);
    sleDept = new TextField("Group:","",20,TextField.ANY);

    append(sleFName);
    append(sleLName);
    append(sleDept);
    setCommandListener(this);
  }

  protected Reportable createItem() {
    return new User();
  }

  public UserScreen(User g,MIDlet p) {
    super(g,p,"User Admin");
  }

  /**
   * This is the method that pulls data from the User object and updates the
   * control fields with the proper data.
   */
  public void updateScreen() {
    User current = (User) theField;
    sleFName.setString(current.fname);
    sleLName.setString(current.lname);
    sleDept.setString(current.dept);
  }

  /**
   * This takes the data out of the screen fields and places the data in the
   * current User object.
   */
  public void updateStore() {
  	User current = (User) theField;
    current.fname = sleFName.getString();
    current.lname = sleLName.getString();
    current.dept = sleDept.getString();
  }

}
