// Title:        Pilot
// Version:      1.0
// Copyright:    Copyright (c) 1998
// Author:       Rob Broadhead
// Company:      Sleepless Nights Software
// Description:  Storage class for Pilot information


package Duel;

/* Include files we need */
import snsBase.Stats;
import java.io.*;

public class Pilot {

   public Pilot(String theName) {
      name_A=theName;
   } // End of Pilot constructor.

   
   public int GetStat(int whichone) {
      return stats_A.GetStat(whichone);
   } // End of GetStat(int).

   
   public int GetSkill(int whichone) {
      return skills_A.GetStat(whichone);
   } // End of GetSkill(int).

   
   public String GetRank() {
      /* Declare the variables to be used */
      String retVal="";

      return retVal;
   } // End of GetRank().

   
   public void GainRank() {
   } // End of GainRank().

   
   public void IncStat(int whichone) {
      /* Declare variables to be used */
      int tempVal;

      // Get the stat and add one.
      tempVal=stats_A.GetStat(whichone) + 1;

      // Set the new value
      stats_A.SetStat(whichone,tempVal);
   } // End of IncStat(int).

   
   public void IncSkill(int whichone) {
      /* Declare variables to be used */
      int tempVal;

      // Get the stat and add one.
      tempVal=skills_A.GetStat(whichone) + 1;

      // Set the new value
      skills_A.SetStat(whichone,tempVal);
   } // End of IncSkill(int).

   
   public void Save(DataOutput out) throws IOException {
   } // End  of Save(DataOutput).

   
   public void Load(DataInput in) throws IOException {
   } // End of Load(DataInput).

   
/* Properties for Pilot class */
   private String name_A;
   private int rank_A;
   private Stats skills_A=new Stats(5);
   private Stats stats_A=new Stats(5);
   
} // End of Pilot Class

/* rank_A:int [0..9] [Novice,Rookie,Pilot,Skilled,Expert,Maven,Ace,Hero,Legend]
skills_A:Stat [Evade,Acc,Repair,Morale,Health] [1..20 + 4*rank]
stats_A:Stat [Kills,Shots,Hits,XP,pay] */

