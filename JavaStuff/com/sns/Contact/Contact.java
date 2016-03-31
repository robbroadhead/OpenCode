/*
Contact Class
Written by: Rob Broadhead
            6-18-1997
$Id
*/

package  com.sns.Contact;

import java.io.*;

class Contact implements Serializable
{
  /* Create a Contact given a name, address, and type */
  public Contact(Name c, Address a)
  {
    custName_A=c;
    location_A=a;
    contactId_A=c.getLast() + c.getFirst();
  }

  public Contact(String id,Name c, Address a,String e)
  {
    contactId_A=id;
    custName_A=c.copy();
    email_A=e;
    location_A=a.copy();
  }

  /* Create a Contact given a key */
  public Contact(String theKey)
  {
    contactId_A=theKey;
  }

  /* Display the Contact on the terminal. */
  public void print()
  {
        System.out.println(contactId_A);
        custName_A.print();
        location_A.print();
        System.out.println(email_A);
  }

  /* Store the Contact data to disk. */
  public void writeData(DataOutput out) throws IOException
  {
    int count;

    /* Generate the file */
    /* Store the information. */
    out.writeUTF(contactId_A);
    custName_A.writeData(out);
    location_A.writeData(out);
    out.writeUTF(email_A);
  }

  /* Load the object from disk. */
  public void readData(DataInput in) throws IOException
  {
    contactId_A = in.readUTF();
    custName_A.readData(in);
    location_A.readData(in);
    email_A = in.readUTF();
  }

  public boolean equals(Contact theVal) {
  	return contactId_A.equals(theVal.getID());
  }

  public Contact copy() {
  	 return new Contact(contactId_A,custName_A,location_A,email_A);
  }

  public String getID() {return contactId_A;}

  /* Attributes for the class */
  private String contactId_A;
  private Name custName_A;
  private Address location_A;
  private String email_A;
} /* End of Contact Class */

