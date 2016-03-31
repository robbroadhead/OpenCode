// Title:        Weapon
// Version:      1.0
// Copyright:    Copyright (c) 1999
// Author:       Rob Broadhead
// Company:      Sleepless Nights Software
// Description:  Class for Weapon properties in Space Duel
package Duel.Play;

import java.io.*;
import java.lang.*;
import snsBase.Stats;

public class Weapon {

   public Weapon(int theType) {
      // Initialize the values
      props_A=new Stats(6);
      props_A.Initialize(0);
      props_A.SetName(0,"Str");   // Structure (damage until destroyed)
      props_A.SetName(1,"Acc");   // Accuracy
      props_A.SetName(2,"Rat");   // Fire rate
      props_A.SetName(3,"Rng");   // Range
      props_A.SetName(4,"Dmg");   // Damage
      props_A.SetName(5,"Egy");   // Energy used

      weapType_A=(byte) theType;
      weapClass_A=-1;
      name_A = "";
      ammoType_A = -1;
   }


   public void setName(String val) {
      name_A=val;
   } // End of setName(String)


   public void setAmmoType(int val) {
      ammoType_A= (byte) val;
   } // End of setAmmoType


   public void setWeaponClass(int val) {
      weapClass_A= (byte) val;
   } // End of setWeaponClass(int)


   public String toString() {
      return "Name: " + name_A + ":" + weapType_A + ":" + ammo_A + props_A;
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
      out.writeByte(ammo_A);
      out.writeByte(ammoType_A);
      out.writeByte(weapType_A);
      
      /* Save the stats */
      props_A.Save(out);
   } // End of Save()

   
   public void Load(DataInput in) throws IOException {
      /* Declare variables to be used */
      int count;

      name_A = in.readUTF();
      ammo_A = in.readByte();
      ammoType_A = in.readByte();
      weapType_A = in.readByte();

      /* Load the stats */
      props_A.Load(in);
   } // End of Load()

   
   public void AdjustAmmo(int val) {
      ammo_A += (byte) val;
      if (ammo_A < 0) {
         ammo_A = 0;
      }
   } // End of AdjustAmmo(byte)


   public String GetName() {
      return name_A;
   } // End of GetName()
   
   
   public byte GetAmmo() {
      return ammo_A;
   } // End of GetAmmo()
   
   
   public byte GetAmmoType() {
      return ammoType_A;
   } // End of GetAmmoType()


   public byte GetWeaponType() {
      return weapType_A;
   } // End of GetWeaponType()


   public byte GetWeaponClass() {
      return weapClass_A;
   } // End of GetWeaponClass()


   public String weaponString() {
      /* Declare the variables to be used */
      String retVal;

      retVal="Invalid Type";
      switch (weapClass_A) {
      case 0:
            retVal="Beam";
         break;
      case 1:
            retVal="Rocket";
         break;
      case 2:
            retVal="Ballistic";
         break;
      case 3:
            retVal="Photon";
         break;
      case 4:
            retVal="Pulse";
         break;
      case 5:
            retVal="Mag";
         break;
      }

      return retVal;
   } // End of weaponString()


   public String ammoString() {
      /* Declare the variables to be used */
      String retVal;

      retVal="Invalid Type";
      switch (ammoType_A) {
      case 0:
            retVal="None";
      case 1:
            retVal="Steel Barb";
      case 2:
            retVal="Steel Baring ";
      case 3:
            retVal="Lead Ball";
      case 4:
            retVal="Titanium Ball";
      case 5:
            retVal="Sparrow Rocket";
      case 6:
            retVal="Raven Rocket";
      case 7:
            retVal="Hawk Rocket";
      case 8:
            retVal="Eagle Rocket";
      case 9:
            retVal="Asp Shell";
      case 10:
            retVal="Viper Shell";
      case 11:
            retVal="Python Shell";
      case 12:
            retVal="Cobra Shell";
      case 13:
            retVal="Ion Sphere";
         break;
      }

      return retVal;
   } // End of ammoString()


   public Stats GetProps() {
      return props_A;
   } // End of GetProps


/* Properties for the class */
   private byte   ammo_A;
   private String name_A;
   private byte   weapType_A;
   private byte   weapClass_A;
   private byte   ammoType_A;
   private Stats  props_A;
} // End of Weapon class.

