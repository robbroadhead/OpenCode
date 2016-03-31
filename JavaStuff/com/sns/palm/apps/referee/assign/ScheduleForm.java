package com.sns.palm.apps.referee.assign;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import com.sns.palm.util.Tracer;
import com.sns.palm.apps.common.BaseForm;
import com.sns.palm.base.Reportable;
import com.sns.palm.apps.referee.Referee;

/**
 * <p>Title: MIDP Referee Log</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: RB Consulting Inc.</p>
 * @author Rob Broadhead
 * @version 1.0
 */


public class ScheduleForm extends BaseForm {
  private ChoiceGroup game;
  private ChoiceGroup center;
  private ChoiceGroup AR1;
  private ChoiceGroup AR2;
  private ChoiceGroup fourth;

  public ScheduleForm(Schedule g,MIDlet p) {
    super(g,p,"Schedule Maint.");
  }

  public void InitForm() {
    Tracer.setActive(true);
    Tracer.setFile("SchedTrace");

    /* Build the screen for game select */
    Match temp = new Match();
    Match.startDate = 1;
    Match.endDate = 1;
    String[] result = temp.listItems(temp.rsName,temp);
    game = new ChoiceGroup("Game:",Choice.EXCLUSIVE,result,null);
    append(game);

    Referee refTemp = new Referee();
    String[] refList = refTemp.listItems(refTemp.rsName,refTemp);

    center = new ChoiceGroup("Center:",Choice.EXCLUSIVE,refList,null);
    center.insert(0,"Not Assigned",null);
    append(center);

    AR1 = new ChoiceGroup("AR/Center:",Choice.EXCLUSIVE,refList,null);
    AR1.insert(0,"Not Assigned",null);
    append(AR1);

    AR2 = new ChoiceGroup("AR:",Choice.EXCLUSIVE,refList,null);
    AR2.insert(0,"Not Assigned",null);
    append(AR2);

    fourth = new ChoiceGroup("4th Off.:",Choice.EXCLUSIVE,refList,null);
    fourth.insert(0,"Not Assigned",null);
    append(fourth);
  }

  public void updateScreen() {
    Schedule current = (Schedule) theField;
    game.setSelectedIndex(current.game,true);
    center.setSelectedIndex(current.center,true);

    if (current.AR1 >= 0) {
      AR1.setSelectedIndex(current.AR1,true);
    } else {
      AR1.setLabel("Not Used");
    }

    if (current.AR2 >= 0) {
      AR2.setSelectedIndex(current.AR2,true);
    } else {
      AR2.setLabel("Not Used");
    }

    if (current.Fourth >= 0) {
      fourth.setSelectedIndex(current.Fourth,true);
    } else {
      fourth.setLabel("Not Used");
    }
  }

  protected Reportable createItem() {
    return new Schedule();
  }

  public void updateStore() {
    Schedule current = (Schedule) theField;
    current.game = game.getSelectedIndex();
    Match tempMatch = new Match();
    tempMatch.ID = current.game;
    tempMatch.load();
    current.Fourth = -2;
    current.AR1 = -2;
    current.AR2 = -2;
    current.center = -2;

    switch (tempMatch.numRefs) {
      case 3:current.Fourth = fourth.getSelectedIndex();
      case 2:current.AR2 = AR2.getSelectedIndex();
      case 1:current.AR1 = AR1.getSelectedIndex();
      case 0:current.center = center.getSelectedIndex();
    break;
    }
  }
}
