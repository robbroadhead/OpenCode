//Title:        Referee Assistant 2.0
//Version:
//Copyright:    Copyright (c) 2001
//Author:       Rob Broadhead
//Company:      Sleepless Nights Software
//Description:
package com.sns.Apps.Referee.ui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import com.sns.Apps.Referee.support.*;

/**
* The frame for displaying information about a game
*
* @see com.sns.Apps.Referee.db.Data
* @version 1.0 30-Apr-2001
* @author Rob Broadhead
*/
public class NewGameFrame extends JFrame {
   /* Properties of the Frame */
   /** contains the buttons.
   * @serial field southPane */
   private JToolBar southPane;
   /** contains the age and association fields
   * @serial northPane */
   private JPanel northPane;
   /** Button with exit text and uses actionHandler
   * @serial cbExit */
   private JButton cbExit, cbDone;

   private JTextField sleHome,sleVis,sleHomeSc,sleVisSc;
   private JComboBox cbxAssoc,cbxAge;
   private JTextField sleRef1,sleRef2,sleDate,sleTime,slePay;
   private JCheckBox cbxPaid,cbxCenter,cbxIndoor;
   private NewGameFrame theFrame;

   public NewGameFrame() {
      /* Give the frame a title */
      super("Add Game Data");

      theFrame = this;
      createPanel();

      /* Add a listener in case user clicks the x to close the frame */
      this.addWindowListener(new windowHandler());
   }

  /**
  * Performs all of the data and graphical object creation and initialization for
  * the frame.
  */
  public void createPanel() {
    /* Set up a
    nd display the frames */
    southPane = new JToolBar();
    northPane = new JPanel();

    /* Set up the layout */
    northPane.setLayout(new GridLayout(7,4));

    /* The exit button */
    cbExit = new JButton("Cancel");
    cbExit.addActionListener(new actionHandler());
    cbExit.setToolTipText("Exit without saving changes");
    southPane.add(cbExit);

    /* The exit button */
    cbDone = new JButton("Done");
    cbDone.addActionListener(new actionHandler());
    cbDone.setToolTipText("Close and save changes to DB.");
    southPane.add(cbDone);

    /* Set up the fields and labels */
    northPane.add(new JLabel("Home Name"));
    sleHome = new JTextField();
    sleHome.setToolTipText("Home team name");
    northPane.add(sleHome);

    northPane.add(new JLabel("Visitor Name"));
    sleVis = new JTextField();
    sleVis.setToolTipText("Visiting team name");
    northPane.add(sleVis);

    northPane.add(new JLabel("Home Score"));
    sleHomeSc = new JTextField();
    sleHomeSc.setToolTipText("Goals scored by home team");
    northPane.add(sleHomeSc);

    northPane.add(new JLabel("Visitor Score"));
    sleVisSc = new JTextField();
    sleVisSc.setToolTipText("Goals scored by visiting team");
    northPane.add(sleVisSc);

    northPane.add(new JLabel("Referee 1"));
    sleRef1 = new JTextField();
    sleRef1.setToolTipText("Center or senior assistant");
    northPane.add(sleRef1);

    northPane.add(new JLabel("Assistant"));
    sleRef2 = new JTextField();
    sleRef2.setToolTipText("2nd assistant");
    northPane.add(sleRef2);

    northPane.add(new JLabel("Date"));
    sleDate = new JTextField();
    sleDate.setToolTipText("Day the game was played");
    northPane.add(sleDate);

    northPane.add(new JLabel("Time"));
    sleTime = new JTextField();
    sleTime.setToolTipText("Start time for the game");
    northPane.add(sleTime);

    String assoc[] = Associations.getList();
    cbxAssoc = new JComboBox(assoc);
    cbxAssoc.setToolTipText("Game association");
    northPane.add(new JLabel("Association"));
    northPane.add(cbxAssoc);

    northPane.add(new JLabel("Pay"));
    slePay = new JTextField();
    slePay.setToolTipText("Pay for this game");
    northPane.add(slePay);

    northPane.add(new JLabel("Paid"));
    cbxPaid = new JCheckBox();
    cbxPaid.setSelected(false);
    cbxPaid.setToolTipText("Have you been paid?");
    northPane.add(cbxPaid);

    String age[] = Age.getList();
    northPane.add(new JLabel("Age"));
    cbxAge = new JComboBox(age);
    cbxAge.setToolTipText("Age bracket of teams");
    northPane.add(cbxAge);

    northPane.add(new JLabel("Center"));
    cbxCenter = new JCheckBox();
    cbxCenter.setSelected(false);
    cbxCenter.setToolTipText("Were you the center?");
    northPane.add(cbxCenter);

    northPane.add(new JLabel("Indoor"));
    cbxIndoor = new JCheckBox();
    cbxIndoor.setSelected(false);
    cbxIndoor.setToolTipText("Was this an indoor game?");
    northPane.add(cbxIndoor);

    /* Set up and display the frame */
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(southPane,"North");
    getContentPane().add(northPane,"Center");
    setSize(500,175);
  } /* End of createPanel() */

/**
* Handles Frame events such as closing the Frame.
*/
class windowHandler extends WindowAdapter {
  public void windowClosing(WindowEvent event) {
     dispose();
  }
} /* End of windowHandler class definition */

/**
* Handles generic events for objects such as closing an application
* or other events that are commonly triggered from multiple objects.
*/
class actionHandler implements ActionListener{
  public void actionPerformed (ActionEvent event) {
	   Object source = event.getSource();

	   if (source == cbExit) {
        theFrame.dispose();
     } else if (source == cbDone) {
        String [] tempRow = new String[15];

        /* Grab the data so we can save it to the DB */
        tempRow[0] = "NONE";
        tempRow[1] = sleHome.getText();
        tempRow[2] = sleVis.getText();
        tempRow[3] = sleHomeSc.getText();
        tempRow[4] = sleVisSc.getText();
        tempRow[5] = sleRef1.getText();
        tempRow[6] = sleRef2.getText();
        tempRow[7] = sleDate.getText();
        tempRow[8] = sleTime.getText();
        String temp = (String) cbxAssoc.getSelectedItem();
        tempRow[9] = Associations.getKey(temp);
        tempRow[10] = slePay.getText();

        if (cbxPaid.isSelected()) {
           tempRow[11] = "Y";
        } else {
           tempRow[11] = "N";
        }

        temp = (String) cbxAge.getSelectedItem();
        tempRow[12] = Age.getKey(temp);
        if (cbxCenter.isSelected()) {
           tempRow[13] = "R";
        } else {
           tempRow[13] = "A";
        }

        if (cbxIndoor.isSelected()) {
           tempRow[14] = "Y";
        } else {
           tempRow[14] = "N";
        }

        RefFrame.thisFrame.addData(tempRow);
        theFrame.dispose();
     }
  } /* End of actionPerformed method */
} /* End of searchHandler class definition */

}
