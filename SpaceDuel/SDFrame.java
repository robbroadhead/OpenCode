
//Title:      Space Duel Version 1.0
//Version:
//Copyright:  Copyright (c) 1998
//Author:     Rob Broadhead
//Company:    Sleepless Nights Software
//Description:A strategy game of space combat.
package Duel;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import com.sun.java.swing.*;
import snsBase.Stats;
import snsBase.Capability;
import Duel.Play.*;
import Duel.Display.*;
import borland.jbcl.layout.*;


public class SDFrame extends JFrame {

   //Construct the frame
   BorderLayout borderLayout1 = new BorderLayout();
   JMenuBar menuBar1 = new JMenuBar();
   JMenu menuFile = new JMenu();
   JMenuItem menuFileExit = new JMenuItem();
   JMenu menuHelp = new JMenu();
   JMenuItem menuHelpAbout = new JMenuItem();
   JToolBar toolBar = new JToolBar();
   JButton jButton1 = new JButton();
   JButton jButton2 = new JButton();
   JButton jButton3 = new JButton();
   ImageIcon image1;
   ImageIcon image2;
   ImageIcon image3;
   JMenu MenuTest = new JMenu();
   JMenuItem MenuTestStats = new JMenuItem();
  JMenuItem menuFileNewPlayer = new JMenuItem();
  JMenuItem menuFileLoadPlayer = new JMenuItem();
  JMenuItem menuFileStartDuel = new JMenuItem();
  JMenuItem menuHelpRules = new JMenuItem();
  JMenuItem menuHelpStrategy = new JMenuItem();
  JMenu menuUtility = new JMenu();
  JMenuItem menuUtilRepair = new JMenuItem();
  JMenuItem menuUtilBuyShip = new JMenuItem();
  JMenuItem menuUtilWeap = new JMenuItem();
   JPanel jPanel1 = new JPanel();
   Duel.Display.WeaponDisplay weaponDisplayTest = new Duel.Display.WeaponDisplay();
   Duel.Display.PlayerDisplay playerDisplayTest = new Duel.Display.PlayerDisplay();
   JTextArea testText = new JTextArea();
   Duel.Display.ShipDisplay shipDisplayTest = new Duel.Display.ShipDisplay();
   FlowLayout flowLayout1 = new FlowLayout();
   
   public SDFrame() {
      enableEvents(AWTEvent.WINDOW_EVENT_MASK);
      try  {
         jbInit();
         jButton1.setIcon(image1);
         jButton2.setIcon(image2);
         jButton3.setIcon(image3);
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }
//Component initialization
   
   private void jbInit() throws Exception  {
      image1 = new ImageIcon(getClass().getResource("openFile.gif"));
      image2 = new ImageIcon(getClass().getResource("closeFile.gif"));
      image3 = new ImageIcon(getClass().getResource("help.gif"));
      this.getContentPane().setLayout(borderLayout1);
      this.setSize(new Dimension(800, 600));
      this.setTitle("Space Duel Version 1.0");
      MenuTest.setText("Test");
      MenuTestStats.setText("Stats");
    MenuTestStats.setText("Test");
    menuFileNewPlayer.setText("New Player");
    menuFileLoadPlayer.setText("Load Player");
    menuFileStartDuel.setText("Start Duel");
    menuHelpRules.setText("Rules");
    menuHelpStrategy.setText("Strategy");
    menuUtility.setText("Utility");
    menuUtilBuyShip.setText("Buy Ships");
    menuUtilRepair.setText("Repair Dock");
    menuUtilWeap.setText("Weapons & Ammo");
      jPanel1.setBackground(Color.white);
      testText.setLineWrap(true);
      testText.setColumns(15);
      testText.setRows(15);
      testText.setMargin(new Insets(100, 100, 100, 100));
      jPanel1.setLayout(flowLayout1);
      MenuTestStats.addActionListener(new SDFrame_MenuTestStats_actionAdapter(this));
      menuFile.setText("File");
      menuFileExit.setText("Exit");
      menuFileExit.addActionListener(new SDFrame_menuFileExit_ActionAdapter(this));
      menuHelp.setText("Help");
      menuHelpAbout.setText("About");
      menuHelpAbout.addActionListener(new SDFrame_menuHelpAbout_ActionAdapter(this));
      jButton1.setToolTipText("Open File");
      jButton2.setToolTipText("Close File");
      jButton3.setToolTipText("Help");
      toolBar.add(jButton1);
      toolBar.add(jButton2);
      toolBar.add(jButton3);
      this.getContentPane().add(jPanel1, BorderLayout.CENTER);
      jPanel1.add(weaponDisplayTest, null);
      jPanel1.add(playerDisplayTest, null);
      jPanel1.add(shipDisplayTest, null);
      jPanel1.add(testText, null);
      menuFile.add(menuFileNewPlayer);
    menuFile.add(menuFileLoadPlayer);
    menuFile.add(menuFileStartDuel);
    menuFile.add(menuFileExit);
      menuHelp.add(menuHelpRules);
    menuHelp.add(menuHelpStrategy);
    menuHelp.add(menuHelpAbout);
      menuBar1.add(menuFile);
      menuBar1.add(menuHelp);
    menuBar1.add(menuUtility);
      menuBar1.add(MenuTest);
      this.setJMenuBar(menuBar1);
      this.getContentPane().add(toolBar, BorderLayout.NORTH);
      MenuTest.add(MenuTestStats);
    menuUtility.add(menuUtilBuyShip);
    menuUtility.add(menuUtilWeap);
    menuUtility.add(menuUtilRepair);
   }
//File | Exit action performed
   
   public void fileExit_actionPerformed(ActionEvent e) {
      System.exit(0);
   }
//Help | About action performed

   public void helpAbout_actionPerformed(ActionEvent e) {
      SDFrame_AboutBox dlg = new SDFrame_AboutBox(this);
      Dimension dlgSize = dlg.getPreferredSize();
      Dimension frmSize = getSize();
      Point loc = getLocation();
      dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
      dlg.setModal(true);
      dlg.show();
   }
//Overriden so we can exit on System Close
   
   protected void processWindowEvent(WindowEvent e) {
      super.processWindowEvent(e);
      if (e.getID() == WindowEvent.WINDOW_CLOSING) {
         fileExit_actionPerformed(null);
      }
   }

   void MenuTestStats_actionPerformed(ActionEvent e) {
      /* Declare the variables to be used */
      Player testVal;
      Weapon weapTest;
      Ship shipTest;

      // Create a stats class
      testVal = new Player("Dude");
      weapTest=new Weapon(2);
      shipTest = new Ship(1,"Robs ship");

      testVal.IncStat(1,1);
      playerDisplayTest.setVal(testVal);
      Rules.SetShipStats(shipTest);
      Rules.SetWeaponStats(weapTest);
      weaponDisplayTest.setVal(weapTest);
      shipDisplayTest.setVal(shipTest);

      // Delete the file we'll use
      testText.append("Results-->" + testVal + "\n");
      testText.append("Results-->" + weapTest + "\n");
      testText.append("Results-->" + shipTest + "\n");
   }
}

class SDFrame_menuFileExit_ActionAdapter implements ActionListener{
   SDFrame adaptee;

   
   SDFrame_menuFileExit_ActionAdapter(SDFrame adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      adaptee.fileExit_actionPerformed(e);
   }
}

class SDFrame_menuHelpAbout_ActionAdapter implements ActionListener{
   SDFrame adaptee;

   
   SDFrame_menuHelpAbout_ActionAdapter(SDFrame adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      adaptee.helpAbout_actionPerformed(e);
   }
}

class SDFrame_MenuTestStats_actionAdapter implements java.awt.event.ActionListener{
   SDFrame adaptee;

   
   SDFrame_MenuTestStats_actionAdapter(SDFrame adaptee) {
      this.adaptee = adaptee;
   }

   public void actionPerformed(ActionEvent e) {
      adaptee.MenuTestStats_actionPerformed(e);
   }
}

 