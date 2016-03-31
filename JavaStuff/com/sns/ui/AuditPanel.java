/*******************************************************************
 ******************************************************************/

package com.sns.ui;

import javax.swing.*;
import java.awt.*;

/**
* The class for displaying the audit fields from a table.  The data is
* presented on a panel and is best suited for addition to a tab pane.
*
* @version 1.0 23-Nov-2000
* @author Rob Broadhead
*/
public class AuditPanel extends JPanel {
   /** The controls and variables used by the panel */
   /* Details */
   JLabel lblDetails[];
   JTextField txtDetails[];

   /** 
   * The no-args constructor calls the loadControls method.
   */
   public AuditPanel() {
      super();
	
      loadControls();	
   }

   /**
   * This is used to update the values in the textfields of the panel.
   * 
   * @param String a The value to be assigned to the active flag.
   * @param String u The value to be assigned to the update time.
   * @param String c The value to be assigned to the creation date.
   */
   public void setVals(String a, String u,String c) {
		txtDetails[0].setText(a);
		txtDetails[1].setText(c);
		txtDetails[2].setText(u);
   }

   /** 
   * This method is used to clear out the values in the textfields of the
   * panel.  This just passes empty strings to the setVals method.
   */
   public void clear() {
	    setVals("","","");
   }	

   /** 
   * This internal method is used to create the controls on the frame.
   */
   private void loadControls() {
	    int count;
	 
	    lblDetails = new JLabel[3];
	    txtDetails = new JTextField[3];
	 
	    setLayout(new GridLayout(3,2,5,5));
	 
	    lblDetails[0] = new JLabel("Active Indicator",SwingConstants.RIGHT);
      lblDetails[1] = new JLabel("Creation Date",SwingConstants.RIGHT);
	    lblDetails[2] = new JLabel("Update Date",SwingConstants.RIGHT);
	    txtDetails[0] = new JTextField("");
	    txtDetails[1] = new JTextField("");
	    txtDetails[2] = new JTextField("");
	    txtDetails[0].setEditable(false);
	    txtDetails[1].setEditable(false);
	    txtDetails[2].setEditable(false);
	 
	    for (count=0;count < 3;count++) {
         add(lblDetails[count]);
	 	    add(txtDetails[count]);
	    }
   }
}
