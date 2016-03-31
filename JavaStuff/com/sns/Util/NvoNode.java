/**
  NvoNode Class
  Written by: Rob Broadhead
  10-26-1998
  $Id
  */

package com.sns.Util;


public class NvoNode {

  public NvoNode(Object theVal) {
    value_A = theVal;
    prev_A = null;
    next_A = null;
  } // End of NvoNode(Object).

  public NvoNode() {
    value_A = null;
    prev_A = null;
    next_A = null;
  } // End of NvoNode().

  public Object GetValue() {
    return value_A;
  }

  public void SetValue(Object theVal) {
    value_A=theVal;
  }

  public NvoNode Next() {
    return next_A;
  }

  public NvoNode Prev() {
    return prev_A;
  }

  public void SetNext(NvoNode theVal) {
    next_A = theVal;
  }

  public void SetPrev(NvoNode theVal) {
    prev_A = theVal;
  }

  public void Append(NvoNode theNode) {
    if (next_A!=null) {
      next_A.SetPrev(theNode);
      theNode.SetNext(next_A);
    }

    next_A = theNode;
    theNode.SetPrev(this);
  } // End of Append(NvoNode).

  public void Remove() {
    if (prev_A != null) {
      prev_A.SetNext(next_A);
    }

    if (next_A != null) {
      next_A.SetPrev(prev_A);
    }

    value_A = null;
  } // End of Remove().

  /* Attributes for the class */
  private NvoNode prev_A;
  private NvoNode next_A;
  private Object value_A;
}
