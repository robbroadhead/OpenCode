<?php
ini_set("display_errors","1");
ini_set("display_startup_errors","1");
ini_set('magic_quotes_runtime', 0);
include("include/dbcommon.php");
include("include/member_variables.php");
if((!isset($_SESSION["count_captcha"])) or ($_SESSION["count_captcha"]>4)) $_SESSION["count_captcha"]=0;
$registered=false;
//event for onsubmit
$onsubmit="";
$strMessage="";
$allow_registration=true;
$strUsername="";
$strPassword="";
$strEmail="";
$values=array();

include('include/xtempl.php');
$xt = new Xtempl();


//	Before Process event
if(function_exists("BeforeProcessRegister"))
	BeforeProcessRegister($conn);


if(@$_POST["btnSubmit"] == "Register")
{
	if(($_SESSION["count_captcha"]==0) or ($_SESSION["count_captcha"]>4))
	{
		if(@strtolower(@$_POST["captcha"])!=strtolower(@$_SESSION["captcha"]))
		{
			$xt->assign("captcha_msg","Invalid security code.");
			$allow_registration=false;
			$captcha_passed=0;
		}
		else
		 	$captcha_passed=1;
	}
    else 
		$captcha_passed=1;

	$filename_values=array();
	$blobfields=array();
	$files_move=array();
	$files_save=array();
	$inlineedit=ADD_SIMPLE;

//	processing Login - start

	$value = postvalue("value_Login");
	$type=postvalue("type_Login");
	if (FieldSubmitted("Login"))
	{
		$value=prepare_for_db("Login",$value,$type);
	}
	else
		$value=false;
	if(!($value===false))
	{


		$blobfields[]="Login";
		$values["Login"]=$value;
	}
//	processibng Login - end
//	processing Password - start

	$value = postvalue("value_Password");
	$type=postvalue("type_Password");
	if (FieldSubmitted("Password"))
	{
		$value=prepare_for_db("Password",$value,$type);
	}
	else
		$value=false;
	if(!($value===false))
	{


		$blobfields[]="Password";
		$values["Password"]=$value;
	}
//	processibng Password - end
//	processing Email - start

	$value = postvalue("value_Email");
	$type=postvalue("type_Email");
	if (FieldSubmitted("Email"))
	{
		$value=prepare_for_db("Email",$value,$type);
	}
	else
		$value=false;
	if(!($value===false))
	{


		$blobfields[]="Email";
		$values["Email"]=$value;
	}
//	processibng Email - end
//	processing DisplayName - start

	$value = postvalue("value_DisplayName");
	$type=postvalue("type_DisplayName");
	if (FieldSubmitted("DisplayName"))
	{
		$value=prepare_for_db("DisplayName",$value,$type);
	}
	else
		$value=false;
	if(!($value===false))
	{


		$blobfields[]="DisplayName";
		$values["DisplayName"]=$value;
	}
//	processibng DisplayName - end

	$strUsername = $values["Login"];
	$strPassword = $values["Password"];
	$strEmail = $values["Email"];


//	add filenames to values
	foreach($filename_values as $key=>$value)
		$values[$key]=$value;

//	check if entered username already exists
	if(!strlen($strUsername))
	{
		$xt->assign("user_msg","Username can not be empty.");
		$allow_registration=false;
	}	
	else
	{
		$strSQL="select count(*) from `member` where `Login`=".add_db_quotes("Login",$strUsername);
	   	$rs=db_query($strSQL,$conn);
		$data=db_fetch_numarray($rs);
		if($data[0]>0)
		{
			$xt->assign("user_msg","Username"." <i>".$strUsername."</i> "."already exists. Choose another username.");
			$allow_registration=false;
		}
	}

//	check if entered email already exists
	
	if(!strlen($strEmail))
	{
		$xt->assign("email_msg","Please enter a valid email address.");
		$allow_registration=false;
	}
	else
	{
		$strSQL="select count(*) from `member` where `Email`=".add_db_quotes("Email",$strEmail);
	   	$rs=db_query($strSQL,$conn);
		$data=db_fetch_numarray($rs);
		if($data[0]>0)
		{
			$xt->assign("email_msg","Email"." <i>".$strEmail."</i> "."already registered. If you forgot your username or password use the password reminder form.");
			$allow_registration=false;
		}
	}

	$retval=true;
	
	if($allow_registration)
	{
		if(function_exists("BeforeRegister"))
			$retval = BeforeRegister($values,$strMessage);
	}
	else
		$retval=false;

	if($retval)
	{
//	encrypt password
		$originalpassword=$values["Password"];
		$values["Password"]=md5($values["Password"]);
		$retval=DoInsertRecord("member",$values,$blobfields);
		$values["Password"]=$originalpassword;
	}
	else
		$retval=false;
	if($retval)
	{
		if(function_exists("AfterSuccessfulRegistration"))
			AfterSuccessfulRegistration($values);
		$url = GetSiteUrl();
		$url.=$_SERVER["SCRIPT_NAME"];


//	show Registartion successful message
		$body=array();
		$body["begin"]="<form method=\"POST\" action=\"login.php\" name=\"loginform\">
		<input type=\"Hidden\" name=username value=\"".htmlspecialchars($strUsername)."\">".
		"<input type=\"Hidden\" name=password value=\"".htmlspecialchars($strPassword)."\">".
		"</form>";
		$xt->assign("registered_block",true);
		$xt->assign("body",$body);
		$xt->assign("loginlink_attrs","onclick=\"document.forms.loginform.submit();return false;\"");
		$_SESSION["count_captcha"]=$_SESSION["count_captcha"]+1;

		$xt->display("register_success.htm");
		return;
	}
	else
	{
		if(function_exists("AfterUnsuccessfulRegistration"))
			AfterUnsuccessfulRegistration($values,$strMessage);
	}
	if(!$retval)
	{
		if($strMessage!="")
		{
			$xt->assign("message",$strMessage);
			$xt->assign("message_block",true);
		}
		if($captcha_passed==1) $_SESSION["count_captcha"]=$_SESSION["count_captcha"]+1;
	}
}
//////////////////////////////////////////////////////////////////	
//	validation stuff
$bodyonload="";
//Basic includes js files
$includes="";
//javascript code
$jscode="";
//	Begin Add validation params for Regester	
	$regex='';
	$regexmessage='';
	$func="";
	$regextype = '';
	$arrValidate = array();
//	for inlineEdit
	$regValidateTypes = array();
	$regValidateFields = array();
	$regValidateUseRTE = array();
	$regValidateCBList = array();
	$regValidateRegex = array();
	$regValidateRegexmes = array();	
	$regValidateRegexmestype = array();	
//	Begin Edit validation	
//if use InnovaEditor or RTE on page add when useRTE will be with  -  "_FLY"
//if use InnovaEditor or RTE on page InineEdit when useRTE will be with out  -  "_FLY"
	AddJSFile("validate");
	
	if(@$_REQUEST["language"])
		$language = $_REQUEST["language"];
	// may be elseif ?
	if(@$_SESSION["language"])
		$language = $_SESSION["language"];
	else
		$language = 'English';
	
	$jscode.="window.current_language='".jsreplace($language)."';\r\n";
	
	$jscode.="regValid = new validation();\r\n";
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
	$jscode.="window.TEXT_INLINE_MATCH_PASSWORDS='".jsreplace("Passwords do not match. Re-enter password")."';\r\n";	
$bodyonload.="regValid.add(document.editform['value_Login'],'IsRequired','','');\r\n";	
$bodyonload.="regValid.add(document.editform['value_Password'],'IsRequired','','');\r\n";	
$bodyonload.="regValid.add(document.editform['value1_Password'],'IsMatchPasswords','','');\r\n";
$bodyonload.="regValid.add(document.editform['value1_Password'],'IsRequired','','');\r\n";
for($i=0;$i<count($arrValidate);$i++)
{
	if($arrValidate[$i][1])
	{
		if ($arrValidate[$i][1]=="Regular expression")
			$bodyonload.="regValid.addRegex(document.editform['value_".$arrValidate[$i][0]."'],'".$arrValidate[$i][1]."','".
				jsreplace($arrValidate[$i][5])."','".jsreplace($arrValidate[$i][6])."','".jsreplace($arrValidate[$i][7])."');\r\n";
		else
			$bodyonload.="regValid.add(document.editform['value_".$arrValidate[$i][0]."'],'".$arrValidate[$i][1]."','".$arrValidate[$i][3]."','".$arrValidate[$i][4]."');\r\n";
	}
	if($arrValidate[$i][2])
	{
		if($arrValidate[$i][3]=='INNOVA_FLY' || $arrValidate[$i][3]=='RTE_FLY')
		{
			$bodyonload.='$("table:last td").each(function(){';
			$bodyonload.='if($("iframe[@name=\'value_'.$arrValidate[$i][0].'\']",this).length)';
			$bodyonload.='regValid.add($("iframe[@name=\'value_'.$arrValidate[$i][0].'\']",this),"'.$arrValidate[$i][2].'","'.$arrValidate[$i][3].'","'.$arrValidate[$i][4].'");});';
			$func.='getDataFromRTEInnova($(\'#value_'.$arrValidate[$i][0].'\'),\''.$arrValidate[$i][3].'\',$(\'#editform\'),\'value_'.$arrValidate[$i][0].'\');'; 
		}
		else
			$bodyonload.="regValid.add(document.editform['".($arrValidate[$i][4]=='disp' ? "display_" : "")."value_".$arrValidate[$i][0].($arrValidate[$i][4]=='CBList' || $arrValidate[$i][4]=='list' ? "[]" : "")."'],'".$arrValidate[$i][2]."','".$arrValidate[$i][3]."','".$arrValidate[$i][4]."');\r\n";
	}		
}
if(($_SESSION["count_captcha"]==0) or ($_SESSION["count_captcha"]>4))
	$bodyonload.="regValid.add(document.editform['captcha'],'IsRequired','','');\r\n";	
$bodyonload.="regValid.add(document.editform['value_Email'],'IsEmail','','');\r\n";	
//	End Add validation params for InlineEdit or Edit
//////////////////////////////////////////////////////////////
////////////////////// time picker
//////////////////////
		$onsubmit.="return regValid.validate();";
$includes.="<script language=\"JavaScript\" src=\"include/jquery.js\"></script>\r\n";
	AddJSFile("ajaxsuggest");
	$includes.="<script language=\"JavaScript\" src=\"include/customlabels.js\"></script>\r\n";
	$includes.="<script language=\"JavaScript\" src=\"include/jsfunctions.js\"></script>\r\n";
	$jscode.= "window.locale_dateformat = ".$locale_info["LOCALE_IDATE"].";\r\n".
	"window.locale_datedelimiter = \"".$locale_info["LOCALE_SDATE"]."\";\r\n".
	"bLoading=false;\r\n".
	"window.TEXT_PLEASE_SELECT='".jsreplace("Please select")."';\r\n";
	$jscode.="SUGGEST_TABLE='member_searchsuggest.php';\r\n";
	$includes.="<div id=\"search_suggest\"></div>\r\n";


$xt->assign("bodyonload",$bodyonload);

if(strlen($onsubmit))
	$onsubmit="onSubmit=\"".$onsubmit."\"";



//	assign values to the controls

if(!count($values))
{
}

//$xt->assign("value_Login",@$values["Login"]);
$control_Login=array();
$control_Login["func"]="xt_buildeditcontrol";
$control_Login["params"] = array();
$control_Login["params"]["field"]="Login";
$control_Login["params"]["value"]=@$values["Login"];
$control_Login["params"]["mode"]="add";
$xt->assignbyref("Login_editcontrol",$control_Login);
$xt->assign("Login_fieldblock",true);
//$xt->assign("value_Email",@$values["Email"]);
$control_Email=array();
$control_Email["func"]="xt_buildeditcontrol";
$control_Email["params"] = array();
$control_Email["params"]["field"]="Email";
$control_Email["params"]["value"]=@$values["Email"];
$control_Email["params"]["mode"]="add";
$xt->assignbyref("Email_editcontrol",$control_Email);
$xt->assign("Email_fieldblock",true);
//$xt->assign("value_DisplayName",@$values["DisplayName"]);
$control_DisplayName=array();
$control_DisplayName["func"]="xt_buildeditcontrol";
$control_DisplayName["params"] = array();
$control_DisplayName["params"]["field"]="DisplayName";
$control_DisplayName["params"]["value"]=@$values["DisplayName"];
$control_DisplayName["params"]["mode"]="add";
$xt->assignbyref("DisplayName_editcontrol",$control_DisplayName);
$xt->assign("DisplayName_fieldblock",true);

$xt->assign("password_attrs","name=\"value_Password\" value=\"".htmlspecialchars(@$values["Password"])."\"");
$xt->assign("confirm_attrs","name=\"value1_Password\" value=\"".htmlspecialchars(@$values["Password"])."\"");
$xt->assign("password_block",true);
$xt->assign("confirm_block",true);
$xt->assign("username_block",true);
$xt->assign("buttons_block",true);

$readonlyfields=array();

//	show readonly fields

if ($useAJAX) {
}
else
{
}

if(($_SESSION["count_captcha"]==0) or ($_SESSION["count_captcha"]>4))
{
 $xt->assign("captcha_block",true);
 $xt->assign("captcha",
 "<object type=\"application/x-shockwave-flash\" data=\"securitycode.swf?ext=php\" width=\"210\" height=\"65\">
	<param name=\"movie\" value=\"securitycode.swf?ext=php\" />
	<param name=\"wmode\" value=\"opaque\" />
	<a href=\"http://www.macromedia.com/go/getflashplayer\"><img src=\"\" alt=\"Download Flash\" /></a>
  </object>");
}
else $xt->assign("captcha_block",false);

$body=array();
$body["begin"]=$includes.
	"<form encType=\"multipart/form-data\" method=\"POST\" action=\"register.php\" id=\"editform\" name=\"editform\" ".$onsubmit.">
	<input type=hidden name=btnSubmit value=\"Register\">";
$jscode.= "flag_but=0; ".$bodyonload."\r\n SetToFirstControl('editform');\r\n";
	PrepareJSCode($jscode,'');
$body["end"]="</form><script>".$jscode."</script>";	
	$xt->assignbyref("body",$body);
$templatefile="register.htm";
if(function_exists("BeforeShowRegister"))
	BeforeShowRegister($xt,$templatefile);

$xt->display($templatefile);

?>
