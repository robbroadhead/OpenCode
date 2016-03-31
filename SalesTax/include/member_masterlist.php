<?php
include("member_settings.php");

function DisplayMasterTableInfo_member($params)
{
	$detailtable=$params["detailtable"];
	$keys=$params["keys"];
	global $conn,$strTableName;
	$xt = new Xtempl();
	
	$oldTableName=$strTableName;
	$strTableName="member";

//$strSQL = "SELECT  MemberID,  Login,  Password,  Email,  DisplayName  FROM member  ";

$sqlHead="SELECT MemberID,  Login,  Password,  Email,  DisplayName ";
$sqlFrom="FROM member ";
$sqlWhere="";
$sqlTail="";

$where="";

if($detailtable=="Tax Summary")
{
		$where.= GetFullFieldName("MemberID")."=".make_db_value("MemberID",$keys[1-1]);
}
if(!$where)
{
	$strTableName=$oldTableName;
	return;
}
	$str = SecuritySQL("Search");
	if(strlen($str))
		$where.=" and ".$str;

	$strWhere=whereAdd($sqlWhere,$where);
	if(strlen($strWhere))
		$strWhere=" where ".$strWhere." ";
	$strSQL= $sqlHead.$sqlFrom.$strWhere.$sqlTail;

//	$strSQL=AddWhere($strSQL,$where);
	LogInfo($strSQL);
	$rs=db_query($strSQL,$conn);
	$data=db_fetch_array($rs);
	$keylink="";
	$keylink.="&key1=".htmlspecialchars(rawurlencode(@$data["MemberID"]));
	

	$strTableName=$oldTableName;
	$xt->display("member_masterlist.htm");
}

// events

?>