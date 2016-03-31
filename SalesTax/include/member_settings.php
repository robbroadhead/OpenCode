<?php

//	field labels
$fieldLabelsmember = array();
$fieldLabelsmember["English"]=array();
$fieldLabelsmember["English"]["MemberID"] = "Member ID";
$fieldLabelsmember["English"]["Login"] = "Login";
$fieldLabelsmember["English"]["Password"] = "Password";
$fieldLabelsmember["English"]["Email"] = "Email";
$fieldLabelsmember["English"]["DisplayName"] = "Display Name";


$tdatamember=array();
	 $tdatamember[".NumberOfChars"]=80; 
	$tdatamember[".ShortName"]="member";
	$tdatamember[".OwnerID"]="MemberID";
	$tdatamember[".OriginalTable"]="member";

	$keys=array();
	$keys[]="MemberID";
	$tdatamember[".Keys"]=$keys;

	
//	MemberID
	$fdata = array();
	 $fdata["Label"]="Member ID"; 
	
	
	$fdata["FieldType"]= 3;
		$fdata["AutoInc"]=true;
	$fdata["EditFormat"]= "Text field";
	$fdata["ViewFormat"]= "";
	
	
		
				$fdata["NeedEncode"]=true;
	
	$fdata["GoodName"]= "MemberID";
		$fdata["FullName"]= "MemberID";
	 $fdata["IsRequired"]=true; 
	
	
	
	$fdata["Index"]= 1;
	
			$fdata["EditParams"]="";
									$tdatamember["MemberID"]=$fdata;
	
//	Login
	$fdata = array();
	
	
	
	$fdata["FieldType"]= 200;
		$fdata["EditFormat"]= "Text field";
	$fdata["ViewFormat"]= "";
	
	
		
				$fdata["NeedEncode"]=true;
	
	$fdata["GoodName"]= "Login";
		$fdata["FullName"]= "Login";
	
	
	
	
	$fdata["Index"]= 2;
	
			$fdata["EditParams"]="";
			$fdata["EditParams"].= " maxlength=30";
								$tdatamember["Login"]=$fdata;
	
//	Password
	$fdata = array();
	
	
	
	$fdata["FieldType"]= 200;
		$fdata["EditFormat"]= "Text field";
	$fdata["ViewFormat"]= "";
	
	
		
				$fdata["NeedEncode"]=true;
	
	$fdata["GoodName"]= "Password";
		$fdata["FullName"]= "Password";
	
	
	
	
	$fdata["Index"]= 3;
	
			$fdata["EditParams"]="";
			$fdata["EditParams"].= " maxlength=30";
					$fdata["FieldPermissions"]=true;
				$tdatamember["Password"]=$fdata;
	
//	Email
	$fdata = array();
	
	
	
	$fdata["FieldType"]= 200;
		$fdata["EditFormat"]= "Text field";
	$fdata["ViewFormat"]= "";
	
	
		
				$fdata["NeedEncode"]=true;
	
	$fdata["GoodName"]= "Email";
		$fdata["FullName"]= "Email";
	
	
	
	
	$fdata["Index"]= 4;
	
			$fdata["EditParams"]="";
			$fdata["EditParams"].= " maxlength=250";
					$fdata["FieldPermissions"]=true;
				$tdatamember["Email"]=$fdata;
	
//	DisplayName
	$fdata = array();
	 $fdata["Label"]="Display Name"; 
	
	
	$fdata["FieldType"]= 200;
		$fdata["EditFormat"]= "Text field";
	$fdata["ViewFormat"]= "";
	
	
		
				$fdata["NeedEncode"]=true;
	
	$fdata["GoodName"]= "DisplayName";
		$fdata["FullName"]= "DisplayName";
	
	
	
	
	$fdata["Index"]= 5;
	
			$fdata["EditParams"]="";
						$fdata["FieldPermissions"]=true;
				$tdatamember["DisplayName"]=$fdata;
$tables_data["member"]=&$tdatamember;
$field_labels["member"] = &$fieldLabelsmember;

?>