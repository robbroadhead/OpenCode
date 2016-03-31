<?php
$strTableName="Tax Summary";
$_SESSION["OwnerID"] = $_SESSION["_".$strTableName."_OwnerID"];

$strOriginalTableName="taxitem";

$gPageSize=20;

$gstrOrderBy="ORDER BY year(Created), month(Created)";
if(strlen($gstrOrderBy) && strtolower(substr($gstrOrderBy,0,8))!="order by")
	$gstrOrderBy="order by ".$gstrOrderBy;

$g_orderindexes=array();
$gsqlHead="SELECT COUNT(itemID) AS Entries,  SUM(Amount) AS Amount,  userID,  Created,  monthname(Created) AS monthname,  year(Created) AS `year` ";
$gsqlFrom="FROM taxitem ";
$gsqlWhereExpr="";
$gsqlTail="GROUP BY monthname(Created), year(Created) ";

include("Tax_Summary_settings.php");

$reportCaseSensitiveGroupFields = false;

$gstrSQL = gSQLWhere("");


?>