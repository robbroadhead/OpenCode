/*******************************************************************
 ******************************************************************/
package com.sns.ui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
* This is a toolbar for use with data maintenance frames.  It incluldes buttons
* with images for close,add,clear,remove,and save functions.  The event listener
* has already been set up but it requires that any frames that use this implement
* the SNSFrameInterface interface.
*
* @version 1.0 30-Sep-2000
* @author Rob Broadhead
* @see SNSFrameInterface
*/
public class SNSToolBar extends JToolBar {
   /** The controls and variables used by the frame */
   JButton cbSave;
   JButton cbRemove;
   JButton cbAdd;
   JButton cbClose;
   JButton cbClear;

   /**
   * This calls super() and the loadControls method.
   */
   public SNSToolBar() {
      super();

      loadControls();
   }

   /**
   * This creates the buttons, assign too tips, assigns images, sets the action
   * listeners and adds the buttons to the toolbar.
   */
   public void loadControls() {
      /* Set up the button panel */
      ImageIcon imgSave = new ImageIcon(getClass().getResource("save.gif"));
      cbSave = new JButton(imgSave);
      cbSave.setToolTipText("Save changes");
      ImageIcon imgRemove = new ImageIcon(getClass().getResource("cut.gif"));
      cbRemove = new JButton(imgRemove);
      cbRemove.setToolTipText("Remove selected record");
      ImageIcon imgAdd = new ImageIcon(getClass().getResource("new.gif"));
      cbAdd = new JButton(imgAdd);
      cbAdd.setToolTipText("Add a new record");
      ImageIcon imgClear = new ImageIcon(getClass().getResource("document.gif"));
      cbClear = new JButton(imgClear);
      cbClear.setToolTipText("Clear the selection and detail fields");
      ImageIcon imgClose = new ImageIcon(getClass().getResource("delete.gif"));
      cbClose = new JButton(imgClose);
      cbClose.setToolTipText("Close the window");
      actionHandler ah = new actionHandler();

      /* Add the listener to the buttons */
      cbClose.addActionListener(ah);
      cbClear.addActionListener(ah);
      cbAdd.addActionListener(ah);
      cbRemove.addActionListener(ah);
      cbSave.addActionListener(ah);

      /* Add the buttons to the toolbar */
      add(cbSave);
      add(cbRemove);
      add(cbAdd);
      add(cbClear);
      add(cbClose);
   }

/**
* This handles the events generated by button clicks on the SNStoolbar.
*/
class actionHandler implements ActionListener{
   public void actionPerformed (ActionEvent event) {
      Component source = (Component) event.getSource();
      Component temp;

      /* Get a reference to the frame*/
      temp = source.getParent();
      while (!(temp instanceof SNSBaseFrame)) {
         temp = temp.getParent();
      }
      SNSBaseFrame tmp;
      tmp = (SNSBaseFrame) temp;
      if (source == cbClose) {
         tmp.close();
      } else if (source == cbSave) {
         tmp.saveChanges();
      } else if (source == cbClear) {
         tmp.clear();
      } else if (source == cbAdd) {
         tmp.addItem();
      } else if (source == cbRemove) {
         tmp.deleteItem();
      }
   } /* End of actionPerformed method */
} /* End of searchHandler class definition */

}
