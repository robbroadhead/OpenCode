#! /usr/bin/perl
# Linux Users administration tool
# Written by: Rob Broadhead 5-20-1996
# Version 1.5: 6-1-1997
# $Id: AdminTool.pm,v 1.17 1997/11/04 23:32:31 Rob Exp Rob $
#
$result = 'a';
while  ($result ne 'q') {
    $theID = $<;
    # Get the user id.
    if ($theID eq 0) {
	# If user is root.
	$result = &MainMenu;
    }
    else {
	# Set the current user to the one running
	# this program.
	$curUser = &GetUserName($theID);

	# Otherwise we limit the choices.
	$result = &UserMenu;
    }
}

# Clean up the temporary files if the
# temporary files exist.
stat("/tmp/temp.lst");
system("rm /tmp/temp.lst") if -e _;


#**************************************
sub MainMenu {
#**************************************
    system 'clear';
    chop($prompt= <<'EOP');
Select Task:
    a) List Users
    b) List Groups
    c) Edit User Information
    d) Edit Group Information
    q) Quit Program
Select an option[q]:
EOP

    print $prompt;
($ans = substr(<STDIN>,0,1)) =~ y/A-Z/a-z/;
{
    system 'clear';
    &ListUser if $ans eq 'a';
    &ListGroups if $ans eq 'b';
    if ($ans eq 'c') {
	# Make sure we clear the user here to avoid 
	# never being able to set it.
	$curUser = '';
	while  ($result ne 'q') {
	    $result = &EditUserMenu;
            $ans = 'c';
	}
    }
    if ($ans eq 'd') {
        # Make sure we clear the group here to avoid 
	# never being able to set it.
        $curGroup = '';
	while  ($result ne 'q') {
            $result = &EditGroupMenu;
            $ans = 'd';
	}
    }
}
# setup for the return value.
$answer = $ans;
} # End of MainMenu subroutine.


#**************************************
sub UserMenu {
# Current User is always the user
# running the application.
#**************************************
    system 'clear';
    chop($prompt= <<'EOP');
Select Task:
    a) Edit Project
    b) Edit Plan
    c) List Users
    d) List Groups
    e) User Information
    q) Quit Program
Select an option[q]:
EOP

    print $prompt;
($ans = substr(<STDIN>,0,1)) =~ y/A-Z/a-z/;
{
    system 'clear';
    &EditUserProject($curUser) if $ans eq 'a';
    &EditUserPlan($curUser) if $ans eq 'b';
    &ListUser if $ans eq 'c';
    &ListGroups if $ans eq 'd';
    &FingerUser($curUser) if $ans eq 'e';
    if ($ans eq 'x') {
        print "That feature has not been implemented!\n";
        getc();
    }
}
# setup for the return value.
$answer = $ans;
} # End of UserMenu subroutine.


#**************************************
sub DeleteGroup {
#**************************************
    $theName = $_[0];
    system 'clear';
# Delete the group
    $oldGroup = $theName;
    &GroupUpdate("DELETE",$theGroup);
# Alter owner of all of the files owned by user
    &GroupFiles($oldGroup);
    print "Select new group to change files to: ";
    $newGroup = &SelectGroup;
    print "Changing group to ".$newGroup."\n";    
    open(tempFile,"/tmp/temp.lst") || die "Unable to open temporary file.\n";
    while (<tempFile>) {
	chop;
	$prog1 = "chgrp ".$newGroup." ".$_."\n";
	system $prog1;
    }

# back up the group file
    system("cp /etc/group /etc/group.bak");
# copy the temp to the actual group
    system("cp /etc/group.new /etc/group");
    print "\nGroup was removed!";
    getc();
} # End of DeleteGroup subroutine


#**************************************
sub DeleteUser {
#**************************************
    system 'clear';
    $theUser = &SelectUser;
    open(passFile,"/etc/passwd") || die "Unable to open passwd file.\n";
    open(newPassFile,">/etc/passwd.new") || die "Unable to create passwd.new file.\n";
    while (<passFile>) {
	chop;
	($login,$passwd,$uid,$gid,$name,$dir,$shell) = split(/:/);
	next if $login eq '';
	if ($login eq $theUser) {
	    next;
	}
	else
	{
	    print newPassFile "$login:$passwd:$uid:$gid:$name:$dir:$shell\n" || do {
		unlink newPassFile;
		die "Can't write to passwd.new!";
	    };
	}
    }
# Now we need to take care of the entry in the shadow file
    $exists = "F";
    stat("/etc/shadow");
    $exists = "T" if -e _;
    if ($exists eq "T") {
	open(shadowFile,"/etc/shadow") || die "Unable to open shadow file.\n";
	open(newShadowFile,">/etc/shadow.new") || die "Unable to create shadow.new file.\n";
	while (<shadowFile>) {
	    chop;
	    ($login,$passwd,$last,$until,$before,$warn,$inactive,$expire,$flag) = split(/:/);
	    next if $login eq '';
	    if ($login eq $theUser) {
		next;
	    }
	    else
	    {
		print newShadowFile "$login:$passwd:$last:$until:$before:$warn:$inactive:$expire:$flag\n" || do {
		    unlink newShadowFile;
		    die "Can't write to shadow.new!";
		};
	    }
	}
    }
# close the files.
    close newPassFile;
    close passFile;
    close shadowFile;
    close newShadowFile;
# Alter owner of all of the files owned by user
    &UserFiles($theUser);
    print "Select new owner for files : ";
    $newUser = &SelectUser;
    print "Changing owner to ".$newUser."\n";    
    open(tempFile,"/tmp/temp.lst") || die "Unable to open temporary file.\n";
    while (<tempFile>) {
	chop;
	$prog1 = "chown ".$newUser." ".$_."\n";
	system $prog1;
    }

# back up the passwd file
    system("cp /etc/passwd /etc/passwd.bak");
# copy the temp to the actual passwd
    system("cp /etc/passwd.new /etc/passwd");
# Get the shadow file if need be.
    if ($exists eq "T") {
	system("cp /etc/shadow /etc/shadow.bak");
	system("cp /etc/shadow.new /etc/shadow");
    }
    
    print "\nUser was removed!";
    getc();
} # End of DeleteUser subroutine


#**************************************
sub EditGroupMenu {
#**************************************
    system 'clear';
    chop($prompt= <<'EOP');
Select Task:
    a) Select Group
    b) Add a Group
    c) Remove a Group
    d) List Group Members
    e) Edit Group Name
    f) Edit Group ID
    g) Add Group Members
    h) Remove Group Members
    q) Quit to Main Menu
Select an option
EOP

    print $prompt."[".$curGroup."]:";
($ans = substr(<STDIN>,0,1)) =~ y/A-Z/a-z/;
{
    system 'clear';
    $curGroup = &SelectGroup if $ans eq 'a';
    if (($ans =~/[cdefgh]/) && ($curGroup eq '')) {
        print "You must select a current group.\n";
	getc();
    }
    else {
	&AddGroup if $ans eq 'b';
	&DeleteGroup($curGroup) if $ans eq 'c';
	&ListMembers($curGroup) if $ans eq 'd';
	&EditGroupName($curGroup) if $ans eq 'e';
	&EditGroupId($curGroup) if $ans eq 'f';
	&AddMembers($curGroup) if $ans eq 'g';
	&RemoveMembers($curGroup) if $ans eq 'h';
    }
}
# setup for the return value.
$answer = $ans;
} # End of EditGroupMenu subroutine.


#**************************************
sub EditUserMenu {
#**************************************
    system 'clear';
    chop($prompt= <<'EOP');
Select Task:
    a) Select User
    b) Add a User
    c) Remove a User
    d) Edit User Name
    e) Edit User Shell
    f) Edit Home Directory
    g) Change Group
    h) Edit User Plan
    i) Edit User Project
    j) Edit User Login
    k) Edit User Id
    l) User Information
    q) Quit to Main Menu
Select an option
EOP

    print $prompt."[".$curUser."]:";
($ans = substr(<STDIN>,0,1)) =~ y/A-Z/a-z/;
{
    system 'clear';
    $curUser = &SelectUser if $ans eq 'a';
    if (($ans =~/[cdefghikl]/) && ($curUser eq '')) {
	print "You must select a current user.\n";
	getc();
    }
    else {
	(system '/sbin/adduser') if $ans eq 'b';
	&DeleteUser if $ans eq 'c';
	&EditUserName($curUser) if $ans eq 'd';
	&EditUserShell($curUser) if $ans eq 'e';
	&EditUserHome($curUser) if $ans eq 'f';
	&EditUserGroup($curUser) if $ans eq 'g';
	&EditUserPlan($curUser) if $ans eq 'h';
	&EditUserProject($curUser) if $ans eq 'i';
	&EditUserLogin($curUser) if $ans eq 'j';
	&EditUserId($curUser) if $ans eq 'k';
        &FingerUser($curUser) if $ans eq 'l';
	if ($ans eq 'x') {
	    print "That feature has not been implemented!\n";
	    getc();
	}
    }
}
# setup for the return value.
$answer = $ans;
} # End of EditUserMenu subroutine.


#**************************************
sub ValidateGroupId {
#**************************************
    $testVal = $_[0];
    $theId = $testVal;
    open(groupFile,"/etc/group") || die "Unable to open group file.\n";
    while (<groupFile>) {
	chop;
	($name,$passwd,$gid,$members) = split(/:/);
        if ($gid eq $testVal) {$theId = ''};
	next;
    }
    
    # Return the result.
    return $theId;
} # End of ValidateGroupId subroutine.


#**************************************
sub ValidateId {
#**************************************
    $testVal = $_[0];
    $theId = $testVal;
    open(userFile,"/etc/passwd") || die "Unable to open user file.\n";
    while (<userFile>) {
	chop;
	($login,$passwd,$uid,$gid,$name,$dir,$shell) = split(/:/);
	if ($uid eq $testVal) {$theId = ''};
	next;
    }
    
    # Return the result.
    return $theId;
} # End of ValidateId subroutine.


#**************************************
sub GetHomeDir {
#**************************************
    $curUser = $_[0];
    $theDir = '';
    open(userFile,"/etc/passwd") || die "Unable to open user file.\n";
    while (<userFile>) {
	chop;
	($login,$passwd,$uid,$gid,$name,$dir,$shell) = split(/:/);
        if ($login eq $curUser) {$theDir = $dir};
	next;
    }
    
    # Return the result.
    return $theDir;
} # End of GetHomeDir subroutine.


#**************************************
sub GetUserName {
#**************************************
    $curId = $_[0];
    $theUser = '';
    open(userFile,"/etc/passwd") || die "Unable to open user file.\n";
    while (<userFile>) {
	chop;
	($login,$passwd,$uid,$gid,$name,$dir,$shell) = split(/:/);
        if ($uid eq $curId) {$theUser = $login};
	next;
    }
    
    # Return the result.
    return $theUser;
} # End of GetUserName subroutine.


#**************************************
sub UpdatePassword {
#**************************************
    $theAttribute = $_[0];
    $newValue = $_[1];
    open(passFile,"/etc/passwd") || die "Unable to open passwd file.\n";
    open(newPassFile,">/etc/passwd.new") || die "Unable to create passwd.new file.\n";
    while (<passFile>) {
	chop;
	($login,$passwd,$uid,$gid,$name,$dir,$shell) = split(/:/);
	next if $login eq '';
	if ($login eq $curUser) {
	    if ($theAttribute eq "HOME")
		{ print newPassFile "$login:$passwd:$uid:$gid:$name:$newValue:$shell\n" || do {
		unlink newPassFile;
		die "Can't write to passwd.new!";
	    }}
	    elsif ($theAttribute eq "GROUP")
		{ print newPassFile "$login:$passwd:$uid:$newValue:$name:$dir:$shell\n" || do {
		unlink newPassFile;
		die "Can't write to passwd.new!";
	    }}
	    elsif ($theAttribute eq "SHELL")
		{ print newPassFile "$login:$passwd:$uid:$gid:$name:$dir:$newValue\n" || do {
		unlink newPassFile;
		die "Can't write to passwd.new!";
	    }}
	    elsif ($theAttribute eq "ID")
		{ print newPassFile "$login:$passwd:$newValue:$gid:$name:$dir:$shell\n" || do {
		unlink newPassFile;
		die "Can't write to passwd.new!";
	    }}
	    elsif ($theAttribute eq "NAME")
		{ print newPassFile "$login:$passwd:$uid:$gid:$newValue:$dir:$shell\n" || do {
		unlink newPassFile;
		die "Can't write to passwd.new!";
	    }}
            elsif ($theAttribute eq "LOGIN")
                { print newPassFile "$newValue:$passwd:$uid:$gid:$name:$dir:$shell\n" || do {
		unlink newPassFile;
		die "Can't write to passwd.new!";
	    }}
	}
	else
	{
	    print newPassFile "$login:$passwd:$uid:$gid:$name:$dir:$shell\n" || do {
		unlink newPassFile;
		die "Can't write to passwd.new!";
	    };
	}
    }

# We need to adjust the shadow file if it exists
# and the login is being adjusted.
    $exists = "F";
    stat("/etc/shadow");
    $exists = "T" if -e _;
    if (($exists eq "T") && ($theAttribute eq "LOGIN")) {
	open(shadowFile,"/etc/shadow") || die "Unable to open shadow file.\n";
	open(newShadowFile,">/etc/shadow.new") || die "Unable to create shadow.new file.\n";
	while (<shadowFile>) {
	    chop;
	    ($login,$passwd,$last,$until,$before,$warn,$inactive,$expire,$flag) = split(/:/);
	    next if $login eq '';
	    if ($login eq $curUser) {
                print newShadowFile "$newValue:$passwd:$last:$until:$before:$warn:$inactive:$expire:$flag\n" || do {
		    unlink newShadowFile;
		    die "Can't write to shadow.new!";
		}
	    }
	    else
	    {
		print newShadowFile "$login:$passwd:$last:$until:$before:$warn:$inactive:$expire:$flag\n" || do {
		    unlink newShadowFile;
		    die "Can't write to shadow.new!";
		};
	    }
	}
    }

# close the files.
    close newPassFile;
    close passFile;

# Deal with shadow files if needed
    if ($exists eq "T") {
	close newShadowFile;
	close ShadowFile;

	system("cp /etc/shadow /etc/shadow.bak");
	system("cp /etc/shadow.new /etc/shadow");
    }
    
# back up the passwd file
    system("cp /etc/passwd /etc/passwd.bak");
# copy the temp to the actual passwd
    system("cp /etc/passwd.new /etc/passwd");
} # End of UpdatePassword subroutine.


#**************************************
sub EditUserHome {
#**************************************
    system 'clear';
    $theUser = $_[0];
    chop($prompt= <<'EOP');
Enter the new home directory?:
EOP
    print $prompt."[".$curUser."]";
    ($newHome = <STDIN>);
    chop($newHome);
    &UpdatePassword("HOME",$newHome);
    print "\nUser home was changed!";
    getc();
} # End of EditUserHome subroutine.


#**************************************
sub FingerUser {
#**************************************
    system 'clear';
    $theUser = $_[0];
    $prog1 = "finger ".$theUser;
    system($prog1);
    print "\nPress <Enter> to continue";
    getc();
} # End of FingerUser subroutine.


#**************************************
sub EditUserPlan {
#**************************************
    system 'clear';
    $theUser = $_[0];
# Get the home directory.
    $planLoc = ">".&GetHomeDir($theUser);
    $planLoc = $planLoc."/.plan";
# Get the plan file.
    open(planFile,$planLoc) || die "Unable to open plan file.\n";
    
    chop($prompt= <<'EOP');
Enter the new plan :
EOP
    print $prompt."[".$curUser."]";
    ($newPlan = <STDIN>);
    chop($newPlan);

# Save the plan file.
    print planFile $newPlan;
# Close the file.
    close planFile;
    
    print "\nUser plan was changed!";
    getc();
} # End of EditUserPlan subroutine.


#**************************************
sub EditUserProject {
#**************************************
    system 'clear';
    $theUser = $_[0];
# Get the home directory.
    $projectLoc = ">".&GetHomeDir($theUser);
    $projectLoc = $projectLoc."/.project";
# Get the project file.
    open(projectFile,$projectLoc) || die "Unable to open project file.\n";

    chop($prompt= <<'EOP');
Enter the new project :
EOP
    print $prompt."[".$curUser."]";
    ($newProject = <STDIN>);
    chop($newProject);
    
# Save the project file.
    print projectFile $newProject;
# Close the file.
    close projectFile;
    
    print "\nUser project was changed!";
    getc();
} # End of EditUserProject subroutine.


#**************************************
sub EditUserLogin {
#**************************************
    system 'clear';
    $theUser = $_[0];
    chop($prompt= <<'EOP');
Enter the new login?:
EOP
    print $prompt."[".$curUser."]";
    ($newLogin = <STDIN>);
    chop($newLogin);
    &UpdatePassword("LOGIN",$newLogin);
    print "\nUser login was changed!";
    getc();
} # End of EditUserLogin subroutine.


#**************************************
sub EditUserId {
#**************************************
    system 'clear';
    $theUser = $_[0];
    $newId = '';
    while ($newId eq '') {
	chop($prompt= <<'EOP');
Enter the new ID:
EOP
	print $prompt."[".$curUser."]";
	($newId = <STDIN>);
	chop($newId);
	$newId = &ValidateId($newId);
    }    
    &UpdatePassword("ID",$newId);
# Alter owner of all of the files owned by user
    &UserFiles($curUser);
    print "Updating files for ".$curUser."\n";    
    open(tempFile,"/tmp/temp.lst") || die "Unable to open temporary file.\n";
    while (<tempFile>) {
	chop;
	$prog1 = "chown ".$curUser." ".$_."\n";
	system $prog1;
    }


    print "\nUser ID was changed!";
    getc();
} # End of EditUserId subroutine.


#**************************************
sub EditUserGroup {
#**************************************
    system 'clear';
    $theUser = $_[0];
    chop($prompt= <<'EOP');
Enter the new group?:
EOP
    print $prompt."[".$curUser."]";
    ($newGroup = <STDIN>);
    chop($newGroup);
# Convert Group to an id
    setgrent;
    ($name,$passwd,$newId,$members) = getgrnam($newGroup);
    if ($newId eq '') {
	print "Group does not exist! Try Again!\n";
	getc();
	return;
    } 
    &UpdatePassword("GROUP",$newId);
    print "\nUser group was changed!";
    getc();
} # End of EditUserGroup subroutine.


#**************************************
sub EditUserShell {
#**************************************
    system 'clear';
    $theUser = $_[0];
    chop($prompt= <<'EOP');
Enter the new path and shell?:
EOP
    print $prompt."[".$curUser."]";
    ($newShell = <STDIN>);
    chop($newShell);
    &UpdatePassword("SHELL",$newShell);
    print "\nUser shell was changed!";
    getc();
} # End of EditUserShell subroutine.


#**************************************
sub EditUserName {
#**************************************
    system 'clear';
    $theUser = $_[0];
    chop($prompt= <<'EOP');
Enter the new user name?:
EOP
    print $prompt."[".$curUser."]";
    ($newName = <STDIN>);
    chop($newName);
    &UpdatePassword("NAME",$newName);
    $curUser = $newName;
    print "\nUser name was changed!";
    getc();
} # End of EditUserName subroutine.


#**************************************
sub ListGroups {
#**************************************
    system 'clear';
    setgrent;
    while(($name,$passwd,$gid,$members) = getgrent) {
	if (length($name) < 10) {
	    print "name:",$name,"         \tGID:",$gid,"\n";
	}
	else
	{
	    print "name:",$name,"\tGID:",$gid,"\n";
	}
    }
    print "\nHit [enter] to continue";
    getc();
    
} # End of ListGroups subroutine.


#**************************************
sub SelectUser {
#**************************************
    $theUser = "";
    while ($theUser eq "") {
	system 'clear';
	setpwent;
	while(($login,$passwd,$uid,$gid,$gcos,$scrap,$name,$dir,$shell) = getpwent) {
	($userName,$pw,$lastchg,$chgAllow,$chgReq,$dayWarn,$dayInact,$dateExp,$flag) = split(/,/,$name);
	    if (length($login) < 10) {
		print "Login:",$login,"        \tName:",$userName,"\n";
	    }
	    else
	    {
		print "Login:",$login,"\tName:",$userName,"\n";
	    }
	}
	
	chop($prompt= <<'EOP');
Which user do you wish to view? [Login]:
EOP
    print $prompt;
	($theUser = <STDIN>);
	chop($theUser);
	$theUser = &ValidateUser($theUser);
    }
    
    # Set up for the return value.
    return $theUser;
} # End of SelectUser subroutine.


#**************************************
sub SelectGroup {
#**************************************
    $theGroup = "";
    while ($theGroup eq "") {
	system 'clear';
	setgrent;
	while(($name,$passwd,$gid,$members) = getgrent) {
	    if (length($name) < 10) {
		print "name:",$name,"         \tGID:",$gid,"\n";
	    }
	    else
	    {
		print "name:",$name,"\tGID:",$gid,"\n";
	    }
	}
	chop($prompt= <<'EOP');
Select a group? [name]:
EOP
    print $prompt;
	($theGroup = <STDIN>);
	chop($theGroup);
	$theGroup = &ValidateGroup($theGroup);
    }

    # Set up for the return value.
    return $theGroup;
} # End of SelectGroup subroutine.


#**************************************
sub ValidateGroup {
#**************************************
    $theGroup = $_[0];
    $resultGroup = "";
    setgrent;
    while(($name,$passwd,$gid,$members) = getgrent) {
        if ($name eq $theGroup) {
	    $resultGroup = $theGroup;
	}
    }
    
    # Set up for the return value.
    return $resultGroup;
} # End of ValidateGroup subroutine.


#**************************************
sub ValidateUser {
#**************************************
    $theUser = $_[0];
    $resultUser = "";
    setpwent;
    while(($login,$passwd,$uid,$gid,$gcos,$scrap,$name,$dir,$shell) = getpwent) {
        if ($login eq $theUser) {
	    $resultUser = $theUser;
	}
    }
    
    # Set up for the return value.
    return $resultUser;
} # End of ValidateUser subroutine.


#**************************************
sub ListUser {
#**************************************
    $theName = &SelectUser;
    system 'clear';
    setpwent;
    if (($login,$passwd,$uid,$gid,$gcos,$scrap,$name,$dir,$shell) = getpwnam($theName)) {
	# Print out the detail info for the user
	($userName,$pw,$lastchg,$chgAllow,$chgReq,$dayWarn,$dayInact,$dateExp,$flag) = split(/,/,$name);
	print "Login:",$login,"        \tUID:",$uid,"   \tName:",$userName,"\n","Shell:",$shell,"\t\tHome:",$dir,"\n";
	print "Groups user is in: ";
	&MemberOf($login,$gid);
    }
    else {
	return;
	}
    print "\nHit [enter] to continue";
    getc();
} # End of ListUser subroutine.


#**************************************
sub MemberOf {
#**************************************
    setgrent;
    $count =0;
    $theGid = $_[1];
    $theMember = $_[0];
    while(($name,$passwd,$gid,$members) = getgrent) {
	if (($members =~ /(.*)($theMember)(.*)/) || ($gid eq $theGid)) {
	    if ($count > 0) {
		print ", ";
	    }
	    print $name;
	    $count++;
	}
    }
} # End of MemberOf subroutine.


#**************************************
sub ListMembers {
#**************************************
    $theGroup = $_[0];
    setgrent;
    system 'clear';
    ($name,$passwd,$gid,$members) = getgrnam($theGroup);
    $theID = $gid;
    $madelist = '0';
    print "Members of [".$theGroup."]: ";
    while(($name,$passwd,$gid,$members) = getgrent) {
	if ($name eq $theGroup) {
	    print $members;
	    $madelist = '1' if $members;
	}
    }
    setpwent;
    while (($login,$passwd,$uid,$gid,$gcos,$scrap,$name,$dir,$shell) = getpwent) {
	if ($gid eq $theID) {
	    if ($madelist eq '1') {
		print ", "
	    }
	    print "[user]:".$login;
	}
    }
    print "\nHit [enter] to continue";
    getc();
    
} # End of ListMembers subroutine.


#**************************************
sub UserFiles {
#**************************************
    system 'clear';
    $theUser = $_[0];
    system "rm /tmp/temp.lst" if -e "/tmp/temp.lst";
    $prog1 = "find / -user ".$theUser." > /tmp/temp.lst";
    print "Searching for files owned by ".$theUser."\n";
    print "This will probably take a while...\n";
    system $prog1;
} # End of UserFiles subroutine.


#**************************************
sub GroupFiles {
#**************************************
    system 'clear';
    $theGroup = $_[0];
    system "rm /tmp/temp.lst" if -e "/tmp/temp.lst";
    $prog1 = "find / -group ".$theGroup." > /tmp/temp.lst";
    print "Searching for files owned by ".$theGroup."\n";
    print "This will probably take a while...\n";
    system $prog1;
} # End of GroupFiles subroutine.


#**************************************
sub AddGroup {
#**************************************
    system 'clear';
    setgrent;
# Get the id
    while(($name,$passwd,$gid,$members) = getgrent) {
	# special check for nogroup with id of -1
	if (($gid > $nextid) && ($name ne 'nogroup')){
	    $nextid = $gid;
	}
    }
    chop($prompt= <<'EOP');
Which group name do you wish to use?:
EOP
    print $prompt;
    ($newName = <STDIN>);
    chop($newName);

    $nextid += 1;
    chop($prompt= <<'EOP');
Which group id do you wish to use?
EOP
    print $prompt,":[",$nextid,"]";
    ($newID = <STDIN>);
    chop($newID);
    if ($newID eq '') {
	$newID = $nextid;
    }
# Get the members
    chop($prompt= <<'EOP');
Which members do you wish to add?[comma separated]:
EOP
    print $prompt;
    ($newMembers = <STDIN>);
    chop($newMembers);
    $groupLine = $newName."::".$newID.":".$newMembers;

# walk through the group file and add the new group.
    open(groupFile,"/etc/group") || die "Unable to open group file.\n";
    open(newGroupFile,">/etc/group.new") || die "Unable to create group.new file.\n";
    $written = '0';
    while (<groupFile>) {
	chop;
	($name,$passwd,$gid,$members) = split(/:/);
	next if $name eq '';
	# special check for nogroup with id of -1
	if (($gid < $newID) && ($name ne "nogroup")){
	    print newGroupFile "$name:$passwd:$gid:$members\n" || do {
		unlink newGroupFile;
		die "Can't write to group.new!";
	    };
	}
	else
	{
	    # special check for nogroup with id of -1
	    if ($written eq '0') {
		print newGroupFile "$groupLine\n" || do {
		    unlink newGroupFile;
		    die "Can't write to group.new!";
		};
		$written = '1';
	    }
	    print newGroupFile "$name:$passwd:$gid:$members\n" || do {
		unlink newGroupFile;
		die "Can't write to group.new!";
	    };
	}
    }

    # Write it now if we haven't already
    if ($written eq '0') {
	print newGroupFile "$groupLine\n" || do {
	    unlink newGroupFile;
	    die "Can't write to group.new!";
	};
    }
# Close the files
    close newGroupFile;
    close groupFile;
# back up the group file
    system("cp /etc/group /etc/group.bak");
# copy the temp to the actual group
    system("cp /etc/group.new /etc/group");
    print "\nGroup was added!";
    getc();
    
} # End of AddGroup subroutine.


#**************************************
sub GroupUpdate {
#**************************************
    $theAttribute = $_[0];
    $newValue = $_[1];
# walk through the group file and add the new members.
    open(groupFile,"/etc/group") || die "Unable to open group file.\n";
    open(newGroupFile,">/etc/group.new") || die "Unable to create group.new file.\n";
    while (<groupFile>) {
	chop;
	($name,$passwd,$gid,$members) = split(/:/);
	next if $name eq '';
	if ($name eq $curGroup) {
	    if ($theAttribute eq "MEMBER") {
		if ($members eq '') {
		    $newValue = $members.$newValue;
		}
		else
		{
		    $newValue = $members.",".$newValue;
		}
		print newGroupFile "$theName:$passwd:$gid:$newValue\n" || do {
		    unlink newGroupFile;
		    die "Can't write to group.new!";
		}}
	    elsif ($theAttribute eq "NAME") {
		print newGroupFile "$newValue:$passwd:$gid:$members\n" || do {
		    unlink newGroupFile;
		    die "Can't write to group.new!";
		}}
	    elsif ($theAttribute eq "ID") {
		print "$theName:$passwd:$newValue:$members\n";
		print newGroupFile "$theName:$passwd:$newValue:$members\n" || do {
		    unlink newGroupFile;
		    die "Can't write to group.new!";
		}}
	    elsif ($theAttribute eq "NEWLIST") {
		print newGroupFile "$theName:$passwd:$gid:$newValue\n" || do {
		    unlink newGroupFile;
		    die "Can't write to group.new!";
		}}
	    elsif ($theAttribute eq "DELETE") {
		next;
	    }}
	else
	{
	    print newGroupFile "$name:$passwd:$gid:$members\n" || do {
		unlink newGroupFile;
		die "Can't write to group.new!";
	    };
	}
    }
# Close the files
    close newGroupFile;
    close groupFile;
# back up the group file
    system("cp /etc/group /etc/group.bak");
# copy the temp to the actual group
    system("cp /etc/group.new /etc/group");
    
} # End of GroupUpdate subroutine.


#**************************************
sub RemoveMembers {
#**************************************
    $curGroup = $_[0];
# Get the members
    chop($prompt= <<'EOP');
Enter the new member list:[comma separated]:
EOP
    print $prompt;
# Really want to list members and select the ones to delete.
    ($newMembers = <STDIN>);
    chop($newMembers);
    &GroupUpdate("NEWLIST",$newMembers);
    print "\nMember list altered!";
    getc();
    
} # End of RemoveMembers subroutine.


#**************************************
sub AddMembers {
#**************************************
    $curGroup = $_[0];
# Get the members
    chop($prompt= <<'EOP');
Which members do you wish to add?[comma separated]:
EOP
    print $prompt;
    ($newMembers = <STDIN>);
    chop($newMembers);
    &GroupUpdate("MEMBER",$newMembers);
    print "\nMembers were added!";
    getc();
    
} # End of AddMembers subroutine.


#**************************************
sub EditGroupName {
#**************************************
    $curGroup = $_[0];
# Get the new name
    chop($prompt= <<'EOP');
What is the new name of the group?:
EOP
    print $prompt;
    ($newName = <STDIN>);
    chop($newName);
    &GroupUpdate("NAME",$newName);
    $curGroup = $newName;
    print "\nGroup name was changed!";
    getc();
    
} # End of EditGroupName subroutine.

#**************************************
sub EditGroupId {
#**************************************
    system 'clear';
    $curGroup = $_[0];
    $newId = '';
    while ($newId eq '') {
	chop($prompt= <<'EOP');
Enter the new ID:
EOP
    print $prompt."[".$curGroup."]";
	($newId = <STDIN>);
	chop($newId);
        $newId = &ValidateGroupId($newId);
    }    
    &GroupUpdate("ID",$newId);
# Alter group of all of the files owned by group
    &GroupFiles($curGroup);
    print "Updating files for ".$curGroup."\n";    
    open(tempFile,"/tmp/temp.lst") || die "Unable to open temporary file.\n";
    while (<tempFile>) {
	chop;
        $prog1 = "chgrp ".$curGroup." ".$_."\n";
	system $prog1;
    }
    
# Alter group id of all users in group.
    open(passFile,"/etc/passwd") || die "Unable to open passwd file.\n";
    open(newPassFile,">/etc/passwd.new") || die "Unable to create passwd.new file.\n";
    while (<passFile>) {
	chop;
	($login,$passwd,$uid,$gid,$name,$dir,$shell) = split(/:/);
        if ($gid eq $newId) {
	    print newPassFile "$login:$passwd:$uid:$newId:$name:$dir:$shell\n" || do {
		unlink newPassFile;
		die "Can't write to passwd.new!";
	    }
	}
	else
	{
	    print newPassFile "$login:$passwd:$uid:$gid:$name:$dir:$shell\n" || do {
		unlink newPassFile;
		die "Can't write to passwd.new!";
	    };
	}
    }
# close the files.
    close newPassFile;
    close passFile;
# back up the passwd file
    system("cp /etc/passwd /etc/passwd.bak");
# copy the temp to the actual passwd
    system("cp /etc/passwd.new /etc/passwd");
    
    print "\nGroup ID was changed!";
    getc();
} # End of EditGroupId subroutine.


