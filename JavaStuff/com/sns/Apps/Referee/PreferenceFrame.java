package com.sns.Apps.Referee;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.event.*;
import java.util.Enumeration;
import java.io.IOException;

/**
* The Frame that gives a graphical interface into the preferences class.
*
* @version 1.0 24-May-2001
* @author Rob Broadhead
*/
public class PreferenceFrame extends JFrame {
   /** The controls and variables used by the frame */
   private JButton cbClose;
   private JButton cbSave;
   private JTable tblPreferences;
   private PreferenceFrame me;

   /** The pref property is used to store the preferences which apply to this
   * frame.
   */
   private Preference pref;

   /**
   * This constructor takes a Preference object and uses that to populate a table
   * of keys and values that can be edited by the user and stored back in the given
   * object
   * @param Preference p The Preference to use to populate the values in this frame.
   */
   public PreferenceFrame(Preference p) {
      super("Preferences");

      me = this;
      pref = p;
      loadControls();
   }

   /**
   * This stores changes made in the interface to the Preference object that is
   * linked to this frame.
   */
   private void saveChanges() {
      int count;
      String prefName,prefKey;

      count = tblPreferences.getRowCount();

      /* Walk through the items */
      for (int i=0;i<count;i++ ) {
         prefKey = (String) tblPreferences.getValueAt(i,0);
         prefName = (String) tblPreferences.getValueAt(i,1);

         /* Set the preference value */
         Preference.setPreference(prefKey,prefName);
      }
   }

   /**
   * This is used to create all of the graphic controls that make up this frame and
   * to build the data that populates those controls.
   */
   public void loadControls() {
      int numKeys;
      String colNames[] = new String[2];
      String[][] dataVal;
      Enumeration keys;

      getContentPane().setLayout(new BorderLayout());
      colNames[0] = "Preference Name";
      colNames[1] = "Preference Value";
      keys = pref.listPreferences();
      numKeys = pref.count();

      dataVal = new String[numKeys][2];
      for (int i=0;i<numKeys;i++) {
         dataVal[i][0] = keys.nextElement().toString();
         dataVal[i][1] = Preference.getPreference(dataVal[i][0]);
      }

      tblPreferences = new JTable(dataVal,colNames);
      tblPreferences.setToolTipText("Double click on the value to edit it.");
      tblPreferences.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      ListSelectionModel rowSM = tblPreferences.getSelectionModel();
      rowSM.addListSelectionListener(new tableHandler());

      /* Set up the button panel */
      cbClose = new JButton("Close");
      cbSave = new JButton("Save");
      actionHandler ah = new actionHandler();
      cbClose.addActionListener(ah);
      cbSave.addActionListener(ah);

      JScrollPane pnlScroll = new JScrollPane(tblPreferences);
      pnlScroll.setSize(200,480);
      getContentPane().add(pnlScroll,"Center");
      JToolBar toolBar = new JToolBar();
      toolBar.add(cbClose);
      toolBar.add(cbSave);
      getContentPane().add(toolBar,"North");
   }

   /** This class is just a generic action handler that is used to direct action
   * events to the methods they require.
   */
   class actionHandler implements ActionListener{
      public void actionPerformed (ActionEvent event) {
	       Object source = event.getSource();

         if (source == cbClose) {
            /* Make any changes due to pref changes here */
            try {
               if (Preference.getPreference("Look and feel").equals("Windows")) {
                  UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
               } else if (Preference.getPreference("Look and feel").equals("Motif")) {
                  UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
               } else if (Preference.getPreference("Look and feel").equals("Metal")) {
                  UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
               } else {
                  JOptionPane.showMessageDialog(getContentPane(),"That is not a valid look and feel setting [Windows,Motif,Metal].");
               }
               SwingUtilities.updateComponentTreeUI(me);
            } catch (Exception exc) {
               System.out.println("Error implementing the new interface manager.");
            }

            try {
               pref.savePrefs();
               dispose();
            } catch (IOException e) {
               JOptionPane.showMessageDialog(getContentPane(),"Unable to save the preferences.");
            }
         } else if (source == cbSave) {
            saveChanges();
         }
      } /* End of actionPerformed method */
   } /* End of searchHandler class definition */

class tableHandler implements ListSelectionListener{
   public void valueChanged(ListSelectionEvent e) {
      //ListSelectionModel lsm = (ListSelectionModel)e.getSource();
   }
} /* End of valueChanged method */
} /* End of tableHandler class definition */

