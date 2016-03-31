<?php

$dal_info=array();
$daltable_taxitem = array();
$daltable_taxitem["itemID"]=array();
$daltable_taxitem["itemID"]["nType"]=3;
	$daltable_taxitem["itemID"]["bKey"]=true;
$daltable_taxitem["itemID"]["varname"]="itemID";
$daltable_taxitem["Storename"]=array();
$daltable_taxitem["Storename"]["nType"]=200;
	$daltable_taxitem["Storename"]["varname"]="Storename";
$daltable_taxitem["Amount"]=array();
$daltable_taxitem["Amount"]["nType"]=14;
	$daltable_taxitem["Amount"]["varname"]="Amount";
$daltable_taxitem["userID"]=array();
$daltable_taxitem["userID"]["nType"]=3;
	$daltable_taxitem["userID"]["varname"]="userID";
$daltable_taxitem["Created"]=array();
$daltable_taxitem["Created"]["nType"]=135;
	$daltable_taxitem["Created"]["varname"]="Created";
$daltable_taxitem["monthname"]=array();
$daltable_taxitem["monthname"]["nType"]=200;
	$daltable_taxitem["monthname"]["varname"]="monthname";
$daltable_taxitem["year"]=array();
$daltable_taxitem["year"]["nType"]=200;
	$daltable_taxitem["year"]["varname"]="year";
$dal_info["taxitem"]=&$daltable_taxitem;
$daltable_member = array();
$daltable_member["MemberID"]=array();
$daltable_member["MemberID"]["nType"]=3;
	$daltable_member["MemberID"]["bKey"]=true;
$daltable_member["MemberID"]["varname"]="MemberID";
$daltable_member["Login"]=array();
$daltable_member["Login"]["nType"]=200;
	$daltable_member["Login"]["varname"]="Login";
$daltable_member["Password"]=array();
$daltable_member["Password"]["nType"]=200;
	$daltable_member["Password"]["varname"]="Password";
$daltable_member["Email"]=array();
$daltable_member["Email"]["nType"]=200;
	$daltable_member["Email"]["varname"]="Email";
$daltable_member["DisplayName"]=array();
$daltable_member["DisplayName"]["nType"]=200;
	$daltable_member["DisplayName"]["varname"]="DisplayName";
$dal_info["member"]=&$daltable_member;



function CustomQuery($dalSQL)
{
	global $conn;
	$rs = db_query($dalSQL,$conn);
	  return $rs;
}

function UsersTableName()
{
	return "`member`";
}


class tDAL
{
	var $taxitem;
	var $member;
  function Table($strTable)
  {
          if(strtoupper($strTable)==strtoupper("taxitem"))
              return $this->taxitem;
          if(strtoupper($strTable)==strtoupper("member"))
              return $this->member;
//	check table names without dbo. and other prefixes
          if(strtoupper(cutprefix($strTable))==strtoupper(cutprefix("taxitem")))
              return $this->taxitem;
          if(strtoupper(cutprefix($strTable))==strtoupper(cutprefix("member")))
              return $this->member;
  }
}

$dal = new tDAL;

class tDALTable
{
	var $m_TableName;
	var $Param = array();
	var $Value = array();
	
	function TableName()
	{
		return AddTableWrappers($this->m_TableName);
	} 
	
	function Add() 
	{
		global $conn,$dal_info;
		$insertFields="";
		$insertValues="";
		$tableinfo = &$dal_info[$this->m_TableName];
//	prepare parameters		
		foreach($tableinfo as $fieldname=>$fld)
		{
			$command='if(isset($this->'.$fld['varname'].'))
			{
				$this->Value[\''.escapesq($fieldname).'\'] = $this->'.$fld['varname'].';
			}';
			eval($command);
			foreach($this->Value as $field=>$value)
			{
				if (strtoupper($field)!=strtoupper($fieldname))
					continue;
				$insertFields.= AddFieldWrappers($fieldname).",";
				if (NeedQuotes($fld["nType"]))
					$insertValues.= "'".db_addslashes($value) . "',";
				else
					$insertValues.= "".(0+$value) . ",";		
				break;
			}
		}
//	prepare and exec SQL
		if ($insertFields!="" && $insertValues!="")		
		{
			$insertFields = substr($insertFields,0,-1);
			$insertValues = substr($insertValues,0,-1);
			$dalSQL = "insert into ".AddTableWrappers($this->m_TableName)." (".$insertFields.") values (".$insertValues.")";
			db_exec($dalSQL,$conn);
		}
//	cleanup		
	    $this->Reset();
	}

	function QueryAll()
	{
		global $conn;
		$dalSQL = "select * from ".AddFieldWrappers($this->m_TableName);
		$rs = db_query($dalSQL,$conn);
		return $rs;
	}

	function Query($swhere="",$orderby="")
	{
		global $conn;
		if ($swhere)
			$swhere = " where ".$swhere;
		if ($orderby)
			$orderby = " order by ".$orderby;
		$dalSQL = "select * from ".AddTableWrappers($this->m_TableName).$swhere.$orderby;
		$rs = db_query($dalSQL,$conn);
		return $rs;
	}

	function Delete()
	{
		global $conn,$dal_info;
		$deleteFields="";
		$tableinfo = &$dal_info[$this->m_TableName];
//	prepare parameters		
		foreach($tableinfo as $fieldname=>$fld)
		{
			$command='if(isset($this->'.$fld['varname'].'))
			{
				$this->Value[\''.escapesq($fieldname).'\'] = $this->'.$fld['varname'].';
			}
			';
			eval($command);
			foreach($this->Value as $field=>$value)
			{
				if (strtoupper($field)!=strtoupper($fieldname))
					continue;
				if (NeedQuotes($fld["nType"]))
					$deleteFields.= AddFieldWrappers($fieldname)."='".db_addslashes($value) . "' and ";
				else
					$deleteFields.= AddFieldWrappers($fieldname)."=". (0+$value) . " and ";		
				break;
			}
		}
//	do delete
		if ($deleteFields)
		{
			$deleteFields = substr($deleteFields,0,-5);
			$dalSQL = "delete from ".AddFieldWrappers($this->m_TableName)." where ".$deleteFields;
			db_exec($dalSQL,$conn);
		}
	
//	cleanup
	    $this->Reset();
	}

	function Reset()
	{
		$this->Value=array();
		$this->Param=array();
		global $dal_info;
		$tableinfo = &$dal_info[$this->m_TableName];
//	prepare parameters		
		foreach($tableinfo as $fieldname=>$fld)
		{
			$command='unset($this->'.$fld["varname"].");";
			eval($command);
		}
	}	

	function Update()
	{
		global $conn,$dal_info;
		$tableinfo = &$dal_info[$this->m_TableName];
		$updateParam = "";
		$updateValue = "";

		foreach($tableinfo as $fieldname=>$fld)
		{
			$command='if(isset($this->'.$fld['varname'].')) { ';
			if($fld["bKey"])
				$command.='$this->Param[\''.escapesq($fieldname).'\'] = $this->'.$fld['varname'].';';
			else
				$command.='$this->Value[\''.escapesq($fieldname).'\'] = $this->'.$fld['varname'].';';
			$command.=' }';
			eval($command);
			if(!$fld["bKey"])
			{
				foreach($this->Value as $field=>$value)
				{
					if (strtoupper($field)!=strtoupper($fieldname))
						continue;
					if (NeedQuotes($fld["nType"]))
						$updateValue.= AddFieldWrappers($fieldname)."='".db_addslashes($value) . "', ";
					else
						$updateValue.= AddFieldWrappers($fieldname)."=".(0+$value) . ", ";
					break;
				}
			}
			else
			{
				foreach($this->Param as $field=>$value)
				{
					if (strtoupper($field)!=strtoupper($fieldname))
						continue;
					if (NeedQuotes($fld["nType"]))
						$updateParam.= AddFieldWrappers($fieldname)."='".db_addslashes($value) . "' and ";
					else
						$updateParam.= AddFieldWrappers($fieldname)."=".(0+$value) . " and ";
					break;
				}
			}
		}

//	construct SQL and do update	
		if ($updateParam)
			$updateParam = substr($updateParam,0,-5);
		if ($updateValue)
			$updateValue = substr($updateValue,0,-2);
		if ($updateValue && $updateParam)
		{
			$dalSQL = "update ".AddTableWrappers($this->m_TableName)." set ".$updateValue." where ".$updateParam;
			db_exec($dalSQL,$conn);
		}

//	cleanup
		$this->Reset();
	}

	function FetchByID()
	{
		global $conn,$dal_info;
		$tableinfo = &$dal_info[$this->m_TableName];

		$dal_where="";
		foreach($tableinfo as $fieldname=>$fld)
		{
			$command='if(isset($this->'.$fld['varname'].')) { ';
			$command.='$this->Value[\''.escapesq($fieldname).'\'] = $this->'.$fld['varname'].';';
			$command.=' }';
			eval($command);
			foreach($this->Value as $field=>$value)
			{
				if (strtoupper($field)!=strtoupper($fieldname))
					continue;
				if (NeedQuotes($fld["nType"]))
					$dal_where.= AddFieldWrappers($fieldname)."='".db_addslashes($value) . "' and ";
				else
					$dal_where.= AddFieldWrappers($fieldname)."=".(0+$value) . " and ";
				break;
			}
		}
//	cleanup
		$this->Reset();
//	construct and run SQL
		if ($dal_where)
			$dal_where = " where ".substr($dal_where,0,-5);
		$dalSQL = "select * from ".AddTableWrappers($this->m_TableName).$dal_where;
		$rs = db_query($dalSQL,$conn);
		return $rs;
	}
}

class class_taxitem extends tDALTable
{
	var $itemID;
	var $Storename;
	var $Amount;
	var $userID;
	var $Created;
	var $monthname;
	var $year;

	function class_taxitem()
	{
		$this->m_TableName = "taxitem";
	}
}
$dal->taxitem = new class_taxitem();
class class_member extends tDALTable
{
	var $MemberID;
	var $Login;
	var $Password;
	var $Email;
	var $DisplayName;

	function class_member()
	{
		$this->m_TableName = "member";
	}
}
$dal->member = new class_member();

class DalRecordset
{
	
	var $m_rs;
	var $m_fields;
	var $m_eof;
	
	function Fields($field="")
	{
		if(!$field)
			return $this->m_fields;
		return $this->Field($field);
	}
	
	function Field($field)
	{
		if($this->m_eof)
			return false;
		foreach($this->m_fields as $name=>$value)
		{
			if(!strcasecmp($name,$field))
				return $value;
		}
		return false;
	}
	function DalRecordset($rs)
	{
		$this->m_rs=$rs;
		$this->MoveNext();
	}
	function EOF()
	{
		return $this->m_eof;
	}
	
	function MoveNext()
	{
		if(!$this->m_eof)
			$this->m_fields=db_fetch_array($this->m_rs);
		$this->m_eof = !$this->m_fields;
		return !$this->m_eof;
	}
}

function cutprefix($table)
{
	$pos=strpos($table,".");
	if($pos===false)
		return $table;
	return substr($table,$pos+1);
}

function escapesq($str)
{
	return str_replace(array("\\","'"),array("\\\\","\\'"),$str);
}

?>