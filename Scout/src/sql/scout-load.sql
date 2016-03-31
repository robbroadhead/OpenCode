insert into lkpMeasure (measureID,fraction, name, description) VALUES (101, 1, 'Day', '1 workday');
insert into lkpMeasure (measureID,fraction, name, description) VALUES (102, 8, 'Hour', '1 man hour');
insert into lkpMeasure (measureID,fraction, name, description) VALUES (103, 480, 'Minutes', '1 man minute');

insert into lkpType (typeID, name, description) VALUES (101, 'Design', 'Design related task.');
insert into lkpType (typeID, name, description) VALUES (102, 'Document', 'Document related task.');
insert into lkpType (typeID, name, description) VALUES (103, 'Test', 'Testing related task.');
insert into lkpType (typeID, name, description) VALUES (104, 'Support', 'Support/Maintenance related task.');
insert into lkpType (typeID, name, description) VALUES (105, 'Milestone', 'Task that is a milestone.');
insert into lkpType (typeID, name, description) VALUES (106, 'Artifact', 'Task that is delivery of an artifact.');
insert into lkpType (typeID, name, description) VALUES (107, 'Implement', 'Implementation task.');
insert into lkpType (typeID, name, description) VALUES (108, 'Defect', 'Fixing a defect.');

insert into lkpStatus (statusID, name, description) VALUES (101, 'New', 'Newly entered task.');
insert into lkpStatus (statusID, name, description) VALUES (102, 'Open', 'Task is being worked on.');
insert into lkpStatus (statusID, name, description) VALUES (103, 'Closed', 'Task completed.');
insert into lkpStatus (statusID, name, description) VALUES (104, 'Cancelled', 'Task was cancelled.');
insert into lkpStatus (statusID, name, description) VALUES (105, 'Deferred', 'Task deferred to a later time.');
insert into lkpStatus (statusID, name, description) VALUES (106, 'Enhancement', 'Task is an enhancement to be addressed later.');
insert into lkpStatus (statusID, name, description) VALUES (107, 'Test', 'Task is ready for testing.');
insert into lkpStatus (statusID, name, description) VALUES (108, 'Failed', 'Task failed testing.');
insert into lkpStatus (statusID, name, description) VALUES (109, 'Deploy', 'Task ready for deployment.');
insert into lkpStatus (statusID, name, description) VALUES (110, 'Signature', 'Task ready for/waiting on sign-off.');
insert into lkpStatus (statusID, name, description) VALUES (111, 'Verified', 'Task passed testing, ready for production.');

insert into lkpPriority (priorityID, name, description) VALUES (101, 'Critical', 'Highest priority task.');
insert into lkpPriority (priorityID, name, description) VALUES (102, 'High', 'High priority task.');
insert into lkpPriority (priorityID, name, description) VALUES (103, 'Average', 'Average priority task.');
insert into lkpPriority (priorityID, name, description) VALUES (104, 'Low', 'Low priority task.');
insert into lkpPriority (priorityID, name, description) VALUES (105, 'Nice To Have', 'Lowest priority task.');

-- Add a dummy project and task.
--INSERT INTO Project (projectID, name, startdate, created) VALUES  ( 0, 'Undefined',  '1980-01-01',  '1980-01-01' );
--INSERT INTO Task (taskID, projectID, created, statusID, typeID, summary, detail) VALUES  (0, 0,  '1980-01-01', 103, 101,'Undefined','Place holder for an empty task.' );

INSERT INTO Groups (groupID,created,displayname,detail) VALUES (0,'1980-01-01','No Group','An Empty Group');
INSERT INTO Users (userID, externalID, created,firstname, lastname, displayname, detail) VALUES (0, 0,'1980-01-01','User','Empty','No User','An Empty User');
    