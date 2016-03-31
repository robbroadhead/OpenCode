<?php
ini_set("display_errors","1");
ini_set("display_startup_errors","1");
ini_set('magic_quotes_runtime', 0);

include("include/dbcommon.php");

if(!@$_SESSION["UserID"] || @$_SESSION["UserID"]=="<Guest>")
{ 
	$_SESSION["MyURL"]=$_SERVER["SCRIPT_NAME"]."?".$_SERVER["QUERY_STRING"];
	header("Location: login.php?message=expired"); 
	return;
}

$message="";
$go=1;

include('include/xtempl.php');
$xt = new Xtempl();

//	Before Process event
if(function_exists("BeforeProcessChangePwd"))
	BeforeProcessChangePwd($conn);

if (@$_POST["btnSubmit"] == "Submit")
{	
	$rstemp=db_query("select * from `member` where 1=0",$conn);
	$go = postvalue("go")+1;
	$xt->assign("backlink_attrs","href=\"javascript:history.go(-".$go.")\"");
	$opass = postvalue("opass");
	$newpass = postvalue("newpass");
	$newpassraw=$newpass;
	$opass = md5($opass);
	$newpass = md5($newpass);
	
	$value = @$_SESSION["UserID"];
	if(FieldNeedQuotes($rstemp,$cUserNameField))
		$value="'".db_addslashes($value)."'";
	else
		$value=(0+$value);
	$passvalue = $newpass;
	if(FieldNeedQuotes($rstemp,$cPasswordField))
		$passvalue="'".db_addslashes($passvalue)."'";
	else
		$passvalue=(0+$passvalue);


    	$sWhere = " where ".AddFieldWrappers($cUserNameField)."=".$value;
		$strSQL = "select * from ".AddTableWrappers($cLoginTable).$sWhere;
		$rstemp=db_query($strSQL,$conn);

		if($row=db_fetch_array($rstemp))
		{
			if($opass == $row[$cPasswordField])
			{
				$retval=true;
				if(function_exists("BeforeChangePassword"))
					$retval=BeforeChangePassword($_POST["opass"],$_POST["newpass"]);
				if($retval)
				{
					$strSQL= "update ".AddTableWrappers($cLoginTable)." set ".AddFieldWrappers($cPasswordField)."=".$passvalue.$sWhere;
					db_exec($strSQL,$conn);
					if(function_exists("AfterChangePassword"))
						AfterChangePassword($_POST["opass"],$_POST["newpass"]);
					$xt->assign("body",true);
					$xt->display("changepwd_success.htm");
					return;
				}
			}
			else
				$message = "Invalid password";
	}
}
else $xt->assign("backlink_attrs","href=\"javascript:history.go(-1)\"");
	
if($message)
{
	$xt->assign("message",$message);
	$xt->assign("message_block",true);
}

$body=array();
$body["begin"]="<script language=\"JavaScript\">
function validate()
{

	
	if (document.forms.form1.cpass.value!=document.forms.form1.newpass.value)
	{	
		alert('".jsreplace("Passwords do not match. Re-enter password").
		"');
		document.forms.form1.newpass.value='';
		document.forms.form1.cpass.value='';
		document.forms.form1.newpass.focus();
		return false;
	}
	return true;
}
</script>
 <form method=\"POST\" action=\"changepwd.php\" id=form1 name=form1 onsubmit=\"return validate();\">
<input type=hidden name=btnSubmit value=\"Submit\">
<input type=hidden name=go value=\"".$go."\">";
$body["end"]="</form>";
$xt->assignbyref("body",$body);

$templatefile="changepwd.htm";
if(function_exists("BeforeShowChangePwd"))
	BeforeShowChangePwd($xt,$templatefile);

$xt->display($templatefile);
?>