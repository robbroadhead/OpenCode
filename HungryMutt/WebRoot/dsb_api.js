
// ====================================================================
// A function to manage dual-select boxes (DSB's) for forms.
// Can handle multiple DSB's on a single page, either in a
// single <form>, or multiple <form>'s.
//
// Version 1.0.3.
//
// Dependencies:
//	None
// --------------------------------------------------------------------

// --------------------------------------------------------------------
// Code written by Conan Theobald and is Copyright (c)
// 2003 by Conan Theobald unless otherwise stated.
//
// Visit: www.conans.co.uk
// Email: software@conans.co.uk
// ====================================================================

// ====================================================================
// Documentation
// ====================================================================

/*

Better documentation is available on my home-page: www.conans.co.uk

--

The document ID's of the elements in your DSB(s) must
follow this naming convention:

	Select box 1 (pool):			<idprefix>_list1
	Select box 2 (selection):		<idprefix>_list2
	Add button:				<idprefix>_add
	Add all button:				<idprefix>_addall
	Remove button:				<idprefix>_rem
	Remove all button:			<idprefix>_remall
	Hidden field:				<idprefix>_hidden

	(Optional buttons)
	Move up button:				<idprefix>_moveup
	Move down button:			<idprefix>_movedown
	Reverse button:				<idprefix>_reverse

(The hidden-field contains the entries selected by the user)

Where <idprefix> is a unique identifier for your DSB(s).
You then call the dsb_Create() function from your
window.onload() function.

dsb_Create() expects one parameter; the ID prefix of one
of your DSB's. If none is specified, the default "dsb" is used.

The dsb_Create() function has error-checked and will warn
you if a required element for a DSB is missing from your page.

-----------------------------------------------------------------------
Example: Copy and paste this into a new file to test the API.
-----------------------------------------------------------------------

<html>

<head>

	<title>dsb api test</title>
	<meta name="Author" content="Conan Theobald" />

	<script type="text/javascript" language="JavaScript" src="dsb_api.js"></script>

	<script type="text/javascript" language="JavaScript">
	<!--
		window.onload = function ( ) {
			dsb_Create ( 'dsb' );
		}
	// -->
	</script>

</head>

<body>

	<form>

		<input type="hidden" id="dsb_hidden" name="fruit" value="" />

		<select id="dsb_list1" size="5" multiple="multiple">
			<option value="Apples">Apples</option>
			<option value="Bananas">Bananas</option>
			<option value="Grapes">Grapes</option>
			<option value="Oranges">Oranges</option>
			<option value="Pears">Pears</option>
		</select>

		<select id="dsb_list2" size="5" multiple="multiple">
		</select>

		<br />

		<input type="button" id="dsb_add" value="Add" />
		<input type="button" id="dsb_addall" value="Add all" />
		<input type="button" id="dsb_rem" value="Remove" />
		<input type="button" id="dsb_remall" value="Remove all" />

	</form>

</body>

</html>

*/

function dsb_Create ( sIDPrefix ) {

	// Check to see if the browser supports the DOM
	if ( ! document.getElementById ) {
		return ( false );
	}

	// Use default value if none specified
	if ( ! sIDPrefix ) sIDPrefix = 'dsb';

	var oList1 = document.getElementById ( sIDPrefix + '_list1' );
	var oList2 = document.getElementById ( sIDPrefix + '_list2' );
	var oAdd = document.getElementById ( sIDPrefix + '_add' );
	var oAddAll = document.getElementById ( sIDPrefix + '_addall' );
	var oRem = document.getElementById ( sIDPrefix + '_rem' );
	var oRemAll = document.getElementById ( sIDPrefix + '_remall' );
	var oHidden = document.getElementById ( sIDPrefix + '_hidden' );

	// Optional buttons
	var oMoveUp = document.getElementById ( sIDPrefix + '_moveup' );
	var oMoveDown = document.getElementById ( sIDPrefix + '_movedown' );
	var oReverse = document.getElementById ( sIDPrefix + '_reverse' );

	// Check to see if these objects are available
	var sErrorMsg = '';

	if ( oList1 == null ) sErrorMsg += 'Expected: <select> with an ID of "' + sIDPrefix + '_list1".\n';
	if ( oList2 == null ) sErrorMsg += 'Expected: <select> with an ID of "' + sIDPrefix + '_list2".\n';
	if ( oAdd == null ) sErrorMsg += 'Expected: <input type="button"> with an ID of "' + sIDPrefix + '_add".\n';
	if ( oAddAll == null ) sErrorMsg += 'Expected: <input type="button"> with an ID of "' + sIDPrefix + '_addall".\n';
	if ( oRem == null ) sErrorMsg += 'Expected: <input type="button"> with an ID of "' + sIDPrefix + '_rem".\n';
	if ( oRemAll == null ) sErrorMsg += 'Expected: <input type="button"> with an ID of "' + sIDPrefix + '_remall".\n';
	if ( oHidden == null ) sErrorMsg += 'Expected: <input type="hidden"> with an ID of "' + sIDPrefix + '_hidden".\n';

	if ( sErrorMsg != '' ) {
		alert ( 'dsb_Create ( ) :: Errors occured.\n\n' + sErrorMsg );
		return ( false );
	}

	// Prevent refreshing the page or clicking the Back button from screwing up the hidden value
	oHidden.value = '';

	// Add a new method to the document object (if it doesn't already exist)
	if ( ! document.copyListToText ) {
		document.copyListToText = function ( oList, oText ) {
			var oArray = new Array ( );

			for ( var i = 0 ; i < oList.options.length ; i ++ ) {
				oArray [ oArray.length ] = oList.options [ i ].value;
			}

			oText.value = oArray.join ( ',' );
		}
	}

	// Configure OnChange() event for "List1" select box
	oList1.Add = oAdd;
	oList1.AddAll = oAddAll;
	oList1.onchange = function ( ) {
		var bDisabled = true;
		for ( var i = 0 ; i < this.options.length ; i ++ ) {
			if ( this.options [ i ].selected ) {
				bDisabled = false;
				break;
			}
		}

		this.AddAll.disabled = this.options.length == 0;
		this.Add.disabled = bDisabled;
	}

	// Configure OnDblClick() event for "List1" select box
	oList1.ondblclick = function ( ) {
		this.Add.onclick ( );
	}

	// Configure OnChange() event for "List2" select box
	oList2.Rem = oRem;
	oList2.RemAll = oRemAll;
	oList2.MoveUp = oMoveUp;
	oList2.MoveDown = oMoveDown;
	oList2.Reverse = oReverse;
	oList2.onchange = function ( ) {
		var bDisabled = true;
		for ( var i = 0 ; i < this.options.length ; i ++ ) {
			if ( this.options [ i ].selected ) {
				bDisabled = false;
				break;
			}
		}

		this.RemAll.disabled = this.options.length == 0;
		this.Rem.disabled = bDisabled;

		if ( this.MoveUp ) { this.MoveUp.disabled = bDisabled; }
		if ( this.MoveDown ) { this.MoveDown.disabled = bDisabled; }
		if ( this.Reverse ) { this.Reverse.disabled = this.options.length == 0; }
	}

	// Configure OnDblClick() event for "List2" select box
	oList2.ondblclick = function ( ) {
		this.Rem.onclick ( );
	}

	// Initialize button states
	oList1.onchange ( );
	oList2.onchange ( );

	// Configure OnClick() event for "Add" button
	oAdd.List1 = oList1;
	oAdd.List2 = oList2;
	oAdd.HiddenFld = oHidden;
	oAdd.onclick = function ( ) {
		var oPool = new Array ( );
		for ( var i = this.List1.options.length - 1 ; i >= 0 ; i -- ) {
			if ( this.List1.options [ i ].selected ) {
				oPool [ oPool.length ] = new Option ( this.List1.options [ i ].text, this.List1.options [ i ].value, false, true );
				this.List1.options [ i ] = null;
			}
		}

		for ( var i = oPool.length - 1 ; i >= 0 ; i -- ) {
				this.List2.options [ this.List2.options.length ] = oPool [ i ];
		}

		// Update hidden field
		document.copyListToText ( this.List2, this.HiddenFld );

		// Update button states
		this.List1.onchange ( );
		this.List2.onchange ( );
	}

	// Configure OnClick() event for "Add all" button
	oAddAll.List1 = oList1;
	oAddAll.List2 = oList2;
	oAddAll.HiddenFld = oHidden;
	oAddAll.onclick = function ( ) {
		var oPool = new Array ( );
		for ( var i = this.List1.options.length - 1 ; i >= 0 ; i -- ) {
			oPool [ oPool.length ] = new Option ( this.List1.options [ i ].text, this.List1.options [ i ].value, false, true );
			this.List1.options [ i ] = null;
		}

		for ( var i = oPool.length - 1 ; i >= 0 ; i -- ) {
				this.List2.options [ this.List2.options.length ] = oPool [ i ];
		}

		// Update hidden field
		document.copyListToText ( this.List2, this.HiddenFld );

		// Update button states
		this.List1.onchange ( );
		this.List2.onchange ( );
	}

	// Configure OnClick() event for "Remove" button
	oRem.List1 = oList1;
	oRem.List2 = oList2;
	oRem.HiddenFld = oHidden;
	oRem.onclick = function ( ) {
		var oPool = new Array ( );
		for ( var i = this.List2.options.length - 1 ; i >= 0 ; i -- ) {
			if ( this.List2.options [ i ].selected ) {
				oPool [ oPool.length ] = new Option ( this.List2.options [ i ].text, this.List2.options [ i ].value, false, true );
				this.List2.options [ i ] = null;
			}
		}

		for ( var i = oPool.length - 1 ; i >= 0 ; i -- ) {
				this.List1.options [ this.List1.options.length ] = oPool [ i ];
		}

		// Update hidden field
		document.copyListToText ( this.List2, this.HiddenFld );

		// Update button states
		this.List1.onchange ( );
		this.List2.onchange ( );
	}

	// Configure OnClick() event for "Remove all" button
	oRemAll.List1 = oList1;
	oRemAll.List2 = oList2;
	oRemAll.HiddenFld = oHidden;
	oRemAll.onclick = function ( ) {
		var oPool = new Array ( );
		for ( var i = this.List2.options.length - 1 ; i >= 0 ; i -- ) {
			oPool [ oPool.length ] = new Option ( this.List2.options [ i ].text, this.List2.options [ i ].value, false, true );
			this.List2.options [ i ] = null;
		}

		for ( var i = oPool.length - 1 ; i >= 0 ; i -- ) {
				this.List1.options [ this.List1.options.length ] = oPool [ i ];
		}

		// Update hidden field
		document.copyListToText ( this.List2, this.HiddenFld );

		// Update button states
		this.List1.onchange ( );
		this.List2.onchange ( );
	}

	// If a "Move up" button exists, add an OnClick() event
	if ( oMoveUp ) {
		oMoveUp.List2 = oList2;
		oMoveUp.HiddenFld = oHidden;
		oMoveUp.onclick = function ( ) {

			for ( var i = 0 ; i < this.List2.options.length ; i ++ ) {
				if ( this.List2.options [ i ].selected && i > 0 && ! this.List2.options [ i - 1 ].selected ) {
					var oNewDown = new Option ( this.List2.options [ i ].text, this.List2.options [ i ].value, false, true );
					var oNewUp = new Option ( this.List2.options [ i - 1 ].text, this.List2.options [ i - 1 ].value, false, false );

					// Swap options around
					this.List2.options [ i ] = oNewUp;
					this.List2.options [ i - 1 ] = oNewDown;
				}
			}

			// Update hidden field
			document.copyListToText ( this.List2, this.HiddenFld );

			// Update button states
			this.List2.onchange ( );
		}

	}

	// If a "Move down" button exists, add an OnClick() event
	if ( oMoveDown ) {
		oMoveDown.List2 = oList2;
		oMoveDown.HiddenFld = oHidden;
		oMoveDown.onclick = function ( ) {
			for ( var i = this.List2.options.length - 1 ; i >= 0  ; i -- ) {
				if ( this.List2.options [ i ].selected && i < this.List2.options.length - 1 && ! this.List2.options [ i + 1 ].selected ) {
					var oNewUp = new Option ( this.List2.options [ i ].text, this.List2.options [ i ].value, false, true );
					var oNewDown = new Option ( this.List2.options [ i + 1 ].text, this.List2.options [ i + 1 ].value, false, false );

					// Swap options around
					this.List2.options [ i ] = oNewDown;
					this.List2.options [ i + 1 ] = oNewUp;
				}
			}

			// Update hidden field
			document.copyListToText ( this.List2, this.HiddenFld );

			// Update button states
			this.List2.onchange ( );
		}

	}

	// If a "Reverse" button exists, add an OnClick() event
	if ( oReverse ) {
		oReverse.List2 = oList2;
		oReverse.HiddenFld = oHidden;
		oReverse.onclick = function ( ) {
			// Reverse list
			for ( var i = 0 ; i < Math.floor ( this.List2.options.length / 2 ) ; i ++ ) {
				var oNewDown = new Option ( this.List2.options [ this.List2.options.length - 1 - i ].text, this.List2.options [ this.List2.options.length - 1 - i ].value, false, this.List2.options [ this.List2.options.length - 1 - i ].selected );
				var oNewUp = new Option ( this.List2.options [ i ].text, this.List2.options [ i ].value, false, this.List2.options [ i ].selected );

				// Swap options around
				this.List2.options [ i ] = oNewDown;
				this.List2.options [ this.List2.options.length - 1 - i ] = oNewUp;
			}

			// Update hidden field
			document.copyListToText ( this.List2, this.HiddenFld );

			// Update button states
			this.List2.onchange ( );
		}

	}

}
