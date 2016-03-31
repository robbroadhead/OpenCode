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
* The frame for displaying information about a Ref
*
* @see com.sns.Apps.Referee.db.Data
* @version 1.0 30-Apr-2001
* @author Rob Broadhead
*/
public class NewRefFrame extends JFrame {
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

   private JTextField sleFirst,sleLast,slePhone,sleBorn,sleSecPhone,sleNotes,sleEmail;
   private JComboBox cbxLine,cbxCenter,cbxGrade,cbxAssoc;
   private JCheckBox chkActive,chkIndoor;
   private NewRefFrame theFrame;

   public NewRefFrame() {
      /* Give the frame a title */
      super("Add Ref Data");

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
    String age[] = Age.getList();

    /* Set up and display the frames */
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
    northPane.add(new JLabel("Last Name"));
    sleLast = new JTextField();
    sleLast.setToolTipText("Referee's last name");
    northPane.add(sleLast);

    northPane.add(new JLabel("First Name"));
    sleFirst = new JTextField();
    sleFirst.setToolTipText("Referee's first name");
    northPane.add(sleFirst);

    northPane.add(new JLabel("Phone"));
    slePhone = new JTextField();
    slePhone.setToolTipText("Primary phone for the referee");
    northPane.add(slePhone);

    northPane.add(new JLabel("Birth Date"));
    sleBorn = new JTextField();
    sleBorn.setToolTipText("Date the referee was born");
    northPane.add(sleBorn);

    northPane.add(new JLabel("Top Center"));
    cbxCenter = new JComboBox(age);
    cbxCenter.setToolTipText("Highest level to center");
    northPane.add(cbxCenter);

    northPane.add(new JLabel("Top Line"));
    cbxLine = new JComboBox(age);
    cbxLine.setToolTipText("Highest level to assist");
    northPane.add(cbxLine);

    northPane.add(new JLabel("Grade"));
    String grade[] = RefGrade.getList();
    cbxGrade = new JComboBox(grade);
    cbxGrade.setToolTipText("Current grade of the referee");
    northPane.add(cbxGrade);

    northPane.add(new JLabel("Indoor"));
    chkIndoor = new JCheckBox();
    chkIndoor.setToolTipText("Is referee ok for indoor?");
    northPane.add(chkIndoor);

    northPane.add(new JLabel("Active"));
    chkActive = new JCheckBox();
    chkActive.setToolTipText("Is referee still active?");
    northPane.add(chkActive);

    String assoc[] = Associations.getList();
    cbxAssoc = new JComboBox(assoc);
    cbxAssoc.setToolTipText("Referee association");
    northPane.add(new JLabel("Association"));
    northPane.add(cbxAssoc);

    northPane.add(new JLabel("2nd Phone"));
    sleSecPhone = new JTextField();
    sleSecPhone.setToolTipText("Secondary phone number");
    northPane.add(sleSecPhone);

    northPane.add(new JLabel("Email"));
    sleEmail = new JTextField();
    sleEmail.setToolTipText("Referee Email address");
    northPane.add(sleEmail);

    northPane.add(new JLabel("Notes"));
    sleNotes = new JTextField();
    sleNotes.setToolTipText("Notes about the referee");
    northPane.add(sleNotes);

    /* Set up and display the frame */
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(southPane,"North");
    getContentPane().add(northPane,"Center");
    setSize(450,200);
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
        String [] tempRow = new String[14];

        /* Grab the data so we can save it to the DB */
        tempRow[0] = "NONE";
        tempRow[1] = sleLast.getText();
        tempRow[2] = sleFirst.getText();
        tempRow[3] = slePhone.getText();
        tempRow[4] = sleBorn.getText();
        String temp = (String) cbxCenter.getSelectedItem();
        tempRow[5] = Age.getKey(temp);
        temp = (String) cbxLine.getSelectedItem();
        tempRow[6] = Age.getKey(temp);
        temp = (String) cbxGrade.getSelectedItem();
        tempRow[7] = RefGrade.getKey(temp);

        if (chkIndoor.isSelected()) {
           tempRow[8] = "Y";
        } else {
           tempRow[8] = "N";
        }

        if (chkActive.isSelected()) {
           tempRow[9] = "Y";
        } else {
           tempRow[9] = "N";
        }

        temp = (String) cbxAssoc.getSelectedItem();
        tempRow[10] = Associations.getKey(temp);
        tempRow[11] = sleSecPhone.getText();
        tempRow[12] = sleEmail.getText();
        tempRow[13] = sleNotes.getText();

        RefList.thisFrame.addData(tempRow);
        theFrame.dispose();
     }
  } /* End of actionPerformed method */
} /* End of searchHandler class definition */

}
