<?php 
ini_set("display_errors","1");
ini_set("display_startup_errors","1");
ini_set('magic_quotes_runtime', 0); 
include("include/dbcommon.php");
header("Expires: Thu, 01 Jan 1970 00:00:01 GMT");
header("Pragma: no-cache");
header("Cache-Control: no-cache");

include("include/taxitem_variables.php");


//	check if logged in

if(!@$_SESSION["UserID"] || !CheckSecurity(@$_SESSION["_".$strTableName."_OwnerID"],"Add"))
{ 
	$_SESSION["MyURL"]=$_SERVER["SCRIPT_NAME"]."?".$_SERVER["QUERY_STRING"];
	header("Location: login.php?message=expired"); 
	return;
}

$pageName = "add.php";
$filename="";
$status="";
$message="";
$usermessage="";
$error_happened=false;
$readavalues=false;


$showKeys = array();
$showValues = array();
$showRawValues = array();
$showFields = array();
$showDetailKeys = array();
$IsSaved = false;
$HaveData = true;

if(@$_REQUEST["editType"]=="inline")
	$inlineedit=ADD_INLINE;
elseif(@$_REQUEST["editType"]=="onthefly")
	$inlineedit=ADD_ONTHEFLY;
else
	$inlineedit=ADD_SIMPLE;
$keys=array();
if($inlineedit==ADD_INLINE)
	$templatefile = "taxitem_inline_add.htm";
else
	$templatefile = "taxitem_add.htm";

$id=postvalue("id");


include('include/xtempl.php');
$xt = new Xtempl();


//	Before Process event
if(function_exists("BeforeProcessAdd"))
	BeforeProcessAdd($conn);


// insert new record if we have to

if(@$_POST["a"]=="added")
{
	$afilename_values=array();
	$avalues=array();
	$blobfields=array();
	$files_move=array();
	$files_save=array();
//	processing year - start
    
	$inlineEditOption = true;
	$inlineEditOption = $inlineedit!=ADD_INLINE;
	if($inlineEditOption)
	{
	$value = postvalue("value_year");
	$type=postvalue("type_year");
	if (FieldSubmitted("year"))
	{
		$value=prepare_for_db("year",$value,$type);
	}
	else
		$value=false;
	if(!($value===false))
	{


		$blobfields[]="year";
		$avalues["year"]=$value;
	}
	}
//	processibng year - end
//	processing Storename - start
    
	$inlineEditOption = true;
	if($inlineEditOption)
	{
	$value = postvalue("value_Storename");
	$type=postvalue("type_Storename");
	if (FieldSubmitted("Storename"))
	{
		$value=prepare_for_db("Storename",$value,$type);
	}
	else
		$value=false;
	if(!($value===false))
	{


		$blobfields[]="Storename";
		$avalues["Storename"]=$value;
	}
	}
//	processibng Storename - end
//	processing Amount - start
    
	$inlineEditOption = true;
	if($inlineEditOption)
	{
	$value = postvalue("value_Amount");
	$type=postvalue("type_Amount");
	if (FieldSubmitted("Amount"))
	{
		$value=prepare_for_db("Amount",$value,$type);
	}
	else
		$value=false;
	if(!($value===false))
	{


		$blobfields[]="Amount";
		$avalues["Amount"]=$value;
	}
	}
//	processibng Amount - end
//	processing Created - start
    
	$inlineEditOption = true;
	if($inlineEditOption)
	{
	$value = postvalue("value_Created");
	$type=postvalue("type_Created");
	if (FieldSubmitted("Created"))
	{
		$value=prepare_for_db("Created",$value,$type);
	}
	else
		$value=false;
	if(!($value===false))
	{


		$blobfields[]="Created";
		$avalues["Created"]=$value;
	}
	}
//	processibng Created - end


//	insert ownerid value if exists
	$avalues["userID"]=prepare_for_db("userID",$_SESSION["_".$strTableName."_OwnerID"]);


//	insert masterkey value if exists and if not specified
	if(@$_SESSION[$strTableName."_mastertable"]=="Tax Summary")
	{
		$avalues["userID"]=prepare_for_db("userID",$_SESSION[$strTableName."_masterkey1"]);
		$avalues["monthname"]=prepare_for_db("monthname",$_SESSION[$strTableName."_masterkey2"]);
		$avalues["year"]=prepare_for_db("year",$_SESSION[$strTableName."_masterkey3"]);
	}




	$failed_inline_add=false;
//	add filenames to values
	foreach($afilename_values as $akey=>$value)
		$avalues[$akey]=$value;
	
//	before Add event
	$retval = true;
	if(function_exists("BeforeAdd"))
		$retval=BeforeAdd($avalues,$usermessage,$inlineedit);
	if($retval)
	{
		if(DoInsertRecord($strOriginalTableName,$avalues,$blobfields))
		{
			$IsSaved=true;
//	after edit event
			if(function_exists("AfterAdd"))
			{
				foreach($keys as $idx=>$val)
					$avalues[$idx]=$val;
				AfterAdd($avalues,$keys,$inlineedit);
			}
		}
	}
	else
	{
		$message = $usermessage;
		$status="DECLINED";
		$readavalues=true;
	}
}

// PRG rule, to avoid POSTDATA resend
//if ($inlineedit==ADD_SIMPLE && @$_POST["a"]=="added"){
if (no_output_done() && $inlineedit==ADD_SIMPLE && $IsSaved){
 
	// saving message
	$_SESSION["message"] = ($message ? $message : "");
	// redirect
	header("Location: taxitem_".$pageName);
	// turned on output buffering, so we need to stop script
	exit();
}
// for PRG rule, to avoid POSTDATA resend. Saving mess in session
if ($inlineedit==ADD_SIMPLE  && isset($_SESSION["message"])){
	$message = $_SESSION["message"];
	unset($_SESSION["message"]);
}


$defvalues=array();


//	copy record
if(array_key_exists("copyid1",$_REQUEST) || array_key_exists("editid1",$_REQUEST))
{
	$copykeys=array();
	if(array_key_exists("copyid1",$_REQUEST))
	{
		$copykeys["itemID"]=postvalue("copyid1");
	}
	else
	{
		$copykeys["itemID"]=postvalue("editid1");
	}
	$strWhere=KeyWhere($copykeys);
	$strWhere=whereAdd($strWhere,SecuritySQL("Search"));
	$strSQL = gSQLWhere($strWhere);

	LogInfo($strSQL);
	$rs=db_query($strSQL,$conn);
	$defvalues=db_fetch_array($rs);
	if(!$defvalues)
		$defvalues=array();
//	clear key fields
	$defvalues["itemID"]="";
//call CopyOnLoad event
	if(function_exists("CopyOnLoad"))
		CopyOnLoad($defvalues,$strWhere);
}
else
{
	$defvalues["Created"]=now();
}

//	set default values for the foreign keys
if(@$_SESSION[$strTableName."_mastertable"]=="Tax Summary")
{
	$defvalues["userID"]=@$_SESSION[$strTableName."_masterkey1"];
	$defvalues["monthname"]=@$_SESSION[$strTableName."_masterkey2"];
	$defvalues["year"]=@$_SESSION[$strTableName."_masterkey3"];
}



if($readavalues)
{
	$defvalues["Storename"]=@$avalues["Storename"];
	$defvalues["Amount"]=@$avalues["Amount"];
	$defvalues["Created"]=@$avalues["Created"];
	$defvalues["year"]=@$avalues["year"];
}
//for basic files
$includes="";
//for javascript code
$jscode="";
$bodyonload="";
$onsubmit="";
//////////////////////////////////////////////////////////////////	
//	Begin Add validation params for InlineAdd or Add or AddOnTheFly	
//	validation stuff
	$onsubmit="$('#message_block').html('');";
	$regex='';
	$regexmessage='';
	$regextype = '';
	$RTEfunc="";
	$needvalidate=false;
	$arrValidate = array();
//	for inlineAdd
	$addValidateTypes = array();
	$addValidateFields = array();
	$addValidateUseRTE = array();
	$addValidateCBList = array();	
	$addValidateRegex = array();
	$addValidateRegexmes = array();
	$addValidateRegexmestype = array();
//	Begin Add validation	
//if use InnovaEditor or RTE on pages add or addonthefly when useRTE will be with  -  "_FLY"
//if use InnovaEditor or RTE on page InineAdd when useRTE will be with out  -  "_FLY"		
if($inlineedit!=ADD_INLINE) 
{
	if($inlineedit!=ADD_ONTHEFLY)
	{
		AddJSFile("validate");
		if(@$_REQUEST["language"])
			$language = $_REQUEST["language"];
		// may be elseif ?
		if(@$_SESSION["language"])
			$language = $_SESSION["language"];
		else
			$language = 'English';
		
		$jscode.="window.current_language='".jsreplace($language)."';\r\n";
		
		$jscode.="addValid = new validation();\r\n";	
	}
	else	
		$jscode.="window.addFlyValid".$id." = new validation();\r\n";
				
	$jscode.="window.TEXT_INLINE_FIELD_REQUIRED='".jsreplace("Required field")."';\r\n";
	$jscode.="window.TEXT_INLINE_FIELD_ZIPCODE='".jsreplace("Field should be a valid zipcode")."';\r\n";
	$jscode.="window.TEXT_INLINE_FIELD_EMAIL='".jsreplace("Field should be a valid email address")."';\r\n";
	$jscode.="window.TEXT_INLINE_FIELD_NUMBER='".jsreplace("Field should be a valid number")."';\r\n";
	$jscode.="window.TEXT_INLINE_FIELD_CURRENCY='".jsreplace("Field should be a valid currency")."';\r\n";
	$jscode.="window.TEXT_INLINE_FIELD_PHONE='".jsreplace("Field should be a valid phone number")."';\r\n";
	$jscode.="window.TEXT_INLINE_FIELD_PASSWORD1='".jsreplace("Field can not be 'password'")."';\r\n";
	$jscode.="window.TEXT_INLINE_FIELD_PASSWORD2='".jsreplace("Field should be at least 4 characters long")."';\r\n";
	$jscode.="window.TEXT_INLINE_FIELD_STATE='".jsreplace("Field should be a valid US state name")."';\r\n";
	$jscode.="window.TEXT_INLINE_FIELD_SSN='".jsreplace("Field should be a valid Social Security Number")."';\r\n";
	$jscode.="window.TEXT_INLINE_FIELD_DATE='".jsreplace("Field should be a valid date")."';\r\n";
	$jscode.="window.TEXT_INLINE_FIELD_TIME='".jsreplace("Field should be a valid time in 24-hour format")."';\r\n";
	$jscode.="window.TEXT_INLINE_FIELD_CC='".jsreplace("Field should be a valid credit card number")."';\r\n";
	$jscode.="window.TEXT_INLINE_FIELD_SSN='".jsreplace("Field should be a valid Social Security Number")."';\r\n";
}
//	validate field - Amount
	$validatetype="IsNumeric";
	$second_validatetype="";
	$strRTE='';
	$lookup='';	
	$arrValidate[] = array(0 => "Amount", 1 => $validatetype, 2 => $second_validatetype, 3 => $strRTE,
						   4 => $lookup, 5 => $regex, 6 => $regexmessage, 7 => $regextype);
//	validate field - Created
	$validatetype="";
	$second_validatetype="IsRequired";
	$strRTE='';
	$lookup='';	
	$arrValidate[] = array(0 => "Created", 1 => $validatetype, 2 => $second_validatetype, 3 => $strRTE,
						   4 => $lookup, 5 => $regex, 6 => $regexmessage, 7 => $regextype);
//	validate field - year
	$validatetype="IsNumeric";
	$second_validatetype="";
	$strRTE='';
	$lookup='';	
	$arrValidate[] = array(0 => "year", 1 => $validatetype, 2 => $second_validatetype, 3 => $strRTE,
						   4 => $lookup, 5 => $regex, 6 => $regexmessage, 7 => $regextype);
for($i=0;$i<count($arrValidate);$i++)
{
	if($arrValidate[$i][1])
	{
		if($inlineedit!=ADD_INLINE)
		{
			$needvalidate=true;
			if($inlineedit==ADD_ONTHEFLY)
			{
				if ($arrValidate[$i][1]=="Regular expression")
					$jscode.="addFlyValid".$id.".addRegex($('#value_".$arrValidate[$i][0]."_".$id."'),'".$arrValidate[$i][1]."','".
						jsreplace($arrValidate[$i][5])."','".jsreplace($arrValidate[$i][6])."','".jsreplace($arrValidate[$i][7])."');\r\n";
				else		
					$jscode.="addFlyValid".$id.".add($('#value_".$arrValidate[$i][0]."_".$id."'),'".$arrValidate[$i][1]."','".$arrValidate[$i][3]."','".$arrValidate[$i][4]."');\r\n";	
			}
			else
			{
				if ($arrValidate[$i][1]=="Regular expression")
					$bodyonload.="addValid.addRegex(document.editform['value_".$arrValidate[$i][0]."'],'".$arrValidate[$i][1]."','".
						jsreplace($arrValidate[$i][5])."','".jsreplace($arrValidate[$i][6])."','".jsreplace($arrValidate[$i][7])."');\r\n";
				else		
					$bodyonload.="addValid.add(document.editform['value_".$arrValidate[$i][0]."'],'".$arrValidate[$i][1]."','".$arrValidate[$i][3]."','".$arrValidate[$i][4]."');\r\n";
			}
		}
		else{
				//	Add Inline validation params
				$addValidateTypes[] = $arrValidate[$i][1];
				$addValidateFields[] = $arrValidate[$i][0];
				$addValidateUseRTE[] = $arrValidate[$i][3];
				$addValidateCBList[] = $arrValidate[$i][4];
				$addValidateRegex[] = jsreplace($arrValidate[$i][5]);
				$addValidateRegexmes[] = jsreplace($arrValidate[$i][6]);
				$addValidateRegexmestype[] = jsreplace($arrValidate[$i][7]);
			}	
	}
	if($arrValidate[$i][2])
	{	
		if($inlineedit!=ADD_INLINE)
		{
			$needvalidate=true;
			if($inlineedit==ADD_ONTHEFLY)
			{	
				if($arrValidate[$i][3]=='INNOVA_FLY' || $arrValidate[$i][3]=='RTE_FLY')
				{
					$jscode.='$("td[@class^=\'editshade_lb\']").each(function(i){';
					$jscode.='if($("iframe[@name=\'value_'.$arrValidate[$i][0].'_'.$id.'\']",this).length)';
					$jscode.='addFlyValid'.$id.'.add($("iframe[@name=\'value_'.$arrValidate[$i][0].'_'.$id.'\']",this),"'.$arrValidate[$i][2].'","'.$arrValidate[$i][3].'","'.$arrValidate[$i][4].'");});';
				//	$func.='getDataFromRTEInnova($(\'#value_'.$arrValidate[$i][0].'_'.$id.'\'),\''.$arrValidate[$i][3].'\',$(\'#editform'.$id.'\'),\'value_'.$arrValidate[$i][0].'\');'; 
				}
				else
					$jscode.="addFlyValid".$id.".add($('".($arrValidate[$i][4]=='CBList' ? "input[@name=\"value_".$arrValidate[$i][0]."[]\"]" : "#value_".$arrValidate[$i][0]."_".$id)."'),'".$arrValidate[$i][2]."','".$arrValidate[$i][3]."','".$arrValidate[$i][4]."');\r\n";
			}
			elseif($arrValidate[$i][3]=='INNOVA_FLY' || $arrValidate[$i][3]=='RTE_FLY')
			{
				$bodyonload.='$("td[@class^=\'editshade_lb\']").each(function(i){';
				$bodyonload.='if($("iframe[@name=\'value_'.$arrValidate[$i][0].'\']",this).length)';
				$bodyonload.='addValid.add($("iframe[@name=\'value_'.$arrValidate[$i][0].'\']",this),"'.$arrValidate[$i][2].'","'.$arrValidate[$i][3].'","'.$arrValidate[$i][4].'");});';
				//$func.='getDataFromRTEInnova($(\'#value_'.$arrValidate[$i][0].'\'),\''.$arrValidate[$i][3].'\',$(\'#editform\'),\'value_'.$arrValidate[$i][0].'\');'; 
			}
			else
				$bodyonload.="addValid.add(document.editform['".($arrValidate[$i][4]=='disp' ? "display_" : "")."value_".$arrValidate[$i][0].($arrValidate[$i][4]=='CBList' || $arrValidate[$i][4]=='list' ? "[]" : "")."'],'".$arrValidate[$i][2]."','".$arrValidate[$i][3]."','".$arrValidate[$i][4]."');\r\n";
		}
		else{
				//	Add Inline validation params
				$addValidateTypes[] = $arrValidate[$i][2];
				$addValidateFields[] = $arrValidate[$i][0];
				$addValidateUseRTE[] = $arrValidate[$i][3];
				$addValidateCBList[] = $arrValidate[$i][4];
				$addValidateRegex[] = jsreplace($arrValidate[$i][5]);
				$addValidateRegexmes[] = jsreplace($arrValidate[$i][6]);
				$addValidateRegexmestype[] = jsreplace($arrValidate[$i][7]);
			}	
	}
}	
//	End Add validation params for InlineAdd or Add or AddOnTheFly
//////////////////////////////////////////////////////////////


////////////////////// time picker
//////////////////////
$body=array();


AddJSFile('customlabels');

$jscode.="window.locale_dateformat = ".$locale_info["LOCALE_IDATE"].";\r\n".
	"window.locale_datedelimiter = \"".$locale_info["LOCALE_SDATE"]."\";\r\n".
	"window.bLoading=false;\r\n".
	"window.TEXT_PLEASE_SELECT='".jsreplace("Please select")."';\r\n";

//	include datepicker files
	
	$jscode.="window.TEXT_MONTH_JAN='".jsreplace("January")."';\r\n";
	$jscode.="window.TEXT_MONTH_FEB='".jsreplace("February")."';\r\n";
	$jscode.="window.TEXT_MONTH_MAR='".jsreplace("March")."';\r\n";
	$jscode.="window.TEXT_MONTH_APR='".jsreplace("April")."';\r\n";
	$jscode.="window.TEXT_MONTH_MAY='".jsreplace("May")."';\r\n";
	$jscode.="window.TEXT_MONTH_JUN='".jsreplace("June")."';\r\n";
	$jscode.="window.TEXT_MONTH_JUL='".jsreplace("July")."';\r\n";
	$jscode.="window.TEXT_MONTH_AUG='".jsreplace("August")."';\r\n";
	$jscode.="window.TEXT_MONTH_SEP='".jsreplace("September")."';\r\n";
	$jscode.="window.TEXT_MONTH_OCT='".jsreplace("October")."';\r\n";
	$jscode.="window.TEXT_MONTH_NOV='".jsreplace("November")."';\r\n";
	$jscode.="window.TEXT_MONTH_DEC='".jsreplace("December")."';\r\n";
	
	$jscode.="window.TEXT_DAY_SU='".jsreplace("Su")."';\r\n";
	$jscode.="window.TEXT_DAY_MO='".jsreplace("Mo")."';\r\n";
	$jscode.="window.TEXT_DAY_TU='".jsreplace("Tu")."';\r\n";
	$jscode.="window.TEXT_DAY_WE='".jsreplace("We")."';\r\n";
	$jscode.="window.TEXT_DAY_TH='".jsreplace("Th")."';\r\n";
	$jscode.="window.TEXT_DAY_FR='".jsreplace("Fr")."';\r\n";
	$jscode.="window.TEXT_DAY_SA='".jsreplace("Sa")."';\r\n";
	
	$jscode.="window.TEXT_TODAY='".jsreplace("today")."';\r\n";	
		
	AddJSFile("calendar");






if($inlineedit!=ADD_INLINE)
{
	if($needvalidate)
	{
		if($RTEfunc)
		{
			if($inlineedit==ADD_ONTHEFLY)
				$onsubmit="if(addFlyValid".$id.".validate()){".$RTEfunc."return true;}else return false;";
			else
				$onsubmit="if(addValid.validate()){".$RTEfunc."return true;}else return false;";	
		}
		else{	
				if($inlineedit==ADD_ONTHEFLY)
					$onsubmit="return addFlyValid".$id.".validate();";
				else
					$onsubmit.="return addValid.validate();";
			}
	}
	elseif($RTEfunc)
		$onsubmit=$RTEfunc."return true;";

	if($inlineedit!=ADD_ONTHEFLY)
	{
		$includes.="<script language=\"JavaScript\" src=\"include/jquery.js\"></script>\r\n";
		AddJSFile("ajaxsuggest");		
		$includes.="<script language=\"JavaScript\" src=\"include/jsfunctions.js\"></script>\r\n";
	}
	
	$jscode.="SUGGEST_TABLE='taxitem_searchsuggest.php';\r\n";
	if($inlineedit!=ADD_ONTHEFLY)
		$includes.="<div id=\"search_suggest\"></div>\r\n";
	

	$xt->assign("Storename_fieldblock",true);
	$xt->assign("Amount_fieldblock",true);
	$xt->assign("Created_fieldblock",true);
	$xt->assign("year_fieldblock",true);
	
	$formname="editform";
	if($onsubmit)
		$onsubmit="onsubmit=\"".$onsubmit."\"";
	if($inlineedit!=ADD_ONTHEFLY)
	{
		$body["begin"]=$includes.
		"<form name=\"editform\" id=\"editform\" encType=\"multipart/form-data\" method=\"post\" action=\"taxitem_add.php\" ".$onsubmit.">".
		"<input type=hidden name=\"a\" value=\"added\">";
		$xt->assign("backbutton_attrs","onclick=\"window.location.href='taxitem_list.php?a=return'\"");
		$xt->assign("back_button",true);
	}
	else
	{
		$formname="editform".$id;
		$body["begin"]="<form name=\"editform".$id."\" id=\"editform".$id."\" encType=\"multipart/form-data\" method=\"post\" action=\"taxitem_add.php\" ".$onsubmit." target=\"flyframe".$id."\">".
		"<input type=hidden name=\"a\" value=\"added\">".
		"<input type=hidden name=\"editType\" value=\"onthefly\">".
		"<input type=hidden name=\"table\" value=\"".postvalue("table")."\">".
		"<input type=hidden name=\"field\" value=\"".postvalue("field")."\">".
		"<input type=hidden name=\"category\" value=\"".postvalue("category")."\">".
		"<input type=hidden name=\"id\" value=\"".$id."\">";
		$xt->assign("cancelbutton_attrs","onclick=\"RemoveFlyDiv('".$id."');\"");
		$xt->assign("cancel_button",true);
		$xt->assign("header","");
	}
	$xt->assign("save_button",true);
	$xt->assign("reset_button",true);
	$xt->assign("resetbutton_attrs",'onclick="resetEditors();"');
}

if($message)
{
	$xt->assign("message_block",true);
	$xt->assign("message",$message);
}
//$xt->assign("status",$status);

$readonlyfields=array();

//	show readonly fields

$linkdata="";
$record_id= postvalue("recordID");

if($inlineedit==ADD_ONTHEFLY)
	$record_id=$id;

	if($inlineedit==ADD_INLINE) 
	{
		$jscode.= "inlineAddValid".$record_id." = new validation();\r\n";
				$types_separated = implode(",", $addValidateTypes);
		$fields_separated = implode(",", $addValidateFields);
		$useRTE_separated = implode(",", $addValidateUseRTE);
		$CBList_separated = implode(",", $addValidateCBList);
		$regex_separated = implode(",", $addValidateRegex);
		$regexmes_separated = implode(",", $addValidateRegexmes);
		$regexmestype_separated = implode(",", $addValidateRegexmestype);
		$jscode.= "var addValidateTypes = String('".$types_separated."').explode(',');\r\n";
		$jscode.= "var addValidateFields = String('".$fields_separated."').explode(',');\r\n";
		$jscode.= "var addValidateUseRTE = String('".$useRTE_separated."').explode(',');\r\n";
		$jscode.= "var addValidateCBList = String('".$CBList_separated."').explode(',');\r\n";
		$jscode.= "var addValidateRegex = String('".$regex_separated."').explode(',');\r\n";
		$jscode.= "var addValidateRegexmes = String('".$regexmes_separated."').explode(',');\r\n";
		$jscode.= "var addValidateRegexmestype = String('".$regexmestype_separated."').explode(',');\r\n";
		$jscode.='$("span[@id^=edit'.$record_id.'_]").each(function(i){
						var j;
						for(j=0;j<addValidateFields.length;j++)
						{
							if(addValidateFields[j]==this.id.substr(this.id.indexOf("_")+1))
							{
								if($(this).attr("type")=="Innova" || $(this).attr("type")=="RTE")
									inlineAddValid'.$record_id.'.add(this, addValidateTypes[j],addValidateUseRTE[j],addValidateCBList[j]);
								else{
										if($("input[@type=text],input[@type=hidden],input[@type=password],input[@type=file],select",this).length)
										{
											if(addValidateCBList[j]=="list")
												elem = $("select",this);
											else
												elem = $("input[@type=text],input[@type=hidden],input[@type=password],input[@type=file],select",this);
											if(addValidateTypes[j]=="Regular expression")
												inlineAddValid'.$record_id.'.addRegex(elem, addValidateTypes[j],addValidateRegex[j],addValidateRegexmes[j],addValidateRegexmestype[j]);	
											else	
												inlineAddValid'.$record_id.'.add(elem, addValidateTypes[j],addValidateUseRTE[j],addValidateCBList[j]);	
										}
									}
							}
						}
					});';	
					} 
	else 
	{
		$jscode.="SetToFirstControl('".$formname."');";
		if($inlineedit==ADD_SIMPLE)
			$jscode.= $bodyonload;
	}
		

if(@$_POST["a"]=="added" && $inlineedit==ADD_ONTHEFLY && !$error_happened && $status!="DECLINED")
{
	$LookupSQL="";
	if($LookupSQL)
		$LookupSQL.=" from ".AddTableWrappers($strOriginalTableName);

	$data=0;
	if(count($keys) && $LookupSQL)
	{
		$where=KeyWhere($keys);
		$LookupSQL.=" where ".$where;
		$rs=db_query($LookupSQL,$conn);
		$data=db_fetch_numarray($rs);
	}
	if(!$data)
	{
		$data=array(@$avalues[$linkfield],@$avalues[$dispfield]);
	}
	echo "<textarea id=\"data\">";
	echo "added";
	print_inline_array($data);
	echo "</textarea>";
	exit();
}


if ( @$_POST["a"]=="added" && $inlineedit==ADD_INLINE ) 
{

	//Preparation   view values
	//	get current values and show edit controls

	$data=0;
	if(count($keys))
	{

		$where=KeyWhere($keys);
		//	select only owned records
		$where=whereAdd($where,SecuritySQL("Search"));
		$strSQL = gSQLWhere($where);

		LogInfo($strSQL);

		$rs=db_query($strSQL,$conn);
		$data=db_fetch_array($rs);
	}
	if(!$data)
	{
		$data=$avalues;
		$HaveData=false;
	}

	//check if correct values added

	
	
	$showKeys[] = htmlspecialchars($keys["itemID"]);

	$keylink="";
	$keylink.="&key1=".htmlspecialchars(rawurlencode(@$data["itemID"]));

//	foreach Fields as @f filter @f.bListPage order @f.nListPageOrder

	////////////////////////////////////////////
	//	year - 
		$value="";
				$value = ProcessLargeText(GetData($data,"year", ""),"","",MODE_LIST);
		$showValues[] = $value;
		$showFields[] = "year";
				$showRawValues[] = substr($data["year"],0,100);
	////////////////////////////////////////////
	//	monthname - 
		$value="";
				$value = ProcessLargeText(GetData($data,"monthname", ""),"","",MODE_LIST);
		$showValues[] = $value;
		$showFields[] = "monthname";
				$showRawValues[] = substr($data["monthname"],0,100);
	////////////////////////////////////////////
	//	userID - 
		$value="";
				$value=DisplayLookupWizard("userID",$data["userID"],$data,$keylink,MODE_LIST);
		$showValues[] = $value;
		$showFields[] = "userID";
				$showRawValues[] = substr($data["userID"],0,100);
	////////////////////////////////////////////
	//	itemID - 
		$value="";
				$value = ProcessLargeText(GetData($data,"itemID", ""),"","",MODE_LIST);
		$showValues[] = $value;
		$showFields[] = "itemID";
				$showRawValues[] = substr($data["itemID"],0,100);
	////////////////////////////////////////////
	//	Storename - 
		$value="";
				$value = ProcessLargeText(GetData($data,"Storename", ""),"","",MODE_LIST);
		$showValues[] = $value;
		$showFields[] = "Storename";
				$showRawValues[] = substr($data["Storename"],0,100);
	////////////////////////////////////////////
	//	Amount - Number
		$value="";
				$value = ProcessLargeText(GetData($data,"Amount", "Number"),"","",MODE_LIST);
		$showValues[] = $value;
		$showFields[] = "Amount";
				$showRawValues[] = substr($data["Amount"],0,100);
	////////////////////////////////////////////
	//	Created - Datetime
		$value="";
				$value = ProcessLargeText(GetData($data,"Created", "Datetime"),"","",MODE_LIST);
		$showValues[] = $value;
		$showFields[] = "Created";
				$showRawValues[] = substr($data["Created"],0,100);
}

if ( @$_POST["a"]=="added" && $inlineedit==ADD_INLINE ) 
{
	echo "<textarea id=\"data\">";
	if($IsSaved && count($showValues))
	{
		if($HaveData)
			echo "saved";
		else
			echo "savnd";
		print_inline_array($showKeys);
		echo "\n";
		print_inline_array($showValues);
		echo "\n";
		print_inline_array($showFields);
		echo "\n";
		print_inline_array($showRawValues);
		echo "\n";
		print_inline_array($showDetailKeys,true);
		echo "\n";
		print_inline_array($showDetailKeys);
		echo "\n";
		echo str_replace(array("&","<","\\","\r","\n"),array("&amp;","&lt;","\\\\","\\r","\\n"),$usermessage);
	}
	else
	{
		if($status=="DECLINED")
			echo "decli";
		else
			echo "error";
		echo str_replace(array("&","<","\\","\r","\n"),array("&amp;","&lt;","\\\\","\\r","\\n"),$message);
	}
	echo "</textarea>";
	exit();
} 

/////////////////////////////////////////////////////////////
//	prepare Edit Controls
/////////////////////////////////////////////////////////////
$jscode.="\r\n window.rteIdArr=".jsreplace("new Object").";\r\n";



$control_Storename=array();
$control_Storename["func"]="xt_buildeditcontrol";
$control_Storename["params"] = array();
$control_Storename["params"]["field"]="Storename";
$control_Storename["params"]["value"]=@$defvalues["Storename"];
$control_Storename["params"]["id"]=$record_id;
if($inlineedit==ADD_INLINE || $inlineedit==ADD_ONTHEFLY)
	$control_Storename["params"]["mode"]="inline_add";
else
	$control_Storename["params"]["mode"]="add";
	
$xt->assignbyref("Storename_editcontrol",$control_Storename);


$control_Amount=array();
$control_Amount["func"]="xt_buildeditcontrol";
$control_Amount["params"] = array();
$control_Amount["params"]["field"]="Amount";
$control_Amount["params"]["value"]=@$defvalues["Amount"];
$control_Amount["params"]["id"]=$record_id;
if($inlineedit==ADD_INLINE || $inlineedit==ADD_ONTHEFLY)
	$control_Amount["params"]["mode"]="inline_add";
else
	$control_Amount["params"]["mode"]="add";
	
$xt->assignbyref("Amount_editcontrol",$control_Amount);


$control_Created=array();
$control_Created["func"]="xt_buildeditcontrol";
$control_Created["params"] = array();
$control_Created["params"]["field"]="Created";
$control_Created["params"]["value"]=@$defvalues["Created"];
$control_Created["params"]["id"]=$record_id;
if($inlineedit==ADD_INLINE || $inlineedit==ADD_ONTHEFLY)
	$control_Created["params"]["mode"]="inline_add";
else
	$control_Created["params"]["mode"]="add";
	
$xt->assignbyref("Created_editcontrol",$control_Created);


$control_year=array();
$control_year["func"]="xt_buildeditcontrol";
$control_year["params"] = array();
$control_year["params"]["field"]="year";
$control_year["params"]["value"]=@$defvalues["year"];
$control_year["params"]["id"]=$record_id;
if($inlineedit==ADD_INLINE || $inlineedit==ADD_ONTHEFLY)
	$control_year["params"]["mode"]="inline_add";
else
	$control_year["params"]["mode"]="add";
	
$xt->assignbyref("year_editcontrol",$control_year);

PrepareJSCode($jscode,$record_id);

	if($inlineedit!=ADD_ONTHEFLY)
	{
		if($inlineedit==ADD_INLINE)
		{
			$jscode=str_replace(array("&","<",">"),array("&amp;","&lt;","&gt;"),$jscode);
			$xt->assignbyref("linkdata",$jscode);
		}
		$body["end"]="</form><script>".$jscode."</script>";
		$xt->assign("body",$body);
		$xt->assign("flybody",true);
	}
	else
	{
		if(!@$_POST["a"]=="added")
		{
			$jscode = str_replace(array("\\","\r","\n"),array("\\\\","\\r","\\n"),$jscode);
			echo $jscode;
			echo "\n";
		}
		else if(@$_POST["a"]=="added" && ($error_happened || $status=="DECLINED"))
		{
			echo "<textarea id=\"data\">decli";
			echo htmlspecialchars($jscode);
			echo "</textarea>";
		}
		$body["end"]="</form>";
		$xt->assign("footer","");
		$xt->assign("flybody",$body);
		$xt->assign("body",true);
	}	

$xt->assign("style_block",true);


if(function_exists("BeforeShowAdd"))
	BeforeShowAdd($xt,$templatefile);

if($inlineedit==ADD_ONTHEFLY)
{
	$xt->load_template($templatefile);
	$xt->display_loaded("style_block");
	$xt->display_loaded("flybody");
}
else
	$xt->display($templatefile);


?>