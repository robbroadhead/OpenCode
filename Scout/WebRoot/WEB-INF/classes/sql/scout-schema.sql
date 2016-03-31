--GRANT ALL PRIVILEGES ON *.* to 'scoutuser'@'%'
--IDENTIFIED BY 'bugtracker' WITH GRANT OPTION;
--GRANT ALL PRIVILEGES ON *.* to 'scoutuser'@'localhost'
--IDENTIFIED BY 'bugtracker' WITH GRANT OPTION;

-------------------------------------------------------------------------
--Project
-------------------------------------------------------------------------
drop table if exists Project;

CREATE TABLE Project
(
        projectID INTEGER NOT NULL,
        name VARCHAR (50) default '' NOT NULL,
        startdate DATETIME NOT NULL,
        created TIMESTAMP NOT NULL,
    PRIMARY KEY(projectID),
    INDEX project_name (name)
);

-------------------------------------------------------------------------
--Task
-------------------------------------------------------------------------
drop table if exists Task;

CREATE TABLE Task
(
        taskID INTEGER NOT NULL,
        projectID INTEGER NOT NULL,
        created TIMESTAMP NOT NULL,
        statusID INTEGER NOT NULL,
        typeID INTEGER NOT NULL,
        summary VARCHAR (80) default '' NOT NULL,
        detail VARCHAR (2000) default '' NOT NULL,
    PRIMARY KEY(taskID),
    FOREIGN KEY (projectID) REFERENCES Project (projectID),
    FOREIGN KEY (typeID) REFERENCES lkpType (typeID),
    FOREIGN KEY (statusID) REFERENCES lkpStatus (statusID)
);

-------------------------------------------------------------------------
--Users
-------------------------------------------------------------------------
drop table if exists Users;

CREATE TABLE Users
(
        userID INTEGER NOT NULL,
        externalID INTEGER NOT NULL,
        created TIMESTAMP NOT NULL,
        firstname VARCHAR (40) default '' NOT NULL,
        lastname VARCHAR (40) default '' NOT NULL,
        displayname VARCHAR (80) default '' NOT NULL,
        detail VARCHAR (250) default '' NOT NULL,
    PRIMARY KEY(userID)
);

-------------------------------------------------------------------------
--Groups
-------------------------------------------------------------------------
drop table if exists Groups;

CREATE TABLE Groups
(
        groupID INTEGER NOT NULL,
        created TIMESTAMP NOT NULL,
        displayname VARCHAR (80) default '' NOT NULL,
        detail VARCHAR (250) default '' NOT NULL,
    PRIMARY KEY(groupID)
);

-------------------------------------------------------------------------
--TaskEstimate
-------------------------------------------------------------------------
drop table if exists TaskEstimate;

CREATE TABLE TaskEstimate
(
        estimateID INTEGER NOT NULL,
        taskID INTEGER NOT NULL,
        priorityID INTEGER NOT NULL,
        startdate DATETIME NOT NULL,
        enddate DATETIME NOT NULL,
        timeValue INTEGER,
        measureID INTEGER,
        cost INTEGER,
    PRIMARY KEY(estimateID),
    FOREIGN KEY (taskID) REFERENCES Task (taskID),
    FOREIGN KEY (priorityID) REFERENCES lkpPriority (priorityID),
    FOREIGN KEY (measureID) REFERENCES lkpMeasure (measureID)
);

-------------------------------------------------------------------------
--TaskActual
-------------------------------------------------------------------------
drop table if exists TaskActual;

CREATE TABLE TaskActual
(
        estimateID INTEGER NOT NULL,
        taskID INTEGER NOT NULL,
        priorityID INTEGER NOT NULL,
        startdate DATETIME NOT NULL,
        enddate DATETIME NOT NULL,
        timeValue INTEGER,
        measureID INTEGER,
        cost INTEGER,
    PRIMARY KEY(estimateID),
    FOREIGN KEY (taskID) REFERENCES Task (taskID),
    FOREIGN KEY (priorityID) REFERENCES lkpPriority (priorityID),
    FOREIGN KEY (measureID) REFERENCES lkpMeasure (measureID)
);

-------------------------------------------------------------------------
--TaskNotes
-------------------------------------------------------------------------
drop table if exists TaskNotes;

CREATE TABLE TaskNotes
(
        noteID INTEGER NOT NULL,
        taskID INTEGER NOT NULL,
        notedate DATETIME NOT NULL,
        detail VARCHAR (2000) NOT NULL,
    PRIMARY KEY(noteID),
    FOREIGN KEY (taskID) REFERENCES Task (taskID)
);

-------------------------------------------------------------------------
--TaskLinks
-------------------------------------------------------------------------
drop table if exists TaskLinks;

CREATE TABLE TaskLinks
(
        linkID INTEGER NOT NULL,
        firstID INTEGER NOT NULL,
        nextID INTEGER NOT NULL,
    PRIMARY KEY(linkID),
    FOREIGN KEY (firstID) REFERENCES Task (taskID),
    FOREIGN KEY (nextID) REFERENCES Task (taskID)
);

-------------------------------------------------------------------------
--lkpType
-------------------------------------------------------------------------
drop table if exists lkpType;

CREATE TABLE lkpType
(
        typeID INTEGER NOT NULL,
        name VARCHAR (20) default '' NOT NULL,
        description VARCHAR (50) default '' NOT NULL,
    PRIMARY KEY(typeID)
);

-------------------------------------------------------------------------
--lkpMeasure
-------------------------------------------------------------------------
drop table if exists lkpMeasure;

CREATE TABLE lkpMeasure
(
        measureID INTEGER NOT NULL,
        fraction INTEGER NOT NULL,
        name VARCHAR (20) default '' NOT NULL,
        description VARCHAR (50) default '' NOT NULL,
    PRIMARY KEY(measureID)
);

-------------------------------------------------------------------------
--lkpStatus
-------------------------------------------------------------------------
drop table if exists lkpStatus;

CREATE TABLE lkpStatus
(
        statusID INTEGER NOT NULL,
        name VARCHAR (20) default '' NOT NULL,
        description VARCHAR (50) default '' NOT NULL,
    PRIMARY KEY(statusID)
);

-------------------------------------------------------------------------
--lkpPriority
-------------------------------------------------------------------------
drop table if exists lkpPriority;

CREATE TABLE lkpPriority
(
        priorityID INTEGER NOT NULL,
        name VARCHAR (20) default '' NOT NULL,
        description VARCHAR (50) default '' NOT NULL,
    PRIMARY KEY(priorityID)
);

-------------------------------------------------------------------------
--UserGroup
-------------------------------------------------------------------------
drop table if exists UserGroup;

CREATE TABLE UserGroup
(
        userID INTEGER NOT NULL,
        groupID INTEGER NOT NULL,
    FOREIGN KEY (groupID) REFERENCES Groups (groupID),
    FOREIGN KEY (userID) REFERENCES Users (userID)
);

-------------------------------------------------------------------------
--UserTask
-------------------------------------------------------------------------
drop table if exists UserTask;

CREATE TABLE UserTask
(
        userID INTEGER NOT NULL,
        taskID INTEGER NOT NULL,
    FOREIGN KEY (taskID) REFERENCES Task (taskID),
    FOREIGN KEY (userID) REFERENCES Users (userID)
);

-------------------------------------------------------------------------
--GroupProject
-------------------------------------------------------------------------
drop table if exists GroupProject;

CREATE TABLE GroupProject
(
        groupID INTEGER NOT NULL,
        projectID INTEGER NOT NULL,
    FOREIGN KEY (groupID) REFERENCES Groups (groupID),
    FOREIGN KEY (projectID) REFERENCES Project (projectID)
);
 
-------------------------------------------------------------------------
--UserProject
-------------------------------------------------------------------------
drop table if exists UserProject;

CREATE TABLE UserProject
(
        userID INTEGER NOT NULL,
        projectID INTEGER NOT NULL,
    FOREIGN KEY (projectID) REFERENCES Project (projectID),
    FOREIGN KEY (userID) REFERENCES Users (userID)
);

-------------------------------------------------------------------------
--GroupTask
-------------------------------------------------------------------------
drop table if exists GroupTask;

CREATE TABLE GroupTask
(
        groupID INTEGER NOT NULL,
        taskID INTEGER NOT NULL,
    FOREIGN KEY (groupID) REFERENCES Groups (groupID),
    FOREIGN KEY (taskID) REFERENCES Task (taskID)
);
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
