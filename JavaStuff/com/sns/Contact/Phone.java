/*
Phone Class
Version 2.0
Written by:Rob Broadhead
$Id
*/
package  com.sns.Contact;

import java.io.*;
import com.sns.Util.TextIO;

public class Phone implements Serializable
{
  private   int main_A = 0;
  private   int prefix_A = 0;
  private   int extension_A = 0;
  private   int areaCode_A = 0;

  public Phone (String theVal) {
		String temp;
		Integer hold;
		int posA,posB;

     // If its null set the number to 000-000-0000
     if (theVal.equals("")) {
     	theVal=new String("000-000-0000");
     }

		// Get the area code.
		posA = theVal.indexOf("-");
		hold = new Integer(theVal.substring(0,posA));
		areaCode_A=hold.intValue();

		// Get the prefix.
		posB = theVal.indexOf("-",posA + 1);
		hold = new Integer(theVal.substring(posA + 1,posB));
		prefix_A=hold.intValue();
		posA = posB + 1;

		// Get the main number.
		posB = theVal.indexOf("-",posA);
		if (posB!=-1) {
			hold = new Integer(theVal.substring(posA,posB));
			main_A=hold.intValue();
			posA = posB + 1;

			// Get the extension.
			posB = theVal.indexOf("-",posA);
			hold = new Integer(theVal.substring(posA,posB));
			extension_A=hold.intValue();
		} else {
			extension_A=0;
			hold = new Integer(theVal.substring(posA));
			main_A=hold.intValue();
		}
  }

  public Phone ( int ac, int prefix, int main,  int x) {
    areaCode_A = ac;
    prefix_A = prefix;
    main_A = main;
    extension_A = x;
  }

  public Phone () {}

  public void print () {
  	if (prefix_A==0) {
     	System.out.println("000-000-0000");
     }
     else if (extension_A==0) {
			System.out.println("(" + areaCode_A + ")" + " " + prefix_A + "-" + main_A );
		}
		else
		{
			System.out.println("(" + areaCode_A + ")" + " " + prefix_A + "-" + main_A + " x" + extension_A);
		}
  }

  public String toString()
  {
  	if (prefix_A==0) {
     	return "000-000-0000";
     }
    if (extension_A==0)
      {
	     return areaCode_A + "-" + prefix_A + "-" + main_A ;
      }
    else
      {
	     return areaCode_A + "-" + prefix_A + "-" + main_A + " x" + extension_A;
      }

  }

  public int getIntPhone() {
    return prefix_A * 10000 + main_A;
  } /* End of getIntPhone() */

  public int getIntAreaCode() {
    return areaCode_A;
  } /* End of getIntPhone() */

  public int getIntExtension() {
    return extension_A;
  } /* End of getIntPhone() */

  public void writeData ( DataOutput out) throws IOException {
    out.writeInt(areaCode_A);
    out.writeInt(prefix_A);
    out.writeInt(main_A);
    out.writeInt(extension_A);
  }

  public void readData ( DataInput in) throws IOException {
    areaCode_A = in.readInt();
    prefix_A = in.readInt();
    main_A = in.readInt();
    extension_A = in.readInt();
  }

  public boolean equals (Phone testVal) {
    return ((getIntAreaCode()==testVal.getIntAreaCode()) &&
	(getIntPhone()==testVal.getIntPhone()) &&
	(getIntExtension()==testVal.getIntExtension()));
  }

  public Phone copy () {
    return new Phone(areaCode_A,prefix_A,main_A,extension_A);
  }

  public boolean isValid () {
    return ((areaCode_A<1000) && (prefix_A<1000) && (main_A<10000));
  }

  public void edit (int theVal, int theType) {
    /* theType determines which value to edit */
    /* 1-areaCode
       2-prefix
       3-main
       4-extension */
    switch (theType) {
    case 1:
      areaCode_A=theVal;
      break;
    case 2:
      prefix_A=theVal;
      break;
    case 3:
      main_A=theVal;
      break;
    case 4:
      extension_A=theVal;
      break;
    }
  }

  public void enterPhone () {
    TextIO screen = new TextIO("");
    String tempNum;

    screen.write("Enter Area Code:");
    areaCode_A = screen.inInt();
    screen.write("Enter Phone Number:");
    tempNum = screen.inString();
    /* break up string into main and prefix */

    screen.write("Enter Extension:");
    extension_A = screen.inInt();
  }
}

