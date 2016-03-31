//Title: TextIO
//Version: 1.0
//Copyright: All Rights Reserved 1997
//Author: Rob Broadhead
//Company: Sleepless Nights Software
//Description: Used to display information of various types
//             on a text-based terminal.
package com.sns.Util;

import java.util.Date;
import java.text.*;

public class TextIO
{

   public TextIO(String p) {
      prompt = p;
   }

   public TextIO() {
      prompt = "";
   }

   public void setPrompt(String newP)
   {
        prompt = newP;
   }

   public void write(String msg) {
      System.out.print(msg);
   }

   public void writeLn(String msg) {
      System.out.println(msg);
   }

   public void printPrompt()
   {
      System.out.print(prompt);
      System.out.flush();
   }

   public String inString()
   {
      /* Declare the variables for the method */
      int ch;
      String retVal = "";
      boolean done = false;

      /* Always start with the prompt  */
      /* All methods use this so don't */
      /* print prompt in them.         */
      printPrompt();

      while (!done)
      {  try
         {  ch = System.in.read();
            if (ch < 0 || (char)ch == '\n')
               done = true;
            else
               retVal = retVal + (char) ch;
         }
         catch(java.io.IOException e)
         {
            done = true;
            System.out.println("Invalid entry!");
            retVal = "";
         }
      }
      return retVal.trim();
   }

   public int inInt()
   {
      while(true)
      {
         try
         {
            return Integer.valueOf(inString().trim()).intValue();
         } catch(NumberFormatException e)
         {
            System.out.println("Not an integer. Please re-enter!");
         }
      }
   }

   public double inDouble()
   {
      while(true)
      {
         try
         {
            return Double.valueOf(inString().trim()).doubleValue();
         } catch(NumberFormatException e)
         {
            System.out.println("Not a floating point number. Please re-enter!");
         }
      }
   }
   public float inFloat()
   {
      while(true)
      {
         try
         {
            return Float.valueOf(inString().trim()).floatValue();
         } catch(NumberFormatException e)
         {
            System.out.println("Not a floating point number. Please re-enter!");
         }
      }
   }

   public Date inDate()
   {
     while(true)
       {
	 try
	   {
      	 Date retVal;
        DateFormat parser=DateFormat.getInstance();

        return parser.parse(inString().trim());
	   } catch(NumberFormatException e)
	     {
	       System.out.println("Not a date. Please re-enter!");
	     }
        catch (ParseException e)
	     {
	       System.out.println("Not a date. Please re-enter!");
	     }
       }
   }

  private String prompt;
}




