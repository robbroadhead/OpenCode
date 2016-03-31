// Title:        Ship
// Version:      1.0
// Copyright:    Copyright (c) 1999
// Author:       Rob Broadhead
// Company:      Sleepless Nights Software
// Description:  Class for Ship properties in Space Duel
package Duel.Play;

import java.io.*;
import java.lang.*;
import snsBase.Stats;
import snsBase.Capability;

public class Ship {

   public Ship(int theType,String theName) {
      /* Declare the variables to be used */
      int count;

      // Initialize the values
      props_A=new Stats(5);
      props_A.Initialize(5);
      props_A.SetName(0,"Spd");   // Speed
      props_A.SetName(1,"Trn");   // Turn
      props_A.SetName(2,"Slp");   // Slip
      props_A.SetName(3,"Brk");   // Breaking
      props_A.SetName(4,"Acc");   // Acceleration

      energy_A=new Capability(0);
      hull_A=new Capability(0);
      storage_A=new Capability(0);

      shipType_A=(byte) theType;
      name_A = theName;
      shields_A=new Capability[4];
      for (count=0;count<4;count++) {
         shields_A[count]=new Capability(0);
      }
      armor_A=new Capability[4];
      for (count=0;count<4;count++) {
         armor_A[count]=new Capability(0);
      }
      weaps_A=new Weapon[4];
      for (count=0;count<4;count++) {
         weaps_A[count]=new Weapon(-1);
      }
      weapCap_A=new byte[4];
   } // End of Constructor()


   public String toString() {
      /* Declare the variables to be used */
      String retVal;

      retVal = "Name: " + name_A + ":" + this.ShipTypeAsString() + props_A;
      retVal = retVal + "\nHull:" + hull_A + "\tStore:"+ storage_A + "\nEnergy:"+energy_A;
      return retVal;
   } // End of toString()


   public int GetStat(int whichone) {
      return props_A.GetStat(whichone);
   } // End of GetStat(int)


   public void SetStat(int whichone,int value) {
      props_A.SetStat(whichone,value);
   } //  End of SetStat(int,int)


   public void Save(DataOutput out) throws IOException {
      /* Declare variables to be used */
      int count;

      out.writeUTF(name_A);
      out.writeByte(shipType_A);

      /* Save hull,energy,storage */
      hull_A.Save(out);
      energy_A.Save(out);
      storage_A.Save(out);

      /* Save the shield values */
      for(count=0;count<4;count++) {
         shields_A[count].Save(out);
      }

      /* Save the armor values */
      for(count=0;count<4;count++) {
         armor_A[count].Save(out);
      }

      /* Save the weapon sizes */
      for(count=0;count<4;count++) {
         out.writeByte(weapCap_A[count]);
      }

      /* Save the weapon classes */
      for(count=0;count<4;count++) {
         weaps_A[count].Save(out);
      }

      /* Save the properties */
      props_A.Save(out);
   } // End of Save()


   public void Load(DataInput in) throws IOException {
      /* Declare variables to be used */
      int count;

      name_A = in.readUTF();
      shipType_A = in.readByte();

      /* Load hull,energy,storage */
      hull_A.Load(in);
      energy_A.Load(in);
      storage_A.Load(in);
      
      /* Load the shield values */
      for(count=0;count<4;count++) {
         shields_A[count].Load(in);
      }

      /* Load the armor values */
      for(count=0;count<4;count++) {
         armor_A[count].Load(in);
      }

      /* Load the weapon sizes */
      for(count=0;count<4;count++) {
         weapCap_A[count] = in.readByte();
      }

      /* Load the weapon classes */
      for(count=0;count<4;count++) {
         weaps_A[count].Load(in);
      }

      /* Load the properties */
      props_A.Load(in);
   } // End of Load()


   public String GetName() {
      return name_A;
   } // End of GetName()


   public void setName(String val) {
      name_A=val;
   } // End of GetName()


   public byte GetShipType() {
      return shipType_A;
   } // End of GetShipType()


   public String ShipTypeAsString() {
      switch (shipType_A) {
      case 0:return "Scrapper";
      case 1:return "Scout";
      case 2:return "Fighter";
      case 3:return "Raider";
      case 4:return "Escort";
      case 5:return "Destroyer";
      case 6:return "Enforcer";
      case 7:return "Cruiser";
      case 8:return "Battleship";
      case 9:return "Leviathon";
      }

      return "Invalid Type";
   } // End of GetShipType()


   public void SetHull(Capability theVal) {
      hull_A = theVal;
   } // End of SetHull(Capability)
   
   
   public void SetStorage(Capability theVal) {
      storage_A = theVal;
   } // End of SetStorage(Capability)
   
   
   public void SetEnergy(Capability theVal) {
      energy_A = theVal;
   } // End of SetEnergy(Capability)
   
   
   public Capability getHull() {
      return hull_A;
   } // End of getHull()
   
   
   public Capability getStorage() {
      return storage_A;
   } // End of getStorage()
   
   
   public Capability getEnergy() {
      return energy_A;
   } // End of getEnergy()
   
   
   public void SetProps(Stats theVal) {
      props_A = theVal;
   } // End of SetProps(Stats)
   

   public Stats GetProps() {
      return props_A;
   } // End of GetProps()


   public void SetShield(char which,Capability theVal) {
      /* Declare the variables used */
      int idx = 0;

      switch (which) {
      case 'F':
         idx=0;
         break;
      case 'S':
         idx=1;
         break;
      case 'R':
         idx=2;
         break;
      case 'P':
         idx=3;
         break;
      }
      shields_A[idx] = theVal;
   } // End of SetShield(Char,Capability)
   
   
   public void SetArmor(char which,Capability theVal) {
      /* Declare the variables used */
      int idx = 0;

      switch (which) {
      case 'F':
         idx=0;
         break;
      case 'S':
         idx=1;
         break;
      case 'R':
         idx=2;
         break;
      case 'P':
         idx=3;
         break;
      }
      armor_A[idx] = theVal;
   } // End of SetArmor(Char,Capability)


   public void SetWeapCap(char which,int theVal) {
      /* Declare the variables used */
      int idx = 0;

      switch (which) {
      case 'F':
         idx=0;
         break;
      case 'S':
         idx=1;
         break;
      case 'R':
         idx=2;
         break;
      case 'P':
         idx=3;
         break;
      }
      weapCap_A[idx] = (byte) theVal;
   } // End of SetWeapCap(Char,byte)
   
   
   public Capability getShield(char which) {
      /* Declare the variables used */
      int idx = 0;

      switch (which) {
      case 'F':
         idx=0;
         break;
      case 'S':
         idx=1;
         break;
      case 'R':
         idx=2;
         break;
      case 'P':
         idx=3;
         break;
      }
      
      return shields_A[idx];
   } // End of getShield(Char)
   
   
   public Capability getArmor(char which) {
      /* Declare the variables used */
      int idx = 0;

      switch (which) {
      case 'F':
         idx=0;
         break;
      case 'S':
         idx=1;
         break;
      case 'R':
         idx=2;
         break;
      case 'P':
         idx=3;
         break;
      }
      
      return armor_A[idx];
   } // End of getArmor(Char)


   public int getWeapCap(char which) {
      /* Declare the variables used */
      int idx = 0;

      switch (which) {
      case 'F':
         idx=0;
         break;
      case 'S':
         idx=1;
         break;
      case 'R':
         idx=2;
         break;
      case 'P':
         idx=3;
         break;
      }
      
      return weapCap_A[idx];
   } // End of getWeapCap(Char)
   
   
/* Properties for the class */
   private byte       shipType_A;
   private String     name_A;
   private Capability hull_A;
   private Capability energy_A;
   private Capability storage_A;
   private Capability shields_A[];
   private Capability armor_A[];
   private Weapon     weaps_A[];
   private byte       weapCap_A[];
   private Stats      props_A;
} // End of Ship class.

