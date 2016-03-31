<?php
ini_set("display_errors","1");
ini_set("display_startup_errors","1");
ini_set('magic_quotes_runtime', 0);

include("include/dbcommon.php");

if(!@$_SESSION["UserID"])
{
	header("Location: login.php");
	return;
}


include('include/xtempl.php');
$xt = new Xtempl();


//	Before Process event
if(function_exists("BeforeProcessMenu"))
	BeforeProcessMenu($conn);
$xt->assign("body",true);
$body=array();
$body["begin"] = "<script type=\"text/javascript\" src=\"include/jquery.js\"></script>".
"<script type=\"text/javascript\" src=\"include/jsfunctions.js\"></script>";
$xt->assignbyref("body",$body);

$xt->assign("username",$_SESSION["UserID"]);
$xt->assign("changepwd_link",$_SESSION["AccessLevel"] != ACCESS_LEVEL_GUEST);
$xt->assign("changepwdlink_attrs","onclick=\"window.location.href='changepwd.php';return false;\"");
$xt->assign("logoutlink_attrs","onclick=\"window.location.href='login.php?a=logout';\"");

$xt->assign("loggedas_block",true);
$xt->assign("logout_link",true);
$createmenu = false;
$allow_member=true;
if($allow_member)
{
	$createmenu=true;
	$xt->assign("member_tablelink",true);
	$page="";
		$page="list";
		$strPerm = GetUserPermissions("member");
	if(strpos($strPerm, "A")!==false && strpos($strPerm, "S")===false)
		$page="add";
	$xt->assign("member_tablelink_attrs","href=\"member_".$page.".php\"");
}
$allow_taxitem=true;
if($allow_taxitem)
{
	$createmenu=true;
	$xt->assign("taxitem_tablelink",true);
	$page="";
		$page="list";
		$strPerm = GetUserPermissions("taxitem");
	if(strpos($strPerm, "A")!==false && strpos($strPerm, "S")===false)
		$page="add";
	$xt->assign("taxitem_tablelink_attrs","href=\"taxitem_".$page.".php\"");
}
$allow_Tax_Summary=true;
if($allow_Tax_Summary)
{
	$createmenu=true;
	$xt->assign("Tax_Summary_tablelink",true);
	$page="";
		$page="list";
		$xt->assign("Tax_Summary_tablelink_attrs","href=\"Tax_Summary_".$page.".php\"");
}

if($createmenu)
	$xt->assign("menustyle_block",true);




$templatefile="menu.htm";
if(function_exists("BeforeShowMenu"))
	BeforeShowMenu($xt,$templatefile);

$xt->display($templatefile);
?>