/**
snsObject Class
Written by: Rob Broadhead
            10-27-1998
$Id
*/

package com.sns.Util;

import java.io.*;

public interface SnsObject {
  public String toString();
  public void writeData(DataOutput out) throws IOException;
  public void readData(DataInput in) throws IOException;

} // End of interface SnsObject
