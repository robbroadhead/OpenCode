<?php
ini_set("display_errors","1");
ini_set("display_startup_errors","1");
ini_set('magic_quotes_runtime', 0);
include("include/dbcommon.php");
header("Expires: Thu, 01 Jan 1970 00:00:01 GMT");
header("Pragma: no-cache");
header("Cache-Control: no-cache");

if(@$_POST["a"]=="logout" || @$_GET["a"]=="logout")
{
	session_unset();
	setcookie("username","",time()-365*1440*60);
	setcookie("password","",time()-365*1440*60);
	header("Location: login.php");
	exit();
}

include('include/xtempl.php');
$xt = new Xtempl();



//	Before Process event
if(function_exists("BeforeProcessLogin"))
	BeforeProcessLogin($conn);


$myurl=@$_SESSION["MyURL"];
unset($_SESSION["MyURL"]);

$defaulturl="";
		$defaulturl="menu.php";




$message="";

$pUsername=postvalue("username");
$pPassword=postvalue("password");

$rememberbox_checked="";
$rememberbox_attrs = "name=\"remember_password\" value=\"1\"";
if(@$_COOKIE["username"] || @$_COOKIE["password"])
	$rememberbox_checked=" checked";

if (@$_POST["btnSubmit"] == "Login")
{
	if(@$_POST["remember_password"] == 1)
	{
		setcookie("username",$pUsername,time()+365*1440*60);
		setcookie("password",$pPassword,time()+365*1440*60);
		$rememberbox_checked=" checked";
	}
	else
	{
		setcookie("username","",time()-365*1440*60);
		setcookie("password","",time()-365*1440*60);
		$rememberbox_checked="";
	}
//   	 username and password are stored in the database
	$strUsername = (string)$pUsername;
	$strPassword = (string)$pPassword;
	$strPassword=md5($strPassword);
	$sUsername=$strUsername;
	$sPassword=$strPassword;
	$rstemp=db_query("select * from `member` where 1=0",$conn);
		
	if(FieldNeedQuotes($rstemp,$cUserNameField))
		$strUsername="'".db_addslashes($strUsername)."'";
	else
		$strUsername=(0+$strUsername);
	if(FieldNeedQuotes($rstemp,$cPasswordField))
		$strPassword="'".db_addslashes($strPassword)."'";
	else
		$strPassword=(0+$strPassword);
		$strSQL = "select * from `member` where ".AddFieldWrappers($cUserNameField).
		"=".$strUsername." and ".AddFieldWrappers($cPasswordField).
		"=".$strPassword;
	$retval=true;
   	$logged=false;
	if(function_exists("BeforeLogin"))
		$retval=BeforeLogin($pUsername,$pPassword,$message);
	if($retval)
	{
		$rs=db_query($strSQL,$conn);
	 	$data=db_fetch_array($rs);
		if($data)
			if(@$data[$cUserNameField]==$sUsername && @$data[$cPasswordField]==$sPassword)
				$logged=true;
	}
	if($logged)
	{
		$_SESSION["UserID"] = $pUsername;
   		$_SESSION["AccessLevel"] = ACCESS_LEVEL_USER;

		$_SESSION["GroupID"] = "";
		



					$_SESSION["OwnerID"] = $data["MemberID"];
	$_SESSION["_member_OwnerID"] = $data["MemberID"];
			$_SESSION["_taxitem_OwnerID"] = $data["MemberID"];
			$_SESSION["_Tax Summary_OwnerID"] = $data["MemberID"];
	

		if(function_exists("AfterSuccessfulLogin"))
			AfterSuccessfulLogin($pUsername,$pPassword,$data);
		if($myurl)
			header("Location: ".$myurl);
		else
			header("Location: ".$defaulturl);
		return;
   	}
	else
	{
		if(function_exists("AfterUnsuccessfulLogin"))
			AfterUnsuccessfulLogin($pUsername,$pPassword,$message);
		if($message=="")
			$message = "Invalid Login";
	}
}

$xt->assign("rememberbox_attrs",$rememberbox_attrs.$rememberbox_checked);


	$xt->assign("guestlink_block",false);

	
$_SESSION["MyURL"]=$myurl;
if($myurl)
	$xt->assign("guestlink_attrs","href=\"".$myurl."\"");
else
	$xt->assign("guestlink_attrs","href=\"".$defaulturl."\"");
	

if(@$_POST["username"] || @$_GET["username"])
	$xt->assign("username_attrs","value=\"".htmlspecialchars($pUsername)."\"");
else
	$xt->assign("username_attrs","value=\"".htmlspecialchars(refine(@$_COOKIE["username"]))."\"");


$password_attrs="onkeydown=\"e=event; if(!e) e = window.event; if (e.keyCode != 13) return; e.cancel = true; e.cancelBubble=true; document.forms[0].submit(); return false;\"";
if(@$_POST["password"])
	$password_attrs.=" value=\"".htmlspecialchars($pPassword)."\"";
else
	$password_attrs.=" value=\"".htmlspecialchars(refine(@$_COOKIE["password"]))."\"";
$xt->assign("password_attrs",$password_attrs);

if(@$_GET["message"]=="expired")
	$message = "Your session has expired. Please login again.";


if($message)
{
	$xt->assign("message_block",true);
	$xt->assign("message",$message);
}

$body=array();
$body["begin"]="<form method=post action=\"login.php\" id=form1 name=form1>
		<input type=hidden name=btnSubmit value=\"Login\">";
$body["end"]="</form>
<script>
function elementVisible(jselement)
{ 
	do
	{
		if (jselement.style.display.toUpperCase() == 'NONE')
			return false;
		jselement=jselement.parentNode; 
	}
	while (jselement.tagName.toUpperCase() != 'BODY'); 
	return true;
}
if(elementVisible(document.forms[0].elements['username']))
	document.forms[0].elements['username'].focus();
</script>";
$xt->assignbyref("body",$body);

$templatefile="login.htm";
if(function_exists("BeforeShowLogin"))
	BeforeShowLogin($xt,$templatefile);

$xt->display($templatefile);
?>