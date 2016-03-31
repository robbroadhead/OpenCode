/*******************************************************************
 ** @(#)$Workfile$
 **
 ** $Author: robbroadhead $
 ** $Revision: 1.1 $ $Date: 2007/12/27 15:23:12 $
 ** 
 ** General description -  
 ** The class for displaying the audit fields from a table.  The data is
 ** presented on a panel and is best suited for addition to a tab pane.
 ** 
 ** See also:
 **  @see {??use for cross-reference to other package, class, etc.}
 ******************************************************************* 
 ** Copyright Notice:
 **   Copyright © 2000 MDL Information Systems, Inc.
 **   All Rights Reserved.
 **
 **   This computer program is protected by copyright law and 
 **   international treaties.  Unauthorized use or distribution of
 **   this program, or any portion of it is strictly prohibited.
 **   Violation may result in severe civil or criminal penalties.
 ******************************************************************
 ** Revision Log:
 **
 ** $Log: AuditPanel.java,v $
 ** Revision 1.1  2007/12/27 15:23:12  robbroadhead
 ** Initial Check-in to new repository.
 **
 ** Revision 1.1  2005/02/28 00:57:19  Rob
 ** Initial Check-in to CVS
 **
 ******************************************************************
 ** $NoKeywords$
 ******************************************************************/

package com.sns.library;

import javax.swing.*;
import java.awt.*;

/**
* The class for displaying the audit fields from a table.  The data is
* presented on a panel and is best suited for addition to a tab pane.
*
* @version 1.0 30-Sep-2000
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
