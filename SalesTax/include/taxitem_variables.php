<?php
$strTableName="taxitem";
$_SESSION["OwnerID"] = $_SESSION["_".$strTableName."_OwnerID"];

$strOriginalTableName="taxitem";

$gPageSize=20;

$gstrOrderBy="";
if(strlen($gstrOrderBy) && strtolower(substr($gstrOrderBy,0,8))!="order by")
	$gstrOrderBy="order by ".$gstrOrderBy;

$g_orderindexes=array();
$gsqlHead="SELECT itemID,  Storename,  Amount,  userID,  Created,  monthname(Created) AS `monthname`,  year(Created) AS `year` ";
$gsqlFrom="FROM taxitem ";
$gsqlWhereExpr="";
$gsqlTail="";

include("taxitem_settings.php");

$reportCaseSensitiveGroupFields = false;

$gstrSQL = gSQLWhere("");


?>