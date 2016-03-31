<?php
ini_set("display_errors","1");
ini_set("display_startup_errors","1");
ini_set('magic_quotes_runtime', 0);

include("include/dbcommon.php");
if((!isset($_SESSION["count_captcha"])) or ($_SESSION["count_captcha"]>5)) $_SESSION["count_captcha"]=0;

$cEmailField = "Email";
$reminded=false;
$strSearchBy="username";

include('include/xtempl.php');
$xt = new Xtempl();

$strUsername="";
$strEmail="";
$strMessage="";


//	Before Process event
if(function_exists("BeforeProcessRemindPwd"))
	BeforeProcessRemindPwd($conn);

$captcha_passed=0;

if (@$_POST["btnSubmit"] == "Remind")
{
    $strSearchBy=$_POST["searchby"];
	$strUsername=refine(@$_POST["username"]);
	$strEmail=refine(@$_POST["email"]);
    if(($_SESSION["count_captcha"]==0) or ($_SESSION["count_captcha"]>5))
    {
		if(@strtolower(@$_POST["captcha"])!=strtolower(@$_SESSION["captcha"]))
			$strMessage="Invalid security code.";
		else 
			$captcha_passed=1;
    }
    else 
		$captcha_passed=1;
	if($captcha_passed==1)
	{	
	$rstemp=db_query("select * from `member` where 1=0",$conn);

	$tosearch=false;
	if($strSearchBy!="email")
	{
		$value=$strUsername;
		if((string)$value!="")
			$tosearch=true;
		if(FieldNeedQuotes($rstemp,$cUserNameField))
			$value="'".db_addslashes($value)."'";
		else
			$value=(0+$value);
		$sWhere=AddFieldWrappers($cUserNameField)."=".$value;
	}
	else
	{
		$value=$strEmail;
		if((string)$value!="")
			$tosearch=true;
		if(FieldNeedQuotes($rstemp,$cEmailField))
			$value="'".db_addslashes($value)."'";
		else
			$value=(0+$value);
		$sWhere=AddFieldWrappers($cEmailField)."=".$value;
	}
	
	if($tosearch && function_exists("BeforeRemindPassword"))
		$tosearch = BeforeRemindPassword($strUsername,$strEmail);
	
	if($tosearch)
	{
		$strSQL="select ".AddFieldWrappers($cUserNameField).",".AddFieldWrappers($cPasswordField).",".AddFieldWrappers($cEmailField)." from `member` where ".$sWhere;
		$rs=db_query($strSQL,$conn);
		if($data=db_fetch_numarray($rs))
		{
			$password=$data[1];
//	generate 6 chars length password
			$password="";
			for($i=0;$i<10;$i++)
			{
				$j=rand(0,35);
				if($j<26)
					$password.=chr(ord('a')+$j);
				else
					$password.=chr(ord('0')-26+$j);
			}
			db_exec("update `member` set ".AddFieldWrappers($cPasswordField)."='".md5($password)."' where ".$sWhere,$conn);
			$url = GetSiteUrl();
			$url.=$_SERVER["SCRIPT_NAME"];
			$message="Password reminder"."\r\n";
			$message.="You asked to remind your username and password at"." ".$url."\r\n";
			$message.="Username".": ".$data[0]."\r\n";
			$message.="Password".": ".$password."\r\n";
			runner_mail(array('to' => $data[2], 'subject' => "Password reminder", 'body' => $message));
			$reminded=true;
			if(function_exists("AfterRemindPassword"))
				AfterRemindPassword($strUsername,$strEmail);
			$loginlink_attrs="href=\"login.php";
			if($strSearchBy!="email")
				$loginlink_attrs.="?username=".rawurlencode($strUsername);
			$loginlink_attrs.="\"";
			$xt->assign("loginlink_attrs",$loginlink_attrs);
			$xt->assign("body",true);
			$_SESSION["count_captcha"]=$_SESSION["count_captcha"]+1;
			$xt->display("remind_success.htm");
			return;
		}
	}
	
	if(!$reminded)
	{
		if($strSearchBy!="email")
			$strMessage="User"." <i>".$strUsername."</i> "."is not registered.";
		else
			$strMessage="This email doesn't exist in our database";
	}
	}
}
$emailradio_attrs="onclick=\"document.forms.form1.searchby.value='email'; UpdateControls();\"";
$usernameradio_attrs="onclick=\"document.forms.form1.searchby.value='username'; UpdateControls();\"";
if($strSearchBy=="username")
{
	$usernameradio_attrs.=" checked";
	$search_disabled = "email";
}
else
{
	$emailradio_attrs.=" checked";
	$search_disabled = "username";
}
$xt->assign("emailradio_attrs",$emailradio_attrs);
$xt->assign("usernameradio_attrs",$usernameradio_attrs);

$xt->assign("username_attrs","value=\"".htmlspecialchars($strUsername)."\"");
$xt->assign("email_attrs","value=\"".htmlspecialchars($strEmail)."\"");

if(@$strMessage)
{
 	$xt->assign("message",@$strMessage);
	$xt->assign("message_block",true);
	if($captcha_passed==1) 
		$_SESSION["count_captcha"]=$_SESSION["count_captcha"]+1;
}

$body=array();
$body["begin"]="<script language = JavaScript>
function OnKeyDown()
{
	e = window.event;
	if (e.keyCode == 13)
	{
		e.cancel = true;
		document.forms[0].submit();
	}	
}

function UpdateControls()
{
	if (document.forms.form1.searchby.value==\"username\")
	{
		document.forms.form1.username.style.backgroundColor='white';
		document.forms.form1.email.style.backgroundColor='gainsboro';
		document.forms.form1.username.disabled=false; 
		document.forms.form1.email.disabled=true;
	}
	else
	{
		document.forms.form1.username.style.backgroundColor='gainsboro';
		document.forms.form1.email.style.backgroundColor='white';
		document.forms.form1.username.disabled=true; 
		document.forms.form1.email.disabled=false;
	}
}
</script>
<form method=post action=\"remind.php\" id=form1 name=form1>
<input type=hidden name=btnSubmit value=\"Remind\">
<input type=\"Hidden\" name=\"searchby\" value=\"".$strSearchBy."\">";
$body["end"]="</form>
	<script language=\"JavaScript\">
	document.forms.form1.".$search_disabled.".disabled=true;
	UpdateControls();
	</script>";

$xt->assignbyref("body",$body);

if(($_SESSION["count_captcha"]==0) or ($_SESSION["count_captcha"]>5))
{
 $xt->assign("captcha_block",true);
 $captcha_control="<object type=\"application/x-shockwave-flash\" data=\"securitycode.swf?ext=php\" width=\"210\" height=\"65\">
			<param name=\"movie\" value=\"securitycode.swf?ext=php\" />
			<a href=\"http://www.macromedia.com/go/getflashplayer\"><img src=\"\" alt=\"Download Flash\" /></a>
		</object>";
 $xt->assign("captcha",$captcha_control);
}
else $xt->assign("captcha_block",false);

$templatefile="remind.htm";
if(function_exists("BeforeShowRemindPwd"))
	BeforeShowRemindPwd($xt,$templatefile);

$xt->display($templatefile);
