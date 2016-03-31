/*
Name Class
Written by: Rob Broadhead
            6-18-1997
$Id
*/

package  com.sns.Contact;

import java.io.*;
import com.sns.Util.TextIO;

public class Name implements Serializable
{
  public void enterName()
  {
    TextIO screen=new TextIO("");

    screen.write("Enter first name:");
    first_A = screen.inString();
    screen.write("Enter middle name:");
    middle_A = screen.inString();
    screen.write("Enter last name:");
    last_A = screen.inString();
    screen.write("Enter salutation:");
    salute_A = screen.inString();
  }

  public Name(String f,String m, String l,String s)
  {
    first_A=f;
    middle_A=m;
    last_A=l;
    salute_A=s;
  }

  public Name() {
    first_A="";
    middle_A="";
    last_A="";
    salute_A="";
  }

  public void print()
  {
    if (salute_A.equals(""))
      {
        System.out.println(last_A + ", " + first_A + " " + middle_A );
      }
    else
      {
        System.out.println(salute_A + " " + first_A + " " + middle_A + " " + last_A);
      }
  }

  public void writeData(DataOutput out) throws IOException
  {
    out.writeUTF(first_A);
    out.writeUTF(middle_A);
    out.writeUTF(last_A);
    out.writeUTF(salute_A);
  }

  public void readData(DataInput in) throws IOException
  {
    first_A = in.readUTF();
    middle_A = in.readUTF();
    last_A = in.readUTF();
    salute_A = in.readUTF();
  }

  public String getFirst() { return first_A; }
  public String getMiddle() { return middle_A; }
  public String getLast() { return last_A; }
  public String getSalute() { return salute_A; }

  public Name copy() {
    return new Name(first_A,middle_A,last_A,salute_A);
  }

  public boolean equals(Name theVal) {
    return ((first_A.equals(theVal.getFirst())) &&
	     (middle_A.equals(theVal.getMiddle())) &&
	     (last_A.equals(theVal.getLast())) &&
	     (salute_A.equals(theVal.getSalute())));
  }

  public String initial() {
    String retVal;
    char temp[] = {first_A.charAt(0),middle_A.charAt(0),last_A.charAt(0)};

    retVal=new String(temp);
    return retVal;
  }

  public void setValue(String theVal,int theItem) {
    /* 1-first
       2-middle
       3-last
       4-salute */
    switch (theItem) {
    case 1:
      first_A=theVal;
      break;
    case 2:
      middle_A=theVal;
      break;
    case 3:
      last_A=theVal;
      break;
    case 4:
      salute_A=theVal;
      break;
    }
  }

  /* Attributes for the class */
  private String first_A;
  private String middle_A;
  private String last_A;
  private String salute_A;

} /* End of Name Class */



