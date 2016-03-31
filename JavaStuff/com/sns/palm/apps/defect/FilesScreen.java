package com.sns.palm.apps.defect;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import com.sns.palm.util.Tracer;
import com.sns.palm.util.Utility;
import com.sns.palm.base.Reportable;
import com.sns.palm.apps.common.BaseForm;

/**
 * <p>Title: Files Screen</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */

public class FilesScreen extends BaseForm {
  private TextField Name;
  private TextField Location;
  private TextField Type;
  private ChoiceGroup owner;

  protected Reportable createItem() {
    return new Files();
  }

  public FilesScreen(Files g,MIDlet p) {
    super(g,p,"Files Admin");
  }


  public void InitForm() {
    theField = new Files();
    
    /* Create the data entry fields */
    Name = new TextField("Name:","",20,TextField.ANY);
    Location = new TextField("Location:","",100,TextField.ANY);
    Type = new TextField("Type:","",20,TextField.ANY);

    /* Build the screen for user select */
    User temp = new User();
    String[] refArr = Utility.sortStringList(((Files) theField).listItems("RBUsers",temp));
    owner = new ChoiceGroup("Owner:",Choice.EXCLUSIVE,refArr,null);


    append(Name);
    append(Location);
    append(Type);
    append(owner);
    setCommandListener(this);
  }

  public void updateScreen() {
    Files current = (Files) theField;
    Name.setString(current.name);
    Location.setString(current.location);
    Type.setString(current.type);

    /* Search for user text */
    int cidx,lidx;
    cidx = 0;
    lidx = 0;

    User tempUser = new User();
    tempUser.ID = current.ownerid;
    if (current.ownerid > 0) {
	  tempUser.load();
      String curName = tempUser.lname.trim() + ", " + tempUser.fname.trim();
      
      while (!owner.getString(cidx).trim().equals(curName)) {
        cidx++;
      }
      
      owner.setSelectedIndex(cidx,true);
    }
    
  	Tracer.log("Leaving updateScreen",Tracer.LOG);
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
    Files current = (Files) theField;
    current.name = Name.getString();
    current.location = Location.getString();
    current.type = Type.getString();

    String userName = owner.getString(owner.getSelectedIndex());
    userName = fixUserName(userName);
    byte[] tempByte = Utility.StrToByte(userName);
    current.ownerid = Utility.searchStore(tempByte,Reportable.BASESIZE,39,"RBUsers");
  }
}
