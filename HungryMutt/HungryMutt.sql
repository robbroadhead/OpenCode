-- MySQL dump 9.11
--
-- Host: localhost    Database: hungrymutt
-- ------------------------------------------------------
-- Server version	4.0.20a-nt

--
-- Table structure for table `id_table`
--

CREATE TABLE id_table (
  ID_TABLE_ID int(11) NOT NULL default '0',
  TABLE_NAME varchar(255) NOT NULL default '',
  NEXT_ID int(11) default NULL,
  QUANTITY int(11) default NULL,
  PRIMARY KEY  (ID_TABLE_ID),
  UNIQUE KEY TABLE_NAME (TABLE_NAME)
) TYPE=MyISAM;

--
-- Dumping data for table `id_table`
--

INSERT INTO id_table VALUES (101,'Recipes',102,1);
INSERT INTO id_table VALUES (102,'Ratings',106,1);

--
-- Table structure for table `ratings`
--

CREATE TABLE ratings (
  RatingID int(11) NOT NULL auto_increment,
  RecipeID int(11) NOT NULL default '0',
  Rating int(11) NOT NULL default '0',
  IPAddress varchar(15) NOT NULL default '',
  DateRated datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (RatingID)
) TYPE=MyISAM;

--
-- Dumping data for table `ratings`
--

INSERT INTO ratings VALUES (100,100,5,'192.168.000.001','2004-12-12 22:45:00');
INSERT INTO ratings VALUES (102,100,3,'255.255.255.255','2004-12-13 09:53:00');
INSERT INTO ratings VALUES (104,100,9,'127.0.0.1','2004-12-14 00:13:00');

--
-- Table structure for table `recipes`
--

CREATE TABLE recipes (
  RecipeID int(11) NOT NULL auto_increment,
  Title varchar(50) default NULL,
  SubmittedBy varchar(50) default NULL,
  RecipeBody longblob NOT NULL,
  RecipeCategory varchar(50) NOT NULL default '',
  Visible tinyint(1) NOT NULL default '0',
  DateSubmitted datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (RecipeID)
) TYPE=MyISAM;

--
-- Dumping data for table `recipes`
--

INSERT INTO recipes VALUES (100,'Robs Test','Rob','This is a test.','Test Food',1,'2004-12-12 14:08:00');

