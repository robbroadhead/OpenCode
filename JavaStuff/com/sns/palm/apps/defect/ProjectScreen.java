package com.sns.palm.apps.defect;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import com.sns.palm.util.Tracer;
import com.sns.palm.util.Utility;
import com.sns.palm.base.Reportable;
import com.sns.palm.apps.common.BaseForm;

/**
 * <p>Title: Project Screen</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */

public class ProjectScreen extends BaseForm {
  private TextField Name;
  private TextField mjVer;
  private TextField mnVer;
  private TextField note;
  private ChoiceGroup owner;

  protected Reportable createItem() {
    return new Project();
  }

  public ProjectScreen(Project g,MIDlet p) {
    super(g,p,"Project Admin");
  }

  public void InitForm() {
    /* Create the data entry fields */
    Name = new TextField("Name:","",20,TextField.ANY);
    mjVer = new TextField("Major Ver.:","",4,TextField.ANY);
    mnVer = new TextField("Minor Ver.:","",4,TextField.ANY);
    note = new TextField("Note:","",60,TextField.ANY);

    /* Build the screen for user select */
    User temp = new User();
    String[] refArr = Utility.sortStringList(((Project) theField).listItems("RBUsers",temp));
    owner = new ChoiceGroup("Contact:",Choice.EXCLUSIVE,refArr,null);


    append(Name);
    append(mjVer);
    append(mnVer);
    append(owner);
    append(note);
    setCommandListener(this);
  }

  public void updateScreen() {
    Project current = (Project) theField;
    Name.setString(current.name);
    mjVer.setString(current.mjVer);
    mnVer.setString(current.mnVer);
    note.setString(current.note);

    /* Search for user text */
    int cidx,lidx;
    cidx = 0;
    lidx = 0;

    User tempUser = new User();
    tempUser.ID = current.userid;
    if (current.userid > 0) {
	  tempUser.load();
      String curName = tempUser.lname.trim() + ", " + tempUser.fname.trim();
      
      while (!owner.getString(cidx).trim().equals(curName)) {
        cidx++;
      }
      owner.setSelectedIndex(cidx,true);
    }

  }

  private String fixUserName(String val) {
    int idx = val.indexOf(", ");
    String fname,lname;

    if (idx >=0) {
      fname=Utility.padString(val.substring(idx + 2,val.length()),19);
      lname=Utility.padString(val.substring(0,idx),20);
      return  fname + lname;
    } else {
      return val;
    }
  }

  public void updateStore() {
  	Tracer.log("Begin Project Update",Tracer.LOG);
    Project current = (Project) theField;
    current.name = Name.getString();
    current.mjVer = mjVer.getString();
    current.mnVer = mnVer.getString();
    current.note = note.getString();

    String userName = owner.getString(owner.getSelectedIndex());
    userName = fixUserName(userName);
    byte[] tempByte = Utility.StrToByte(userName);
  	Tracer.log("Project Update 5",Tracer.LOG);
    current.userid = Utility.searchStore(tempByte,Reportable.BASESIZE,39,"RBUsers");
  	Tracer.log("End Project Update",Tracer.LOG);
  }
}
