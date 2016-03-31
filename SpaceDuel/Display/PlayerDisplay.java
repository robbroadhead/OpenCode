//Title:       Player Display
//Version:     1.0
//Copyright:   SNS 1999
//Author:      Rob Broadhead
//Company:     Sleepless Nights Software
//Description: Display panel for the Player class.


package  Duel.Display;

import java.awt.*;
import java.awt.event.*;
import borland.jbcl.layout.*;
import borland.jbcl.control.*;
import borland.jbcl.view.*;
import borland.jbcl.util.BlackBox;
import Duel.Play.Player;
import com.sun.java.swing.*;

public class PlayerDisplay extends BeanPanel implements BlackBox{
  BevelPanel bevelPanel1 = new BevelPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  XYLayout xYLayout1 = new XYLayout();

  public PlayerDisplay() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception{
    bevelPanel1.setLayout(xYLayout1);
    this.setLayout(borderLayout1);
    bevelPanel1.setBevelInner(BevelPanel.FLAT);
    jLabel1.setText("Name:");
    jLabel1.setFont(new Font("TimesRoman", 1, 12));
      sleName.setToolTipText("The Player\'s Name");
      jLabel2.setText("Rank:");
    jLabel2.setFont(new Font("TimesRoman", 1, 12));
      sleRank.setForeground(Color.red);
      sleRank.setToolTipText("Player\'s Current Rank");
      sleRank.setEditable(false);
      jLabel3.setText("Funds:");
    jLabel3.setFont(new Font("TimesRoman", 1, 12));
      jLabel4.setText("Earnings:");
    jLabel4.setFont(new Font("TimesRoman", 1, 12));
      jLabel5.setText("W/L/T:");
    jLabel5.setFont(new Font("TimesRoman", 1, 12));
    jLabel5.setToolTipText("Wins/Losses/Ties");
      jLabel8.setText("Kills:");
    jLabel8.setFont(new Font("TimesRoman", 1, 12));
      jLabel9.setText("Deaths:");
    jLabel9.setFont(new Font("TimesRoman", 1, 12));
      sleFunds.setForeground(Color.red);
      sleFunds.setEditable(false);
      sleEarnings.setForeground(Color.red);
      sleEarnings.setEditable(false);
      sleWLT.setEnabled(false);
    sleWLT.setForeground(Color.red);
    sleWLT.setEditable(false);
      sleKills.setForeground(Color.red);
      sleKills.setEditable(false);
    sleDeaths.setForeground(Color.red);
      sleDeaths.setEditable(false);
      this.add(bevelPanel1, BorderLayout.CENTER);
      bevelPanel1.add(jLabel1, new XYConstraints(1, 1, -1, -1));
      bevelPanel1.add(sleName, new XYConstraints(40, 1, 143, -1));
    bevelPanel1.add(jLabel2, new XYConstraints(195, 1, -1, -1));
      bevelPanel1.add(sleRank, new XYConstraints(230, 1, 95, -1));
    bevelPanel1.add(jLabel3, new XYConstraints(1, 25, -1, -1));
    bevelPanel1.add(jLabel4, new XYConstraints(167, 25, -1, -1));
    bevelPanel1.add(jLabel5, new XYConstraints(1, 50, -1, -1));
    bevelPanel1.add(jLabel8, new XYConstraints(148, 50, -1, -1));
    bevelPanel1.add(jLabel9, new XYConstraints(235, 50, -1, -1));
    bevelPanel1.add(sleFunds, new XYConstraints(40, 24, 102, -1));
    bevelPanel1.add(sleEarnings, new XYConstraints(223, 24, 102, -1));
    bevelPanel1.add(sleWLT, new XYConstraints(40, 48, 103, -1));
    bevelPanel1.add(sleKills, new XYConstraints(180, 48, 50, -1));
    bevelPanel1.add(sleDeaths, new XYConstraints(275, 48, 50, -1));
  }


  public void setVal(Player s) {
    /* Declare the variables we'll use */
    String statString;

    /* Store the object */
    val_A=s;

    /* Set the display fields */
    sleName.setText(s.GetName());
    sleRank.setText(s.GetRankString());
    statString=s.GetStat(0) + "/" + s.GetStat(1) + "/" +s.GetStat(2);
    sleWLT.setText(statString);
    statString = "" + s.GetStat(3);
    sleKills.setText(statString);
    statString = "" + s.GetStat(4);
    sleDeaths.setText(statString);
    statString = "" + s.GetStat(5);
    sleFunds.setText(statString);
    statString = "" + s.GetStat(6);
    sleEarnings.setText(statString);
  }


  public Player getVal(){
    /* Not much the player can change */
    val_A.setName(sleName.getText());
    return val_A;
  }


/* Properties for the class */
   private Player val_A;
   JLabel jLabel1 = new JLabel();
   JTextField sleName = new JTextField();
   JLabel jLabel2 = new JLabel();
   JTextField sleRank = new JTextField();
   JLabel jLabel3 = new JLabel();
   JLabel jLabel4 = new JLabel();
   JLabel jLabel5 = new JLabel();
   JLabel jLabel8 = new JLabel();
   JLabel jLabel9 = new JLabel();
   JTextField sleFunds = new JTextField();
   JTextField sleEarnings = new JTextField();
   JTextField sleWLT = new JTextField();
   JTextField sleKills = new JTextField();
   JTextField sleDeaths = new JTextField();
}
 