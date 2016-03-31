/**
Address Class
Written by: Rob Broadhead
            6-18-1997
$Id
*/

package  com.sns.Contact;

import java.io.*;
import java.util.*;

public class Address implements Serializable
{
  public Address(String a1,String a2,String c,String s,int z5,int z4,Phone p,Phone ap)
  {
		addr1_A=a1;
		addr2_A=a2;
		city_A=c;
		state_A=s;
		zip5_A=z5;
		zip4_A=z4;
		phone_A=p;
		altPhone_A=ap;
  }

	public Address(String a1,String a2,String c,String s,String z,String p,String ap)
	{
		addr1_A=a1;
		addr2_A=a2;
		city_A=c;
		state_A=s;
     if (z.length()>4) {
			setItem(5,z);
     }
		zip4_A=0;
		phone_A=new Phone(p);
		altPhone_A=new Phone(ap);
  }

  public Address() {
         Hashtable temp=new Hashtable();
		phone_A=new Phone();
		altPhone_A=new Phone();
  }

  public void print()
  {
    System.out.println("-------------------------");
    System.out.println(addr1_A);
    if (!(addr2_A.equals(""))) {
      System.out.println(addr2_A);
    }
    System.out.println(city_A + ", " + state_A);
    if (zip4_A==0)
      {
        System.out.println(zip5_A);
      }
    else
      {
        System.out.println(zip5_A + "-" + zip4_A);
      }
    phone_A.print();
    altPhone_A.print();
    System.out.println("-------------------------");
  }

  public void writeData(DataOutput out) throws IOException
  {
    out.writeUTF(addr1_A);
    out.writeUTF(addr2_A);
    out.writeUTF(city_A);
    out.writeUTF(state_A);
    out.writeInt(zip5_A);
    out.writeInt(zip4_A);
    phone_A.writeData(out);
    altPhone_A.writeData(out);
  }

  public void readData(DataInput in) throws IOException
  {
    addr1_A=in.readUTF();
    addr2_A=in.readUTF();
    city_A=in.readUTF();
    state_A=in.readUTF();
    zip5_A=in.readInt();
    zip4_A=in.readInt();
    phone_A.readData(in);
    altPhone_A.readData(in);
  }

  public void setItem(int theItem, String theVal) {
    /* 1-addr1_A
       2-Addr2_A
       3-City_A
       4-State_A
       5-Zip
       6-Phone
       7-Alt Phone */
    String retVal,temp;
    Integer temp2;

    switch(theItem) {
    case 1:
      addr1_A=theVal;
      break;
    case 2:
      addr2_A=theVal;
      break;
    case 3:
      city_A=theVal;
      break;
    case 4:
      state_A=theVal;
      break;
    case 5:
			if (theVal.length()>5) {
				temp=theVal.substring(0,5);
				temp2=new Integer(temp);
           zip5_A=temp2.intValue();
				temp=theVal.substring(6);
				temp2=new Integer(temp);
				zip4_A=temp2.intValue();
        } else {
				temp2=new Integer(theVal);
				zip5_A=temp2.intValue();
        }
      break;
    case 6:
      phone_A=new Phone(theVal);
      break;
    case 7:
      altPhone_A=new Phone(theVal);
      break;
    }
  } /* End of setItem(int,String) */

  public String getItem(int theItem) {
    /* 1-addr1_A
       2-Addr2_A
       3-City_A
       4-State_A
       5-Zip
       6-Phone
       7-Alt Phone */
    String retVal=new String();

    switch(theItem) {
    case 1:
      retVal= addr1_A;
      break;
    case 2:
      retVal= addr2_A;
      break;
    case 3:
      retVal= city_A;
      break;
    case 4:
      retVal= state_A;
      break;
    case 5:
      if (zip4_A==0) {
      	retVal = zip5_A +"";
      } else {
      	retVal= zip5_A + "-" + zip4_A;
      }
      break;
    case 6:
      retVal= phone_A.toString();
      break;
    case 7:
      retVal= altPhone_A.toString();
      break;
    }

    return retVal;
  } /* End of getItem(int) */

  public Address copy() {
    Address retVal=new Address();

    retVal.setItem(1,getItem(1));
    retVal.setItem(2,getItem(2));
    retVal.setItem(3,getItem(3));
    retVal.setItem(4,getItem(4));
    retVal.setItem(5,getItem(5));
    retVal.setItem(6,getItem(6));
    retVal.setItem(7,getItem(7));

    return retVal;
  }

  public boolean equals(Address theVal) {
    String temp=Integer.toString(zip5_A);

    temp=temp + Integer.toString(zip4_A);
    return ((addr1_A.equals(getItem(1))) &&
	    (addr2_A.equals(getItem(2))) &&
	    (city_A.equals(getItem(3))) &&
	    (state_A.equals(getItem(4))) &&
	    (temp.equals(getItem(5))));
  } /* End of equals(Address) */

  /* Attributes for the class */
  private String addr1_A;
  private String addr2_A;
  private String city_A;
  private String state_A;
  private int zip5_A;
  private int zip4_A;
  private Phone phone_A;
  private Phone altPhone_A;

} /* End of Address Class */

