package com.sns.ui;

/**
* This interface is required for all frames that use the MDLToolBar class.
* The events in the toolbar will try to call the methods declared in this
* interface.
*
* @version 1.0 23-Nov-2000
* @author Rob Broadhead
* @see library.SNSToolBar
*/
public interface SNSBaseFrame {
   /** Code for saving changes to data in this method. */
   public void saveChanges();
   /** Code for closing the frame goes here. */
   public void close();
   /** Code for adding items goes here. */
   public void addItem();
   /** Code for deleting items goes here. */
   public void deleteItem();
   /** Code for clearing data entry forms and selections goes here. */
   public void clear();
}
