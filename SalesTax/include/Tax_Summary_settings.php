<?php

//	field labels
$fieldLabelsTax_Summary = array();
$fieldLabelsTax_Summary["English"]=array();
$fieldLabelsTax_Summary["English"]["userID"] = "User ID";
$fieldLabelsTax_Summary["English"]["Created"] = "Created";
$fieldLabelsTax_Summary["English"]["Entries"] = "Entry Count";
$fieldLabelsTax_Summary["English"]["monthname"] = "Month";
$fieldLabelsTax_Summary["English"]["year"] = "Year";
$fieldLabelsTax_Summary["English"]["Amount"] = "Amount";


$tdataTax_Summary=array();
	 $tdataTax_Summary[".NumberOfChars"]=80; 
	$tdataTax_Summary[".ShortName"]="Tax_Summary";
	$tdataTax_Summary[".OwnerID"]="userID";
	$tdataTax_Summary[".OriginalTable"]="taxitem";

	$keys=array();
	$tdataTax_Summary[".Keys"]=$keys;

	
//	Entries
	$fdata = array();
	 $fdata["Label"]="Entry Count"; 
	
	
	$fdata["FieldType"]= 3;
		$fdata["EditFormat"]= "Text field";
	$fdata["ViewFormat"]= "";
	
	
		
				$fdata["NeedEncode"]=true;
	
	$fdata["GoodName"]= "Entries";
		$fdata["FullName"]= "COUNT(itemID)";
	
	
	
	
	$fdata["Index"]= 1;
	
			$fdata["EditParams"]="";
						$fdata["FieldPermissions"]=true;
				$fdata["ListPage"]=true;
	$tdataTax_Summary["Entries"]=$fdata;
	
//	Amount
	$fdata = array();
	
	
	
	$fdata["FieldType"]= 14;
		$fdata["EditFormat"]= "Text field";
	$fdata["ViewFormat"]= "Number";
	
	
		
				$fdata["NeedEncode"]=true;
	
	$fdata["GoodName"]= "Amount";
		$fdata["FullName"]= "SUM(Amount)";
	
	
	
	
	$fdata["Index"]= 2;
	
			$fdata["EditParams"]="";
						$fdata["FieldPermissions"]=true;
				$fdata["ListPage"]=true;
	$tdataTax_Summary["Amount"]=$fdata;
	
//	userID
	$fdata = array();
	 $fdata["Label"]="User ID"; 
	
	
	$fdata["FieldType"]= 3;
		$fdata["EditFormat"]= "Lookup wizard";
	$fdata["ViewFormat"]= "";
	
	
		
		$fdata["LookupType"]=1;
			$fdata["LinkField"]="`MemberID`";
	$fdata["LinkFieldType"]=3;
		$fdata["DisplayField"]="`DisplayName`";
	$fdata["LookupTable"]="member";
				$fdata["NeedEncode"]=true;
	
	$fdata["GoodName"]= "userID";
		$fdata["FullName"]= "userID";
	
	
	
	
	$fdata["Index"]= 3;
	
									$tdataTax_Summary["userID"]=$fdata;
	
//	Created
	$fdata = array();
	
	
	
	$fdata["FieldType"]= 135;
		$fdata["EditFormat"]= "Date";
	$fdata["ViewFormat"]= "Datetime";
	
	
		
				$fdata["NeedEncode"]=true;
	
	$fdata["GoodName"]= "Created";
		$fdata["FullName"]= "Created";
	 $fdata["IsRequired"]=true; 
	
	
	
	$fdata["Index"]= 4;
	 $fdata["DateEditType"]=13; 
									$tdataTax_Summary["Created"]=$fdata;
	
//	monthname
	$fdata = array();
	 $fdata["Label"]="Month"; 
	
	
	$fdata["FieldType"]= 200;
		$fdata["EditFormat"]= "Text field";
	$fdata["ViewFormat"]= "";
	
	
		
				$fdata["NeedEncode"]=true;
	
	$fdata["GoodName"]= "monthname";
		$fdata["FullName"]= "monthname(Created)";
	
	
	
	
	$fdata["Index"]= 5;
	
			$fdata["EditParams"]="";
						$fdata["FieldPermissions"]=true;
				$fdata["ListPage"]=true;
	$tdataTax_Summary["monthname"]=$fdata;
	
//	year
	$fdata = array();
	 $fdata["Label"]="Year"; 
	
	
	$fdata["FieldType"]= 3;
		$fdata["EditFormat"]= "Text field";
	$fdata["ViewFormat"]= "";
	
	
		
				$fdata["NeedEncode"]=true;
	
	$fdata["GoodName"]= "year";
		$fdata["FullName"]= "year(Created)";
	
	
	
	
	$fdata["Index"]= 6;
	
			$fdata["EditParams"]="";
						$fdata["FieldPermissions"]=true;
				$fdata["ListPage"]=true;
	$tdataTax_Summary["year"]=$fdata;
$tables_data["Tax Summary"]=&$tdataTax_Summary;
$field_labels["Tax_Summary"] = &$fieldLabelsTax_Summary;

?>