package com.sns.palm.apps.defect;

import com.sns.palm.base.Reportable;
import com.sns.palm.util.Utility;

/**
 * <p>Title: MIDP Defect Log</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sleepless Nights Software</p>
 * @author Rob Broadhead
 * @version 1.0
 */
public class Application extends Reportable {

  public String name;
  public String target;

  public Application() {
    super();

    name="No App Name";
    target="No specific target";
    rsName = "AppNames";
    recordSize = 65 + BASESIZE;
  }

  public String shortDisplay() {
    return name.trim();
  }

  public String Display() {
    return name.trim() + "-->" + target.trim();
  }

  public void parseStream(byte[] tempIn) {
    /* Declare the variables to be used */
    byte textBytes[] = new byte[30];
    int x = BASESIZE;

    for (int count = 0;count < 30;count++) {
       textBytes[count] = tempIn[count + x];
    }
    name = Utility.ByteToStr(textBytes);

    for (int count = 0;count < 30;count++) {
       textBytes[count] = tempIn[count + 30 + x];
    }
    target = Utility.ByteToStr(textBytes);
  }

  public boolean isValid() {
    return (!name.equals(""));
  }

  /** Convert the class to a byte array */
  public byte[] toByteArray(byte[] output) {
    byte tempOut[];
    int x = BASESIZE;

    name = Utility.padString(name,30);
    tempOut = Utility.StrToByte(name);
    for (int i=0;i<30;i++) {
      output[i+x] = tempOut[i];
    }
    target = Utility.padString(target,30);
    tempOut = Utility.StrToByte(target);
    for (int i=0;i<30;i++) {
      output[i+30 + x] = tempOut[i];
    }

    return output;
  }
}