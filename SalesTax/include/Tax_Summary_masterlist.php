<?php
include("Tax_Summary_settings.php");

function DisplayMasterTableInfo_Tax_Summary($params)
{
	$detailtable=$params["detailtable"];
	$keys=$params["keys"];
	global $conn,$strTableName;
	$xt = new Xtempl();
	
	$oldTableName=$strTableName;
	$strTableName="Tax Summary";

//$strSQL = "SELECT  COUNT(itemID) AS Entries,  SUM(Amount) AS Amount,  userID,  Created,  monthname(Created) AS monthname,  year(Created) AS `year`  FROM taxitem  GROUP BY monthname(Created), year(Created)  ORDER BY year(Created), month(Created)  ";

$sqlHead="SELECT COUNT(itemID) AS Entries,  SUM(Amount) AS Amount,  userID,  Created,  monthname(Created) AS monthname,  year(Created) AS `year` ";
$sqlFrom="FROM taxitem ";
$sqlWhere="";
$sqlTail="GROUP BY monthname(Created), year(Created) ";

$where="";

if($detailtable=="taxitem")
{
		$where.= GetFullFieldName("userID")."=".make_db_value("userID",$keys[1-1]);
		$where.=" and ";
	$where.= GetFullFieldName("monthname")."=".make_db_value("monthname",$keys[2-1]);
		$where.=" and ";
	$where.= GetFullFieldName("year")."=".make_db_value("year",$keys[3-1]);
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
	


//	Entries - 
			$value="";
				$value = ProcessLargeText(GetData($data,"Entries", ""),"field=Entries".$keylink);
			$xt->assign("Entries_mastervalue",$value);

//	Amount - Number
			$value="";
				$value = ProcessLargeText(GetData($data,"Amount", "Number"),"field=Amount".$keylink);
			$xt->assign("Amount_mastervalue",$value);

//	monthname - 
			$value="";
				$value = ProcessLargeText(GetData($data,"monthname", ""),"field=monthname".$keylink);
			$xt->assign("monthname_mastervalue",$value);

//	year - 
			$value="";
				$value = ProcessLargeText(GetData($data,"year", ""),"field=year".$keylink);
			$xt->assign("year_mastervalue",$value);
	$strTableName=$oldTableName;
	$xt->display("Tax_Summary_masterlist.htm");
}

// events

?>