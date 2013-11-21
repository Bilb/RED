var searchTypeSelection = 1;
			var course_id = -1;
			
			/* creates and displays a toast message */
function createToast(){
	var options = {
		duration: 5000,
		sticky: false,
		type: "success"
	};
	
	$.toast('<h4>Success</h4> You\'ve been add to this course session.', options);
}

function populateLocationList(select, data) {
    var items = [];
    $.each(data, function (id, option) {
        items.push('<li role="presentation"> <a role="menuitem" tabindex="-1" href="#">' + option.city + '</a></li>');
    });  
    select.html(items.join(''));
}

function setCookie(c_name,value,exdays)
{
	var exdate=new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
	document.cookie=c_name + "=" + c_value;
}

function getCookie(c_name)
{
	var c_value = document.cookie;
	var c_start = c_value.indexOf(" " + c_name + "=");
	if (c_start == -1)
	  {
	  c_start = c_value.indexOf(c_name + "=");
	  }
	if (c_start == -1)
	  {
	  c_value = null;
	  }
	else
	  {
	  c_start = c_value.indexOf("=", c_start) + 1;
	  var c_end = c_value.indexOf(";", c_start);
	  if (c_end == -1)
	  {
	c_end = c_value.length;
	}
	c_value = unescape(c_value.substring(c_start,c_end));
	}
	return c_value;
}

function setUserCookie()
{
	setCookie('lastname',document.getElementById('lastname').value,1);
	setCookie('firstname',document.getElementById('firstname').value,1);
	setCookie('address',document.getElementById('address').value,1);
	setCookie('email',document.getElementById('email').value,1);
	setCookie('phone',document.getElementById('phone').value,1);
	$('#myModal').modal({
		  show: false
		})	;
	subscribe(-1);
}

function subscribe(sessionid) {
	if(sessionid != -1) {
			course_id = sessionid;
	}
	// check if we have user info
  	var username=getCookie("lastname");
  	if (username!=null && username!="")
  	{
		$.getJSON("http://localhost:8080/RED/service/subscribe/"+ course_id + "/"
			+ getCookie('lastname') + "/"
			+ getCookie('firstname')+ "/"
			+ getCookie('address') +"/"
			+ getCookie('phone') +"/"
			+ getCookie('email'),
			function (data) {}	
		);
		// trigger toast
		createToast();
		course_id=-1;
		
  	}
  	else {
		$('#myModal').modal({
			  show: true
			});					  
  	}
	
}

/* update table data from url */
function updateTable(jsonData)
{
	 $("#dataTable").jsonTable({
		head : ['Code','Title','Start','End', 'Location', 'Subscribe'],
		json : ['courseCode','title', 'start', 'end', 'location', 'Subscribe']
	});
	
	
	jQuery.each(jsonData, function(i, val) {
		val['Subscribe']="<button type=\"button\" class=\"btn btn-default btn-lg\" onclick=\"subscribe("+val['id'] + ");\"><span class=\"glyphicon glyphicon-plus-sign\"></span></button>";

	});
	

	var options = {
		source : jsonData,
		rowClass : "rowClass"
	};
	document.getElementById("dataTable").style.height = "10px";
	$("#dataTable").jsonTableUpdate(options);
	
	/* initiate table with new columns get from JSON */
	$('table.tableSorter').tableSort();
}


function setSearch(id) {
	searchTypeSelection = id;
	switch(searchTypeSelection) {
	case 0:
		document.getElementById('titleForm').style.display = 'none';
		document.getElementById('locationSaver').style.display = 'none';
		document.getElementById('launchScript').style.display = 'none';
		document.getElementById('datepicker').style.display = 'inline';
		break;
	case 1:
		document.getElementById('titleForm').style.display = 'none';
		document.getElementById('locationSaver').style.display = 'inline';
		document.getElementById('launchScript').style.display = 'none';
		document.getElementById('datepicker').style.display = 'none';
		break;
	case 2:
		document.getElementById('titleForm').style.display = 'inline';
		document.getElementById('locationSaver').style.display = 'none';
		document.getElementById('launchScript').style.display = 'inline';
		document.getElementById('datepicker').style.display = 'none';
		break;
	}
}

function getJsonUpdateTable() {
	if (searchTypeSelection == 2) {
		$.getJSON("http://localhost:8080/RED/service/byKeyword/" + document.getElementById('titleForm').value, function(data) {
			
			updateTable(data);
			});
	}
}

function initiateToastsParameters() {
	$.toast.config.align = 'right';
	$.toast.config.width = 400;
}

function initiateTitleFormEvents() {
	$('#titleForm').keydown(function(event){
	    if(event.keyCode == 13) {
	    	getJsonUpdateTable();
	      return false;
	    }
	});
}



/* autoclose datepicker when date changed */
$(function() {
	$('#datepicker').datepicker().on('changeDate', function (ev) {
		if(ev.viewMode === 'days'){
			$(this).datepicker('hide');
			$.getJSON("http://localhost:8080/RED/service/byDate/" + $('#datepicker').val(), function(data) { 
				updateTable(data);
			});
		}
		
	});
});

function getAndPopulateLocationList() {
	$.getJSON("http://localhost:8080/RED/service/byLocation", function(json) {
		populateLocationList($('#locationValues'), json);
		$("#locationValues li a").click(function() {
			$("#locationSaver").val($(this).text());
			$.getJSON("http://localhost:8080/RED/service/byLocation/" + $(this).text(), function(data) { 
				updateTable(data);
				});
			});
	});
}