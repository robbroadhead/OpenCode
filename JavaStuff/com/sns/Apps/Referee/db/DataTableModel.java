/**
* This model is used to populate the table display for database used
* by the Referee application.
*
* @version 1.0 27-Mar-2001
* @author Rob Broadhead
*/
package com.sns.Apps.Referee.db;

import javax.swing.table.*;
public class DataTableModel extends AbstractTableModel {

  /* Properties for the class */
  /** Database currently modeled by DataTableModel.
  * @serial theDB */
  private Data theDB;
  /** The field version of the filter.
  * @serial theFilter*/
  private DataInfo[] theFilter;
  /** The string version of the filter.
  * @serial filterString */
  private String filterString;

  /**
  * This constructor takes a database that will be loaded into the model.
  * @param Data val The database to tie to this data model.
  */
  public DataTableModel(Data val) {
     theDB = val;
  } /* End of constructor */

  /**
  * This method builds the filter for the data.  The filter is essentially
  * a mapping function to relate filtered rows back to the actual Data file.
  *
  * @param String filterString A string to be passed to the criteriaFind method.
  */
  public void setFilter(String val) {
     filterString = val;
     theFilter = theDB.criteriaFind(val);
  }

  /**
  * This method returns the string version of the current filter
  *
  * @return String A string version of the current filter.
  * @see Data#criteriaFind
  */
  public String getFilter() {
     return filterString;
  }

  /**
  * This method updates the Data file with information from this model.  If it
  * is not called the changes in the graphical table will be lost.
  */
  public void updateData() {
     /* Go through each row and update the information */
     for (int counter=0;counter < theFilter.length;counter++) {
        try {
           theDB.modify(theFilter[counter]);
        } catch (DatabaseException e) {
        }
     }
  }

  /**
  * Override getColumnName to work with the Referee DB.
  *
  * @param int col an integer for the column desired starting with 0.
  * @return String the column name
  */
  public String getColumnName(int col) {
    FieldInfo[] temp;

    temp = theFilter[0].getFields();
    return temp[col + 1].getName();
  }

  /**
  * Translate the filtered row to the actual row.
  *
  * @param int row The row to be translated.
  * @return int The translation value.
  * @exception DatabaseException if the row is not valid.
  */
  public DataInfo getRecord(int row) throws DatabaseException {
    if ((row > theFilter.length) | (row < 0)) {
       throw (new DatabaseException());
    }

    return theFilter[row];
  }

  /**
  * Override getColumnClass to work with Referee DB.
  *
  * @param int col an integer for the column desired starting with 0.
  * @return Class The class of the objects in the column.
  */
  public Class getColumnClass(int col) {
    FieldInfo[] temp;

    temp = theFilter[0].getFields();
    return temp[col + 1].getClass();
  }

  /**
  * Override getValueAt to return the value of the row and column specified.
  *
  * @param int row an integer for the row the value is at.
  * @param int col an integer for the column the value is at.
  * @return Object The object at the given row and column.
  */
  public Object getValueAt(int row, int col) {
    Object retVal;
    String[] tempRow;

    retVal = null;
    try {
      tempRow = theFilter[row].getValues();
      retVal = tempRow[col + 1];
    } catch (Exception e) {
      return retVal;
    }

    return retVal;
  }

  /**
  * This method is overwritten to return the number of rows in the current
  * data set.
  *
  * @return int The number of rows in the set.
  */
  public int getRowCount() {
    return theFilter.length;
  }

  /**
  * This method is overwritten to return the number of columns within
  * the data structure.
  *
  * @return int number of columns in structure
  */
  public int getColumnCount() {
    FieldInfo[] temp;

    temp = theDB.getFieldInfo();
    return temp.length - 1;
  }
} /* End of DataTableModel class definition */
