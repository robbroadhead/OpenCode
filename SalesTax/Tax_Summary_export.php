<?php 
ini_set("display_errors","1");
ini_set("display_startup_errors","1");
session_cache_limiter("none");
ini_set('magic_quotes_runtime', 0);

include("include/dbcommon.php");
include("include/Tax_Summary_variables.php");

if(!@$_SESSION["UserID"])
{ 
	$_SESSION["MyURL"]=$_SERVER["SCRIPT_NAME"]."?".$_SERVER["QUERY_STRING"];
	header("Location: login.php?message=expired"); 
	return;
}
if(!CheckSecurity(@$_SESSION["_".$strTableName."_OwnerID"],"Export"))
{
	echo "<p>"."You don't have permissions to access this table"."<a href=\"login.php\">"."Back to login page"."</a></p>";
	return;
}


//	Before Process event
if(function_exists("BeforeProcessExport"))
	BeforeProcessExport($conn);

$strWhereClause="";

$options = "1";
if (@$_REQUEST["a"]!="")
{
	$options = "";
	$sWhere = "1=0";	

//	process selection
	$selected_recs=array();
	if (@$_REQUEST["mdelete"])
	{
		foreach(@$_REQUEST["mdelete"] as $ind)
		{
			$keys=array();
			$selected_recs[]=$keys;
		}
	}
	elseif(@$_REQUEST["selection"])
	{
		foreach(@$_REQUEST["selection"] as $keyblock)
		{
			$arr=explode("&",refine($keyblock));
			if(count($arr)<0)
				continue;
			$keys=array();
			$selected_recs[]=$keys;
		}
	}

	foreach($selected_recs as $keys)
	{
		$sWhere = $sWhere . " or ";
		$sWhere.=KeyWhere($keys);
	}

	$sWhere = whereAdd($sWhere,SecuritySQL("Search"));

	$strSQL = gSQLWhere($sWhere);
	$strWhereClause=$sWhere;
	
	$_SESSION[$strTableName."_SelectedSQL"] = $strSQL;
	$_SESSION[$strTableName."_SelectedWhere"] = $sWhere;
}

if ($_SESSION[$strTableName."_SelectedSQL"]!="" && @$_REQUEST["records"]=="") 
{
	$strSQL = $_SESSION[$strTableName."_SelectedSQL"];
	$strWhereClause=@$_SESSION[$strTableName."_SelectedWhere"];
}
else
{
	$strWhereClause=@$_SESSION[$strTableName."_where"];
	if($strWhereClause=="")
		$strWhereClause = whereAdd($strWhereClause,SecuritySQL("Search"));
	$strSQL=gSQLWhere($strWhereClause);
}


$mypage=1;
if(@$_REQUEST["type"])
{
//	order by
	$strOrderBy=$_SESSION[$strTableName."_order"];
	if(!$strOrderBy)
		$strOrderBy=$gstrOrderBy;
	$strSQL.=" ".trim($strOrderBy);

	$strSQLbak = $strSQL;
	if(function_exists("BeforeQueryExport"))
		BeforeQueryExport($strSQL,$strWhereClause,$strOrderBy);
//	Rebuild SQL if needed
	if($strSQL!=$strSQLbak)
	{
//	changed $strSQL - old style	
		$numrows=GetRowCount($strSQL);
	}
	else
	{
		$strSQL = gSQLWhere($strWhereClause);
		$strSQL.=" ".trim($strOrderBy);
		$numrows=gSQLRowCount($strWhereClause,1);
	}
	LogInfo($strSQL);

//	 Pagination:

	$nPageSize=0;
	if(@$_REQUEST["records"]=="page" && $numrows)
	{
		$mypage=(integer)@$_SESSION[$strTableName."_pagenumber"];
		$nPageSize=(integer)@$_SESSION[$strTableName."_pagesize"];
		if($numrows<=($mypage-1)*$nPageSize)
			$mypage=ceil($numrows/$nPageSize);
		if(!$nPageSize)
			$nPageSize=$gPageSize;
		if(!$mypage)
			$mypage=1;

		$strSQL.=" limit ".(($mypage-1)*$nPageSize).",".$nPageSize;
	}

	$rs=db_query($strSQL,$conn);

	if(!ini_get("safe_mode"))
		set_time_limit(300);
	
	if(@$_REQUEST["type"]=="excel")
	{
		ExportToExcel();
	}
	else if(@$_REQUEST["type"]=="word")
	{
		ExportToWord();
	}
	else if(@$_REQUEST["type"]=="xml")
	{
		ExportToXML();
	}
	else if(@$_REQUEST["type"]=="csv")
	{
		ExportToCSV();
	}
	else if(@$_REQUEST["type"]=="pdf")
	{
		ExportToPDF();
	}

	db_close($conn);
	return;
}

header("Expires: Thu, 01 Jan 1970 00:00:01 GMT"); 

include('include/xtempl.php');
$xt = new Xtempl();
if($options)
{
	$xt->assign("rangeheader_block",true);
	$xt->assign("range_block",true);
}
$body=array();
$body["begin"]="<form action=\"Tax_Summary_export.php\" method=get id=frmexport name=frmexport>";
$body["end"]="</form>";
$xt->assignbyref("body",$body);
$xt->display("Tax_Summary_export.htm");


function ExportToExcel()
{
	global $cCharset;
	header("Content-Type: application/vnd.ms-excel");
	header("Content-Disposition: attachment;Filename=Tax_Summary.xls");

	echo "<html>";
	echo "<html xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns=\"http://www.w3.org/TR/REC-html40\">";
	
	echo "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=".$cCharset."\">";
	echo "<body>";
	echo "<table border=1>";

	WriteTableData();

	echo "</table>";
	echo "</body>";
	echo "</html>";
}

function ExportToWord()
{
	global $cCharset;
	header("Content-Type: application/vnd.ms-word");
	header("Content-Disposition: attachment;Filename=Tax_Summary.doc");

	echo "<html>";
	echo "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=".$cCharset."\">";
	echo "<body>";
	echo "<table border=1>";

	WriteTableData();

	echo "</table>";
	echo "</body>";
	echo "</html>";
}

function ExportToXML()
{
	global $nPageSize,$rs,$strTableName,$conn;
	header("Content-Type: text/xml");
	header("Content-Disposition: attachment;Filename=Tax_Summary.xml");
	if(!($row=db_fetch_array($rs)))
		return;
	global $cCharset;
	echo "<?xml version=\"1.0\" encoding=\"".$cCharset."\" standalone=\"yes\"?>\r\n";
	echo "<table>\r\n";
	$i=0;
	while((!$nPageSize || $i<$nPageSize) && $row)
	{
		echo "<row>\r\n";
		$field=htmlspecialchars(XMLNameEncode("Entries"));
		echo "<".$field.">";
		echo htmlspecialchars(GetData($row,"Entries",""));
		echo "</".$field.">\r\n";
		$field=htmlspecialchars(XMLNameEncode("Amount"));
		echo "<".$field.">";
		echo htmlspecialchars(GetData($row,"Amount",""));
		echo "</".$field.">\r\n";
		$field=htmlspecialchars(XMLNameEncode("monthname"));
		echo "<".$field.">";
		echo htmlspecialchars(GetData($row,"monthname",""));
		echo "</".$field.">\r\n";
		$field=htmlspecialchars(XMLNameEncode("year"));
		echo "<".$field.">";
		echo htmlspecialchars(GetData($row,"year",""));
		echo "</".$field.">\r\n";
		echo "</row>\r\n";
		$i++;
		$row=db_fetch_array($rs);
	}
	echo "</table>\r\n";
}

function ExportToCSV()
{
	global $rs,$nPageSize,$strTableName,$conn;
	header("Content-Type: application/csv");
	header("Content-Disposition: attachment;Filename=Tax_Summary.csv");

	if(!($row=db_fetch_array($rs)))
		return;

	$totals=array();
	$totals["Entries"]=0;
	$totals["Amount"]=0;

	
// write header
	$outstr="";
	if($outstr!="")
		$outstr.=",";
	$outstr.= "\"Entries\"";
	if($outstr!="")
		$outstr.=",";
	$outstr.= "\"Amount\"";
	if($outstr!="")
		$outstr.=",";
	$outstr.= "\"monthname\"";
	if($outstr!="")
		$outstr.=",";
	$outstr.= "\"year\"";
	echo $outstr;
	echo "\r\n";

// write data rows
	$iNumberOfRows = 0;
	while((!$nPageSize || $iNumberOfRows<$nPageSize) && $row)
	{
							$totals["Entries"]+=($row["Entries"]+0);
							$totals["Amount"]+=($row["Amount"]+0);
		$outstr="";
		if($outstr!="")
			$outstr.=",";
			$format="";
		$outstr.='"'.htmlspecialchars(GetData($row,"Entries",$format)).'"';
		if($outstr!="")
			$outstr.=",";
			$format="Number";
		$outstr.='"'.htmlspecialchars(GetData($row,"Amount",$format)).'"';
		if($outstr!="")
			$outstr.=",";
			$format="";
		$outstr.='"'.htmlspecialchars(GetData($row,"monthname",$format)).'"';
		if($outstr!="")
			$outstr.=",";
			$format="";
		$outstr.='"'.htmlspecialchars(GetData($row,"year",$format)).'"';
		echo $outstr;
		echo "\r\n";
		$iNumberOfRows++;
		$row=db_fetch_array($rs);
	}

//	display totals
	$first=true;
	if(!$first)
		echo ",";
	else
		$first=false;
	echo "\"";
						echo "Total".": ";
	echo htmlspecialchars(GetTotals("Entries",$totals["Entries"],"TOTAL",$iNumberOfRows,""));
	echo "\"";
	if(!$first)
		echo ",";
	else
		$first=false;
	echo "\"";
						echo "Total".": ";
	echo htmlspecialchars(GetTotals("Amount",$totals["Amount"],"TOTAL",$iNumberOfRows,"Number"));
	echo "\"";
	if(!$first)
		echo ",";
	else
		$first=false;
	echo "\"";
		echo "\"";
	if(!$first)
		echo ",";
	else
		$first=false;
	echo "\"";
		echo "\"";
	echo "\r\n";

}


function WriteTableData()
{
	global $rs,$nPageSize,$strTableName,$conn;
	if(!($row=db_fetch_array($rs)))
		return;
// write header
	echo "<tr>";
	if($_REQUEST["type"]=="excel")
	{
	echo '<td style="width: 100" x:str>'.PrepareForExcel("Entry Count").'</td>';	
	echo '<td style="width: 100" x:str>'.PrepareForExcel("Amount").'</td>';	
	echo '<td style="width: 100" x:str>'.PrepareForExcel("Month").'</td>';	
	echo '<td style="width: 100" x:str>'.PrepareForExcel("Year").'</td>';	
	}
	else
	{
		echo "<td>"."Entry Count"."</td>";
		echo "<td>"."Amount"."</td>";
		echo "<td>"."Month"."</td>";
		echo "<td>"."Year"."</td>";
	}
	echo "</tr>";

	$totals=array();
	$totals["Entries"]=0;
	$totals["Amount"]=0;
// write data rows
	$iNumberOfRows = 0;
	while((!$nPageSize || $iNumberOfRows<$nPageSize) && $row)
	{
							$totals["Entries"]+=($row["Entries"]+0);
							$totals["Amount"]+=($row["Amount"]+0);
		echo "<tr>";
	echo '<td>';

		$format="";
			echo htmlspecialchars(GetData($row,"Entries",$format));
	echo '</td>';
	echo '<td>';

		$format="Number";
			echo htmlspecialchars(GetData($row,"Amount",$format));
	echo '</td>';
	if($_REQUEST["type"]=="excel")
		echo '<td x:str>';
	else
		echo '<td>';

		$format="";
			if($_REQUEST["type"]=="excel")
			echo PrepareForExcel(GetData($row,"monthname",$format));
		else
			echo htmlspecialchars(GetData($row,"monthname",$format));
	echo '</td>';
	echo '<td>';

		$format="";
			echo htmlspecialchars(GetData($row,"year",$format));
	echo '</td>';
		echo "</tr>";
		$iNumberOfRows++;
		$row=db_fetch_array($rs);
	}
	echo "<tr>";
	echo "<td>";
						echo "Total".": ";
	echo htmlspecialchars(GetTotals("Entries",$totals["Entries"],"TOTAL",$iNumberOfRows,""));
	echo "</td>";
	echo "<td>";
						echo "Total".": ";
	echo htmlspecialchars(GetTotals("Amount",$totals["Amount"],"TOTAL",$iNumberOfRows,"Number"));
	echo "</td>";
	echo "<td>";
		echo "</td>";
	echo "<td>";
		echo "</td>";
	echo "</tr>";

}

function XMLNameEncode($strValue)
{	
	$search=array(" ","#","'","/","\\","(",")",",","[");
	$ret=str_replace($search,"",$strValue);
	$search=array("]","+","\"","-","_","|","}","{","=");
	$ret=str_replace($search,"",$ret);
	return $ret;
}

function PrepareForExcel($str)
{
	$ret = htmlspecialchars($str);
	if (substr($ret,0,1)== "=") 
		$ret = "&#61;".substr($ret,1);
	return $ret;

}





?>