
-------------------------------------------------------------------------
--Team
-------------------------------------------------------------------------
drop table if exists Team;

CREATE TABLE Team
(
        teamID INTEGER NOT NULL,
        username VARCHAR (32) default '' NOT NULL,
        pwd VARCHAR (32) default '' NOT NULL,
        adminname VARCHAR (32) default '' NOT NULL,
        adminpwd VARCHAR (32) default '' NOT NULL,
        coach VARCHAR (60) default '' NOT NULL,
        phone VARCHAR (60) default '' NOT NULL,
        acoach VARCHAR (60) default '' NOT NULL,
        aphone VARCHAR (60) default '' NOT NULL,
        teamname VARCHAR (40) default '' NOT NULL,
        season VARCHAR (40) default '' NOT NULL,
        primarycolor VARCHAR (10) default '' NOT NULL,
        secondarycolor VARCHAR (10) default '' NOT NULL,
        piclogo VARCHAR (200) default '' NOT NULL,
        teamtype INTEGER,
    PRIMARY KEY(teamID)
);

-------------------------------------------------------------------------
--Player
-------------------------------------------------------------------------
drop table if exists Player;

CREATE TABLE Player
(
        playerID INTEGER NOT NULL,
        teamID INTEGER NOT NULL,
        jerseynum INTEGER,
        position INTEGER,
        firstname VARCHAR (40) default '' NOT NULL,
        lastname VARCHAR (40) default '' NOT NULL,
        nickname VARCHAR (120) default '' NOT NULL,
        email VARCHAR (150) default '' NOT NULL,
        email2 VARCHAR (150) default '' NOT NULL,
        phone VARCHAR (20) default '' NOT NULL,
        phone2 VARCHAR (20) default '' NOT NULL,
        parentnames VARCHAR (200) default '' NOT NULL,
    PRIMARY KEY(playerID),
    FOREIGN KEY (teamID) REFERENCES Team (teamID)
);

-------------------------------------------------------------------------
--Event
-------------------------------------------------------------------------
drop table if exists Event;

CREATE TABLE Event
(
        eventID INTEGER NOT NULL,
        teamID INTEGER NOT NULL,
        title VARCHAR (40) NOT NULL,
        link VARCHAR (100),
        msg VARCHAR (1000) NOT NULL,
        startdate DATETIME NOT NULL,
        enddate DATETIME NOT NULL,
    PRIMARY KEY(eventID),
    FOREIGN KEY (teamID) REFERENCES Team (teamID),
    INDEX eventdate_idx (startdate)
);

-------------------------------------------------------------------------
--Game
-------------------------------------------------------------------------
drop table if exists Game;

CREATE TABLE Game
(
        gameID INTEGER NOT NULL,
        teamID INTEGER NOT NULL,
        goalieID INTEGER NOT NULL,
        scoreus INTEGER,
        scorethem INTEGER,
        result INTEGER,
        opponent VARCHAR (100) NOT NULL,
        summary VARCHAR (250) NOT NULL,
        period1 VARCHAR (1000) NOT NULL,
        period2 VARCHAR (1000) NOT NULL,
        period3 VARCHAR (1000) NOT NULL,
        overtime VARCHAR (1000) NOT NULL,
        highlight VARCHAR (1000) NOT NULL,
        bottomline VARCHAR (250) NOT NULL,
        star1 VARCHAR (500) NOT NULL,
        star2 VARCHAR (500) NOT NULL,
        star3 VARCHAR (500) NOT NULL,
    PRIMARY KEY(gameID),
    FOREIGN KEY (goalieID) REFERENCES Player (playerID),
    FOREIGN KEY (teamID) REFERENCES Team (teamID)
);

-------------------------------------------------------------------------
--PlayerPoints
-------------------------------------------------------------------------
drop table if exists PlayerPoints;

CREATE TABLE PlayerPoints
(
        playerID INTEGER NOT NULL,
        gameID INTEGER NOT NULL,
        pointtype INTEGER,
    FOREIGN KEY (playerID) REFERENCES Player (playerID),
    FOREIGN KEY (gameID) REFERENCES Game (gameID)
);

-------------------------------------------------------------------------
--Link
-------------------------------------------------------------------------
drop table if exists Link;

CREATE TABLE Link
(
        linkID INTEGER NOT NULL,
        teamID INTEGER NOT NULL,
        title VARCHAR (40) NOT NULL,
        link VARCHAR (250),
    PRIMARY KEY(linkID),
    FOREIGN KEY (teamID) REFERENCES Team (teamID)
);

-------------------------------------------------------------------------
--TeamMsg
-------------------------------------------------------------------------
drop table if exists TeamMsg;

CREATE TABLE TeamMsg
(
        msgID INTEGER NOT NULL,
        teamID INTEGER NOT NULL,
        title VARCHAR (40) NOT NULL,
        link VARCHAR (250),
        msg VARCHAR (2000) NOT NULL,
        expiredate DATETIME NOT NULL,
    PRIMARY KEY(msgID),
    FOREIGN KEY (teamID) REFERENCES Team (teamID)
);

-------------------------------------------------------------------------
--GameComments
-------------------------------------------------------------------------
drop table if exists GameComments;

CREATE TABLE GameComments
(
        commentID INTEGER NOT NULL,
        gameID INTEGER NOT NULL,
        msg VARCHAR (500) NOT NULL,
    PRIMARY KEY(commentID),
    FOREIGN KEY (gameID) REFERENCES Game (gameID)
);

-------------------------------------------------------------------------
--lkpTeamType
-------------------------------------------------------------------------
drop table if exists lkpTeamType;

CREATE TABLE lkpTeamType
(
        ID INTEGER NOT NULL,
        name VARCHAR (20) default '' NOT NULL,
        description VARCHAR (50) default '' NOT NULL,
    PRIMARY KEY(ID)
);

-------------------------------------------------------------------------
--lkpPosition
-------------------------------------------------------------------------
drop table if exists lkpPosition;

CREATE TABLE lkpPosition
(
        ID INTEGER NOT NULL,
        name VARCHAR (20) default '' NOT NULL,
        description VARCHAR (50) default '' NOT NULL,
    PRIMARY KEY(ID)
);

-------------------------------------------------------------------------
--lkpResultType
-------------------------------------------------------------------------
drop table if exists lkpResultType;

CREATE TABLE lkpResultType
(
        ID INTEGER NOT NULL,
        name VARCHAR (20) default '' NOT NULL,
        description VARCHAR (50) default '' NOT NULL,
    PRIMARY KEY(ID)
);
  
  
  
  
  
  
  
  
  
  
  
