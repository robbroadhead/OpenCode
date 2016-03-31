<?php
ini_set("display_errors","1");
ini_set("display_startup_errors","1");
ini_set('magic_quotes_runtime', 0);
include("include/dbcommon.php");
header("Expires: Thu, 01 Jan 1970 00:00:01 GMT");
header("Pragma: no-cache");
header("Cache-Control: no-cache");


include("include/taxitem_variables.php");

if(!@$_SESSION["UserID"])
{ 
	$_SESSION["MyURL"]=$_SERVER["SCRIPT_NAME"]."?".$_SERVER["QUERY_STRING"];
	header("Location: login.php?message=expired"); 
	return;
}
if(!CheckSecurity(@$_SESSION["_".$strTableName."_OwnerID"],"Search") && !CheckSecurity(@$_SESSION["_".$strTableName."_OwnerID"],"Add"))
{
	$adminuser=false;
	if($adminuser)
		echo "<p>"."You don't have permissions to access this table"."<br><a href=\"admin_rights_list.php\">"."Proceed to Admin Area"."</a> "."to set up user permissions"."</p>";
	else
		echo "<p>"."You don't have permissions to access this table"." <a href=\"login.php\">"."Back to login page"."</a></p>";
	exit();
}

$pageName = "list.php";

include('include/xtempl.php');
$xt = new Xtempl();

// for different modes.
$sessionPrefix=$strTableName;

//	process reqest data, fill session variables

$mode=LIST_SIMPLE;
if(postvalue("mode")=="lookup")
	$mode=LIST_LOOKUP;
$id=postvalue("id");
$xt->assign("id",$id);

if($mode==LIST_LOOKUP)
{	
	$lookupwhere="";
	$categoryfield="";
	$linkfield="";
	$lookupfield=postvalue("field");
	$lookupcontrol=postvalue("control");
	$lookupcategory=postvalue("category");
	$lookuptable=postvalue("table");
	$lookupparams="mode=lookup&id=".$id."&field=".rawurlencode($lookupfield)
		."&control=".rawurlencode($lookupcontrol)."&category=".rawurlencode($lookupcategory)
		."&table=".rawurlencode($lookuptable);

	//for mode lookup
	$sessionPrefix=$strTableName."_lookup_".$lookuptable.'_'.$lookupfield;
//	determine which field should be used to select values
	$lookupSelectField="";
	$lookupSelectField="year";
	if(AppearOnListPage($dispfield))
		$lookupSelectField=$dispfield;

	if($categoryfield)
	{
		if(!strlen(GetFullFieldName($categoryfield)))
			$categoryfield="";
	}
	if(!$categoryfield)
		$lookupcategory="";
	
}

$firsttime=postvalue("firsttime");

// clear lookup session data, while loading at first time
if ($mode==LIST_LOOKUP && $firsttime)
{
	$sessLookUpUnset = array();
	foreach($_SESSION as $key=>$value)
		if(strpos($key, "_lookup_") !== false)
			$sessLookUpUnset[] = $key;
			
	foreach($sessLookUpUnset as $key)
		unset($_SESSION[$key]);			
}


if(!count($_POST) && (!count($_GET) || count($_GET)==1 && isset($_GET["menuItemId"])))
{
	$sess_unset = array();
	foreach($_SESSION as $key=>$value)
		if(substr($key,0,strlen($strTableName)+1)==$strTableName."_" &&
			strpos(substr($key,strlen($strTableName)+1),"_")===false)
			$sess_unset[] = $key;
		
	foreach($sess_unset as $key)
		unset($_SESSION[$key]);
}

//	Before Process event
if(function_exists("BeforeProcessList"))
	BeforeProcessList($conn);

if(@$_REQUEST["a"]=="showall")
	$_SESSION[$sessionPrefix."_search"]=0;
else if(@$_REQUEST["a"]=="search")
{
	$basicSearchFieldsArr = array();
	$basicSearchFieldsArr[]="Storename";
	$basicSearchFieldsArr[]="Created";
	$basicSearchFieldsArr[]="year";

	if (postvalue("SearchField")=="" || in_array(postvalue("SearchField"), $basicSearchFieldsArr) === true)
	{
		$_SESSION[$sessionPrefix."_searchfield"]=postvalue("SearchField");
		$_SESSION[$sessionPrefix."_searchoption"]=postvalue("SearchOption");
		$_SESSION[$sessionPrefix."_searchfor"]=postvalue("SearchFor");
		if(postvalue("SearchFor")!="" || postvalue("SearchOption")=='Empty')
			$_SESSION[$sessionPrefix."_search"]=1;
		else
			$_SESSION[$sessionPrefix."_search"]=0;
		$_SESSION[$sessionPrefix."_pagenumber"]=1;
	}
	else 
	{
		$_SESSION[$sessionPrefix."_search"]=0;
	}
	
	
}
else if(@$_REQUEST["a"]=="advsearch")
{
	$_SESSION[$sessionPrefix."_asearchnot"]=array();
	$_SESSION[$sessionPrefix."_asearchopt"]=array();
	$_SESSION[$sessionPrefix."_asearchfor"]=array();
	$_SESSION[$sessionPrefix."_asearchfor2"]=array();
	$tosearch=0;
	$asearchfield = postvalue("asearchfield");
	$_SESSION[$sessionPrefix."_asearchtype"] = postvalue("type");
	if(!$_SESSION[$sessionPrefix."_asearchtype"])
		$_SESSION[$sessionPrefix."_asearchtype"]="and";
	foreach($asearchfield as $field)
	{
		$gfield=GoodFieldName($field);
		$asopt=postvalue("asearchopt_".$gfield);
		$value1=postvalue("value_".$gfield);
		$type=postvalue("type_".$gfield);
		$value2=postvalue("value1_".$gfield);
		$not=postvalue("not_".$gfield);
		if($value1!=='' || $asopt=='Empty')
		{
			$tosearch=1;
			$_SESSION[$sessionPrefix."_asearchopt"][$field]=$asopt;
			if(!is_array($value1))
				$_SESSION[$sessionPrefix."_asearchfor"][$field]=$value1;
			else
				$_SESSION[$sessionPrefix."_asearchfor"][$field]=combinevalues($value1);
			$_SESSION[$sessionPrefix."_asearchfortype"][$field]=$type;
			if($value2!=='')
				$_SESSION[$sessionPrefix."_asearchfor2"][$field]=$value2;
			$_SESSION[$sessionPrefix."_asearchnot"][$field]=($not=="on");
		}
	}
	if($tosearch)
		$_SESSION[$sessionPrefix."_search"]=2;
	else
		$_SESSION[$sessionPrefix."_search"]=0;
	$_SESSION[$sessionPrefix."_pagenumber"]=1;
}

//	process masterkey value
$mastertable=postvalue("mastertable");
if($mastertable!="")
{
	$_SESSION[$sessionPrefix."_mastertable"]=$mastertable;
//	copy keys to session
	$i=1;
	while(isset($_REQUEST["masterkey".$i]))
	{
		$_SESSION[$sessionPrefix."_masterkey".$i]=$_REQUEST["masterkey".$i];
		$i++;
	}
	if(isset($_SESSION[$sessionPrefix."_masterkey".$i]))
		unset($_SESSION[$sessionPrefix."_masterkey".$i]);
//	reset search and page number
	$_SESSION[$sessionPrefix."_search"]=0;
	$_SESSION[$sessionPrefix."_pagenumber"]=1;
}
else
	$mastertable=$_SESSION[$sessionPrefix."_mastertable"];


if(@$_REQUEST["orderby"])
	$_SESSION[$sessionPrefix."_orderby"]=@$_REQUEST["orderby"];

if(@$_REQUEST["pagesize"])
{
	$_SESSION[$sessionPrefix."_pagesize"]=@$_REQUEST["pagesize"];
	$_SESSION[$sessionPrefix."_pagenumber"]=1;
}

if(@$_REQUEST["goto"])
	$_SESSION[$sessionPrefix."_pagenumber"]=@$_REQUEST["goto"];


//	process reqest data - end

$jscode="";
$jscode_end="";
$html_begin="";
$html_end="";

	
AddJSFile("validate");

if(@$_REQUEST["language"])
	$language = $_REQUEST["language"];
// may be elseif ?
if(@$_SESSION["language"])
	$language = $_SESSION["language"];
else
	$language = 'English';

$jscode.="window.current_language='".jsreplace($language)."';\r\n";


AddJSFile("inlineedit");
if($mode==LIST_LOOKUP)
{
//	this code must be executed after the inlineedit.js is loaded
	$afteredited_handler="";
	if($lookupSelectField)
	{
		$select_onclick='$("#display_'.$lookupcontrol.'").val($("#edit"+id+"_'.GoodFieldName($dispfield).'").attr("val")); $("#'.$lookupcontrol.'").val($("#edit"+id+"_'.GoodFieldName($linkfield).'").attr("val")); if($("#'.$lookupcontrol.'")[0].onchange) $("#'.$lookupcontrol.'")[0].onchange();RemoveFlyDiv('.$id.');';
		$afteredited_handler = 'window.inlineEditing'.$id.'.afterRecordEdited = function(id) {
			var span=$("#edit"+id+"_'.GoodFieldName($lookupSelectField).'");
			
			if(!span.length)
				return;
			$(span).html("<a href=#>"+$(span).html()+"</a>"); 
			$("a:first",span).click(function() {'.$select_onclick.'});
		};';
	}

	$jscode_end.='
			window.inlineEditing'.$id.' = new InlineEditing(\'taxitem\',\'php\','.$id.');
			'.$afteredited_handler;
	if(strlen($lookupcategory))
	{
		$jscode_end.='window.inlineEditing'.$id.'.lookupfield = \''.jsreplace($lookupfield).'\';';
		$jscode_end.='window.inlineEditing'.$id.'.lookuptable = \''.jsreplace($lookuptable).'\';';
		$jscode_end.='window.inlineEditing'.$id.'.categoryvalue = \''.jsreplace($lookupcategory).'\';';
	}
}
if($mode!=LIST_LOOKUP)
{
	$jscode_end .= 'window.inlineEditing'.$id.' = new InlineEditing(\'taxitem\',\'php\');';
}
AddJSFile("ajaxsuggest");

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
				
	//	include datepicker files
	
	$jscode.="window.TEXT_MONTH_JAN='".jsreplace("January")."';\r\n";
	$jscode.="window.TEXT_MONTH_FEB='".jsreplace("February")."';\r\n";
	$jscode.="window.TEXT_MONTH_MAR='".jsreplace("March")."';\r\n";
	$jscode.="window.TEXT_MONTH_APR='".jsreplace("April")."';\r\n";
	$jscode.="window.TEXT_MONTH_MAY='".jsreplace("May")."';\r\n";
	$jscode.="window.TEXT_MONTH_JUN='".jsreplace("June")."';\r\n";
	$jscode.="window.TEXT_MONTH_JUL='".jsreplace("July")."';\r\n";
	$jscode.="window.TEXT_MONTH_AUG='".jsreplace("August")."';\r\n";
	$jscode.="window.TEXT_MONTH_SEP='".jsreplace("September")."';\r\n";
	$jscode.="window.TEXT_MONTH_OCT='".jsreplace("October")."';\r\n";
	$jscode.="window.TEXT_MONTH_NOV='".jsreplace("November")."';\r\n";
	$jscode.="window.TEXT_MONTH_DEC='".jsreplace("December")."';\r\n";
	
	$jscode.="window.TEXT_DAY_SU='".jsreplace("Su")."';\r\n";
	$jscode.="window.TEXT_DAY_MO='".jsreplace("Mo")."';\r\n";
	$jscode.="window.TEXT_DAY_TU='".jsreplace("Tu")."';\r\n";
	$jscode.="window.TEXT_DAY_WE='".jsreplace("We")."';\r\n";
	$jscode.="window.TEXT_DAY_TH='".jsreplace("Th")."';\r\n";
	$jscode.="window.TEXT_DAY_FR='".jsreplace("Fr")."';\r\n";
	$jscode.="window.TEXT_DAY_SA='".jsreplace("Sa")."';\r\n";
	
	$jscode.="window.TEXT_TODAY='".jsreplace("today")."';\r\n";
	
	AddJSFile("calendar");

if($mode==LIST_SIMPLE)
	$jscode.="\nbSelected=false;";
$jscode.="\nwindow.TEXT_FIRST = \""."First"."\";".
"\nwindow.TEXT_PREVIOUS = \""."Previous"."\";".
"\nwindow.TEXT_NEXT = \""."Next"."\";".
"\nwindow.TEXT_LAST = \""."Last"."\";".
"\nwindow.TEXT_CTRL_CLICK = \""."CTRL + click for multiple sorting"."\";".
"\nwindow.TEXT_PLEASE_SELECT='".jsreplace("Please select")."';".
"\nwindow.TEXT_SAVE='".jsreplace("Save")."';".
"\nwindow.TEXT_CANCEL='".jsreplace("Cancel")."';".
"\nwindow.TEXT_INLINE_ERROR='".jsreplace("Error occurred")."';".
"\nwindow.TEXT_PREVIEW='".jsreplace("preview")."';".
"\nwindow.TEXT_HIDE='".jsreplace("hide")."';".
"\nwindow.TEXT_LOADING='".jsreplace("loading")."';".
"\nlocale_dateformat = ".$locale_info["LOCALE_IDATE"].";".
"\nocale_datedelimiter = \"".$locale_info["LOCALE_SDATE"]."\";".
"\nbLoading=false;\r\n";
	$jscode.="SUGGEST_TABLE='taxitem_searchsuggest.php';\r\n";
	$jscode.="MASTER_PREVIEW_TABLE='taxitem_masterpreview.php';\r\n";
$html_begin.="<div id=\"search_suggest".$id."\"></div>";
$html_begin.="<div id=\"master_details".$id."\" onmouseover=\"RollDetailsLink.showPopup();\" onmouseout=\"RollDetailsLink.hidePopup();\"> </div>";
if($mode==LIST_SIMPLE)
	$html_begin.="<div id=\"inline_error".$id."\"></div>";

$body = array();
if($mode==LIST_SIMPLE)
	$html_begin.="<form name=\"frmSearch\" method=\"GET\" action=\"taxitem_list.php\">";
else
{
	$html_begin.="<form name=\"frmSearch".$id."\" target=\"flyframe".$id."\" method=\"GET\" action=\"taxitem_list.php\">";
	$html_begin.="<input type=\"Hidden\" name=\"mode\" value=\"lookup\">";
	$html_begin.="<input type=\"Hidden\" name=\"id\" value=\"".$id."\">";
	$html_begin.="<input type=\"Hidden\" name=\"field\" value=\"".htmlspecialchars($lookupfield)."\">";
	$html_begin.="<input type=\"Hidden\" name=\"control\" value=\"".htmlspecialchars($lookupcontrol)."\">";
	$html_begin.="<input type=\"Hidden\" name=\"category\" value=\"".htmlspecialchars($lookupcategory)."\">";
	$html_begin.="<input type=\"Hidden\" name=\"table\" value=\"".htmlspecialchars($lookuptable)."\">";
}
$html_begin.='<input type="Hidden" name="a" value="search">
<input type="Hidden" name="value" value="1">
<input type="Hidden" name="SearchFor" value="">
<input type="Hidden" name="SearchOption" value="">
<input type="Hidden" name="SearchField" value="">
</form>';
$includes_vars="true";
if($mode==LIST_SIMPLE)
{
	$body["begin"]="";
	$body["begin"].="<script type=\"text/javascript\" src=\"include/jquery.js\"></script>";	
	$body["begin"].="<script language=\"JavaScript\" src=\"include/jsfunctions.js\"></script>\r\n";
	//$jscode .='correctListHeight(""); ';
		$body["begin"].="<script language=\"JavaScript\" src=\"include/customlabels.js\"></script>\r\n";
	$body["begin"].=$html_begin;
}
elseif($mode==LIST_LOOKUP)
{
	$body["begin"].=$html_begin;
	$xt->assign("header","");
}
//	process session variables
//	order by
$strOrderBy="";
$order_ind=-1;


$recno=1;
$recid=$recno+$id;
$numrows=0;
$maxpages=1;
$rowid=0;
//orderlinkattrs for Storename
$href="taxitem_list.php?orderby=aStorename";
$orderlinkattrs="";
if($mode==LIST_LOOKUP)
{
	$href.="&".$lookupparams;
	$orderlinkattrs="onclick=\"window.frames['flyframe".$id."'].location='".$href."';return false;\" href=\"".$href."\"";
}
else
	$orderlinkattrs=" href=\"".$href."\" OnMouseDown=\"sort(event,this.href);\" OnMouseOver=\"addspan(event);\" OnMouseMove=\"movespan(event);\" OnMouseOut=\"delspan();\"";
$xt->assign("Storename_orderlinkattrs",$orderlinkattrs);
$xt->assign("Storename_fieldheader",true);
//orderlinkattrs for Amount
$href="taxitem_list.php?orderby=aAmount";
$orderlinkattrs="";
if($mode==LIST_LOOKUP)
{
	$href.="&".$lookupparams;
	$orderlinkattrs="onclick=\"window.frames['flyframe".$id."'].location='".$href."';return false;\" href=\"".$href."\"";
}
else
	$orderlinkattrs=" href=\"".$href."\" OnMouseDown=\"sort(event,this.href);\" OnMouseOver=\"addspan(event);\" OnMouseMove=\"movespan(event);\" OnMouseOut=\"delspan();\"";
$xt->assign("Amount_orderlinkattrs",$orderlinkattrs);
$xt->assign("Amount_fieldheader",true);
//orderlinkattrs for Created
$href="taxitem_list.php?orderby=aCreated";
$orderlinkattrs="";
if($mode==LIST_LOOKUP)
{
	$href.="&".$lookupparams;
	$orderlinkattrs="onclick=\"window.frames['flyframe".$id."'].location='".$href."';return false;\" href=\"".$href."\"";
}
else
	$orderlinkattrs=" href=\"".$href."\" OnMouseDown=\"sort(event,this.href);\" OnMouseOver=\"addspan(event);\" OnMouseMove=\"movespan(event);\" OnMouseOut=\"delspan();\"";
$xt->assign("Created_orderlinkattrs",$orderlinkattrs);
$xt->assign("Created_fieldheader",true);
//orderlinkattrs for year
$href="taxitem_list.php?orderby=ayear";
$orderlinkattrs="";
if($mode==LIST_LOOKUP)
{
	$href.="&".$lookupparams;
	$orderlinkattrs="onclick=\"window.frames['flyframe".$id."'].location='".$href."';return false;\" href=\"".$href."\"";
}
else
	$orderlinkattrs=" href=\"".$href."\" OnMouseDown=\"sort(event,this.href);\" OnMouseOver=\"addspan(event);\" OnMouseMove=\"movespan(event);\" OnMouseOut=\"delspan();\"";
$xt->assign("year_orderlinkattrs",$orderlinkattrs);
$xt->assign("year_fieldheader",true);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Sorting fields
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Array fields for sort	
	$arrFieldForSort=array();
//Array how fields sort	
	$arrHowFieldSort=array();
	$arrImplicitSortFields=array();
	$key=array();
//Session field for sort		
	if(@$_SESSION[$sessionPrefix."_arrFieldForSort"]) 
		$arrFieldForSort=$_SESSION[$sessionPrefix."_arrFieldForSort"];
//Session how field sort
	if(@$_SESSION[$sessionPrefix."_arrHowFieldSort"]) 
		$arrHowFieldSort=$_SESSION[$sessionPrefix."_arrHowFieldSort"]; 
//Session key fields for sort		
	if(@$_SESSION[$sessionPrefix."_key"])
		$key=$_SESSION[$sessionPrefix."_key"];
	else{	
			if(GetFieldIndex("itemID"))
				$key[]=GetFieldIndex("itemID");
			$_SESSION[$sessionPrefix."_key"]=$key; 
		}
	$lenkey=count($key);
	if(!isset($_SESSION[$sessionPrefix."_order"]))
	{
		$arrFieldForSort=array();
		$arrHowFieldSort=array();
		if(count($g_orderindexes))
		{
			for($i=0;$i<count($g_orderindexes);$i++)
			{
				$arrFieldForSort[]=$g_orderindexes[$i][0];
				$arrHowFieldSort[]=$g_orderindexes[$i][1];
			}
		}
		elseif($gstrOrderBy!='')
			$_SESSION[$sessionPrefix."_noNextPrev"] = 1; 
	//add sorting on key fields
		if(count($key))
		{
			for($i=0;$i<$lenkey;$i++)
			{
				$idsearch=array_search($key[$i],$arrFieldForSort);
				if($idsearch===false)
				{
					$arrFieldForSort[]=$key[$i];
					$arrHowFieldSort[]="ASC";
					$arrImplicitSortFields[]=$key[$i];
				}
			}
		}
	}
	$lenArr=count($arrFieldForSort);
	//Sorting defined on the sheet
	if(@$_SESSION[$sessionPrefix."_orderby"])
	{
		$order_field=GetFieldByGoodFieldName(substr($_SESSION[$sessionPrefix."_orderby"],1));
		$order_dir=substr($_SESSION[$sessionPrefix."_orderby"],0,1);
		$order_ind=GetFieldIndex($order_field);
		if($order_ind)
		{
			if(!@$_REQUEST["a"] and !@$_REQUEST["goto"] and !@$_REQUEST["pagesize"])
			{
				if(@$_REQUEST["ctrl"])
				{
					$idsearch=array_search($order_ind,$arrFieldForSort);
					if($idsearch===false)
					{
						if($order_dir=="a")
						{
							$arrFieldForSort[]=$order_ind;
							$arrHowFieldSort[]="ASC";
						}
						else{
								$arrFieldForSort[]=$order_ind;
								$arrHowFieldSort[]="DESC";
							}
					}
					else
						$arrHowFieldSort[$idsearch]=($order_dir=="a" ? "ASC" : "DESC");
				}
				else{
						$arrFieldForSort=array();
						$arrHowFieldSort=array();
						if(!empty($_SESSION[$sessionPrefix."_orderNo"]))
							unset($_SESSION[$sessionPrefix."_orderNo"]);
						$_SESSION[$sessionPrefix."_noNextPrev"] = 0;
						if($order_dir=="a")
						{
							$arrFieldForSort[]=$order_ind;
							$arrHowFieldSort[]="ASC";
						}
						else{
								$arrFieldForSort[]=$order_ind;
								$arrHowFieldSort[]="DESC";
							}
					}
			}
		}
	}
	$lenArr=count($arrFieldForSort);
	//Draw pictures of fields for sorting
	$condition=true;
	if(!count($arrFieldForSort))
		$condition=false;
	elseif(!$arrFieldForSort[0])
		$condition=false;
	if($condition)
	{
		for($i=0;$i<$lenArr;$i++)
		{
			$order_field=GetFieldByIndex($arrFieldForSort[$i]);
			$order_dir=$arrHowFieldSort[$i]=="ASC" ? "a" : "d";
			$idsearch=array_search($arrFieldForSort[$i],$arrImplicitSortFields);
			if($idsearch===false)
				$xt->assign_section(GoodFieldName($order_field)."_fieldheader","","<img src=\"images/".($order_dir=="a" ? "up" : "down").".gif\" border=0>");

			// default ASC for key fields	
			if ($idsearch!==false && in_array(GetFieldIndex($order_field), $arrImplicitSortFields))
			{
				$href="taxitem_list.php?orderby=a".GoodFieldName($order_field);
			}
			else
			{
				$href="taxitem_list.php?orderby=".($order_dir=="a" ? "d" : "a").GoodFieldName($order_field);	
			}
			$orderlinkattrs="";
			if($mode==LIST_LOOKUP)
			{
				$href.="&".$lookupparams;
				$orderlinkattrs="onclick=\"window.frames['flyframe".$id."'].location='".$href."';return false;\"  href=\"".$href."\"";
			}
			else
				$orderlinkattrs.=" href=\"".$href."\" OnMouseDown=\"sort(event,this.href);\" OnMouseOver=\"addspan(event);\" OnMouseMove=\"movespan(event);\" OnMouseOut=\"delspan();\"";
			$xt->assign(GoodFieldName($order_field)."_orderlinkattrs",$orderlinkattrs);
		}
	}
	//Shape sorting line for a request
	if($lenArr > 0)
	{
		for($i=0;$i<$lenArr;$i++)
			$strOrderBy .=(GetFieldByIndex($arrFieldForSort[$i]) ? ($strOrderBy!="" ? ", " : " ORDER BY ").$arrFieldForSort[$i]." ".$arrHowFieldSort[$i] : "");
	}
	if($_SESSION[$sessionPrefix."_noNextPrev"]==1)
	{
		$strOrderBy = $gstrOrderBy;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//	page number
$mypage=(integer)$_SESSION[$sessionPrefix."_pagenumber"];
if(!$mypage)
	$mypage=1;

//	page size
$PageSize=(integer)$_SESSION[$sessionPrefix."_pagesize"];
if(!$PageSize)
	$PageSize=$gPageSize;
if($mode==LIST_LOOKUP)
	$PageSize=20;

$xt->assign("rpp".$PageSize."_selected","selected");

// delete record
$selected_recs=array();
if (@$_REQUEST["mdelete"])
{
	foreach(@$_REQUEST["mdelete"] as $ind)
	{
		$keys=array();
		$keys["itemID"]=refine($_REQUEST["mdelete1"][mdeleteIndex($ind)]);
		$selected_recs[]=$keys;
	}
}
elseif(@$_REQUEST["selection"])
{
	foreach(@$_REQUEST["selection"] as $keyblock)
	{
		$arr=explode("&",refine($keyblock));
		if(count($arr)<1)
			continue;
		$keys=array();
		$keys["itemID"]=urldecode(@$arr[0]);
		$selected_recs[]=$keys;
	}
}

$records_deleted=0;
foreach($selected_recs as $keys)
{
	$where = KeyWhere($keys);
//	delete only owned records
	$where = whereAdd($where,SecuritySQL("Delete"));

	$strSQL="delete from ".AddTableWrappers($strOriginalTableName)." where ".$where;
	$retval=true;
	if(function_exists("AfterDelete") || function_exists("BeforeDelete"))
	{
		$deletedrs = db_query(gSQLWhere($where),$conn);
		$deleted_values = db_fetch_array($deletedrs);
	}
	if(function_exists("BeforeDelete"))
		$retval = BeforeDelete($where,$deleted_values);
	if($retval && @$_REQUEST["a"]=="delete")
	{
		$records_deleted++;
				LogInfo($strSQL);
		db_exec($strSQL,$conn);
		if(function_exists("AfterDelete"))
			AfterDelete($where,$deleted_values);
	}
}

if(count($selected_recs))
{
	if(function_exists("AfterMassDelete"))
		AfterMassDelete($records_deleted);
}


// PRG rule, to avoid POSTDATA resend
if(no_output_done() && count($selected_recs) && $mode==LIST_SIMPLE){	
	// redirect, add a=return param for saving SESSION
	header("Location: taxitem_".$pageName."?a=return");
	// turned on output buffering, so we need to stop script
	exit();
}



//deal with permissions
//	table selector
$allow_member=true;
$allow_taxitem=true;
$allow_Tax_Summary=true;

$createmenu=false;

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
	$xt->assign("member_optionattrs","value=\"member_".$page.".php\"");
}
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
	$xt->assign("taxitem_optionattrs","value=\"taxitem_".$page.".php\"");
}
if($allow_Tax_Summary)
{
	$createmenu=true;
	$xt->assign("Tax_Summary_tablelink",true);
	$page="";
		$page="list";
		$xt->assign("Tax_Summary_tablelink_attrs","href=\"Tax_Summary_".$page.".php\"");
	$xt->assign("Tax_Summary_optionattrs","value=\"Tax_Summary_".$page.".php\"");
}

if($createmenu && $mode==LIST_SIMPLE)
	$xt->assign("menu_block",true);


// for mode simple
if($mode==LIST_SIMPLE)
{
	$xt->assign("shiftstyle_block",true);
	$xt->assign("security_block",true);
	$xt->assign("left_block",true);
}

$strPerm = GetUserPermissions();
$allow_add=(strpos($strPerm,"A")!==false);
$allow_delete=(strpos($strPerm,"D")!==false);
$allow_edit=(strpos($strPerm,"E")!==false);
$allow_search=(strpos($strPerm,"S")!==false);
$allow_export=(strpos($strPerm,"P")!==false);
$allow_import=(strpos($strPerm,"I")!==false);



//	make sql "select" string

$strWhereClause="";

//	add search params

if(@$_SESSION[$sessionPrefix."_search"]==1)
//	 regular search
{  
	$strSearchFor=trim($_SESSION[$sessionPrefix."_searchfor"]);
	$strSearchOption=trim($_SESSION[$sessionPrefix."_searchoption"]);
	if(@$_SESSION[$sessionPrefix."_searchfield"])
	{
		$strSearchField = $_SESSION[$sessionPrefix."_searchfield"];
		if($where = StrWhereExpression($strSearchField, $strSearchFor, $strSearchOption, ""))
			$strWhereClause = whereAdd($strWhereClause,$where);
		else
			$strWhereClause = whereAdd($strWhereClause,"1=0");
	}
	else
	{
		$strWhere = "1=0";
		if($where=StrWhereExpression("Storename", $strSearchFor, $strSearchOption, ""))
			$strWhere .= " or ".$where;
		if($where=StrWhereExpression("Created", $strSearchFor, $strSearchOption, ""))
			$strWhere .= " or ".$where;
		if($where=StrWhereExpression("year", $strSearchFor, $strSearchOption, ""))
			$strWhere .= " or ".$where;
		$strWhereClause = whereAdd($strWhereClause,$strWhere);
	}
}
else if(@$_SESSION[$sessionPrefix."_search"]==2)
//	 advanced search
{
	$sWhere="";
	foreach(@$_SESSION[$sessionPrefix."_asearchfor"] as $f => $sfor)
		{
			$strSearchFor=trim($sfor);
			$strSearchFor2="";
			$type=@$_SESSION[$sessionPrefix."_asearchfortype"][$f];
			if(array_key_exists($f,@$_SESSION[$sessionPrefix."_asearchfor2"]))
				$strSearchFor2=trim(@$_SESSION[$sessionPrefix."_asearchfor2"][$f]);
			if($strSearchFor!="" || true)
			{
				if (!$sWhere) 
				{
					if($_SESSION[$sessionPrefix."_asearchtype"]=="and")
						$sWhere="1=1";
					else
						$sWhere="1=0";
				}
				$strSearchOption=trim($_SESSION[$sessionPrefix."_asearchopt"][$f]);
				if($where=StrWhereAdv($f, $strSearchFor, $strSearchOption, $strSearchFor2,$type))
				{
					if($_SESSION[$sessionPrefix."_asearchnot"][$f])
						$where="not (".$where.")";
					if($_SESSION[$sessionPrefix."_asearchtype"]=="and")
	   					$sWhere .= " and ".$where;
					else
	   					$sWhere .= " or ".$where;
				}
			}
		}
		$strWhereClause = whereAdd($strWhereClause,$sWhere);
	}



if(SecuritySQL("Search"))
	$strWhereClause = whereAdd($strWhereClause,SecuritySQL("Search"));

if($mode==LIST_LOOKUP)
{
	if(strlen($lookupcategory))
		$strWhereClause = whereAdd($strWhereClause,GetFullFieldName($categoryfield)."=".make_db_value($categoryfield,$lookupcategory));
	if(strlen($lookupwhere))
		$strWhereClause = whereAdd($strWhereClause,$lookupwhere);
}

if($mastertable=="Tax Summary")
{
	$where ="";
		$where.= GetFullFieldName("userID")."=".make_db_value("userID",$_SESSION[$sessionPrefix."_masterkey1"]);
		$where.=" and ";
	$where.= GetFullFieldName("monthname")."=".make_db_value("monthname",$_SESSION[$sessionPrefix."_masterkey2"]);
		$where.=" and ";
	$where.= GetFullFieldName("year")."=".make_db_value("year",$_SESSION[$sessionPrefix."_masterkey3"]);
	$strWhereClause = whereAdd($strWhereClause,$where);
}

$strSQL = gSQLWhere($strWhereClause);

//	order by
$strSQL.=" ".trim($strOrderBy);

//	save SQL for use in "Export" and "Printer-friendly" pages

$_SESSION[$sessionPrefix."_sql"] = $strSQL;
$_SESSION[$sessionPrefix."_where"] = $strWhereClause;
$_SESSION[$sessionPrefix."_order"] = $strOrderBy;
$_SESSION[$sessionPrefix."_arrFieldForSort"] = $arrFieldForSort;
$_SESSION[$sessionPrefix."_arrHowFieldSort"] = $arrHowFieldSort;

$rowsfound=false;

//	select and display records
if($allow_search)
{
	$diffDetailMasterDataTypes = false;	
 	
	
	
	
	$strSQLbak = $strSQL;
	if(function_exists("BeforeQueryList"))
		BeforeQueryList($strSQL,$strWhereClause,$strOrderBy);
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
		$numrows=gSQLRowCount($strWhereClause,0);
	}
	LogInfo($strSQL);
	
//	 Pagination:
	if(!$numrows)
	{
		$rowsfound=false;
		$message="No records found";
		$message_block=array();
		$message_block["begin"]="<span name=\"notfound_message".$id."\">";
		$message_block["end"]="</span>";
		$xt->assignbyref("message_block",$message_block);
		$xt->assign("message",$message);
	}
	else
	{
		$rowsfound=true;
		$maxRecords = $numrows;
		$xt->assign("records_found",$numrows);
		$maxpages=ceil($maxRecords/$PageSize);
		if($mypage > $maxpages)
			$mypage = $maxpages;
		if($mypage<1) 
			$mypage=1;
		$maxrecs=$PageSize;
		$xt->assign("page",$mypage);
		$xt->assign("maxpages",$maxpages);
		

//	write pagination
	if($maxpages>1)
	{
		$xt->assign("pagination_block",true);
		if($mode==LIST_SIMPLE)
			$jscode_end.="window.GotoPage = function(nPageNumber)
				{
					window.location='taxitem_list.php?goto='+nPageNumber;
				};";
		else
			$jscode_end.="window.GotoPage".$id." = function (nPageNumber)
				{
					window.frames['flyframe".$id."'].location='taxitem_list.php?".$lookupparams."&goto='+nPageNumber;
				};";
	
			$pagination="<table rows='1' cols='1' align='center' width='auto' border='0'>";
			$pagination.="<tr valign='center'><td align='center'>";
			$counterstart = $mypage - 9; 
			if($mypage%10) 
				$counterstart = $mypage - ($mypage%10) + 1; 
			$counterend = $counterstart + 9;
			if($counterend > $maxpages) $counterend = $maxpages; 
			if($counterstart != 1) 
			{
				$pagination.="<a href='JavaScript:GotoPage".$id."(1);' style='TEXT-DECORATION: none;'>"."First"."</a>";
				$pagination.="&nbsp;:&nbsp;";
				$pagination.="<a href='JavaScript:GotoPage".$id."(".($counterstart-1).");' style='TEXT-DECORATION: none;'>"."Previous"."</a>";
				$pagination.="&nbsp;";
			}
			$pagination.="<b>[</b>"; 
			for($counter = $counterstart;$counter<=$counterend;$counter++)
			{
				if ($counter != $mypage)
					$pagination.="&nbsp;<a href='JavaScript:GotoPage".$id."(".$counter.");' class='pag_n' style='TEXT-DECORATION: none;'>".$counter."</a>";
				else 
					$pagination.="&nbsp;<b>".$counter."</b>";
			}
			$pagination.="&nbsp;<b>]</b>";
			if ($counterend != $maxpages) 
			{
				$pagination.="&nbsp;<a href='JavaScript:GotoPage".$id."(".($counterend+1).");' style='TEXT-DECORATION: none;'>"."Next"."</a>";
				$pagination.="&nbsp;:&nbsp;";
				$pagination.="&nbsp;<a href='JavaScript:GotoPage".$id."(".($maxpages).");' style='TEXT-DECORATION: none;'>"."Last"."</a>";
			}
			$pagination.="</td></tr></table>";
			$xt->assign("pagination",$pagination);
		}
	}

	if($maxpages>1)
	{
		$strSQL.=" limit ".(($mypage-1)*$PageSize).",".$PageSize;
	}
	$rs=db_query($strSQL,$conn);

//	hide colunm headers if needed
	$recordsonpage=$numrows-($mypage-1)*$PageSize;
	if($recordsonpage>$PageSize)
	$recordsonpage=$PageSize;
	$colsonpage=1;
	if($colsonpage>$recordsonpage)
		$colsonpage=$recordsonpage;
	if($colsonpage<1)
		$colsonpage=1;


//	fill $rowinfo array
	$rowinfo = array();
	$rowinfo["data"]=array();
	$shade=false;
	$editlink="";
	$copylink="";

	if($allow_add )
	{
//	add inline add row	
		$row=array();
		$row["rowattrs"]="class=\"addarea".$id."\" rowid=\"add\"";
		$row["rowattrs"].=' style="display: none;"';
		$row["rowspace_attrs"]="class=\"addarea".$id."\"";
		$record=array();
		$record["edit_link"]=true;
		$record["inlineedit_link"]=true;
		$record["view_link"]=true;
		$record["copy_link"]=true;
		$record["checkbox"]=true;
		$record["checkbox"]=true;
		$record["editlink_attrs"]="id=\"editlink_add".$id."\"";
				if($allow_edit)
			$record["inlineeditlink_attrs"]= "id=\"ieditlink_add".$id."\"";
		$record["copylink_attrs"]="id=\"copylink_add".$id."\"";
		$record["viewlink_attrs"]="id=\"viewlink_add".$id."\"";
		$record["checkbox_attrs"]="id=\"check_add".$id."\" name=\"selection[]\"";
		$record["year_value"] = "<span id=\"add".$id."_year\">&nbsp;</span>";
				if(!$allow_edit || $mode==LIST_LOOKUP)
			$record["year_value"] = "<span id=\"ieditlink_add".$id."\"></span>".$record["year_value"];

//	add spans for link and display fieldsif they don't appear on the List page		
		if($mode==LIST_LOOKUP && $lookupSelectField)
		{
			$span="";
			if(!AppearOnListPage($linkfield))
			{
				$span.="<span style=\"display:none\" ";
				$span.="id=\"add".$id."_".GoodFieldName($linkfield)."\" ";
				$span.="></span>";
			}
			if($dispfield!=$linkfield && !AppearOnListPage($dispfield))
			{
				$span.="<span  style=\"display:none\" ";
				$span.="id=\"add".$id."_".GoodFieldName($dispfield)."\" ";
				$span.="></span>";
			}
			$record[GoodFieldName($lookupSelectField)."_value"].=$span;
		}
		$record["Storename_value"] = "<span id=\"add".$id."_Storename\">&nbsp;</span>";
//	add spans for link and display fieldsif they don't appear on the List page		
		if($mode==LIST_LOOKUP && $lookupSelectField)
		{
			$span="";
			if(!AppearOnListPage($linkfield))
			{
				$span.="<span style=\"display:none\" ";
				$span.="id=\"add".$id."_".GoodFieldName($linkfield)."\" ";
				$span.="></span>";
			}
			if($dispfield!=$linkfield && !AppearOnListPage($dispfield))
			{
				$span.="<span  style=\"display:none\" ";
				$span.="id=\"add".$id."_".GoodFieldName($dispfield)."\" ";
				$span.="></span>";
			}
			$record[GoodFieldName($lookupSelectField)."_value"].=$span;
		}
		$record["Amount_value"] = "<span id=\"add".$id."_Amount\">&nbsp;</span>";
//	add spans for link and display fieldsif they don't appear on the List page		
		if($mode==LIST_LOOKUP && $lookupSelectField)
		{
			$span="";
			if(!AppearOnListPage($linkfield))
			{
				$span.="<span style=\"display:none\" ";
				$span.="id=\"add".$id."_".GoodFieldName($linkfield)."\" ";
				$span.="></span>";
			}
			if($dispfield!=$linkfield && !AppearOnListPage($dispfield))
			{
				$span.="<span  style=\"display:none\" ";
				$span.="id=\"add".$id."_".GoodFieldName($dispfield)."\" ";
				$span.="></span>";
			}
			$record[GoodFieldName($lookupSelectField)."_value"].=$span;
		}
		$record["Created_value"] = "<span id=\"add".$id."_Created\">&nbsp;</span>";
//	add spans for link and display fieldsif they don't appear on the List page		
		if($mode==LIST_LOOKUP && $lookupSelectField)
		{
			$span="";
			if(!AppearOnListPage($linkfield))
			{
				$span.="<span style=\"display:none\" ";
				$span.="id=\"add".$id."_".GoodFieldName($linkfield)."\" ";
				$span.="></span>";
			}
			if($dispfield!=$linkfield && !AppearOnListPage($dispfield))
			{
				$span.="<span  style=\"display:none\" ";
				$span.="id=\"add".$id."_".GoodFieldName($dispfield)."\" ";
				$span.="></span>";
			}
			$record[GoodFieldName($lookupSelectField)."_value"].=$span;
		}
		if($colsonpage>1)
			$record["endrecord_block"]=true;
		$record["grid_recordheader"]=true;
		$record["grid_vrecord"]=true;
		$row["grid_record"]=array("data"=>array());
		$row["grid_record"]["data"][]=$record;
		for($i=1;$i<$colsonpage;$i++)
		{
			$rec=array();
			if($i<$colsonpage-1)
				$rec["endrecord_block"]=true;
			$row["grid_record"]["data"][]=$rec;
		}

		$row["grid_rowspace"]=true;
		$row["grid_recordspace"] = array("data"=>array());
		for($i=0;$i<$colsonpage*2-1;$i++)
			$row["grid_recordspace"]["data"][]=true;
		$rowinfo["data"][]=$row;
	}
	

//	add grid data	
	
	while($data=db_fetch_array($rs))
	{
		if(function_exists("BeforeProcessRowList"))
		{
			if(!BeforeProcessRowList($data))
				continue;
		}
		break;
	}
	while($data && $recno<=$PageSize)
	{
		$row=array();
		if(!$shade)
		{
			$row["rowattrs"]="class='shade'";
			$row["rowattrs"].=" onmouseover=\"this.className = 'rowselected';\" onmouseout=\"this.className = 'shade';\"";
			$shade=true;
		}
		else
		{
			$row["rowattrs"]="onmouseover=\"this.className = 'rowselected';\" onmouseout=\"this.className = '';\"";
			$shade=false;
		}
		$row["grid_record"]=array();
		$row["grid_record"]["data"]=array();
		$row["rowattrs"].=" rowid=\"".$rowid."\"";
		$rowid++;
		for($col=1;$data && $recno<=$PageSize && $col<=$colsonpage;$col++)
		{
			$recid=$recno+$id;
			$record=array();

	$editable=CheckSecurity($data["userID"],"Edit");
	$record["edit_link"]=$editable;
	$record["inlineedit_link"]=$editable;
	$record["view_link"]=true;
	$record["copy_link"]=true;

	
//	detail tables

//	key fields
	$keyblock="";
	$editlink="";
	$copylink="";
	$keylink="";
		$keyblock.=rawurlencode($data["itemID"]);
	$editlink.="editid1=".htmlspecialchars(rawurlencode($data["itemID"]));
	$copylink.="copyid1=".htmlspecialchars(rawurlencode($data["itemID"]));
	$keylink.="&key1=".htmlspecialchars(rawurlencode(@$data["itemID"]));

	$record["editlink_attrs"]="href=\"taxitem_edit.php?".$editlink."\" id=\"editlink".$recid."\"";
	$record["inlineeditlink_attrs"]= "href=\"taxitem_edit.php?".$editlink."\" onclick=\"return inlineEditing".$id.".inlineEdit('".$recid."','".$editlink."');\" id=\"ieditlink".$recid."\"";
	$record["copylink_attrs"]="href=\"taxitem_add.php?".$copylink."\" id=\"copylink".$recid."\"";
	$record["viewlink_attrs"]="href=\"taxitem_view.php?".$editlink."\" id=\"viewlink".$recid."\"";
	if($mode!=LIST_LOOKUP)
	{
		$record["checkbox"]=$editable;
		if($allow_export)
			$record["checkbox"]=true;
		$record["checkbox_attrs"]="name=\"selection[]\" value=\"".$keyblock."\" id=\"check".$recid."\"";
	}
	else
	{
		$checkbox_attrs="name=\"selection[]\" value=\"".htmlspecialchars(@$data[$linkfield])."\" id=\"check".$recid."\"";
		$record["checkbox"]=array("begin"=>"<input type=radio ".$checkbox_attrs.">", "data"=>array());
	}


//	year - 
			$value="";
				$value = ProcessLargeText(GetData($data,"year", ""),"field=year".$keylink,"",MODE_LIST);
			$record["year_value"]=$value;

//	Storename - 
			$value="";
				$value = ProcessLargeText(GetData($data,"Storename", ""),"field=Storename".$keylink,"",MODE_LIST);
			$record["Storename_value"]=$value;

//	Amount - Number
			$value="";
				$value = ProcessLargeText(GetData($data,"Amount", "Number"),"field=Amount".$keylink,"",MODE_LIST);
			$record["Amount_value"]=$value;

//	Created - Datetime
			$value="";
				$value = ProcessLargeText(GetData($data,"Created", "Datetime"),"field=Created".$keylink,"",MODE_LIST);
			$record["Created_value"]=$value;
			if(function_exists("BeforeMoveNextList"))
				BeforeMoveNextList($data,$row,$record);
			if($mode==LIST_LOOKUP && $lookupSelectField)
				$jscode_end.='inlineEditing'.$id.'.afterRecordEdited('.$recid.');';
			
			$cleanvalue=$record["year_value"];
			$span="<span ";
			$span.="id=\"edit".$recid."_year\" ";
					$span.="val=\"".htmlspecialchars($data["year"])."\" ";
			$span.=">";
			$record["year_value"] = $span.$record["year_value"]."</span>";
			if(!strlen($cleanvalue))
				$record["year_value"] .="&nbsp;";
			$cleanvalue=$record["Storename_value"];
			$span="<span ";
			$span.="id=\"edit".$recid."_Storename\" ";
					$span.="val=\"".htmlspecialchars($data["Storename"])."\" ";
			$span.=">";
			$record["Storename_value"] = $span.$record["Storename_value"]."</span>";
			if(!strlen($cleanvalue))
				$record["Storename_value"] .="&nbsp;";
			$cleanvalue=$record["Amount_value"];
			$span="<span ";
			$span.="id=\"edit".$recid."_Amount\" ";
					$span.="val=\"".htmlspecialchars($data["Amount"])."\" ";
			$span.=">";
			$record["Amount_value"] = $span.$record["Amount_value"]."</span>";
			if(!strlen($cleanvalue))
				$record["Amount_value"] .="&nbsp;";
			$cleanvalue=$record["Created_value"];
			$span="<span ";
			$span.="id=\"edit".$recid."_Created\" ";
					$span.="val=\"".htmlspecialchars($data["Created"])."\" ";
			$span.=">";
			$record["Created_value"] = $span.$record["Created_value"]."</span>";
			if(!strlen($cleanvalue))
				$record["Created_value"] .="&nbsp;";
		//	add spans with the link and display field values to the row
			if($mode==LIST_LOOKUP && $lookupSelectField)
			{
				$span="";
				if(!AppearOnListPage($linkfield))
				{
					$span.="<span ";
					$span.="id=\"edit".$recid."_".GoodFieldName($linkfield)."\" ";
					$span.="val=\"".htmlspecialchars($data[$linkfield])."\" ";
					$span.="></span>";
				}
				if($dispfield!=$linkfield && !AppearOnListPage($dispfield))
				{
					$span.="<span ";
					$span.="id=\"edit".$recid."_".GoodFieldName($dispfield)."\" ";
					$span.="val=\"".htmlspecialchars($data[$dispfield])."\" ";
					$span.="></span>";
				}
				$record[GoodFieldName($lookupSelectField)."_value"].=$span;
			}
			if($col<$colsonpage)
				$record["endrecord_block"]=true;
			$record["grid_recordheader"]=true;
			$record["grid_vrecord"]=true;
			$row["grid_record"]["data"][]=$record;
			while($data=db_fetch_array($rs))
			{
				if(function_exists("BeforeProcessRowList"))
				{
					if(!BeforeProcessRowList($data))
						continue;
				}
				break;
			}
			$recno++;
			
		}
		while($col<=$colsonpage)
		{
			$record = array();
			if($col<$colsonpage)
				$record["endrecord_block"]=true;
			$row["grid_record"]["data"][]=$record;
			$col++;
		}
//	assign row spacings for vertical layout
		$row["grid_rowspace"]=true;
		$row["grid_recordspace"] = array("data"=>array());
		for($i=0;$i<$colsonpage*2-1;$i++)
			$row["grid_recordspace"]["data"][]=true;
		
		$rowinfo["data"][]=$row;
	}
	if(count($rowinfo["data"]))
		$rowinfo["data"][count($rowinfo["data"])-1]["grid_rowspace"]=false;
	$xt->assignbyref("grid_row",$rowinfo);


}


if($allow_search)
{

	$searchfor_attrs="autocomplete=off onkeydown=\"return listenEvent(event,this,'ordinary');\" onkeyup=\"searchSuggest(event,this,'ordinary');\"";
	if($mode==LIST_LOOKUP)
		$searchfor_attrs="onkeydown=\"e=event; if(!e) e = window.event; if (e.keyCode != 13) return true; e.cancel = true; RunSearch('".$id."'); return false;\"";
	if($_SESSION[$sessionPrefix."_search"]==1)
	{
//	fill in search variables
	//	field selection
		if(@$_SESSION[$sessionPrefix."_searchfield"])
			$xt->assign(GoodFieldName(@$_SESSION[$sessionPrefix."_searchfield"])."_searchfieldoption","selected");
	// search type selection
		$xt->assign(GoodFieldName(@$_SESSION[$sessionPrefix."_searchoption"])."_searchtypeoption","selected");
		$searchfor_attrs.=" value=\"".htmlspecialchars(@$_SESSION[$sessionPrefix."_searchfor"])."\"";
	}
	$searchfor_attrs.=" name=\"ctlSearchFor".$id."\" id=\"ctlSearchFor".$id."\"";
	$xt->assign("searchfor_attrs",$searchfor_attrs);
	$xt->assign("searchbutton_attrs","onClick=\"javascript: RunSearch('".$id."');\"");
	$xt->assign("showallbutton_attrs","onClick=\"javascript: document.forms.frmSearch".$id.".a.value = 'showall'; document.forms.frmSearch".$id.".submit();\"");
}


if($mode==LIST_SIMPLE)
{

		$xt->assign("login_block",true);
	$xt->assign("username",htmlspecialchars($_SESSION["UserID"]));
	$xt->assign("logoutlink_attrs","onclick=\"window.location.href='login.php?a=logout';\"");


	$xt->assign("toplinks_block",true);

	$xt->assign("print_link",$allow_export);
	$xt->assign("printall_link",$allow_export);
	$xt->assign("printlink_attrs","href=\"taxitem_print.php\" onclick=\"window.open('taxitem_print.php','wPrint');return false;\"");
	$xt->assign("printalllink_attrs","href=\"taxitem_print.php?all=1\" onclick=\"window.open('taxitem_print.php?all=1','wPrint');return false;\"");
	$xt->assign("export_link",$allow_export);
	$xt->assign("exportlink_attrs","href=\"taxitem_export.php\" onclick=\"window.open('taxitem_export.php','wExport');return false;\"");
	
	$xt->assign("printselected_link",$allow_export);
	$xt->assign("printselectedlink_attrs","disptype=\"control1\" onclick=\"
	if(!\$('input[@type=checkbox][@checked][@name^=selection]').length)
		return true;
	document.forms.frmAdmin.action='taxitem_print.php';
	document.forms.frmAdmin.target='_blank';
	document.forms.frmAdmin.submit(); 
	document.forms.frmAdmin.action='taxitem_list.php'; 
	document.forms.frmAdmin.target='_self';return false\"
	href=\"taxitem_print.php\"");
	$xt->assign("exportselected_link",$allow_export);
	$xt->assign("exportselectedlink_attrs","disptype=\"control1\" onclick=\"
	if(!\$('input[@type=checkbox][@checked][@name^=selection]').length)
		return true;
	document.forms.frmAdmin.action='taxitem_export.php';
	document.forms.frmAdmin.target='_blank';
	document.forms.frmAdmin.submit(); 
	document.forms.frmAdmin.action='taxitem_list.php'; 
	document.forms.frmAdmin.target='_self';return false;\"
	href=\"taxitem_export.php\"");
	
	$xt->assign("add_link",$allow_add);
	$xt->assign("copy_column",$allow_add);
	$xt->assign("addlink_attrs","href=\"taxitem_add.php\" onClick=\"window.location.href='taxitem_add.php'\"");
	$xt->assign("inlineadd_link",$allow_add);
	$xt->assign("inlineaddlink_attrs","href=\"taxitem_add.php\" onclick=\"return inlineEditing".$id.".inlineAdd(flyid++,null,'taxitem_add.php');\"");

	$xt->assign("selectall_link",$allow_delete || $allow_export  || $allow_edit);
	$xt->assign("selectalllink_attrs","href=# onclick=\"var i; 
	bSelected=!bSelected;
if ((typeof frmAdmin.elements['selection[]'].length)=='undefined')
	frmAdmin.elements['selection[]'].checked=bSelected;
else
for (i=0;i<frmAdmin.elements['selection[]'].length;++i) 
	frmAdmin.elements['selection[]'][i].checked=bSelected\"");
	
	$xt->assign("checkbox_column",$allow_delete || $allow_export  || $allow_edit);
	$xt->assign("checkbox_header",true);
	$xt->assign("checkboxheader_attrs","onClick = \"var i; 
if ((typeof frmAdmin.elements['selection[]'].length)=='undefined')
	frmAdmin.elements['selection[]'].checked=this.checked;
else
for (i=0;i<frmAdmin.elements['selection[]'].length;++i) 
	frmAdmin.elements['selection[]'][i].checked=this.checked;\"");
	$xt->assign("editselected_link",$allow_edit);
	$xt->assign("editselectedlink_attrs","href=\"taxitem_edit.php\" disptype=\"control1\" name=\"edit_selected".$id."\" onclick=\"\$('input[@type=checkbox][@checked][@id^=check]').each(function(i){
				if(!isNaN(parseInt(this.id.substr(5))))
					\$('a#ieditlink'+this.id.substr(5)).click();});\"");
	$xt->assign("saveall_link",$allow_edit||$allow_edit);
	$xt->assign("savealllink_attrs","disptype=\"control1\" name=\"saveall_edited".$id."\"  onclick=\"\$('a[@id^=save_]').click();\"");
	$xt->assign("savealllink_span","style=\"display:none\"");
	$xt->assign("cancelall_link",$allow_edit||$allow_edit);
	$xt->assign("cancelalllink_attrs","disptype=\"control1\" name=\"revertall_edited".$id."\" onclick=\"\$('a[@id^=revert_]').click();\"");
	$xt->assign("canselalllink_span","style=\"display:none\"");

	$xt->assign("edit_column",$allow_edit);
	$xt->assign("edit_headercolumn",$allow_edit);
	$xt->assign("edit_footercolumn",$allow_edit);
	$xt->assign("inlineedit_column",$allow_edit);
	$xt->assign("inlineedit_headercolumn",$allow_edit);
	$xt->assign("inlineedit_footercolumn",$allow_edit);
	
	$xt->assign("view_column",$allow_search);


	$xt->assign("delete_link",$allow_delete);
	$xt->assign("deletelink_attrs","onclick=\"
		if(\$('input[@type=checkbox][@checked][@name^=selection]').length && confirm('"."Do you really want to delete these records?"."'))
			frmAdmin.submit(); 
		return false;\"");

}
elseif ($mode==LIST_LOOKUP)
{
//	$xt->assign("checkbox_column",true);
	$xt->assign("inlineadd_link",$allow_add);
	$xt->assign("inlineaddlink_attrs","href=\"taxitem_add.php\" onclick=\"return inlineEditing".$id.".inlineAdd(flyid++,".$id.",'taxitem_add.php');\"");
//	$xt->assign("inlineedit_column",$allow_edit);
}

$xt->assign("year_fieldheadercolumn",true);
$xt->assign("year_fieldcolumn",true);
$xt->assign("year_fieldfootercolumn",true);
$xt->assign("Storename_fieldheadercolumn",true);
$xt->assign("Storename_fieldcolumn",true);
$xt->assign("Storename_fieldfootercolumn",true);
$xt->assign("Amount_fieldheadercolumn",true);
$xt->assign("Amount_fieldcolumn",true);
$xt->assign("Amount_fieldfootercolumn",true);
$xt->assign("Created_fieldheadercolumn",true);
$xt->assign("Created_fieldcolumn",true);
$xt->assign("Created_fieldfootercolumn",true);
	
$display_grid = $allow_add || $allow_search && $rowsfound;

$xt->assign("asearch_link",$allow_search);
$xt->assign("asearchlink_attrs","href=\"taxitem_search.php\" onclick=\"window.location.href='taxitem_search.php';return false;\"");
$xt->assign("import_link",$allow_import);
$xt->assign("importlink_attrs","href=\"taxitem_import.php\" onclick=\"window.location.href='taxitem_import.php';return false;\"");

$xt->assign("search_records_block",$allow_search);
$xt->assign("searchform",$allow_search);
$xt->assign("searchform_showall",$allow_search);
if($mode!=LIST_LOOKUP)
{
	$xt->assign("searchform_field",$allow_search);
	$xt->assign("searchform_option",$allow_search);
}
$xt->assign("searchform_text",$allow_search);
$xt->assign("searchform_search",$allow_search);

$xt->assign("usermessage",true);

if($display_grid)
{
	if($mode==LIST_SIMPLE)
		$xt->assign_section("grid_block",
		"<form method=\"POST\" action=\"taxitem_list.php\" name=\"frmAdmin\" id=\"frmAdmin\">
		<input type=\"hidden\" id=\"a\" name=\"a\" value=\"delete\">",
		"</form>");
	elseif($mode==LIST_LOOKUP)
		$xt->assign_section("grid_block",
		"<form method=\"POST\" action=\"taxitem_list.php\" name=\"frmAdmin".$id."\" id=\"frmAdmin".$id."\" target=\"flyframe".$id."\">
		<input type=\"hidden\" id=\"a".$id."\" name=\"a\" value=\"delete\">",
		"</form>");
	
	$record_header=array("data"=>array());
	$record_footer=array("data"=>array());
	for($i=0;$i<$colsonpage;$i++)
	{
		$rheader=array();
		$rfooter=array();
		if($i<$colsonpage-1)
		{
			$rheader["endrecordheader_block"]=true;
			$rfooter["endrecordfooter_block"]=true;
		}
		$record_header["data"][]=$rheader;
		$record_footer["data"][]=$rfooter;
	}
	$xt->assignbyref("record_header",$record_header);
	$xt->assignbyref("record_footer",$record_footer);
	$xt->assign("grid_header",true);
	// hiding header, if no rows
	if (!$numrows)
		$xt->assign("gridHeader_attrs",'id="gridHeaderTr" style="display: none;"');
	
	$xt->assign("grid_footer",true);

	$xt->assign("record_controls",true);
}

$xt->assign("recordcontrols_block",$allow_add || $display_grid);

$xt->assign("newrecord_controls",$allow_add);

if($mode==LIST_SIMPLE)
{
	$xt->assign("details_block",$allow_search && $rowsfound);
	$xt->assign("recordspp_block",$allow_search && $rowsfound);
	$xt->assign("recordspp_attrs","onchange=\"javascript: document.location='taxitem_list.php?pagesize='+this.options[this.selectedIndex].value;\"");
	$xt->assign("pages_block",$allow_search && $rowsfound);
}
else
	$xt->assign("recordspp_attrs","onchange=\"javascript: window.frames['flyframe".$id."'].location='taxitem_list.php?".$lookupparams."&pagesize='+this.options[this.selectedIndex].value;\"");
$xt->assign("grid_controls",$display_grid);



//	display Back to Master link and master table info
$masterkeys=array();
if($mastertable=="Tax Summary")
{
//	include proper masterlist.php code
	include("include/Tax_Summary_masterlist.php");
	$masterkeys[]=@$_SESSION[$sessionPrefix."_masterkey1"];
	$masterkeys[]=@$_SESSION[$sessionPrefix."_masterkey2"];
	$masterkeys[]=@$_SESSION[$sessionPrefix."_masterkey3"];
	$params=array("detailtable"=>"taxitem","keys"=>$masterkeys);
	$master=array();
	$master["func"]="DisplayMasterTableInfo_Tax_Summary";
	$master["params"]=$params;
	$xt->assignbyref("showmasterfile",$master);
	$xt->assign("mastertable_block",true);
	$xt->assign("backtomasterlink_attrs","href=\"Tax_Summary_list.php?a=return\"");
}

$jscode_end.="if(flyid<".($recid+1).") flyid=".($recid+1).";\r\n";
	$jscode_end.="\$(\".addarea".$id."\").each(function(i) { \$(this).hide();});\r\n";
	if(!$numrows)
	{
		$jscode_end .= "$('#record_controls".$id."').hide();";
		if($mode==LIST_SIMPLE)
			$jscode_end .= "$('[@name=maintable]').hide();";
		else
		{
			$jscode_end .= "$('[@name=maintable]',$('#fly".$id."')).hide();";
		}
	}

$html_end .= "<style>
#inline_error {
	font-family: Verdana, Arial, Helvetica, sans serif;
	font-size: 11px;
	position: absolute;
	background-color: white;
	border: 1px solid red;
	padding: 10px;
	background-repeat: no-repeat;
	display: none;
	}
</style>";
if($mode==LIST_SIMPLE)
	$jscode_end.="if(!$('[@disptype=control1]').length && $('[@disptype=controltable1]').length)
		$('[@disptype=controltable1]').hide();";
if($_SESSION[$sessionPrefix."_search"]==1)
	$jscode_end.= "if(document.getElementById('ctlSearchFor".$id."')) document.getElementById('ctlSearchFor".$id."').focus();";

	
if($mode==LIST_SIMPLE)
{
	$jscode.=$jscode_end;
	PrepareJSCode($jscode,$id);
	$body["end"]="<script>".$jscode."</script>";
	$body["end"].=$html_end;
}
elseif($mode==LIST_LOOKUP)
{
	$body["end"].=$html_end;
	$xt->assign("footer","");
}
$xt->assignbyref("body",$body);
$xt->assign("style_block",true);
$xt->assign("iestyle_block",true);


$strSQL=$_SESSION[$sessionPrefix."_sql"];
$xt->assign("changepwd_link",$_SESSION["AccessLevel"] != ACCESS_LEVEL_GUEST);
$xt->assign("changepwdlink_attrs","href=\"changepwd.php\" onclick=\"window.location.href='changepwd.php';return false;\"");



$xt->assign("quickjump_attrs","onfocus =\"window.selectcurrent = this.selectedIndex;\" onchange=\"if(this.options[this.selectedIndex].value){window.location.href=this.options[this.selectedIndex].value;}else{this.selectedIndex=window.selectcurrent;}\"");


$xt->assign("endrecordblock_attrs","colid=\"endrecord\"");
$templatefile = "taxitem_list.htm";
if(function_exists("BeforeShowList"))
	BeforeShowList($xt,$templatefile);

if($mode==LIST_SIMPLE)
	$xt->display($templatefile);
elseif($mode==LIST_LOOKUP)
{
	$jscode.=$jscode_end;
	PrepareJSCode($jscode,$id);
	if($firsttime)
	{
		echo str_replace(array("\\","\r","\n"),array("\\\\","\\r","\\n"),$jscode);
		echo "\n";
	}
	else
	{
		echo "<textarea id=data>decli";
		echo htmlspecialchars($jscode);
		echo "</textarea>";
	}
	$xt->load_template($templatefile);
	// add search controls for left menu layouts (Rome, London)
	$lookupSearchControls = $xt->fetch_loaded('searchform_field').'&nbsp;'
		.$xt->fetch_loaded('searchform_option').'&nbsp;'
		.$xt->fetch_loaded('searchform_text').'&nbsp;'
		.$xt->fetch_loaded('searchform_search').'&nbsp;'
		.$xt->fetch_loaded('searchform_showall').'&nbsp;';
	$xt->assign("lookupSearchControls",$lookupSearchControls);
	
	$xt->display_loaded("style_block");
	$xt->display_loaded("iestyle_block");
	$xt->display_loaded("body");
}

?>
