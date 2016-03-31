package com.sns.palm.apps.referee.assign;

import javax.microedition.lcdui.*;
import com.sns.palm.util.RefereeUtils;
import com.sns.palm.apps.common.BaseForm;
import com.sns.palm.base.Reportable;
import javax.microedition.midlet.MIDlet;

/**
 * <p>Title: MIDP Pay Scale Maintenance</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */


public class PayScreen extends BaseForm {
  private TextField center;
  private TextField assist;
  private TextField fourth;
  private ChoiceGroup ageBrack;
  private Pay current;

  public PayScreen(Pay g,MIDlet p) {
    super(g,p,"Pay Scale Maintenance");
  }

  public void InitForm() {
    /* Create the data entry fields */
    center = new TextField("Center:","",8,TextField.ANY);
    assist = new TextField("Assistant:","",8,TextField.ANY);
    fourth = new TextField("Fourth Off.:","",8,TextField.ANY);

    /* Build the screen for age select */
    ageBrack = new ChoiceGroup("Age:",Choice.EXCLUSIVE,RefereeUtils.LoadAges(),null);

    append(center);
    append(assist);
    append(fourth);
    append(ageBrack);
  }

  public void updateScreen() {
    current = (Pay) theField;
    center.setString(current.screenDisplay(current.centerd,current.centerc));
    assist.setString(current.screenDisplay(current.assistd,current.assistc));
    fourth.setString(current.screenDisplay(current.fourthd,current.fourthc));
    ageBrack.setSelectedIndex(current.ageBracket,true);
  }

  private void StringToPay(String val,int pos) {
    // Remove a dollar sign
    val = val.replace('$',' ');
    val = val.trim();

    int curpos = val.indexOf(".");
    byte l,r;

    if (curpos >= 0) {
      l = (byte) Integer.parseInt(val.substring(0,curpos));
      r = (byte) Integer.parseInt(val.substring(curpos + 1,val.length()));
    } else {
      if (val.length() > 0) {
        l = (byte) Integer.parseInt(val);
        r = (byte) 0;
      } else {
        l=0;
        r=0;
      }
    }

    if (pos==0) {
      current.centerd = l;
      current.centerc = r;
    } else if (pos==1) {
      current.assistd = l;
      current.assistc = r;
    } else if (pos==2) {
      current.fourthd = l;
      current.fourthc = r;
    }
//    Tracer.log("Money Amount: $" + l + "." + r,Tracer.MSG);
  }

  protected Reportable createItem() {
    return new Pay();
  }


  public void updateStore() {
    current = (Pay) theField;
    StringToPay(center.getString().trim(),0);
    StringToPay(assist.getString().trim(),1);
    StringToPay(fourth.getString().trim(),2);
    current.ageBracket = (byte) ageBrack.getSelectedIndex();
  }
}
