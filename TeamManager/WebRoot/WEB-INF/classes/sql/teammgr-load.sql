drop table if exists ID_TABLE;

CREATE TABLE ID_TABLE
(
        id_table_id INTEGER NOT NULL,
        table_name VARCHAR (255) default '' NOT NULL,
        next_id INTEGER,
        quantity INTEGER,
    PRIMARY KEY(id_table_id)
);

insert into lkpTeamType (ID, name, description) VALUES (1, 'Hockey', 'Hockey Team');
insert into lkpTeamType (ID, name, description) VALUES (2, 'Football', 'Football Team');
insert into lkpTeamType (ID, name, description) VALUES (3, 'Basketball', 'Basketball Team');
insert into lkpTeamType (ID, name, description) VALUES (4, 'Soccer', 'Soccer Team');
insert into lkpTeamType (ID, name, description) VALUES (5, 'Rugby', 'Rugby Team');
insert into lkpTeamType (ID, name, description) VALUES (6, 'Swimming', 'Swim Team');

insert into lkpResultType(ID,name,description) values (1,'Win','Win');
insert into lkpResultType(ID,name,description) values (2,'Loss','Loss');
insert into lkpResultType(ID,name,description) values (3,'Tie','Tie');
insert into lkpResultType(ID,name,description) values (4,'OTL','Overtime Loss');

insert into lkpPosition(ID,name,description) values (0,'O/D','Any');
insert into lkpPosition(ID,name,description) values (1,'G','Goalie');
insert into lkpPosition(ID,name,description) values (2,'O','Offense');
insert into lkpPosition(ID,name,description) values (3,'W','Wing');
insert into lkpPosition(ID,name,description) values (4,'LW','Left Wing');
insert into lkpPosition(ID,name,description) values (5,'RW','Right Wing');
insert into lkpPosition(ID,name,description) values (6,'C','Center');
insert into lkpPosition(ID,name,description) values (7,'D','Defense');

-- Alter statements for version 2.0
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


alter table Team add primarycolor VARCHAR (10) default '' NOT NULL;
alter table Team add secondarycolor VARCHAR (10) default '' NOT NULL;
update Team set primarycolor=colors;

alter table Player modify position INTEGER default 0;
alter table Player add email2 VARCHAR (150) default '' NOT NULL;
alter table Player add phone2 VARCHAR (20) default '' NOT NULL;

alter table Game add goalieid integer NULL;

alter table Team delete colors VARCHAR (10) default '' NOT NULL;

