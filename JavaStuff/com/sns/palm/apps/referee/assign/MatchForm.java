package com.sns.palm.apps.referee.assign;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.Date;
import com.sns.palm.apps.common.BaseForm;
import com.sns.palm.base.Reportable;

/**
 * <p>Title: MIDP Referee Log</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */


public class MatchForm extends BaseForm {
  private TextField home;
  private TextField visitor;
  private TextField homeNote;
  private TextField visNote;
  private DateField playDate;
  private ChoiceGroup numRefs;
  private ChoiceGroup ageBracket;
  private ChoiceGroup field;
  private ChoiceGroup homeColor;
  private ChoiceGroup visColor;

  public MatchForm(Match g,MIDlet p) {
    super(g,p,"Match Maint.");
  }

  public void InitForm() {
    /* Create the data entry fields */
    home = new TextField("Home:","",25,TextField.ANY);
    visitor = new TextField("Visitor:","",25,TextField.ANY);
    homeNote = new TextField("Note:","",25,TextField.ANY);
    visNote = new TextField("Note:","",25,TextField.ANY);
    playDate = new DateField("Date:",DateField.DATE_TIME);

    String items[] = new String[4];
    items[0] = "Center only";
    items[1] = "2-man";
    items[2] = "3-man";
    items[3] = "4-man";
    numRefs = new ChoiceGroup("Refs:",Choice.EXCLUSIVE,items,null);

    /* Build the screen for age select */
    Age temp = new Age();
    ageBracket = new ChoiceGroup("Age:",Choice.EXCLUSIVE,temp.listItems("AgeData",temp),null);
    Field temp2 = new Field();
    field = new ChoiceGroup("Field:",Choice.EXCLUSIVE,temp2.listItems("FieldData",temp2),null);
    homeColor = new ChoiceGroup("Home:",Choice.EXCLUSIVE,Match.POS_LIST,null);
    visColor = new ChoiceGroup("Visitor:",Choice.EXCLUSIVE,Match.POS_LIST,null);

    append(home);
    append(homeNote);
    append(homeColor);
    append(visitor);
    append(visNote);
    append(visColor);
    append(ageBracket);
    append(field);
    append(numRefs);
    append(playDate);
  }


  public void updateScreen() {
    Match current = (Match) theField;
    home.setString(current.home);
    visitor.setString(current.visitor);
    homeNote.setString(current.homeNote);
    visNote.setString(current.visNote);
    playDate.setDate(new Date(current.playDate));
    ageBracket.setSelectedIndex(current.ageBracket,true);
    visColor.setSelectedIndex(current.visColor,true);
    homeColor.setSelectedIndex(current.homeColor,true);
    field.setSelectedIndex(current.field,true);
    numRefs.setSelectedIndex(current.numRefs,true);
  }

  protected Reportable createItem() {
    return new Match();
  }

  public void updateStore() {
    Match current = (Match) theField;
    current.home = home.getString();
    current.visitor = visitor.getString();
    current.homeNote = homeNote.getString();
    current.visNote = visNote.getString();
    current.homeColor = (byte) homeColor.getSelectedIndex();
    current.visColor = (byte) visColor.getSelectedIndex();
    current.field = (byte) field.getSelectedIndex();
    current.ageBracket = (byte) ageBracket.getSelectedIndex();
    Date temp = playDate.getDate();
    if (temp!=null) {
      current.playDate = temp.getTime();
    }
    current.numRefs = (byte) numRefs.getSelectedIndex();
  }
}
