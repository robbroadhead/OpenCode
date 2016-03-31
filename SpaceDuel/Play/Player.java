// Title:        Player
// Version:      1.0
// Copyright:    Copyright (c) 1999
// Author:       Rob Broadhead
// Company:      Sleepless Nights Software
// Description:  Class for Player properties in Space Duel
package Duel.Play;

import java.io.*;
import java.lang.*;
import snsBase.Stats;

public class Player {

   public Player(String theName) {
      /* Declare the variables to be used */
      int count;
      
      // Initialize the values
      props_A=new Stats(7);
      props_A.Initialize(0);
      props_A.SetName(0,"Wins");
      props_A.SetName(1,"Losses");
      props_A.SetName(2,"Ties");
      props_A.SetName(3,"Kills");
      props_A.SetName(4,"Deaths");
      props_A.SetName(5,"Funds");
      props_A.SetStat(5,100);
      props_A.SetName(6,"Earned");

      name_A = theName;
      rank_A = 1;
      numShips_A=0;
      ships_A=new Ship[25];
   }

		
   public String toString() {
      return "Name: " + name_A + ":" + rank_A + props_A;
   } // End of toString()


   public void setName(String val) {
      name_A=val;
   } // End of setName(String).


   public String GetRankString() {
      /* Declare the variables to be used */
      String retVal;

      retVal="";
      
      switch (rank_A) {
      case 1: retVal="Pond Scum";
         break;
      case 2: retVal="Slime";
         break;
      case 3: retVal="Lowlife";
         break;
      case 4: retVal="Inept";
         break;
      case 5: retVal="Ditch Digger";
         break;
      case 6: retVal="Apprentice";
         break;
      case 7: retVal="Fighter";
         break;
      case 8: retVal="Cool";
         break;
      case 9: retVal="Promising";
         break;
      case 10:retVal="Skilled";
         break;
      }

      return retVal;
   } // End of GetRankString()

   
   public int GetStat(int whichone) {
      return props_A.GetStat(whichone);
   } // End of GetStat(int)

   
   public void IncStat(int whichone,int value) {
      props_A.IncStat(whichone,value);
   } //  End of SetStat(int,int)

   
   public void Save() {
      /* Declare variables to be used */
      String fileName;
      int count;

      /* Set the file name */
      fileName = name_A.trim() + ".dat";
      try {
         DataOutputStream oFile=new DataOutputStream(new FileOutputStream(fileName));
         oFile.writeUTF(name_A);
         oFile.writeByte(rank_A);
         oFile.writeByte(numShips_A);
      
         /* Save the stats */
         props_A.Save(oFile);

         /* Save the Ships */
         for (count=0;count<numShips_A;count++) {
            ships_A[count].Save(oFile);
         }
      } catch (IOException tempExcept) {
         tempExcept.getMessage();
      }
      /* close the file */
   } // End of Save()

   
   public void Load() {
      /* Declare variables to be used */
      String fileName;
      int count;

      /* Set the file name */
      fileName = name_A.trim() + ".dat";
      try {
         DataInputStream iFile=new DataInputStream(new FileInputStream(fileName));
         name_A=iFile.readUTF();
         rank_A=iFile.readByte();
         numShips_A=iFile.readByte();
      
         /* Load the stats */
         props_A.Load(iFile);

         /* Load the Ships */
         for (count=0;count<numShips_A;count++) {
            ships_A[count].Load(iFile);
         }
      } catch (IOException tempExcept) {
         tempExcept.getMessage();
      }
      /* Close the file */
   } // End of Load()


   public int GetRank() {
      return rank_A;
   }

      
   public void GainRank() {
      rank_A ++;
      if (rank_A < 20) {
         rank_A = 20;
      }
   } // End of GainRank()


   public String GetName() {
      return name_A;
   } // End of GetName()
   
   
/* Properties for the class */
   private byte   rank_A;
   private String name_A;
   private Stats  props_A;
   private Ship   ships_A[];
   private byte    numShips_A;
} // End of Player class.

