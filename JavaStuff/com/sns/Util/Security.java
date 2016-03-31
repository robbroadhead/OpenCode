/*
Security Class
Written by:Rob Broadhead
$Id
*/

package com.sns.Util;

public class Security
{
   private   String[] userids_A;
   private   String[] passwords_A;
   private   String[] useraccess_A;

   public boolean logon (String id, String pwd) {
   return true;
   }

   public Security() {
   }

   public void addUser (String name, String pwd, String verify) {
   }

   public void removeUser () {
   }

   public String[] userList () {
   	return userids_A;
   }

   public void changePass (String old, String newp, String user) {
   }
}
