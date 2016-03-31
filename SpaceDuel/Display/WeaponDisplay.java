//Title:       WeaponDisplay
//Version:     1.0
//Copyright:   1999 Sleepless Nights Software
//Author:      Rob Broadhead
//Company:     Sleepless Nights Software
//Description: Display properties of a Weapon class


package  Duel.Display;

import java.awt.*;
import java.awt.event.*;
import borland.jbcl.layout.*;
import borland.jbcl.control.*;
import borland.jbcl.view.*;
import borland.jbcl.util.BlackBox;
import Duel.Play.Weapon;
import com.sun.java.swing.*;

public class WeaponDisplay extends BeanPanel implements BlackBox{
  BevelPanel bevelPanel1 = new BevelPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  XYLayout xYLayout1 = new XYLayout();

  public WeaponDisplay() {
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
    jLabel2.setText("Class:");
    jLabel2.setFont(new Font("TimesRoman", 1, 12));
    jLabel3.setText("Ammo:");
    jLabel3.setFont(new Font("TimesRoman", 1, 12));
    jLabel4.setText("Structure:");
    jLabel4.setFont(new Font("TimesRoman", 1, 12));
    jLabel5.setText("Range:");
    jLabel5.setFont(new Font("TimesRoman", 1, 12));
    jLabel6.setText("Damage:");
    jLabel6.setFont(new Font("TimesRoman", 1, 12));
    jLabel7.setText("Energy:");
    jLabel7.setFont(new Font("TimesRoman", 1, 12));
    jLabel8.setText("R.O.F.:");
    jLabel8.setFont(new Font("TimesRoman", 1, 12));
    jLabel9.setText("Accuracy:");
    jLabel9.setFont(new Font("TimesRoman", 1, 12));
    sleStructure.setForeground(Color.red);
    sleRange.setForeground(Color.red);
    sleDamage.setForeground(Color.red);
    sleEnergy.setForeground(Color.red);
    sleROF.setForeground(Color.red);
    sleAccuracy.setForeground(Color.red);
    this.add(bevelPanel1, BorderLayout.CENTER);
    bevelPanel1.add(jLabel1, new XYConstraints(1, 1, -1, -1));
    bevelPanel1.add(sleName, new XYConstraints(40, 1, 124, -1));
    bevelPanel1.add(jLabel2, new XYConstraints(170, 1, -1, -1));
    bevelPanel1.add(sleClass, new XYConstraints(207, 1, 80, -1));
    bevelPanel1.add(jLabel3, new XYConstraints(1, 23, -1, -1));
    bevelPanel1.add(sleAmmo, new XYConstraints(40, 23, 80, -1));
    bevelPanel1.add(jLabel4, new XYConstraints(203, 23, -1, -1));
    bevelPanel1.add(jLabel5, new XYConstraints(68, 46, -1, -1));
    bevelPanel1.add(jLabel6, new XYConstraints(211, 46, -1, -1));
    bevelPanel1.add(jLabel7, new XYConstraints(136, 46, -1, -1));
    bevelPanel1.add(jLabel8, new XYConstraints(1, 46, -1, -1));
    bevelPanel1.add(jLabel9, new XYConstraints(124, 23, -1, -1));
    bevelPanel1.add(sleStructure, new XYConstraints(262, 22, 25, -1));
    bevelPanel1.add(sleRange, new XYConstraints(108, 44, 25, -1));
    bevelPanel1.add(sleDamage, new XYConstraints(262, 44, 25, -1));
    bevelPanel1.add(sleEnergy, new XYConstraints(178, 44, 25, -1));
    bevelPanel1.add(sleROF, new XYConstraints(40, 44, 25, -1));
    bevelPanel1.add(sleAccuracy, new XYConstraints(175, 22, 25, -1));
  }


  public void setVal(Weapon s) {
    /* Declare the variables we'll use */
    String statString;

    /* Store the object */
    val_A=s;

    /* Set the display fields */
    sleName.setText(s.GetName());
    sleClass.setText(s.weaponString());
    sleAmmo.setText(s.ammoString());

    statString = "" + s.GetStat(0);
    sleStructure.setText(statString);
    statString = "" + s.GetStat(1);
    sleAccuracy.setText(statString);
    statString = "" + s.GetStat(2);
    sleROF.setText(statString);
    statString = "" + s.GetStat(3);
    sleRange.setText(statString);
    statString = "" + s.GetStat(4);
    sleDamage.setText(statString);
    statString = "" + s.GetStat(5);
    sleEnergy.setText(statString);
  }


  public Weapon getVal(){
    return val_A;
  }

/* Properties for the class */
  private Weapon val_A;
  JLabel jLabel1 = new JLabel();
  JTextField sleName = new JTextField();
  JLabel jLabel2 = new JLabel();
  JTextField sleClass = new JTextField();
  JLabel jLabel3 = new JLabel();
  JTextField sleAmmo = new JTextField();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();
  JTextField sleStructure = new JTextField();
  JTextField sleRange = new JTextField();
  JTextField sleDamage = new JTextField();
  JTextField sleEnergy = new JTextField();
  JTextField sleROF = new JTextField();
  JTextField sleAccuracy = new JTextField();
}
 