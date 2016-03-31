package com.sns.palm.apps.referee;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.Date;
import com.sns.palm.util.Utility;
import com.sns.palm.util.Tracer;
import com.sns.palm.base.Reportable;
import com.sns.palm.util.RefereeUtils;
import com.sns.palm.apps.common.BaseForm;

/**
 * <p>Title: MIDP Referee Log</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */


public class GameForm extends BaseForm {
  private TextField homeName;
  private TextField visName;
  private TextField homeSc;
  private TextField visSc;
  private DateField gameDate;
  private ChoiceGroup chkBoxes;
  private ChoiceGroup ageList;
  private ChoiceGroup centerList;
  private ChoiceGroup lineList;
  private ChoiceGroup association;

  protected Reportable createItem() {
    return new Game();
  }

  public GameForm(Game g,MIDlet p) {
    super(g,p,"Ref's Assistant");
  }

  public void InitForm() {
    /* Create the data entry fields */
    homeName = new TextField("Home:","",30,TextField.ANY);
    visName = new TextField("Visitor:","",25,TextField.ANY);
    homeSc = new TextField("Score:","",3,TextField.NUMERIC);
    visSc = new TextField("Score:","",3,TextField.NUMERIC);
    gameDate = new DateField("Date:",DateField.DATE_TIME);
    gameDate.setDate(new Date());
    String items[] = new String[3];
    items[0] = "Indoor";
    items[1] = "Paid";
    items[2] = "Center";
    chkBoxes = new ChoiceGroup("Options:",ChoiceGroup.MULTIPLE,items,null);

    /* Build the screen for age select */
    ageList = new ChoiceGroup("Age:",Choice.EXCLUSIVE,RefereeUtils.LoadAges(),null);

    /* Build the screen for Referee 1 select */
    Referee temp = new Referee();
    String[] refArr = Utility.sortStringList(((Game) theField).listItems("RefereeData",temp));
    centerList = new ChoiceGroup("Referee 1:",Choice.EXCLUSIVE,refArr,null);

    /* Build the screen for Referee 2 select */
    lineList = new ChoiceGroup("Referee 2:",Choice.EXCLUSIVE,refArr,null);

    String[] result = ((Game) theField).listItems("AssocData",new Association());
    association = new ChoiceGroup("Assoc.:",Choice.EXCLUSIVE,result,null);

    append(homeName);
    append(homeSc);
    append(visName);
    append(visSc);
    append(centerList);
    append(lineList);
    append(ageList);
    append(association);
    append(chkBoxes);
    append(gameDate);
    setCommandListener(this);
  }

  public void updateScreen() {
    Game current = (Game) theField;
    homeName.setString(current.Home);
    visName.setString(current.Visitor);
    homeSc.setString("" + current.HomeScore);
    visSc.setString("" + current.VisScore);
    gameDate.setDate(new Date(current.PlayDate));
    boolean theFlags[] = new boolean[3];
    theFlags[0] = current.Indoor;
    theFlags[1] = current.Paid;
    theFlags[2] = current.Center;
    chkBoxes.setSelectedFlags(theFlags);
    ageList.setSelectedIndex(current.Age,true);

    /* Search for center text */
    int cidx,lidx;
    cidx = 0;
    lidx = 0;

    Tracer.setActive(true);
    Referee tempRef = new Referee();
    tempRef.ID = current.Ref1;
    tempRef.load();
    String curName = tempRef.Name.trim();
    int idx = curName.indexOf(" ");
    if (idx > 0) {
      curName = curName.substring(idx,curName.length()) + ", " + curName.substring(0,idx);
    }
    curName = curName.trim();
    while (!centerList.getString(cidx).trim().equals(curName)) {
      cidx++;
    }
    centerList.setSelectedIndex(cidx,true);

    /* Search for assistant text */
    tempRef.ID = current.Ref2;
    tempRef.load();
    curName = tempRef.Name.trim();
    idx = curName.indexOf(" ");
    if (idx > 0) {
      curName = curName.substring(idx,curName.length()) + ", " + curName.substring(0,idx);
    }
    curName = curName.trim();
    while (!lineList.getString(lidx).trim().equals(curName)) {
      lidx++;
    }
    lineList.setSelectedIndex(lidx,true);
    ageList.setSelectedIndex(current.Age,true);
    association.setSelectedIndex(current.assoc,true);
  }

  private String fixRefName(String val) {
    int idx = val.indexOf(", ");

    if (idx >=0) {
      return val.substring(idx + 2,val.length()) + val.substring(0,idx);
    } else {
      return val;
    }
  }


  public void updateStore() {
    Game current = (Game) theField;
    current.Home = homeName.getString();
    current.Visitor = visName.getString();
    current.HomeScore = Integer.parseInt(homeSc.getString());
    current.VisScore = Integer.parseInt(visSc.getString());
    Date temp = gameDate.getDate();
    if (temp!=null) {
      current.PlayDate = temp.getTime();
    }
    current.Indoor = chkBoxes.isSelected(0);
    current.Paid = chkBoxes.isSelected(1);
    current.Center = chkBoxes.isSelected(2);
    current.Age = ageList.getSelectedIndex();
    String refName = centerList.getString(centerList.getSelectedIndex());
    refName = fixRefName(refName);
    refName = Utility.padString(refName,30);
    byte[] tempByte = Utility.StrToByte(refName);
    current.Ref1 = Utility.searchStore(tempByte,Reportable.BASESIZE,30,"RefereeData");
    refName = lineList.getString(lineList.getSelectedIndex());
    refName = fixRefName(refName);
    refName = Utility.padString(refName,30);
    tempByte = Utility.StrToByte(refName);
    current.Ref2 = Utility.searchStore(tempByte,Reportable.BASESIZE,30,"RefereeData");
    current.assoc = association.getSelectedIndex();
  }
}
