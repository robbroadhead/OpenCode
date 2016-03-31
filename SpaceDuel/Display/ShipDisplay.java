//Title:       Ship Display
//Version:     1.0
//Copyright:   SNS 1999
//Author:      Rob Broadhead
//Company:     Sleepless Nights Software
//Description: Display panel for the Ship class.


package  Duel.Display;

import java.awt.*;
import java.awt.event.*;
import borland.jbcl.layout.*;
import borland.jbcl.control.*;
import borland.jbcl.view.*;
import borland.jbcl.util.BlackBox;
import Duel.Play.Ship;
import com.sun.java.swing.*;

public class ShipDisplay extends BeanPanel implements BlackBox{
  BevelPanel bevelPanel1 = new BevelPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  XYLayout xYLayout1 = new XYLayout();

  public ShipDisplay() {
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
      sleName.setToolTipText("The Ship Name");
      jLabel2.setText("Hull:");
    jLabel2.setFont(new Font("TimesRoman", 1, 12));
    sleHull.setForeground(Color.red);
      sleHull.setToolTipText("Ship\'s Current Rank");
      sleHull.setEditable(false);
    sleFShield.setForeground(Color.red);
    sleSShield.setForeground(Color.red);
    sleRShield.setForeground(Color.red);
    slePShield.setForeground(Color.red);
    sleFArmor.setForeground(Color.red);
    sleSArmor.setForeground(Color.red);
    sleRArmor.setForeground(Color.red);
    slePArmor.setForeground(Color.red);
    jLabel6.setText("F:");
    jLabel6.setFont(new Font("TimesRoman", 1, 12));
    jLabel9.setText("S:");
    jLabel9.setFont(new Font("TimesRoman", 1, 12));
    jLabel10.setText("R:");
    jLabel10.setFont(new Font("TimesRoman", 1, 12));
    jLabel11.setText("P:");
    jLabel11.setFont(new Font("TimesRoman", 1, 12));
    jLabel12.setText("F:");
    jLabel12.setFont(new Font("TimesRoman", 1, 12));
    jLabel13.setText("S:");
    jLabel13.setFont(new Font("TimesRoman", 1, 12));
    jLabel14.setText("R:");
    jLabel14.setFont(new Font("TimesRoman", 1, 12));
    jLabel15.setText("P:");
    jLabel15.setFont(new Font("TimesRoman", 1, 12));
      jLabel3.setText("Energy:");
    jLabel3.setFont(new Font("TimesRoman", 1, 12));
      jLabel4.setText("Storage:");
    jLabel4.setFont(new Font("TimesRoman", 1, 12));
      jLabel5.setText("Shields:");
    jLabel5.setFont(new Font("TimesRoman", 1, 12));
    jLabel5.setToolTipText("");
      jLabel8.setText("Armor:");
    jLabel8.setFont(new Font("TimesRoman", 1, 12));
      sleEnergy.setForeground(Color.red);
      sleEnergy.setEditable(false);
      sleStorage.setForeground(Color.red);
      sleStorage.setEditable(false);
      this.add(bevelPanel1, BorderLayout.CENTER);
      bevelPanel1.add(jLabel1, new XYConstraints(1, 1, -1, -1));
      bevelPanel1.add(sleName, new XYConstraints(40, 1, 111, -1));
    bevelPanel1.add(jLabel2, new XYConstraints(155, 1, -1, -1));
      bevelPanel1.add(sleHull, new XYConstraints(182, 1, 40, -1));
    bevelPanel1.add(jLabel3, new XYConstraints(125, 25, -1, -1));
    bevelPanel1.add(jLabel4, new XYConstraints(1, 25, -1, -1));
    bevelPanel1.add(jLabel5, new XYConstraints(1, 46, -1, -1));
    bevelPanel1.add(jLabel8, new XYConstraints(111, 46, -1, -1));
    bevelPanel1.add(sleEnergy, new XYConstraints(172, 23, 50, -1));
    bevelPanel1.add(sleStorage, new XYConstraints(55, 23, 50, -1));

    /* Shield and Armor values */
    bevelPanel1.add(sleFShield, new XYConstraints(62, 46, 40, -1));
    bevelPanel1.add(sleSShield, new XYConstraints(62, 69, 40, -1));
    bevelPanel1.add(sleRShield, new XYConstraints(62, 91, 40, -1));
    bevelPanel1.add(slePShield, new XYConstraints(62, 114, 40, -1));
    bevelPanel1.add(sleFArmor, new XYConstraints(172, 46, 40, -1));
    bevelPanel1.add(sleSArmor, new XYConstraints(172, 69, 40, -1));
    bevelPanel1.add(sleRArmor, new XYConstraints(172, 91, 40, -1));
    bevelPanel1.add(slePArmor, new XYConstraints(172, 114, 40, -1));

    /* All of the labels */
    bevelPanel1.add(jLabel6, new XYConstraints(48, 46, -1, -1));
    bevelPanel1.add(jLabel9, new XYConstraints(48, 69, -1, -1));
    bevelPanel1.add(jLabel10, new XYConstraints(48, 91, -1, -1));
    bevelPanel1.add(jLabel11, new XYConstraints(48, 114, -1, -1));
    bevelPanel1.add(jLabel12, new XYConstraints(158, 46, -1, -1));
    bevelPanel1.add(jLabel13, new XYConstraints(158, 69, -1, -1));
    bevelPanel1.add(jLabel14, new XYConstraints(158, 91, -1, -1));
    bevelPanel1.add(jLabel15, new XYConstraints(158, 114, -1, -1));
  }


  public void setVal(Ship s) {
    /* Declare the variables we'll use */
    String statString;

    /* Store the object */
    val_A=s;

    /* Set the display fields */
    sleName.setText(s.GetName());
    statString= "" + s.getHull();
    sleHull.setText(statString);
    statString= "" + s.getStorage();
    sleStorage.setText(statString);
    statString= "" + s.getEnergy();
    sleEnergy.setText(statString);

    /* Shield values */
    statString = "" + s.getShield('F');
    sleFShield.setText(statString);
    statString = "" + s.getShield('S');
    sleSShield.setText(statString);
    statString = "" + s.getShield('R');
    sleRShield.setText(statString);
    statString = "" + s.getShield('P');
    slePShield.setText(statString);

    /* Armor values */
    statString = "" + s.getArmor('F');
    sleFArmor.setText(statString);
    statString = "" + s.getArmor('S');
    sleSArmor.setText(statString);
    statString = "" + s.getArmor('R');
    sleRArmor.setText(statString);
    statString = "" + s.getArmor('P');
    slePArmor.setText(statString);
  } // End of setVal()


  public Ship getVal(){
    /* Not much the Ship can change */
    val_A.setName(sleName.getText());
    return val_A;
  }


/* Properties for the class */
   private Ship val_A;
   JLabel jLabel1 = new JLabel();
   JTextField sleName = new JTextField();
   JLabel jLabel2 = new JLabel();
   JTextField sleHull = new JTextField();
   JLabel jLabel3 = new JLabel();
   JLabel jLabel4 = new JLabel();
   JLabel jLabel5 = new JLabel();
   JLabel jLabel8 = new JLabel();
   JTextField sleEnergy = new JTextField();
   JTextField sleStorage = new JTextField();
   JTextField sleFShield = new JTextField();
   JTextField sleSShield = new JTextField();
   JTextField sleRShield = new JTextField();
   JTextField slePShield = new JTextField();
   JTextField sleFArmor = new JTextField();
   JTextField sleSArmor = new JTextField();
   JTextField sleRArmor = new JTextField();
   JTextField slePArmor = new JTextField();
   JLabel jLabel6 = new JLabel();
   JLabel jLabel9 = new JLabel();
   JLabel jLabel10 = new JLabel();
   JLabel jLabel11 = new JLabel();
   JLabel jLabel12 = new JLabel();
   JLabel jLabel13 = new JLabel();
   JLabel jLabel14 = new JLabel();
   JLabel jLabel15 = new JLabel();
}
 
