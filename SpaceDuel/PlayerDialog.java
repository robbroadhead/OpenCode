
//Title:        Space Duel Version 1.0
//Version:      
//Copyright:    Copyright (c) 1998
//Author:       Rob Broadhead
//Company:      Sleepless Nights Software
//Description:  A strategy game of space combat.

package Duel;

import java.awt.*;
import com.sun.java.swing.*;
import Duel.Display.*;
import jclass.bwt.*;

public class PlayerDialog extends JDialog {
   JPanel panel1 = new JPanel();
   jclass.bwt.JCTabManager jCTabManager1 = new jclass.bwt.JCTabManager();
   BorderLayout borderLayout1 = new BorderLayout();
   Duel.Display.PlayerDisplay playerDisplayVal = new Duel.Display.PlayerDisplay();
   Duel.Display.ShipDisplay shipDisplay1 = new Duel.Display.ShipDisplay();

   
   public PlayerDialog(Frame frame, String title, boolean modal) {
      super(frame, title, modal);
      try  {
         jbInit();
         pack();
      }
      catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   
   public PlayerDialog() {
      this(null, "", false);
   }

   void jbInit() throws Exception {
      panel1.setLayout(borderLayout1);
      jCTabManager1.setFirstVisibleTab(1);
      jCTabManager1.setTabLabels(jclass.util.JCUtilConverter.toStringList(new String("Player\nShips"), '\n'));
      jCTabManager1.setTabStretch(true);
      getContentPane().add(panel1);
      panel1.add(jCTabManager1, BorderLayout.CENTER);
      jCTabManager1.add(playerDisplayVal, BorderLayout.NORTH);
      jCTabManager1.add(shipDisplay1, BorderLayout.WEST);
   }
}

                
