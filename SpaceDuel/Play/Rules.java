// Title:        Rules
// Version:      1.0
// Copyright:    Copyright (c) 1999
// Author:       Rob Broadhead
// Company:      Sleepless Nights Software
// Description:  Functions to implement the rules of Space Duel
package Duel.Play;

import snsBase.Stats;
import snsBase.Capability;

/** A static class to hold the methods used by SD classes that are
defined by the SD rules.*/
public class Rules {

   public static void SetShipStats(Ship theShip) {
      /* Declare the variables to be used */
      byte shipType;
      Stats curProps;
      Capability tempCap;

      /* Initialization stuff */
      curProps = theShip.GetProps();
      shipType=theShip.GetShipType();
      
      switch (shipType) {
      case 0: // Scrapper
         /* Set hull size and energy */
         tempCap = new Capability(20,20);
         theShip.SetHull(tempCap);
         tempCap = new Capability(100,100);
         theShip.SetEnergy(tempCap);
         tempCap = new Capability(10,10);
         theShip.SetStorage(tempCap);

         /* Set the armor values */
         tempCap = new Capability(10,10);
         theShip.SetArmor('F',tempCap);
         tempCap = new Capability(5,5);
         theShip.SetArmor('S',tempCap);
         tempCap = new Capability(10,10);
         theShip.SetArmor('R',tempCap);
         tempCap = new Capability(5,5);
         theShip.SetArmor('P',tempCap);

         /* Set the shield values */
         tempCap = new Capability(25);
         theShip.SetShield('F',tempCap);
         tempCap = new Capability(20);
         theShip.SetShield('S',tempCap);
         tempCap = new Capability(25);
         theShip.SetShield('R',tempCap);
         tempCap = new Capability(20);
         theShip.SetShield('P',tempCap);

         /* Set the weapon capability values */
         theShip.SetWeapCap('F',2);
         theShip.SetWeapCap('S',0);
         theShip.SetWeapCap('R',1);
         theShip.SetWeapCap('P',0);

         /* Set properties */
         curProps.SetStat(0,3);
         curProps.SetStat(1,3);
         curProps.SetStat(2,3);
         curProps.SetStat(3,3);
         curProps.SetStat(4,0);

         break;
      case 1: // Scout
         /* Set hull size and energy */
         tempCap = new Capability(35,35);
         theShip.SetHull(tempCap);
         tempCap = new Capability(350,350);
         theShip.SetEnergy(tempCap);
         tempCap = new Capability(5,5);
         theShip.SetStorage(tempCap);

         /* Set the armor values */
         tempCap = new Capability(15,15);
         theShip.SetArmor('F',tempCap);
         tempCap = new Capability(5,5);
         theShip.SetArmor('S',tempCap);
         tempCap = new Capability(15,15);
         theShip.SetArmor('R',tempCap);
         tempCap = new Capability(5,5);
         theShip.SetArmor('P',tempCap);

         /* Set the shield values */
         tempCap = new Capability(60);
         theShip.SetShield('F',tempCap);
         tempCap = new Capability(15);
         theShip.SetShield('S',tempCap);
         tempCap = new Capability(75);
         theShip.SetShield('R',tempCap);
         tempCap = new Capability(15);
         theShip.SetShield('P',tempCap);

         /* Set the weapon capability values */
         theShip.SetWeapCap('F',2);
         theShip.SetWeapCap('S',1);
         theShip.SetWeapCap('R',0);
         theShip.SetWeapCap('P',1);

         /* Set properties */
         curProps.SetStat(0,5);
         curProps.SetStat(1,4);
         curProps.SetStat(2,3);
         curProps.SetStat(3,3);
         curProps.SetStat(4,1);

         break;
      case 2: // Fighter
         /* Set hull size and energy */
         tempCap = new Capability(60,60);
         theShip.SetHull(tempCap);
         tempCap = new Capability(275,275);
         theShip.SetEnergy(tempCap);
         tempCap = new Capability(20,20);
         theShip.SetStorage(tempCap);

         /* Set the armor values */
         tempCap = new Capability(30,30);
         theShip.SetArmor('F',tempCap);
         tempCap = new Capability(10,10);
         theShip.SetArmor('S',tempCap);
         tempCap = new Capability(25,25);
         theShip.SetArmor('R',tempCap);
         tempCap = new Capability(10,10);
         theShip.SetArmor('P',tempCap);

         /* Set the shield values */
         tempCap = new Capability(100);
         theShip.SetShield('F',tempCap);
         tempCap = new Capability(35);
         theShip.SetShield('S',tempCap);
         tempCap = new Capability(75);
         theShip.SetShield('R',tempCap);
         tempCap = new Capability(35);
         theShip.SetShield('P',tempCap);

         /* Set the weapon capability values */
         theShip.SetWeapCap('F',3);
         theShip.SetWeapCap('S',2);
         theShip.SetWeapCap('R',0);
         theShip.SetWeapCap('P',2);

         /* Set properties */
         curProps.SetStat(0,5);
         curProps.SetStat(1,4);
         curProps.SetStat(2,4);
         curProps.SetStat(3,4);
         curProps.SetStat(4,2);

         break;
      case 3: // Raider
         /* Set hull size and energy */
         tempCap = new Capability(50,50);
         theShip.SetHull(tempCap);
         tempCap = new Capability(450,450);
         theShip.SetEnergy(tempCap);
         tempCap = new Capability(30,30);
         theShip.SetStorage(tempCap);

         /* Set the armor values */
         tempCap = new Capability(80,80);
         theShip.SetArmor('F',tempCap);
         tempCap = new Capability(20,20);
         theShip.SetArmor('S',tempCap);
         tempCap = new Capability(75,75);
         theShip.SetArmor('R',tempCap);
         tempCap = new Capability(20,20);
         theShip.SetArmor('P',tempCap);

         /* Set the shield values */
         tempCap = new Capability(200);
         theShip.SetShield('F',tempCap);
         tempCap = new Capability(50);
         theShip.SetShield('S',tempCap);
         tempCap = new Capability(125);
         theShip.SetShield('R',tempCap);
         tempCap = new Capability(50);
         theShip.SetShield('P',tempCap);

         /* Set the weapon capability values */
         theShip.SetWeapCap('F',4);
         theShip.SetWeapCap('S',1);
         theShip.SetWeapCap('R',3);
         theShip.SetWeapCap('P',1);

         /* Set properties */
         curProps.SetStat(0,6);
         curProps.SetStat(1,5);
         curProps.SetStat(2,2);
         curProps.SetStat(3,2);
         curProps.SetStat(4,3);

         break;
      case 4: // Escort
         /* Set hull size and energy */
         tempCap = new Capability(75,75);
         theShip.SetHull(tempCap);
         tempCap = new Capability(400,400);
         theShip.SetEnergy(tempCap);
         tempCap = new Capability(15,15);
         theShip.SetStorage(tempCap);

         /* Set the armor values */
         tempCap = new Capability(50,50);
         theShip.SetArmor('F',tempCap);
         tempCap = new Capability(45,45);
         theShip.SetArmor('S',tempCap);
         tempCap = new Capability(20,20);
         theShip.SetArmor('R',tempCap);
         tempCap = new Capability(45,45);
         theShip.SetArmor('P',tempCap);

         /* Set the shield values */
         tempCap = new Capability(400);
         theShip.SetShield('F',tempCap);
         tempCap = new Capability(250);
         theShip.SetShield('S',tempCap);
         tempCap = new Capability(400);
         theShip.SetShield('R',tempCap);
         tempCap = new Capability(250);
         theShip.SetShield('P',tempCap);

         /* Set the weapon capability values */
         theShip.SetWeapCap('F',3);
         theShip.SetWeapCap('S',2);
         theShip.SetWeapCap('R',3);
         theShip.SetWeapCap('P',2);

         /* Set properties */
         curProps.SetStat(0,4);
         curProps.SetStat(1,4);
         curProps.SetStat(2,3);
         curProps.SetStat(3,4);
         curProps.SetStat(4,2);

         break;
      case 5: // Destroyer
         /* Set hull size and energy */
         tempCap = new Capability(100,100);
         theShip.SetHull(tempCap);
         tempCap = new Capability(500,500);
         theShip.SetEnergy(tempCap);
         tempCap = new Capability(50,50);
         theShip.SetStorage(tempCap);

         /* Set the armor values */
         tempCap = new Capability(100,100);
         theShip.SetArmor('F',tempCap);
         tempCap = new Capability(100,100);
         theShip.SetArmor('S',tempCap);
         tempCap = new Capability(100,100);
         theShip.SetArmor('R',tempCap);
         tempCap = new Capability(100,100);
         theShip.SetArmor('P',tempCap);

         /* Set the shield values */
         tempCap = new Capability(750);
         theShip.SetShield('F',tempCap);
         tempCap = new Capability(600);
         theShip.SetShield('S',tempCap);
         tempCap = new Capability(750);
         theShip.SetShield('R',tempCap);
         tempCap = new Capability(600);
         theShip.SetShield('P',tempCap);

         /* Set the weapon capability values */
         theShip.SetWeapCap('F',4);
         theShip.SetWeapCap('S',2);
         theShip.SetWeapCap('R',4);
         theShip.SetWeapCap('P',2);

         /* Set properties */
         curProps.SetStat(0,4);
         curProps.SetStat(1,3);
         curProps.SetStat(2,2);
         curProps.SetStat(3,2);
         curProps.SetStat(4,1);

         break;
      case 6: // Enforcer
         /* Set hull size and energy */
         tempCap = new Capability(125,125);
         theShip.SetHull(tempCap);
         tempCap = new Capability(800,800);
         theShip.SetEnergy(tempCap);
         tempCap = new Capability(50,50);
         theShip.SetStorage(tempCap);

         /* Set the armor values */
         tempCap = new Capability(350,350);
         theShip.SetArmor('F',tempCap);
         tempCap = new Capability(100,100);
         theShip.SetArmor('S',tempCap);
         tempCap = new Capability(300,300);
         theShip.SetArmor('R',tempCap);
         tempCap = new Capability(100,100);
         theShip.SetArmor('P',tempCap);

         /* Set the shield values */
         tempCap = new Capability(1000);
         theShip.SetShield('F',tempCap);
         tempCap = new Capability(400);
         theShip.SetShield('S',tempCap);
         tempCap = new Capability(1000);
         theShip.SetShield('R',tempCap);
         tempCap = new Capability(400);
         theShip.SetShield('P',tempCap);

         /* Set the weapon capability values */
         theShip.SetWeapCap('F',5);
         theShip.SetWeapCap('S',3);
         theShip.SetWeapCap('R',3);
         theShip.SetWeapCap('P',3);

         /* Set properties */
         curProps.SetStat(0,5);
         curProps.SetStat(1,3);
         curProps.SetStat(2,3);
         curProps.SetStat(3,3);
         curProps.SetStat(4,3);

         break;
      case 7: // Cruiser
         /* Set hull size and energy */
         tempCap = new Capability(200,200);
         theShip.SetHull(tempCap);
         tempCap = new Capability(750,750);
         theShip.SetEnergy(tempCap);
         tempCap = new Capability(100,100);
         theShip.SetStorage(tempCap);

         /* Set the armor values */
         tempCap = new Capability(300,300);
         theShip.SetArmor('F',tempCap);
         tempCap = new Capability(300,300);
         theShip.SetArmor('S',tempCap);
         tempCap = new Capability(300,300);
         theShip.SetArmor('R',tempCap);
         tempCap = new Capability(300,300);
         theShip.SetArmor('P',tempCap);

         /* Set the shield values */
         tempCap = new Capability(1400);
         theShip.SetShield('F',tempCap);
         tempCap = new Capability(750);
         theShip.SetShield('S',tempCap);
         tempCap = new Capability(1250);
         theShip.SetShield('R',tempCap);
         tempCap = new Capability(750);
         theShip.SetShield('P',tempCap);

         /* Set the weapon capability values */
         theShip.SetWeapCap('F',5);
         theShip.SetWeapCap('S',3);
         theShip.SetWeapCap('R',5);
         theShip.SetWeapCap('P',3);

         /* Set properties */
         curProps.SetStat(0,3);
         curProps.SetStat(1,2);
         curProps.SetStat(2,1);
         curProps.SetStat(3,1);
         curProps.SetStat(4,1);

         break;
      case 8: // BattleShip
         /* Set hull size and energy */
         tempCap = new Capability(350,350);
         theShip.SetHull(tempCap);
         tempCap = new Capability(1000,1000);
         theShip.SetEnergy(tempCap);
         tempCap = new Capability(200,200);
         theShip.SetStorage(tempCap);

         /* Set the armor values */
         tempCap = new Capability(400,400);
         theShip.SetArmor('F',tempCap);
         tempCap = new Capability(400,400);
         theShip.SetArmor('S',tempCap);
         tempCap = new Capability(400,400);
         theShip.SetArmor('R',tempCap);
         tempCap = new Capability(400,400);
         theShip.SetArmor('P',tempCap);

         /* Set the shield values */
         tempCap = new Capability(1500);
         theShip.SetShield('F',tempCap);
         tempCap = new Capability(1250);
         theShip.SetShield('S',tempCap);
         tempCap = new Capability(1500);
         theShip.SetShield('R',tempCap);
         tempCap = new Capability(1250);
         theShip.SetShield('P',tempCap);

         /* Set the weapon capability values */
         theShip.SetWeapCap('F',6);
         theShip.SetWeapCap('S',5);
         theShip.SetWeapCap('R',4);
         theShip.SetWeapCap('P',5);

         /* Set properties */
         curProps.SetStat(0,3);
         curProps.SetStat(1,1);
         curProps.SetStat(2,1);
         curProps.SetStat(3,1);
         curProps.SetStat(4,0);

         break;
      case 9: // Leviathon
         /* Set hull size and energy */
         tempCap = new Capability(500,500);
         theShip.SetHull(tempCap);
         tempCap = new Capability(1250,1250);
         theShip.SetEnergy(tempCap);
         tempCap = new Capability(250,250);
         theShip.SetStorage(tempCap);

         /* Set the armor values */
         tempCap = new Capability(500,500);
         theShip.SetArmor('F',tempCap);
         tempCap = new Capability(500,500);
         theShip.SetArmor('S',tempCap);
         tempCap = new Capability(500,500);
         theShip.SetArmor('R',tempCap);
         tempCap = new Capability(500,500);
         theShip.SetArmor('P',tempCap);

         /* Set the shield values */
         tempCap = new Capability(2000);
         theShip.SetShield('F',tempCap);
         tempCap = new Capability(2000);
         theShip.SetShield('S',tempCap);
         tempCap = new Capability(2000);
         theShip.SetShield('R',tempCap);
         tempCap = new Capability(2000);
         theShip.SetShield('P',tempCap);

         /* Set the weapon capability values */
         theShip.SetWeapCap('F',6);
         theShip.SetWeapCap('S',6);
         theShip.SetWeapCap('R',5);
         theShip.SetWeapCap('P',6);

         /* Set properties */
         curProps.SetStat(0,4);
         curProps.SetStat(1,1);
         curProps.SetStat(2,1);
         curProps.SetStat(3,1);
         curProps.SetStat(4,0);

         break;
      default:
         /* Set hull size and energy */
         tempCap = new Capability(15,15);
         theShip.SetHull(tempCap);
         tempCap = new Capability(50,50);
         theShip.SetEnergy(tempCap);
         tempCap = new Capability(0,0);
         theShip.SetStorage(tempCap);

         /* Set the armor values */
         tempCap = new Capability(5,5);
         theShip.SetArmor('F',tempCap);
         tempCap = new Capability(5,5);
         theShip.SetArmor('S',tempCap);
         tempCap = new Capability(5,5);
         theShip.SetArmor('R',tempCap);
         tempCap = new Capability(5,5);
         theShip.SetArmor('P',tempCap);

         /* Set the shield values */
         tempCap = new Capability(20);
         theShip.SetShield('F',tempCap);
         tempCap = new Capability(20);
         theShip.SetShield('S',tempCap);
         tempCap = new Capability(20);
         theShip.SetShield('R',tempCap);
         tempCap = new Capability(20);
         theShip.SetShield('P',tempCap);

         /* Set the weapon capability values */
         theShip.SetWeapCap('F',2);
         theShip.SetWeapCap('S',0);
         theShip.SetWeapCap('R',0);
         theShip.SetWeapCap('P',0);

         /* Set properties */
         curProps.SetStat(0,3);
         curProps.SetStat(1,3);
         curProps.SetStat(2,3);
         curProps.SetStat(3,3);
         curProps.SetStat(4,2 );

         break;
      }

      /* Save props to the Ship */
      theShip.SetProps(curProps);
   } // End of SetShipStats(Ship)

   public static void SetWeaponStats(Weapon theWeapon) {
      /* Declare the variables to be used */
      byte weaponType;
      Stats curProps;

      /* Initialization stuff */
      curProps = theWeapon.GetProps();
      weaponType=theWeapon.GetWeaponType();

      switch (weaponType) {
      case 0: // Needler
         theWeapon.setName("Needler");
         theWeapon.setAmmoType(1);
         theWeapon.setWeaponClass(2);

         /* Set properties */
         curProps.SetStat(0,2);
         curProps.SetStat(1,30);
         curProps.SetStat(2,5);
         curProps.SetStat(3,2);
         curProps.SetStat(4,1);
         curProps.SetStat(5,0);
         break;
      case 1: // Slugger
         theWeapon.setName("Slugger");
         theWeapon.setAmmoType(2);
         theWeapon.setWeaponClass(2);

         /* Set properties */
         curProps.SetStat(0,10);
         curProps.SetStat(1,35);
         curProps.SetStat(2,3);
         curProps.SetStat(3,3);
         curProps.SetStat(4,5);
         curProps.SetStat(5,0);
         break;
      case 2: // Cannon
         theWeapon.setName("Cannon");
         theWeapon.setAmmoType(3);
         theWeapon.setWeaponClass(2);

         /* Set properties */
         curProps.SetStat(0,35);
         curProps.SetStat(1,40);
         curProps.SetStat(2,1);
         curProps.SetStat(3,4);
         curProps.SetStat(4,40);
         curProps.SetStat(5,0);
         break;
      case 3: // Mauler
         theWeapon.setName("Mauler");
         theWeapon.setAmmoType(4);
         theWeapon.setWeaponClass(2);

         /* Set properties */
         curProps.SetStat(0,50);
         curProps.SetStat(1,50);
         curProps.SetStat(2,1);
         curProps.SetStat(3,5);
         curProps.SetStat(4,50);
         curProps.SetStat(5,0);
         break;
      case 4: // Laser
         theWeapon.setName("Laser");
         theWeapon.setAmmoType(0);
         theWeapon.setWeaponClass(0);

         /* Set properties */
         curProps.SetStat(0,3);
         curProps.SetStat(1,60);
         curProps.SetStat(2,4);
         curProps.SetStat(3,4);
         curProps.SetStat(4,10);
         curProps.SetStat(5,3);
         break;
      case 5: // Phaser
         theWeapon.setName("Phaser");
         theWeapon.setAmmoType(0);
         theWeapon.setWeaponClass(0);

         /* Set properties */
         curProps.SetStat(0,5);
         curProps.SetStat(1,80);
         curProps.SetStat(2,5);
         curProps.SetStat(3,6);
         curProps.SetStat(4,20);
         curProps.SetStat(5,15);
         break;
      case 6: // Disruptor
         theWeapon.setName("Disruptor");
         theWeapon.setAmmoType(0);
         theWeapon.setWeaponClass(0);

         /* Set properties */
         curProps.SetStat(0,10);
         curProps.SetStat(1,100);
         curProps.SetStat(2,6);
         curProps.SetStat(3,8);
         curProps.SetStat(4,30);
         curProps.SetStat(5,25);
         break;
      case 7: // Disintegrator
         theWeapon.setName("Disentegrator");
         theWeapon.setAmmoType(0);
         theWeapon.setWeaponClass(0);

         /* Set properties */
         curProps.SetStat(0,20);
         curProps.SetStat(1,110);
         curProps.SetStat(2,7);
         curProps.SetStat(3,10);
         curProps.SetStat(4,40);
         curProps.SetStat(5,35);
         break;
      case 8: // Sparrow
         theWeapon.setName("Sparrow Launcher");
         theWeapon.setAmmoType(5);
         theWeapon.setWeaponClass(1);

         /* Set properties */
         curProps.SetStat(0,35);
         curProps.SetStat(1,50);
         curProps.SetStat(2,3);
         curProps.SetStat(3,5);
         curProps.SetStat(4,15);
         curProps.SetStat(5,1);
         break;
      case 9: // Raven
         theWeapon.setName("Raven Launcher");
         theWeapon.setAmmoType(6);
         theWeapon.setWeaponClass(1);

         /* Set properties */
         curProps.SetStat(0,35);
         curProps.SetStat(1,60);
         curProps.SetStat(2,3);
         curProps.SetStat(3,6);
         curProps.SetStat(4,25);
         curProps.SetStat(5,2);
         break;
      case 10: // Hawk
         theWeapon.setName("Hawk Launcher");
         theWeapon.setAmmoType(7);
         theWeapon.setWeaponClass(1);

         /* Set properties */
         curProps.SetStat(0,35);
         curProps.SetStat(1,70);
         curProps.SetStat(2,3);
         curProps.SetStat(3,7);
         curProps.SetStat(4,35);
         curProps.SetStat(5,2);
         break;
      case 11: // Eagle
         theWeapon.setName("Eagle Launcher");
         theWeapon.setAmmoType(8);
         theWeapon.setWeaponClass(1);

         /* Set properties */
         curProps.SetStat(0,35);
         curProps.SetStat(1,80);
         curProps.SetStat(2,3);
         curProps.SetStat(3,8);
         curProps.SetStat(4,50);
         curProps.SetStat(5,3);
         break;
      case 12: // Asp
         theWeapon.setName("Asp Launcher");
         theWeapon.setAmmoType(9);
         theWeapon.setWeaponClass(3);

         /* Set properties */
         curProps.SetStat(0,50);
         curProps.SetStat(1,60);
         curProps.SetStat(2,2);
         curProps.SetStat(3,7);
         curProps.SetStat(4,15);
         curProps.SetStat(5,5);
         break;
      case 13: // Viper
         theWeapon.setName("Viper Launcher");
         theWeapon.setAmmoType(10);
         theWeapon.setWeaponClass(3);

         /* Set properties */
         curProps.SetStat(0,50);
         curProps.SetStat(1,70);
         curProps.SetStat(2,2);
         curProps.SetStat(3,8);
         curProps.SetStat(4,25);
         curProps.SetStat(5,5);
         break;
      case 14: // Python
         theWeapon.setName("Python Launcher");
         theWeapon.setAmmoType(11);
         theWeapon.setWeaponClass(3);

         /* Set properties */
         curProps.SetStat(0,50);
         curProps.SetStat(1,80);
         curProps.SetStat(2,2);
         curProps.SetStat(3,9);
         curProps.SetStat(4,35);
         curProps.SetStat(5,5);
         break;
      case 15: // Cobra
         theWeapon.setName("Cobra Launcher");
         theWeapon.setAmmoType(12);
         theWeapon.setWeaponClass(3);

         /* Set properties */
         curProps.SetStat(0,50);
         curProps.SetStat(1,90);
         curProps.SetStat(2,2);
         curProps.SetStat(3,10);
         curProps.SetStat(4,45);
         curProps.SetStat(5,5);
         break;
      case 16: // Blaster
         theWeapon.setName("Blaster");
         theWeapon.setAmmoType(0);
         theWeapon.setWeaponClass(4);

         /* Set properties */
         curProps.SetStat(0,10);
         curProps.SetStat(1,90);
         curProps.SetStat(2,1);
         curProps.SetStat(3,1);
         curProps.SetStat(4,40);
         curProps.SetStat(5,50);
         break;
      case 17: // Negator
         theWeapon.setName("Negator");
         theWeapon.setAmmoType(0);
         theWeapon.setWeaponClass(4);

         /* Set properties */
         curProps.SetStat(0,20);
         curProps.SetStat(1,100);
         curProps.SetStat(2,1);
         curProps.SetStat(3,2);
         curProps.SetStat(4,100);
         curProps.SetStat(5,100);
         break;
      case 18: // Crusher
         theWeapon.setName("Crusher");
         theWeapon.setAmmoType(13);
         theWeapon.setWeaponClass(5);

         /* Set properties */
         curProps.SetStat(0,40);
         curProps.SetStat(1,40);
         curProps.SetStat(2,1);
         curProps.SetStat(3,2);
         curProps.SetStat(4,60);
         curProps.SetStat(5,1);
         break;
      case 19: // Annihilator
         theWeapon.setName("Annihilator");
         theWeapon.setAmmoType(13);
         theWeapon.setWeaponClass(5);

         /* Set properties */
         curProps.SetStat(0,50);
         curProps.SetStat(1,65);
         curProps.SetStat(2,1);
         curProps.SetStat(3,4);
         curProps.SetStat(4,75);
         curProps.SetStat(5,1);
         break;
      }
   } // End of SetWeaponStats(Weapon)
} // End of Rules class.

