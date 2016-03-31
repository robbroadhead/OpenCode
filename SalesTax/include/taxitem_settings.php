<?php

//	field labels
$fieldLabelstaxitem = array();
$fieldLabelstaxitem["English"]=array();
$fieldLabelstaxitem["English"]["itemID"] = "Item ID";
$fieldLabelstaxitem["English"]["Storename"] = "Store";
$fieldLabelstaxitem["English"]["Amount"] = "Amount";
$fieldLabelstaxitem["English"]["userID"] = "User ID";
$fieldLabelstaxitem["English"]["Created"] = "Created";
$fieldLabelstaxitem["English"]["monthname"] = "Monthname";
$fieldLabelstaxitem["English"]["year"] = "Year";


$tdatataxitem=array();
	 $tdatataxitem[".NumberOfChars"]=80; 
	$tdatataxitem[".ShortName"]="taxitem";
	$tdatataxitem[".OwnerID"]="userID";
	$tdatataxitem[".OriginalTable"]="taxitem";

	$keys=array();
	$keys[]="itemID";
	$tdatataxitem[".Keys"]=$keys;

	
//	itemID
	$fdata = array();
	 $fdata["Label"]="Item ID"; 
	
	
	$fdata["FieldType"]= 3;
		$fdata["AutoInc"]=true;
	$fdata["EditFormat"]= "Text field";
	$fdata["ViewFormat"]= "";
	
	
		
				$fdata["NeedEncode"]=true;
	
	$fdata["GoodName"]= "itemID";
		$fdata["FullName"]= "itemID";
	 $fdata["IsRequired"]=true; 
	
	
	
	$fdata["Index"]= 1;
	
			$fdata["EditParams"]="";
						$fdata["FieldPermissions"]=true;
				$tdatataxitem["itemID"]=$fdata;
	
//	Storename
	$fdata = array();
	 $fdata["Label"]="Store"; 
	
	
	$fdata["FieldType"]= 200;
		$fdata["EditFormat"]= "Text field";
	$fdata["ViewFormat"]= "";
	
	
		
				$fdata["NeedEncode"]=true;
	
	$fdata["GoodName"]= "Storename";
		$fdata["FullName"]= "Storename";
	
	
	
	
	$fdata["Index"]= 2;
	
			$fdata["EditParams"]="";
			$fdata["EditParams"].= " maxlength=100";
					$fdata["FieldPermissions"]=true;
				$fdata["ListPage"]=true;
	$tdatataxitem["Storename"]=$fdata;
	
//	Amount
	$fdata = array();
	
	
	
	$fdata["FieldType"]= 14;
		$fdata["EditFormat"]= "Text field";
	$fdata["ViewFormat"]= "Number";
	
	
		
				$fdata["NeedEncode"]=true;
	
	$fdata["GoodName"]= "Amount";
		$fdata["FullName"]= "Amount";
	
	
	
	
	$fdata["Index"]= 3;
	
			$fdata["EditParams"]="";
						$fdata["FieldPermissions"]=true;
				$fdata["ListPage"]=true;
	$tdatataxitem["Amount"]=$fdata;
	
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
	
	
	
	
	$fdata["Index"]= 4;
	
									$tdatataxitem["userID"]=$fdata;
	
//	Created
	$fdata = array();
	
	
	
	$fdata["FieldType"]= 135;
		$fdata["EditFormat"]= "Date";
	$fdata["ViewFormat"]= "Datetime";
	
	
		
				$fdata["NeedEncode"]=true;
	
	$fdata["GoodName"]= "Created";
		$fdata["FullName"]= "Created";
	 $fdata["IsRequired"]=true; 
	
	
	
	$fdata["Index"]= 5;
	 $fdata["DateEditType"]=13; 
						$fdata["FieldPermissions"]=true;
				$fdata["ListPage"]=true;
	$tdatataxitem["Created"]=$fdata;
	
//	monthname
	$fdata = array();
	 $fdata["Label"]="Monthname"; 
	
	
	$fdata["FieldType"]= 200;
		$fdata["EditFormat"]= "Text field";
	$fdata["ViewFormat"]= "";
	
	
		
				$fdata["NeedEncode"]=true;
	
	$fdata["GoodName"]= "monthname";
		$fdata["FullName"]= "monthname(Created)";
	
	
	
	
	$fdata["Index"]= 6;
	
			$fdata["EditParams"]="";
									$tdatataxitem["monthname"]=$fdata;
	
//	year
	$fdata = array();
	 $fdata["Label"]="Year"; 
	
	
	$fdata["FieldType"]= 3;
		$fdata["EditFormat"]= "Text field";
	$fdata["ViewFormat"]= "";
	
	
		
				$fdata["NeedEncode"]=true;
	
	$fdata["GoodName"]= "year";
		$fdata["FullName"]= "year(Created)";
	
	
	
	
	$fdata["Index"]= 7;
	
			$fdata["EditParams"]="";
						$fdata["FieldPermissions"]=true;
				$fdata["ListPage"]=true;
	$tdatataxitem["year"]=$fdata;
$tables_data["taxitem"]=&$tdatataxitem;
$field_labels["taxitem"] = &$fieldLabelstaxitem;

?>